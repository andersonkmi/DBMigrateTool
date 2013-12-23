package org.sharpsw.dbmigrate.connectivity;

import org.sharpsw.dbmigrate.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionCreator {
    private DatabaseConfig configuration;

    public DatabaseConnectionCreator(DatabaseConfig configuration) throws DatabaseConnectionDriverLoadException {
        this.configuration = configuration;
        String driverClass = this.configuration.getDriverClassName();

        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException classNotFoundExc) {
            StringBuilder message = new StringBuilder();
            message.append("Error when loading the driver: '").append(driverClass).append("'");
            throw new DatabaseConnectionDriverLoadException(message.toString(), classNotFoundExc);
        } catch (ExceptionInInitializerError error) {
            StringBuilder message = new StringBuilder();
            message.append("Error when loading the driver: '").append(driverClass).append("'");

            throw new DatabaseConnectionDriverLoadException(message.toString(), error);
        } catch (LinkageError linkageErr) {
            StringBuilder message = new StringBuilder();
            message.append("Error when loading the driver: '").append(driverClass).append("'");
            throw new DatabaseConnectionDriverLoadException(message.toString(), linkageErr);
        }
    }

    public Connection getConnection() throws DatabaseConnectionCreateException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(this.configuration.getConnectionString());
            return connection;
        } catch (SQLException exception) {
            StringBuilder message = new StringBuilder();
            message.append("Error when creating a new connection to the database.");
            throw new DatabaseConnectionCreateException(message.toString(), exception);
        }
    }
}
