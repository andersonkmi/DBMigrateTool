package org.sharpsw.dbmigrate.utility;

import java.sql.Connection;

import org.sharpsw.dbmigrate.config.DatabaseConfig;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionCreateException;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionCreator;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionDriverLoadException;
import org.sharpsw.dbmigrate.data.Database;
import org.sharpsw.dbmigrate.data.MetadataGenerationException;
import org.sharpsw.dbmigrate.data.MetadataGenerator;

public class DatabaseExportToolSingleXmlFile {
	private DatabaseConfig configuration;
	private String exportFile;
	
	public DatabaseExportToolSingleXmlFile(DatabaseConfig configuration, String exportFile) {
		this.configuration = configuration;
		this.exportFile = exportFile;
	}
	
	public void export() throws XMLExportException {
		try {
			DatabaseConnectionCreator connCreator = new DatabaseConnectionCreator(this.configuration);
			Connection connection = connCreator.getConnection();
			MetadataGenerator generator = new MetadataGenerator(connection);
			Database database = generator.generate();			
		} catch (DatabaseConnectionDriverLoadException exception) {
			throw new XMLExportException("JDBC driver exception", exception);
		} catch (DatabaseConnectionCreateException exception) {
			throw new XMLExportException("Connection exception", exception);
		} catch (MetadataGenerationException exception) {
			throw new XMLExportException("Metadata error", exception);
		}
	}
}
