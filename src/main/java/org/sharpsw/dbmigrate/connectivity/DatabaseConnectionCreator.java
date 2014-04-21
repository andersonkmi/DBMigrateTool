package org.sharpsw.dbmigrate.connectivity;

import org.apache.log4j.Logger;
import org.sharpsw.dbmigrate.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionCreator {
    private static final Logger logger = Logger.getLogger(DatabaseConnectionCreator.class);

    public Connection getConnection(final DatabaseConfig configuration) throws DatabaseConnectionCreateException, DatabaseConnectionDriverLoadException {
        if(logger.isDebugEnabled()) {
            logger.debug(String.format("Creating a new database connection for configuration: '%s'", configuration.getConnectionString()));
        }

        try {
            Class.forName(configuration.getDriverClassName());
            Connection connection = DriverManager.getConnection(configuration.getConnectionString());

            logger.info("Database connection created successfully");
            return connection;
        } catch (SQLException exception) {
            logger.error(String.format("Error when creating a new database connection: '%s", exception.getMessage()), exception);
            throw new DatabaseConnectionCreateException(String.format("Error when creating a new connection to the database: %s", exception.getMessage()), exception);
        } catch (ClassNotFoundException classNotFoundExc) {
            logger.error(String.format("Error when loading the database driver '%s", configuration.getDriverClassName()), classNotFoundExc);
            throw new DatabaseConnectionDriverLoadException(String.format("Error when loading driver '%s'", configuration.getDriverClassName()), classNotFoundExc);
        }
    }
}
