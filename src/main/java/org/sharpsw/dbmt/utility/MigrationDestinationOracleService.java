package org.sharpsw.dbmt.utility;

import org.sharpsw.dbmt.base.Database;
import org.sharpsw.dbmt.config.IDatabaseConfiguration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class MigrationDestinationOracleService extends MigrationDestinationBaseService {
    private List<String> tablesToDrop;

    MigrationDestinationOracleService(IDatabaseConfiguration configuration) {
        super(configuration);
        this.tablesToDrop = new ArrayList<String>();
    }

    @Override
    protected void generateStatements(Database database) throws SQLException {
    }

    @Override
    protected void undoTableCreation() throws SQLException {
        StringBuffer select = new StringBuffer();
        select.append("SELECT TABLE_NAME FROM USER_TABLES");
        Statement statement = this.connection.createStatement();
        ResultSet result = statement.executeQuery(select.toString());

        while (result.next()) {
            String tableName = result.getString("TABLE_NAME");
            this.tablesToDrop.add(tableName);
        }
        result.close();

        for (String table : this.tablesToDrop) {
            try {
                StringBuffer dropStatementString = new StringBuffer();
                dropStatementString.append("DROP TABLE ").append(table);
                Statement dropStatement = this.connection.createStatement();
                dropStatement.execute(dropStatementString.toString());
                dropStatement.close();
            } catch (SQLException dropExc) {
                // Nothing is performed here.
            }
        }
    }

}
