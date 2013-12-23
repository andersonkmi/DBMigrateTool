package org.sharpsw.dbmigrate.config;

public class HyperSQLConfiguration implements DatabaseConfig {
	private String server;
	private String database;
	private String user;
	private String password;
	private Integer port;
	private String driverClassName;
	
	public HyperSQLConfiguration() {
		this.server = "";
		this.database = "";
		this.user = "";
		this.password = "";
		this.port = new Integer(9001);
		this.driverClassName = "";
	}
	
	public HyperSQLConfiguration(String server, String database, Integer port, String user, String password, String driverClassName) {
		this.server = server;
		this.database = database;
		this.port = port;
		this.user = user;
		this.password = password;
		this.driverClassName = driverClassName;
	}
	
	public void setServer(String server) {
		this.server = server;
	}
	
	public void setDatabase(String database) {
		this.database = database;
	}
	
	public void setPort(Integer port) {
		this.port = port;
	}
	
	public void setUser(String userName) {
		this.user = userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	
	@Override
	public String getConnectionString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("jdbc:hsqldb:hsql://").append(this.server).append(":").append(this.port.toString()).append("/").append(this.database).append(";username=").append(this.user).append(";password=").append(this.password);
		return buffer.toString();
	}

	@Override
	public DatabaseVendor getDatabaseVendor() {
		return DatabaseVendor.HYPERSQL;
	}

	@Override
	public String getDriverClassName() {
		return this.driverClassName;
	}

}
