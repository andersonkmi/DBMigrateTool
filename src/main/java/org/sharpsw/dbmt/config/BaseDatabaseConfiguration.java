package org.sharpsw.dbmt.config;

public abstract class BaseDatabaseConfiguration implements IDatabaseConfiguration {
	private String connectionString;
	private DatabaseVendor vendor;
	private String driverClassName;

    public BaseDatabaseConfiguration(final String connectionString, final DatabaseVendor vendor, final String driverClassName) {
        this.connectionString = connectionString;
        this.vendor = vendor;
        this.driverClassName = driverClassName;
    }

	@Override
	public String getConnectionString() {
        return this.connectionString;
    }

    @Override
    public void setConnectionString(final String connection) {
        this.connectionString = connection;
    }

	@Override
	public DatabaseVendor getDatabaseVendor() {
        return this.vendor;
	}

    @Override
    public void setDatabaseVendor(final DatabaseVendor vendor) {
        this.vendor = vendor;
    }

	@Override
	public String getDriverClassName() {
        return this.driverClassName;
	}

    @Override
    public void setDriverClassName(final String driverClassName) {
        this.driverClassName = driverClassName;
    }

}
