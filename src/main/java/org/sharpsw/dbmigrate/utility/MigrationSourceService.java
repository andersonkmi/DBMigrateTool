package org.sharpsw.dbmigrate.utility;

import java.sql.Connection;
import java.sql.SQLException;

import org.sharpsw.dbmigrate.config.DatabaseConfig;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionCreateException;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionCreator;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionDriverLoadException;
import org.sharpsw.dbmigrate.data.Database;
import org.sharpsw.dbmigrate.data.MetadataGenerationException;
import org.sharpsw.dbmigrate.data.MetadataGenerator;

class MigrationSourceService {
	private DatabaseConfig configuration;
	private Connection connection;
	
	@SuppressWarnings("unused")
	private MigrationSourceService() {
		
	}
	
	MigrationSourceService(DatabaseConfig configuration) {
		this.configuration = configuration;
	}
	
	public Database getDatabase() throws DBMigrationException {
		try {
			Database database = null;
			DatabaseConnectionCreator connCreator = new DatabaseConnectionCreator();
			this.connection = connCreator.getConnection(this.configuration);
			MetadataGenerator generator = new MetadataGenerator(this.connection);
			database = generator.generate();
			return database;
		} catch (DatabaseConnectionDriverLoadException exception) {
			StringBuffer message = new StringBuffer();
			message.append("Error when loading the database driver: ").append(exception.getMessage());
			throw new DBMigrationException(message.toString(), exception);
		} catch (DatabaseConnectionCreateException exception) {
			StringBuffer message = new StringBuffer();
			message.append("Error when creating the connection to database server: ").append(exception.getMessage());
			throw new DBMigrationException(message.toString(), exception);			
		} catch (MetadataGenerationException exception) {
			StringBuffer message = new StringBuffer();
			message.append("Error when creating the database metadata: ").append(exception.getMessage());
			throw new DBMigrationException(message.toString(), exception);						
		} finally {
			try {
				if(this.connection != null) {
					this.connection.close();
				}
			} catch (SQLException exception) {
				// TODO: insert handling code here later on
			}			
		}
	}
}
