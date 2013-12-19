package org.sharpsw.dbmt.utility;

import java.sql.Connection;

import org.sharpsw.dbmigrate.base.Database;
import org.sharpsw.dbmigrate.base.MetadataGenException;
import org.sharpsw.dbmigrate.base.MetadataGenerator;
import org.sharpsw.dbmt.config.DatabaseConfig;
import org.sharpsw.dbmt.connectivity.DBConnectionCreateException;
import org.sharpsw.dbmt.connectivity.DBConnectionCreator;
import org.sharpsw.dbmt.connectivity.DBConnectionDriverLoadException;

public class DatabaseExportToolSingleXmlFile {
	private DatabaseConfig configuration;
	private String exportFile;
	
	public DatabaseExportToolSingleXmlFile(DatabaseConfig configuration, String exportFile) {
		this.configuration = configuration;
		this.exportFile = exportFile;
	}
	
	public void export() throws XMLExportException {
		try {
			DBConnectionCreator connCreator = new DBConnectionCreator(this.configuration);
			Connection connection = connCreator.getConnection();
			MetadataGenerator generator = new MetadataGenerator(connection);
			Database database = generator.generate();			
		} catch (DBConnectionDriverLoadException exception) {
			throw new XMLExportException("JDBC driver exception", exception);
		} catch (DBConnectionCreateException exception) {
			throw new XMLExportException("Connection exception", exception);
		} catch (MetadataGenException exception) {
			throw new XMLExportException("Metadata error", exception);
		}
	}
}
