package org.sharpsw.dbmigrate.connectivity;

import org.sharpsw.dbmigrate.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionCreator {

    public Connection getConnection(final DatabaseConfig configuration) throws DatabaseConnectionCreateException, DatabaseConnectionDriverLoadException {
        Connection connection = null;
        try {
            Class.forName(configuration.getDriverClassName());
            connection = DriverManager.getConnection(configuration.getConnectionString());
            return connection;
        } catch (SQLException exception) {
            StringBuilder message = new StringBuilder();
            message.append("Error when creating a new connection to the database.");
            throw new DatabaseConnectionCreateException(message.toString(), exception);
        } catch (ClassNotFoundException classNotFoundExc) {
            StringBuilder message = new StringBuilder();
            message.append("Error when loading the driver: '").append(configuration.getDriverClassName()).append("'");
            throw new DatabaseConnectionDriverLoadException(message.toString(), classNotFoundExc);
        }
    }
}
