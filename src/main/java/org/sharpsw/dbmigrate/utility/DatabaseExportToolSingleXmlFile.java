package org.sharpsw.dbmigrate.utility;

import org.sharpsw.dbmigrate.config.DatabaseConfig;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionFactory;
import org.sharpsw.dbmigrate.data.Database;
import org.sharpsw.dbmigrate.data.DatabaseSchemaParseException;
import org.sharpsw.dbmigrate.data.DatabaseSchemaParser;

public class DatabaseExportToolSingleXmlFile {
	
	public void export(final DatabaseConfig config) throws XMLExportException {
		try {
			DatabaseConnectionFactory connCreator = new DatabaseConnectionFactory();
			DatabaseSchemaParser generator = new DatabaseSchemaParser(connCreator);
			Database database = generator.load(config);			
		} catch (DatabaseSchemaParseException exception) {
			throw new XMLExportException("Metadata error", exception);
		}
	}
}
