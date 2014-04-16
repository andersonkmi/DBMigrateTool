package org.sharpsw.dbmigrate.utility;

import org.sharpsw.dbmigrate.config.DatabaseConfig;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionCreator;
import org.sharpsw.dbmigrate.data.Database;
import org.sharpsw.dbmigrate.data.MetadataGenerationException;
import org.sharpsw.dbmigrate.data.MetadataGenerator;

public class DatabaseExportToolSingleXmlFile {
	
	public void export(final DatabaseConfig config) throws XMLExportException {
		try {
			DatabaseConnectionCreator connCreator = new DatabaseConnectionCreator();
			MetadataGenerator generator = new MetadataGenerator(connCreator);
			Database database = generator.generate(config);			
		} catch (MetadataGenerationException exception) {
			throw new XMLExportException("Metadata error", exception);
		}
	}
}
