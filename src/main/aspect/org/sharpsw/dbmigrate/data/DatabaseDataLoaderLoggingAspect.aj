package org.sharpsw.dbmigrate.data;

import org.apache.log4j.Logger;
import org.sharpsw.dbmigrate.config.DatabaseConfig;

public aspect DatabaseDataLoaderLoggingAspect {
	private static final Logger logger = Logger.getLogger(DatabaseDataLoader.class);
	
	pointcut loadExecution(DatabaseConfig configuration) : execution(public Database DatabaseDataLoader.load(DatabaseConfig)) && args(configuration);
	
	before(DatabaseConfig configuration) : loadExecution(configuration) {
		if(logger.isDebugEnabled()) {
			logger.debug(String.format("Invoking DatabaseDataLoader.load() method for database configuration '%s'", configuration.getConnectionString()));
		}
	}
	
	after(DatabaseConfig configuration) : loadExecution(configuration) {
		if(logger.isDebugEnabled()) {
			logger.debug("Leaving DatabaseDataLoader.load()");
		}
	}
	
	
}
