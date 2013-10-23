package org.sharpsw.dbmt.utility;

import java.sql.Connection;

import org.sharpsw.dbmt.base.Database;
import org.sharpsw.dbmt.base.MetadataGenException;
import org.sharpsw.dbmt.base.MetadataGenerator;
import org.sharpsw.dbmt.config.IDatabaseConfiguration;
import org.sharpsw.dbmt.connectivity.DBConnectionCreateException;
import org.sharpsw.dbmt.connectivity.DBConnectionCreator;
import org.sharpsw.dbmt.connectivity.DBConnectionDriverLoadException;

public class DatabaseExportToolSingleXmlFile {
	private IDatabaseConfiguration configuration;
	private String exportFile;
	
	public DatabaseExportToolSingleXmlFile(IDatabaseConfiguration configuration, String exportFile) {
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
