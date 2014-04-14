package org.sharpsw.dbmigrate.config;

import static org.sharpsw.dbmigrate.config.DatabaseVendor.H2;

public class H2Configuration implements DatabaseConfig {
	private String server;
	private String database;
	private String user;
	private String password;
	private Integer port;
	
	public H2Configuration() {
		this.server = "";
		this.database = "";
		this.user = "";
		this.password = "";
		this.port = new Integer(9001);
	}
	
	public H2Configuration(String server, String database, Integer port, String user, String password) {
		this.server = server;
		this.database = database;
		this.port = port;
		this.user = user;
		this.password = password;
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
	
	@Override
	public String getConnectionString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("jdbc:h2:tcp://").append(this.server).append(":").append(this.port.toString()).append("/").append(this.database).append(";username=").append(this.user).append(";password=").append(this.password);
		return buffer.toString();
	}

	@Override
	public DatabaseVendor getDatabaseVendor() {
		return H2;
	}

	@Override
	public String getDriverClassName() {
		return H2.getDriverClassName();
	}

}
