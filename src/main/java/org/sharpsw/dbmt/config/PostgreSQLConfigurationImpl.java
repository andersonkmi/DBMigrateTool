package org.sharpsw.dbmt.config;

public class PostgreSQLConfigurationImpl implements IDatabaseConfiguration {
	private String server;
	private Integer port;
	private String userName;
	private String password;
	private String database;
	private String driverClassName;
	
	public PostgreSQLConfigurationImpl() {
		this.server = "";
		this.port = new Integer(5432);
		this.userName = "";
		this.password = "";
		this.database = "";
		this.driverClassName = "";
	}
	
	public PostgreSQLConfigurationImpl(String server, Integer port, String userName, String password, String database, String driverClassName) {
		this.server = server;
		this.port = port;
		this.userName = userName;
		this.password = password;
		this.database = database;
		this.driverClassName = driverClassName;
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
	
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
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
		return DatabaseVendor.POSTGRESQL;
	}

	@Override
	public String getDriverClassName() {
		return this.driverClassName;
	}

}
