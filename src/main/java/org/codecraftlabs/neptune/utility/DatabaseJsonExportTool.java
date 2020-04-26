package org.codecraftlabs.neptune.utility;

import org.apache.log4j.Logger;
import org.codecraftlabs.neptune.config.DatabaseConfig;
import org.codecraftlabs.neptune.connectivity.ConnectionFactory;
import org.codecraftlabs.neptune.data.Database;
import org.codecraftlabs.neptune.data.DatabaseSchemaParseException;
import org.codecraftlabs.neptune.data.DatabaseSchemaParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DatabaseJsonExportTool {
	private static final Logger logger = Logger.getLogger(DatabaseJsonExportTool.class);
	
	public String export(final DatabaseConfig config) throws DatabaseSchemaExportException {
		if(logger.isInfoEnabled()) {
			logger.info("Starting the export process");
		}
		try {
			DatabaseSchemaParser parser = new DatabaseSchemaParser(new ConnectionFactory());
			Database database = parser.load(config);
			Gson gson = new GsonBuilder().serializeNulls().create();
			return gson.toJson(database);
		} catch (DatabaseSchemaParseException exception) {
			logger.error(String.format("Error when exporting database information: %s", exception.getMessage()));
			throw new DatabaseSchemaExportException(String.format("Error when exporting schema to JSON: %s", exception.getMessage()), exception);
		}
		
	}
}
