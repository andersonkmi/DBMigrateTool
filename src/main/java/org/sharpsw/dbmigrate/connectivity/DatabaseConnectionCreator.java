package org.sharpsw.dbmigrate.connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sharpsw.dbmigrate.config.DatabaseConfig;

public class DatabaseConnectionCreator {

    public Connection getConnection(final DatabaseConfig configuration) throws DatabaseConnectionCreateException, DatabaseConnectionDriverLoadException {
        try {
            Class.forName(configuration.getDriverClassName());
            Connection connection = DriverManager.getConnection(configuration.getConnectionString());
            return connection;
        } catch (SQLException exception) {
            throw new DatabaseConnectionCreateException(String.format("Error when creating a new connection to the database: %s", exception.getMessage()), exception);
        } catch (ClassNotFoundException classNotFoundExc) {
            throw new DatabaseConnectionDriverLoadException(String.format("Error when loading driver '%s'", configuration.getDriverClassName()), classNotFoundExc);
        }
    }
}
