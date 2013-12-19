package org.sharpsw.dbmt.config;

public interface DatabaseConfig {
	public String getConnectionString();

	public DatabaseVendor getDatabaseVendor();

	public String getDriverClassName();
}
