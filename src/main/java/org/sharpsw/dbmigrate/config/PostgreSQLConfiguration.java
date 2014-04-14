package org.sharpsw.dbmigrate.config;

import static org.sharpsw.dbmigrate.config.DatabaseVendor.POSTGRESQL;

public class PostgreSQLConfiguration implements DatabaseConfig {
	private String server;
	private Integer port;
	private String userName;
	private String password;
	private String database;
	
	public PostgreSQLConfiguration() {
		this.server = "";
		this.port = new Integer(5432);
		this.userName = "";
		this.password = "";
		this.database = "";
	}
	
	public PostgreSQLConfiguration(String server, Integer port, String userName, String password, String database) {
		this.server = server;
		this.port = port;
		this.userName = userName;
		this.password = password;
		this.database = database;		
	}
	
	public void setServer(String server) {
		this.server = server;
	}
	
	public void setPort(Integer port) {
		this.port = port;
	}
	
	public void setUser(String userName) {
		this.userName = userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setDatabase(String database) {
		this.database = database;
	}
	
	public String getServer() {
		return this.server;
	}
	
	public Integer getPort() {
		return this.port;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getDatabase() {
		return this.database;
	}
	
	
	@Override
	public String getConnectionString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("jdbc:postgresql://").append(this.server).append(":").append(this.port).append("/").append(this.database).append("?user=").append(this.userName).append("&password=").append(this.password);
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
