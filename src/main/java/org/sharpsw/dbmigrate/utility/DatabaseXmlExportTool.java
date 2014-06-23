package org.sharpsw.dbmigrate.utility;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.sharpsw.dbmigrate.config.DatabaseConfig;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionFactory;
import org.sharpsw.dbmigrate.data.Column;
import org.sharpsw.dbmigrate.data.Database;
import org.sharpsw.dbmigrate.data.DatabaseSchemaParseException;
import org.sharpsw.dbmigrate.data.DatabaseSchemaParser;
import org.sharpsw.dbmigrate.data.Table;

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
