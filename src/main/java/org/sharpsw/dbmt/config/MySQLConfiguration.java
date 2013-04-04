package org.sharpsw.dbmt.config;

public class MySQLConfiguration implements IDatabaseConfiguration {

	private String server;
	private Integer port;
	private String database;
	private String user;
	private String password;
	private String driverClassName;
	
	public MySQLConfiguration() {
		this.server = "";
		this.port = new Integer(0);
		this.database = "";
		this.user = "";
		this.password = "";
		this.driverClassName = "";
	}
	
	public MySQLConfiguration(String server, Integer port, String database, String user, String password, String driverClassName) {
		this.server = server;
		this.port = port;
		this.database = database;
		this.user = user;
		this.password = password;
		this.driverClassName = driverClassName;
	}
	
	public void setServer(String server) {
		this.server = server;
	}
	
	public void setPort(Integer port) {
		this.port = port;
	}
	
	public void setDatabase(String database) {
		this.database = database;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	
	public String getServer() {
		return this.server;
	}
	
	public Integer getPort() {
		return this.port;
	}
	
	public String getDatabase() {
		return this.database;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public String getConnectionString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("jdbc:mysql://").append(this.server).append(":").append(this.port.toString()).append("/").append(this.database).append("?user=").append(this.user).append("&password=").append(this.password);
		return buffer.toString();
	}

	@Override
	public DatabaseVendor getDatabaseVendor() {
		return DatabaseVendor.MYSQL;
	}

	@Override
	public String getDriverClassName() {
		return this.driverClassName;
	}

}
