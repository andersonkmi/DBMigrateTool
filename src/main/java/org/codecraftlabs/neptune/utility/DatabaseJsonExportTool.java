package org.codecraftlabs.neptune.utility;

import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.codecraftlabs.neptune.config.DatabaseConfig;
import org.codecraftlabs.neptune.connectivity.ConnectionFactory;
import org.codecraftlabs.neptune.data.DatabaseSchemaParseException;
import org.codecraftlabs.neptune.data.DatabaseSchemaParser;

import javax.annotation.Nonnull;

public class DatabaseJsonExportTool {
	private static final Logger logger = Logger.getLogger(DatabaseJsonExportTool.class);

	@Nonnull
	public String export(@Nonnull final DatabaseConfig config) throws DatabaseSchemaExportException {
		logger.info("Starting the export process");
		try {
			var parser = new DatabaseSchemaParser(new ConnectionFactory());
			var database = parser.load(config);
			var gson = new GsonBuilder().serializeNulls().create();
			return gson.toJson(database);
		} catch (DatabaseSchemaParseException exception) {
			logger.error(String.format("Error when exporting database information: %s", exception.getMessage()), exception);
			throw new DatabaseSchemaExportException(String.format("Error when exporting schema to JSON: %s", exception.getMessage()), exception);
		}
	}
}
