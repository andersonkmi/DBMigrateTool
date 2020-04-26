package org.codecraftlabs.neptune.config;

import static org.codecraftlabs.neptune.config.DatabaseVendor.MYSQL;

public class MySQLConfiguration extends BaseDatabaseConfiguration {
	
	public MySQLConfiguration() {
		super("", 3306, "", "", "");
	}
	
	public MySQLConfiguration(String server, Integer port, String database, String user, String password) {
		super(server, port, database, user, password);
	}
		
	@Override
	public String getConnectionString() {
		return String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s", this.getServer(), this.getPort(), this.getDatabase(), this.getUser(), this.getPassword());
	}

	@Override
	public DatabaseVendor getDatabaseVendor() {
		return MYSQL;
	}

	@Override
	public String getDriverClassName() {
		return MYSQL.getDriverClassName();
	}

}
