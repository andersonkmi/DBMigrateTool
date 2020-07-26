package org.codecraftlabs.neptune.config;

class PostgreSQLConfiguration extends BaseDatabaseConfiguration {
	
	public PostgreSQLConfiguration() {
		super("", 5432, "", "", "");
	}

	@Override
	public String getConnectionString() {
		return String.format("jdbc:postgresql://%s:%d/%s?user=%s&password=%s", this.getServer(), this.getPort(), this.getDatabase(), this.getUser(), this.getPassword());
	}

	@Override
	public DatabaseVendor getDatabaseVendor() {
		return DatabaseVendor.POSTGRESQL;
	}

	@Override
	public String getDriverClassName() {
		return DatabaseVendor.POSTGRESQL.getDriverClassName();
	}

}
