package org.sharpsw.dbmigrate.connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.sharpsw.dbmigrate.config.DatabaseConfig;

public class DatabaseConnectionFactory {
	private static final Logger logger = Logger.getLogger(DatabaseConnectionFactory.class);
	
    public Connection getConnection(final DatabaseConfig configuration) throws DatabaseConnectionFactoryException, DatabaseConnectionDriverLoadException {
    	if(logger.isInfoEnabled()) {
    		logger.info("Obtaining a new database connection.");
    	}
    	
        try {
            Class.forName(configuration.getDriverClassName());
            Connection connection = DriverManager.getConnection(configuration.getConnectionString());
            return connection;
        } catch (SQLException exception) {
        	logger.error(String.format("Error when creating a new database connection: %s", exception.getMessage()), exception);
        	
            throw new DatabaseConnectionFactoryException(String.format("Error when creating a new connection to the database: %s", exception.getMessage()), exception);
        } catch (ClassNotFoundException classNotFoundExc) {
        	logger.error(String.format("Error when loading database driver: %s", classNotFoundExc.getMessage()), classNotFoundExc);
            throw new DatabaseConnectionDriverLoadException(String.format("Error when loading driver '%s'", configuration.getDriverClassName()), classNotFoundExc);
        }
    }
}
