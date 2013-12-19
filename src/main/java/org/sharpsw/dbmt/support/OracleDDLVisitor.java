package org.sharpsw.dbmt.support;

import org.sharpsw.dbmigrate.base.*;

import java.sql.Types;
import java.util.*;

public class OracleDDLVisitor {
    private List<String> statements;
    private List<String> columnDefinitions;
    private String autoIncrementColumn;
    private String currentTable;
    private List<String> primaryKeyStatements;
    private List<String> foreignKeyStatements;
    private List<String> autoIncrementStatements;

    public OracleDDLVisitor() {
        this.statements = new ArrayList<String>();
        this.columnDefinitions = new ArrayList<String>();
        this.foreignKeyStatements = new ArrayList<String>();
        this.primaryKeyStatements = new ArrayList<String>();
        this.autoIncrementStatements = new ArrayList<String>();
        this.currentTable = "";
        this.autoIncrementColumn = "";
    }

    public List<String> getTableCreateStatements() {
        return this.statements;
    }

    public List<String> getForeignKeyCreateStatements() {
        return this.foreignKeyStatements;
    }

    public List<String> getPrimaryKeyCreateStatements() {
        return this.primaryKeyStatements;
    }

    public List<String> getAutoIncrementCreateStatements() {
        return this.autoIncrementStatements;
    }

    public void visitColumn(Object node) {
        if (node instanceof Column) {
            Column column = (Column) node;
            StringBuffer columnCreate = new StringBuffer().append(column.getName()).append(" ");
            switch (column.getDataType()) {
                case Types.CHAR:
                    columnCreate.append(" CHAR(").append(column.getLength()).append(")");
                    break;
                case Types.DATE:
                    columnCreate.append(" DATE");
                    break;
                case Types.DECIMAL:
                case Types.DOUBLE:
                case Types.FLOAT:
                case Types.REAL:
                    if (column.getPrecision() == 0) {
                        columnCreate.append(" NUMBER(").append(column.getLength()).append(") ");
                    } else {
                        columnCreate.append(" NUMBER(").append(column.getLength()).append(", ").append(column.getPrecision()).append(")");
                    }
                    break;
                case Types.INTEGER:
                case Types.NUMERIC:
                case Types.SMALLINT:
                    columnCreate.append(" NUMBER(").append(column.getLength()).append(") ");
                    break;
                case Types.TIME:
                case Types.TIMESTAMP:
                    columnCreate.append(" TIMESTAMP ");
                    break;
                case Types.VARCHAR:
                    columnCreate.append(" VARCHAR2(").append(column.getLength()).append(") ");
                    break;
            }

            if (column.isAutoIncrement()) {
                this.autoIncrementColumn = column.getName();
            }


            if (!column.isNullable()) {
                columnCreate.append(" NOT NULL ");
            }

            if (column.getDefaultValue().equals("")) {
                columnCreate.append(" DEFAULT VALUE ").append(column.getDefaultValue());
            }
            this.columnDefinitions.add(columnCreate.toString());
        }
    }

    public void visitDatabase(Object node) {
        if (node instanceof Database) {
            Database db = (Database) node;
            List<Table> tables = db.getTables();
            for (Table table : tables) {
                //table.accept(this);
            }
        }
    }

    public void visitTable(Object node) {
        if (node instanceof Table) {
            Table table = (Table) node;
            String name = table.getName();
            this.currentTable = name;
            List<Column> columns = table.getColumns();
            for (Column column : columns) {
                //column.accept(this);
            }

            StringBuffer buffer = new StringBuffer();
            buffer.append("CREATE TABLE ").append(name).append(" (");
            int index = 0;
            for (String colDef : this.columnDefinitions) {
                index++;
                if (index < this.columnDefinitions.size()) {
                    buffer.append(colDef).append(", ");
                } else {
                    buffer.append(colDef);
                }
            }
            buffer.append(")");

            this.statements.add(buffer.toString());
            generateSequenceStatement();
            generateTriggerStatement();
            generatePrimaryKeyStatements(table);
            generateForeignKeyStatements(table);
            this.columnDefinitions.clear();
            this.autoIncrementColumn = "";
        }
    }

    private void generateSequenceStatement() {
        if (!this.autoIncrementColumn.isEmpty()) {
            StringBuffer statement = new StringBuffer();
            statement.append("CREATE SEQUENCE SEQ_").append(this.currentTable.toUpperCase());
            statement.append(" MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER NOCYCLE");
            this.autoIncrementStatements.add(statement.toString());
        }
    }

    private void generateTriggerStatement() {
        if (!this.autoIncrementColumn.isEmpty()) {
            StringBuffer statement = new StringBuffer();
            statement.append("create or replace TRIGGER ").append("TR_").append(this.currentTable.toUpperCase());
            statement.append(" BEFORE INSERT ON ").append(this.currentTable.toUpperCase()).append(" REFERENCING OLD AS OLD NEW AS NEW FOR EACH ROW ");
            statement.append(" BEGIN SELECT SEQ_").append(this.currentTable.toUpperCase()).append(".NEXTVAL INTO :NEW.").append(this.autoIncrementColumn).append(" FROM DUAL; END");
            this.autoIncrementStatements.add(statement.toString());
        }
    }

    private void generatePrimaryKeyStatements(Table table) {
        List<PrimaryKey> keys = table.getPrimaryKeys();
        Iterator<PrimaryKey> iterator = keys.iterator();
        int size = keys.size();
        int counter = 1;

        StringBuffer pkCols = new StringBuffer();
        while (iterator.hasNext()) {
            pkCols.append(iterator.next().getColumn());
            if (counter < size) {
                pkCols.append(", ");
            }
            counter++;
        }

        StringBuffer statement = new StringBuffer();
        statement.append("ALTER TABLE ").append(table.getName()).append(" ADD CONSTRAINT ").append(table.getName()).append("_pk ");
        statement.append("PRIMARY KEY (").append(pkCols).append(")");
        this.primaryKeyStatements.add(statement.toString());
    }

    private void generateForeignKeyStatements(Table table) {
        if (table.hasForeignKeys()) {
            Map<String, List<ForeignKey>> foreignKeys = table.getForeignKeys();
            Set<String> keys = foreignKeys.keySet();
            for (String key : keys) {
                StringBuffer buffer = new StringBuffer();
                if (key.startsWith("Fake_FK")) {
                    buffer.append("FK_").append(table.getName()).append("_").append(key);
                } else {
                    buffer.append(key);
                }

                StringBuffer statement = new StringBuffer();
                statement.append("ALTER TABLE ").append(table.getName()).append(" ADD CONSTRAINT ").append(buffer.toString());
                statement.append(" FOREIGN KEY (");

                StringBuffer fkColumns = new StringBuffer();
                StringBuffer refColumns = new StringBuffer();
                String parentTable = "";

                List<ForeignKey> fkList = foreignKeys.get(key);
                int size = fkList.size();
                int counter = 1;
                for (ForeignKey element : fkList) {
                    parentTable = element.getPrimaryKeyTableName();

                    fkColumns.append(element.getForeignKeyColumnName());
                    if (counter < size) {
                        fkColumns.append(", ");
                    }

                    refColumns.append(element.getPrimaryKeyColumnName());
                    if (counter < size) {
                        refColumns.append(", ");
                    }
                    ++counter;
                }

                statement.append(fkColumns.toString()).append(") REFERENCES ").append(parentTable).append("(");
                statement.append(refColumns.toString()).append(")");
                String line = statement.toString();
                this.foreignKeyStatements.add(line);
            }
        }
    }
}