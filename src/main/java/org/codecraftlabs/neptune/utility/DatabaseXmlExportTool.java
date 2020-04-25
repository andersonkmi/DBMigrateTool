package org.codecraftlabs.neptune.utility;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.codecraftlabs.neptune.config.DatabaseConfig;
import org.codecraftlabs.neptune.connectivity.DatabaseConnectionFactory;
import org.codecraftlabs.neptune.data.Column;
import org.codecraftlabs.neptune.data.Database;
import org.codecraftlabs.neptune.data.DatabaseSchemaParseException;
import org.codecraftlabs.neptune.data.DatabaseSchemaParser;
import org.codecraftlabs.neptune.data.Table;

import com.thoughtworks.xstream.XStream;

public class DatabaseXmlExportTool {
	private static final Logger logger = Logger.getLogger(DatabaseXmlExportTool.class);
	
	public void export(final DatabaseConfig config, final String fileName) throws DatabaseSchemaExportException {
		if(logger.isInfoEnabled()) {
			logger.info("Starting the export process");
		}
		
		try {
			DatabaseConnectionFactory connCreator = new DatabaseConnectionFactory();
			DatabaseSchemaParser generator = new DatabaseSchemaParser(connCreator);
			Database database = generator.load(config);					
			FileOutputStream outputFile = new FileOutputStream(fileName);
			
			XStream xstream = new XStream();
			xstream.alias("database", Database.class);
			xstream.alias("table", Table.class);
			xstream.alias("column", Column.class);
			xstream.toXML(database, outputFile);
			outputFile.close();
		} catch (DatabaseSchemaParseException exception) {
			throw new DatabaseSchemaExportException("Metadata error", exception);
		} catch (IOException exception) {
			logger.error(String.format("Error when exporting database information: %s", exception.getMessage()));
			throw new DatabaseSchemaExportException(String.format("Error when exporting database to XML format: %s", exception.getMessage()), exception);
		}
	}
	
	public String export(final DatabaseConfig config) throws DatabaseSchemaExportException {
		if(logger.isInfoEnabled()) {
			logger.info("Starting the export process");
		}
		
		try {
			DatabaseConnectionFactory connCreator = new DatabaseConnectionFactory();
			DatabaseSchemaParser generator = new DatabaseSchemaParser(connCreator);
			Database database = generator.load(config);								
			XStream xstream = new XStream();
			xstream.alias("database", Database.class);
			xstream.alias("table", Table.class);
			xstream.alias("column", Column.class);
			return xstream.toXML(database);
		} catch (DatabaseSchemaParseException exception) {
			throw new DatabaseSchemaExportException("Metadata error", exception);
		}		
	}
}
