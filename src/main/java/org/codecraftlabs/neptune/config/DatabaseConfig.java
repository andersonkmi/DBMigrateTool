package org.codecraftlabs.neptune.config;

public interface DatabaseConfig {
	public String getConnectionString();

	public DatabaseVendor getDatabaseVendor();

	public String getDriverClassName();
	
	public String getDatabase();
	
	public String getDatabaseConfigDescription();
}
