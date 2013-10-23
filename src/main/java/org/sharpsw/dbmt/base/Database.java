package org.sharpsw.dbmt.base;

import java.util.*;

public class Database {
    private String productName;
    private String productVersion;
    private int majorVersion;
    private int minorVersion;
    private int majorJDBCVersion;
    private int minorJDBCVersion;
    private String schema;
    private List<Table> tables;

    public Database(final String schema) {
        this.schema = schema;
        this.tables = new ArrayList<Table>();
    }

    public void setSchema(String name) {
        this.schema = name;
    }

    public String getSchema() {
        return this.schema;
    }

    public void setProductName(String name) {
        this.productName = name;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductVersion(String version) {
        this.productVersion = version;
    }

    public String getProductVersion() {
        return this.productVersion;
    }

    public void setMajorVersion(int version) {
        this.majorVersion = version;
    }

    public int getMajorVersion() {
        return this.majorVersion;
    }

    public void setMinorVersion(int version) {
        this.minorVersion = version;
    }

    public int getMinorVersion() {
        return this.minorVersion;
    }

    public void setMajorJDBCVersion(int version) {
        this.majorJDBCVersion = version;
    }

    public int getMajorJDBCVersion() {
        return this.majorJDBCVersion;
    }

    public void setMinorJDBCVersion(int version) {
        this.minorJDBCVersion = version;
    }

    public int getMinorJDBCVersion() {
        return this.minorJDBCVersion;
    }

    public void add(Table table) {
        if (!this.tables.contains(table)) {
            this.tables.add(table);
        }
    }

    public void remove(Table table) {
        this.tables.remove(table);
    }

    public List<Table> getTables() {
        organizeTables();
        return this.tables;
    }

    private void organizeTables() {
        List<Table> organizedTables = new ArrayList<Table>();
        Iterator<Table> iterator = this.tables.iterator();
        while (iterator.hasNext()) {
            Table table = iterator.next();
            if (!table.hasForeignKeys()) {
                organizedTables.add(table);
            }
        }

        iterator = organizedTables.iterator();
        while (iterator.hasNext()) {
            Table table = iterator.next();
            this.tables.remove(table);
        }

        while (!this.tables.isEmpty()) {
            Iterator<Table> iter = this.tables.iterator();
            boolean isRemoved = false;
            while (iter.hasNext() && !isRemoved) {
                Table element = iter.next();
                Map<String, List<ForeignKey>> keys = element.getForeignKeys();
                Set<String> keySet = keys.keySet();
                Iterator<String> keyIter = keySet.iterator();
                boolean isAllKeysOk = true;
                while (keyIter.hasNext() && isAllKeysOk) {
                    String tableName = keyIter.next();
                    Table fake = new Table();
                    fake.setName(tableName);
                    if (!organizedTables.contains(fake)) {
                        isAllKeysOk = false;
                    }
                }

                if (isAllKeysOk) {
                    organizedTables.add(element);
                    this.tables.remove(element);
                    isRemoved = true;
                }
            }
        }

        Iterator<Table> tableIter = organizedTables.iterator();
        while (tableIter.hasNext()) {
            Table element = tableIter.next();
            this.tables.add(element);
        }
    }
}
