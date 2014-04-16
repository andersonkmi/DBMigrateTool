package org.sharpsw.dbmigrate.utility;

import org.sharpsw.dbmigrate.config.DatabaseConfig;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionCreator;
import org.sharpsw.dbmigrate.data.Database;
import org.sharpsw.dbmigrate.data.DataLoadException;
import org.sharpsw.dbmigrate.data.DatabaseDataLoader;

public class DatabaseExportToolSingleXmlFile {
	
	public void export(final DatabaseConfig config) throws XMLExportException {
		try {
			DatabaseConnectionCreator connCreator = new DatabaseConnectionCreator();
			DatabaseDataLoader generator = new DatabaseDataLoader(connCreator);
			Database database = generator.load(config);			
		} catch (DataLoadException exception) {
			throw new XMLExportException("Metadata error", exception);
		}
	}
}
