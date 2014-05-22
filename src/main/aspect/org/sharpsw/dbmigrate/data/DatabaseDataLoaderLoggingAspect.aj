package org.sharpsw.dbmigrate.data;

import org.apache.log4j.Logger;
import org.sharpsw.dbmigrate.config.DatabaseConfig;

public aspect DatabaseDataLoaderLoggingAspect {
	private static final Logger logger = Logger.getLogger(DatabaseSchemaParser.class);
	
	pointcut loadExecution(DatabaseConfig configuration) : execution(public Database DatabaseSchemaParser.load(DatabaseConfig)) && args(configuration);
	
	before(DatabaseConfig configuration) : loadExecution(configuration) {
		if(logger.isDebugEnabled()) {
			if(configuration != null) {
				logger.debug(String.format("Invoking DatabaseDataLoader.load() method for database configuration '%s'", configuration.getConnectionString()));				
			} else {
				logger.debug("Invoking DatabaseDataLoader.load() method for database configuration 'null'");
			}
		}
	}
	
	after(DatabaseConfig configuration) : loadExecution(configuration) {
		if(logger.isDebugEnabled()) {
			logger.debug("Leaving DatabaseDataLoader.load()");
		}
	}
	
	
}
