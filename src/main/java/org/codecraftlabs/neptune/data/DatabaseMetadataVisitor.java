package org.codecraftlabs.neptune.data;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

class DatabaseMetadataVisitor {
    private Database database;

    public DatabaseMetadataVisitor(String name) {
        this.database = new Database(name);
    }

    public void visit(DatabaseMetaData metaData) throws DatabaseSchemaParseException {
        visitDatabaseInfo(metaData);
    }

    public Database getDatabase() {
        return database;
    }

    private void visitDatabaseInfo(final DatabaseMetaData metadata) throws DatabaseSchemaParseException {
        try {
            var databaseProductName = metadata.getDatabaseProductName();
            var databaseProductVersion = metadata.getDatabaseProductVersion();
            var majorJDBCVersion = metadata.getJDBCMajorVersion();
            var minorJDBCVersion = metadata.getJDBCMinorVersion();
            var majorVersion = metadata.getDatabaseMajorVersion();
            var minorVersion = metadata.getDatabaseMinorVersion();

            database.setMajorJDBCVersion(majorJDBCVersion);
            database.setMinorJDBCVersion(minorJDBCVersion);
            database.setMajorVersion(majorVersion);
            database.setMinorVersion(minorVersion);
            database.setProductName(databaseProductName);
            database.setProductVersion(databaseProductVersion);
        } catch (SQLException exception) {
            throw new DatabaseSchemaParseException(String.format("Error when loading basic database information: %s", exception.getMessage()), exception);
        }
    }
}
