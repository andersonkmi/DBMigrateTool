package org.codecraftlabs.neptune.config;

public interface DatabaseConfig {
	String getConnectionString();

	DatabaseVendor getDatabaseVendor();

	String getDriverClassName();
	
	String getDatabase();
	
	String getDatabaseConfigDescription();
}
