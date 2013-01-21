package org.sharpsw.dbmt.config;

public class MSAccessConfigurationImpl implements IDatabaseConfiguration {
	public MSAccessConfigurationImpl() {
		this.databaseFile = "";
		this.driverName = "";
		this.user = "";
		this.password = "";
		this.driverClassName = "";
	}
	
	public MSAccessConfigurationImpl(String driverClassName, String databaseFile, String driverName, String user, String password) {
		this.databaseFile = databaseFile;
		this.driverName = driverName;
		this.user = user;
		this.password = password;
		this.driverClassName = driverClassName;
	}
	
	public void setDatabaseFile(String databaseFile) {
		this.databaseFile = databaseFile;
	}
	
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDatabaseFile() {
		return this.databaseFile;
	}
	
	public String getDriverName() {
		return this.driverName;
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
		buffer.append("jdbc:odbc:Driver={").append(this.driverName).append("};");
		buffer.append("DBQ=").append(this.databaseFile).append(";");
		buffer.append("DriverID=22;READONLY=false;");		
		return buffer.toString();
	}
	
	@Override
	public DatabaseVendor getDatabaseVendor() {
		return DatabaseVendor.MSACCESS;
	}
	
	@Override
	public String getDriverClassName() {
		return this.driverClassName;
	}
	
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
/*
	private static final String accessDBURLPrefix = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
    private static final String accessDBURLSuffix = ";DriverID=22;READONLY=false}";

    // Initialize the JdbcOdbc Bridge Driver
    static {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        } catch(ClassNotFoundException e) {
            System.err.println("JdbcOdbc Bridge Driver not found!");
        }
    }

    public static Connection getAccessDBConnection(String filename) throws SQLException {
        filename = filename.replace('', '/').trim();
        String databaseURL = accessDBURLPrefix + filename + accessDBURLSuffix;
        return DriverManager.getConnection(databaseURL, "", "");
    } */
	
	
	private String databaseFile;
	private String driverName;
	private String user;
	private String password;
	private String driverClassName;
}
