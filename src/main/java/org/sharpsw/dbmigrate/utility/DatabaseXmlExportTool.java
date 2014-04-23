package org.sharpsw.dbmigrate.utility;

import java.io.FileOutputStream;
import java.io.IOException;

import org.sharpsw.dbmigrate.config.DatabaseConfig;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionFactory;
import org.sharpsw.dbmigrate.data.Database;
import org.sharpsw.dbmigrate.data.DatabaseSchemaParseException;
import org.sharpsw.dbmigrate.data.DatabaseSchemaParser;

import com.thoughtworks.xstream.XStream;

public class DatabaseXmlExportTool {
	
	public void export(final DatabaseConfig config, final String fileName) throws XMLExportException {
		try {
			DatabaseConnectionFactory connCreator = new DatabaseConnectionFactory();
			DatabaseSchemaParser generator = new DatabaseSchemaParser(connCreator);
			Database database = generator.load(config);					
			FileOutputStream outputFile = new FileOutputStream(fileName);
			
			XStream xstream = new XStream();
			xstream.toXML(database, outputFile);
			outputFile.close();
		} catch (DatabaseSchemaParseException exception) {
			throw new XMLExportException("Metadata error", exception);
		} catch (IOException exception) {
			throw new XMLExportException(String.format("Error when exporting database to XML format: %s", exception.getMessage()), exception);
		}
	}
	
	public String export(final DatabaseConfig config) throws XMLExportException {
		try {
			DatabaseConnectionFactory connCreator = new DatabaseConnectionFactory();
			DatabaseSchemaParser generator = new DatabaseSchemaParser(connCreator);
			Database database = generator.load(config);								
			XStream xstream = new XStream();
			return xstream.toXML(database);
		} catch (DatabaseSchemaParseException exception) {
			throw new XMLExportException("Metadata error", exception);
		}		
	}
}
