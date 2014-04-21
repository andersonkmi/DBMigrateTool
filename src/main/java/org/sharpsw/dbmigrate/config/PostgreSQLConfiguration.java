package org.sharpsw.dbmigrate.config;

import static org.sharpsw.dbmigrate.config.DatabaseVendor.POSTGRESQL;

public class PostgreSQLConfiguration extends BaseDatabaseConfiguration {
	
	public PostgreSQLConfiguration() {
		super("", new Integer(5432), "", "", "");
	}
	
	public PostgreSQLConfiguration(String server, Integer port, String userName, String password, String database) {
		super(server, port, database, userName, password);
	}
		
	@Override
	public String getConnectionString() {
		return String.format("jdbc:postgresql://%s:%d/%s?user=%s&password=%s", this.getServer(), this.getPort(), this.getDatabase(), this.getUser(), this.getPassword());
	}

	@Override
	public DatabaseVendor getDatabaseVendor() {
		return POSTGRESQL;
	}

	@Override
	public String getDriverClassName() {
		return POSTGRESQL.getDriverClassName();
	}

}
