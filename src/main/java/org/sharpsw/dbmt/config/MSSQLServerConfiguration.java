package org.sharpsw.dbmt.config;

public class MSSQLServerConfiguration implements IDatabaseConfiguration {
	public MSSQLServerConfiguration() {
		this.server = "";
		this.instance = "";
		this.port = new Integer(1433);
		this.user = "";
		this.password = "";
		this.database = "";
		this.driverClassName = "";
	}
	
	public MSSQLServerConfiguration(String driverClassName, String server, String instance, Integer port, String user, String password, String database) {
		this.server = server;
		this.instance = instance;
		this.port = port;
		this.user = user;
		this.password = password;
		this.database = database;
		this.driverClassName = driverClassName;
	}
	
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	
	@Override
	public String getDriverClassName() {
		return this.driverClassName;
	}
	
	public void setServer(String server) {
		this.server = server;
	}
	
	public void setInstance(String instance) {
		this.instance = instance;
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
	
	public String getServer() {
		return this.server;
	}
	
	public Integer getPort() {
		return this.port;
	}
	
	public String getInstance() {
		return this.instance;
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
		buffer.append("jdbc:sqlserver://").append(this.server);		
		if(!this.instance.isEmpty()) {
			buffer.append("\\").append(this.instance);
		}		
		buffer.append(":").append(this.port.toString());
		buffer.append(";databaseName=").append(this.database);
		buffer.append(";user=").append(this.user);
		buffer.append(";password=").append(this.password);
		return buffer.toString();
	}
	
	@Override
	public DatabaseVendor getDatabaseVendor() {
		return DatabaseVendor.MSSQLSERVER;
	}

	private String server;
	private String instance;
	private Integer port;
	private String user;
	private String password;
	private String database;
	private String driverClassName;
}
