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
		StringBuffer buffer = new StringBuffer();
		buffer.append("jdbc:postgresql://").append(this.getServer()).append(":").append(this.getPort()).append("/").append(this.getDatabase()).append("?user=").append(this.getUser()).append("&password=").append(this.getPassword());
		return buffer.toString();
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
