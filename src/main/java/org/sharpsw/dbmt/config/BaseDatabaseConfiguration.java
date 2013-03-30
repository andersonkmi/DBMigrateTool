package org.sharpsw.dbmt.config;

public abstract class BaseDatabaseConfiguration implements IDatabaseConfiguration {
	private String connectionString;
	private DatabaseVendor vendor;
	private String driverClassName;
	
	@Override
	public String getConnectionString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DatabaseVendor getDatabaseVendor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDriverClassName() {
		// TODO Auto-generated method stub
		return null;
	}

}
