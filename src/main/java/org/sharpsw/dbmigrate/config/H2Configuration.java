package org.sharpsw.dbmigrate.config;

import static org.sharpsw.dbmigrate.config.DatabaseVendor.H2;

public class H2Configuration extends BaseDatabaseConfiguration {
	
	public H2Configuration() {
		super("", new Integer(9001), "", "", "");
	}
	
	public H2Configuration(String server, String database, Integer port, String user, String password) {
		super(server, port, database, user, password);
	}
	
	@Override
	public String getConnectionString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("jdbc:h2:tcp://").append(this.getServer()).append(":").append(this.getPort().toString()).append("/").append(this.getDatabase()).append(";username=").append(this.getUser()).append(";password=").append(this.getPassword());
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
