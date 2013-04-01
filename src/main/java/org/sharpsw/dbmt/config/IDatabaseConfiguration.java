package org.sharpsw.dbmt.config;

public interface IDatabaseConfiguration {
	public String getConnectionString();
    public void setConnectionString(final String connection);

	public DatabaseVendor getDatabaseVendor();
    public void setDatabaseVendor(final DatabaseVendor vendor);

	public String getDriverClassName();
    public void setDriverClassName(final String driverClass);
}
