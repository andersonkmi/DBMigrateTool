package org.codecraftlabs.neptune.connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.codecraftlabs.neptune.config.DatabaseConfig;

public class ConnectionFactory {
	private static final Logger logger = Logger.getLogger(ConnectionFactory.class);
	
    public Connection getConnection(final DatabaseConfig configuration) throws ConnectionFactoryException, DatabaseDriverLoadException {
        logger.info("Obtaining a new database connection.");
        try {
            Class.forName(configuration.getDriverClassName());
            return DriverManager.getConnection(configuration.getConnectionString());
        } catch (SQLException exception) {
        	logger.error(String.format("Error when creating a new database connection: %s", exception.getMessage()), exception);
            throw new ConnectionFactoryException(String.format("Error when creating a new connection to the database: %s", exception.getMessage()), exception);
        } catch (ClassNotFoundException classNotFoundExc) {
        	logger.error(String.format("Error when loading database driver: %s", classNotFoundExc.getMessage()), classNotFoundExc);
            throw new DatabaseDriverLoadException(String.format("Error when loading driver '%s'", configuration.getDriverClassName()), classNotFoundExc);
        }
    }
}
