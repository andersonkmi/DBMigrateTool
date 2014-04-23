package org.sharpsw.dbmigrate.utility;

import org.sharpsw.dbmigrate.config.DatabaseConfig;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionFactory;
import org.sharpsw.dbmigrate.data.Database;
import org.sharpsw.dbmigrate.data.DatabaseSchemaParseException;
import org.sharpsw.dbmigrate.data.DatabaseSchemaParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DatabaseJsonExportTool {
	public String export(final DatabaseConfig config) throws DatabaseSchemaExportException {
		try {
			DatabaseSchemaParser parser = new DatabaseSchemaParser(new DatabaseConnectionFactory());
			Database database = parser.load(config);
			Gson gson = new GsonBuilder().serializeNulls().create();
			return gson.toJson(database);
		} catch (DatabaseSchemaParseException exception) {
			throw new DatabaseSchemaExportException(String.format("Error when exporting schema to JSON: %s", exception.getMessage()), exception);
		}
		
	}
}
