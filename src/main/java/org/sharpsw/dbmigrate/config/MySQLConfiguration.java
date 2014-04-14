package org.sharpsw.dbmigrate.config;

import static org.sharpsw.dbmigrate.config.DatabaseVendor.MYSQL;

public class MySQLConfiguration extends BaseDatabaseConfiguration {
	
	public MySQLConfiguration() {
		super("", new Integer(3306), "", "", "");
	}
	
	public MySQLConfiguration(String server, Integer port, String database, String user, String password) {
		super(server, port, database, user, password);
	}
		
	@Override
	public String getConnectionString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("jdbc:mysql://").append(this.getServer()).append(":").append(this.getPort().toString()).append("/").append(this.getDatabase()).append("?user=").append(this.getUser()).append("&password=").append(this.getPassword());
		return buffer.toString();
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
