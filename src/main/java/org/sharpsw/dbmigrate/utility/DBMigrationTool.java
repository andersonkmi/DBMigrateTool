package org.sharpsw.dbmigrate.utility;

import org.sharpsw.dbmigrate.config.DatabaseConfig;
import org.sharpsw.dbmigrate.data.Database;

public class DBMigrationTool {
	private MigrationSourceService sourceService;
	private MigrationDestinationBaseService destinationService;
	
	@SuppressWarnings("unused")
	private DBMigrationTool() {
		// No action performed
	}
	
	public DBMigrationTool(DatabaseConfig sourceConfig, DatabaseConfig destinationConfig) {
		this.sourceService = new MigrationSourceService(sourceConfig);
		this.destinationService = MigrationDestinationServiceFactory.getService(destinationConfig);		
	}
	
	public void migrate() throws DBMigrationException {
		Database database = this.sourceService.getDatabase();
		this.destinationService.migrate(database);
	}
}
