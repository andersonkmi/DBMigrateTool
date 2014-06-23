package org.sharpsw.dbmigrate.utility;

import org.apache.log4j.Logger;
import org.sharpsw.dbmigrate.config.DatabaseConfig;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionFactory;
import org.sharpsw.dbmigrate.data.Database;
import org.sharpsw.dbmigrate.data.DatabaseSchemaParseException;
import org.sharpsw.dbmigrate.data.DatabaseSchemaParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DatabaseJsonExportTool {
	private static final Logger logger = Logger.getLogger(DatabaseJsonExportTool.class);
	
	public String export(final DatabaseConfig config) throws DatabaseSchemaExportException {
		if(logger.isInfoEnabled()) {
			logger.info("Starting the export process");
		}
		try {
			DatabaseSchemaParser parser = new DatabaseSchemaParser(new DatabaseConnectionFactory());
			Database database = parser.load(config);
			Gson gson = new GsonBuilder().serializeNulls().create();
			return gson.toJson(database);
		} catch (DatabaseSchemaParseException exception) {
			logger.error(String.format("Error when exporting database information: %s", exception.getMessage()));
			throw new DatabaseSchemaExportException(String.format("Error when exporting schema to JSON: %s", exception.getMessage()), exception);
		}
		
	}
}
