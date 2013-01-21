package org.sharpsw.dbmt.config;

public interface IDatabaseConfiguration {
	public String getConnectionString();
	public DatabaseVendor getDatabaseVendor();
	public String getDriverClassName();
}
