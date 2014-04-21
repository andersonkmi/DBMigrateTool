package org.sharpsw.dbmigrate.connectivity;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.sharpsw.dbmigrate.config.DatabaseConfig;

public aspect DatabaseConnectionCreatorLoggingAspect {
	private static final Logger logger = Logger.getLogger(DatabaseConnectionCreator.class);
	
	pointcut getConnectionExecution(final DatabaseConfig configuration) : execution(public Connection DatabaseConnectionCreator.getConnection(DatabaseConfig)) && args(configuration);
	
	before(DatabaseConfig configuration) : getConnectionExecution(configuration) {
		if(logger.isDebugEnabled()) {
			logger.debug(String.format("Invoking DatabaseConnectionCreator.getConnection() method for database '%s'", configuration.getConnectionString())); 
		}
	}
	
	after(DatabaseConfig configuration) : getConnectionExecution(configuration) {
		if(logger.isDebugEnabled()) {
			logger.debug("Leaving DatabaseConnectionCreator.getConnection() method.");
		}
	}
}
