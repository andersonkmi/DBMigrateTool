package org.sharpsw.dbmigrate.config;

public abstract class BaseDatabaseConfiguration implements DatabaseConfig {
	private String server;
	private Integer port;
	private String database;
	private String user;
	private String password;
	
	public BaseDatabaseConfiguration(final String server, final Integer port, final String database, final String user, final String password) {
		this.server = server;
		this.port = port;
		this.user = user;
		this.password = password;
		this.database = database;
	}
	
	@Override
	public abstract String getConnectionString();

	@Override
	public abstract DatabaseVendor getDatabaseVendor();

	@Override
	public abstract String getDriverClassName();
	
	public void setServer(final String server) {
		this.server = server;
	}
	
	public String getServer() {
		return this.server;
	}
	
	public void setPort(final Integer port) {
		this.port = port;
	}
	
	public Integer getPort() {
		return port;
	}
	
	public void setUser(final String user) {
		this.user = user;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setPassword(final String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setDatabase(final String database) {
		this.database = database;
	}
	
	public String getDatabase() {
		return database;
	}
	
	public String getDatabaseConfigDescription() {
		return String.format("Database config = [vendor = '%s'; server = '%s'; port = '%d'; schema = '%s'; user = '%s']", this.getDatabaseVendor().getDescription(), this.getServer(), this.getPort(), this.getDatabase(), this.getUser());
	}
}
