package org.sharpsw.dbmt.config;

public aspect DatabaseConfigValidationAspect {
	pointcut hsqldbConfigInit(String server, String database, Integer port, String user, String password, String driver) : initialization(public HyperSQLConfigurationImpl.new(String, String, Integer, String, String, String)) && 
	                                                                                                                       args(server, database, port, user, password, driver);
	before(String server, String database, Integer port, String user, String password, String driver) throws InvalidConfigurationException : hsqldbConfigInit(server, database, port, user, password, driver) {
		validateServerName(server);
		validateDatabase(database);
		validatePort(port);
		validateUser(user);
		validatePassword(password);
		validateDriver(driver);
		validateHyperSQLJdbcDriverClass(driver);
	}
	
	pointcut msSqlServerConfigInit(String driverClassName, String server, String instance, Integer port, String user, String password, String database) : initialization(public MSSQLServerConfigurationImpl.new(String, String, String, Integer, String, String, String)) && args(driverClassName, server, instance, port, user, password, database);
	before(String driverClassName, String server, String instance, Integer port, String user, String password, String database) throws InvalidConfigurationException : msSqlServerConfigInit(driverClassName, server, instance, port, user, password, database) {
		validateDriver(driverClassName);
		validateServerName(server);
		validatePort(port);
		validateUser(user);
		validatePassword(password);
		validateDatabase(database);
		validateMSSQLServerJdbcDriverClass(driverClassName);
	}
	
	pointcut oracleConfigInit(String driverClassName, String server, Integer port, String service, String user, String password) : initialization(public OracleConfigurationImpl.new(String, String, Integer, String, String, String)) && args(driverClassName, server, port, service, user, password);
	before(String driverClassName, String server, Integer port, String service, String user, String password) throws InvalidConfigurationException : oracleConfigInit(driverClassName, server, port, service, user, password) {
		validateDriver(driverClassName);
		validateServerName(server);
		validatePort(port);
		validateOracleService(service);
		validateUser(user);
		validatePassword(password);
		validateOracleJdbcDriverClass(driverClassName);
	}
	
	pointcut postgresqlConfigInit(String server, Integer port, String user, String password, String database, String driverClassName) : initialization(public PostgreSQLConfigurationImpl.new(String, Integer, String, String, String, String)) && args(server, port, user, password, database, driverClassName);
	before(String server, Integer port, String user, String password, String database, String driverClassName) throws InvalidConfigurationException : postgresqlConfigInit(server, port, user, password, database, driverClassName) {
		validateServerName(server);
		validatePort(port);
		validateUser(user);
		validatePassword(password);
		validateDatabase(database);
		validateDriver(driverClassName);
		validatePostgreSQLJdbcDriverClass(driverClassName);
	}
	
	pointcut accessConfigInit(String driverClassName, String databaseFile, String driverName, String user, String password) : initialization(public MSAccessConfigurationImpl.new(String, String, String, String, String)) && args(driverClassName, databaseFile, driverName, user, password);
	before(String driverClassName, String databaseFile, String driverName, String user, String password) throws InvalidConfigurationException : accessConfigInit(driverClassName, databaseFile, driverName, user, password) {
		validateDriver(driverClassName);
		validateAccessDBFile(databaseFile);
		validateAccessODBCDriver(driverName);
		validateMSAccessJdbcDriverClass(driverClassName);
	}
	
	pointcut setServer(String server) : (execution(public void HyperSQLConfigurationImpl.setServer(String)) || execution(public void MSSQLServerConfigurationImpl.setServer(String)) || execution(public void OracleConfigurationImpl.setServer(String)) || execution(public void PostgreSQLConfigurationImpl.setServer(String))) && args(server);
	before(String server) throws InvalidConfigurationException : setServer(server) {
		validateServerName(server);
	}
	
	pointcut setDatabase(String database) : (execution(public void HyperSQLConfigurationImpl.setDatabase(String)) || execution(public void MSSQLServerConfigurationImpl.setDatabase(String)) || execution(public void PostgreSQLConfigurationImpl.setDatabase(String))) && args(database);
	before(String database) throws InvalidConfigurationException : setDatabase(database) {
		validateDatabase(database);
	}
	
	pointcut setPort(Integer port) : (execution (public void HyperSQLConfigurationImpl.setPort(Integer)) || execution(public void MSSQLServerConfigurationImpl.setPort(Integer)) || execution(public void OracleConfigurationImpl.setPort(Integer)) || execution(public void PostgreSQLConfigurationImpl.setPort(Integer))) && args(port);
	before(Integer port) throws InvalidConfigurationException : setPort(port) {
		validatePort(port);
	}
	
	pointcut setUser(String user) : (execution(public void HyperSQLConfigurationImpl.setUser(String)) || execution(public void MSAccessConfigurationImpl.setUser(String)) || execution(public void MSSQLServerConfigurationImpl.setUser(String)) || execution(public void OracleConfigurationImpl.setUser(String)) || execution(public void PostgreSQLConfigurationImpl.setUser(String))) && args(user);
	before(String user) throws InvalidConfigurationException : setUser(user) {
		validateUser(user);
	}
	
	pointcut setPassword(String password) : (execution(public void HyperSQLConfigurationImpl.setPassword(String)) || execution(public void MSAccessConfigurationImpl.setPassword(String)) || execution(public void MSSQLServerConfigurationImpl.setPassword(String)) || execution(public void OracleConfigurationImpl.setPassword(String)) || execution(public void PostgreSQLConfigurationImpl.setPassword(String))) && args(password);
	before(String password) throws InvalidConfigurationException : setPassword(password) {
		validatePassword(password);
	}	

	pointcut setDriverClassName(String driver) : (execution(public void HyperSQLConfigurationImpl.setDriverClassName(String)) || execution(public void MSAccessConfigurationImpl.setDriverClassName(String)) || execution(public void MSSQLServerConfigurationImpl.setDriverClassName(String)) || execution(public void OracleConfigurationImpl.setDriverClassName(String)) || execution(public void PostgreSQLConfigurationImpl.setDriverClassName(String))) && args(driver);
	before(String driver) throws InvalidConfigurationException : setDriverClassName(driver) {
		validateDriver(driver);
	}	

	private void validateServerName(String server) throws InvalidConfigurationException {
		if(server == null) {
			throw new InvalidConfigurationException("Server is null");
		} else if(server.isEmpty()) {
			throw new InvalidConfigurationException("Server name is empty");
		}
	}
	
	private void validateDatabase(String database) throws InvalidConfigurationException {
		if(database == null) {
			throw new InvalidConfigurationException("Database is null");
		} else if(database.isEmpty()) {
			throw new InvalidConfigurationException("Database name is empty");
		}
	}
	
	private void validatePort(Integer port) throws InvalidConfigurationException {
		if(port == null) {
			throw new InvalidConfigurationException("Port number is null");
		} else if(port.intValue() == 0) {
			throw new InvalidConfigurationException("Port number is zero");
		} else if(port.intValue() < 0) {
			throw new InvalidConfigurationException("Port number is negative");
		}
	}
	
	private void validateUser(String user) throws InvalidConfigurationException {
		if(user == null) {
			throw new InvalidConfigurationException("User is null");
		} else if(user.isEmpty()) {
			throw new InvalidConfigurationException("User is empty");
		}
	}
	
	private void validatePassword(String password) throws InvalidConfigurationException {
		if(password == null) {
			throw new InvalidConfigurationException("Password is null");
		} else if(password.isEmpty()) {
			throw new InvalidConfigurationException("Password is empty");
		}
	}
	
	private void validateDriver(String driver) throws InvalidConfigurationException {
		if(driver == null) {
			throw new InvalidConfigurationException("Driver is null");
		} else if(driver.isEmpty()) {
			throw new InvalidConfigurationException("Driver is empty");
		}
	}
	
	private void validateAccessDBFile(String file) throws InvalidConfigurationException {
		if(file == null) {
			throw new InvalidConfigurationException("MS Access database file is null");
		} else if(file.isEmpty()) {
			throw new InvalidConfigurationException("MS Access database file is empty");
		}
	}
	
	private void validateAccessODBCDriver(String driver) throws InvalidConfigurationException {
		if(driver == null) {
			throw new InvalidConfigurationException("MS Access ODBC driver is null");
		} else if(driver.isEmpty()) {
			throw new InvalidConfigurationException("MS Access ODBC driver is empty");
		}		
	}
	
	private void validateHyperSQLJdbcDriverClass(String driverClass) throws InvalidConfigurationException {
		if(!driverClass.equals("org.hsqldb.jdbcDriver")) {
			StringBuffer message = new StringBuffer();
			message.append("Invalid JDBC driver class. Expected class is 'org.hsqldb.jdbcDriver'");
			throw new InvalidConfigurationException(message.toString());
		}
	}
	
	private void validateOracleJdbcDriverClass(String driverClass) throws InvalidConfigurationException {
		if(!driverClass.equals("org.jdbc.driver.OracleDriver")) {
			throw new InvalidConfigurationException("Invalid JDBC driver class. Expected class is 'org.jdbc.driver.OracleDriver'");
		}
	}
	
	private void validateOracleService(String service) throws InvalidConfigurationException {
		if(service == null) {
			throw new InvalidConfigurationException("Oracle service is null");
		} else if(service.isEmpty()) {
			throw new InvalidConfigurationException("Oracle service is empty");
		}
	}
	
	private void validateMSSQLServerJdbcDriverClass(String driver) throws InvalidConfigurationException {
		if(!driver.equals("com.microsoft.sqlserver.jdbc.SQLServerDriver")) {
			throw new InvalidConfigurationException("Invalid JDBC driver class. Expected class is 'com.microsoft.sqlserver.jdbc.SQLServerDriver'");
		}
	}
	
	private void validatePostgreSQLJdbcDriverClass(String driver) throws InvalidConfigurationException {
		if(!driver.equals("org.postgresql.Driver")) {
			throw new InvalidConfigurationException("Invalid JDBC driver class. Expected class is 'org.postgresql.Driver'");
		}
	}
	
	private void validateMSAccessJdbcDriverClass(String driver) throws InvalidConfigurationException {
		if(!driver.equals("sun.jdbc.odbc.JdbcOdbcDriver")) {
			throw new InvalidConfigurationException("Invalid JDBC driver class. Expected class is 'sun.jdbc.odbc.JdbcOdbcDriver'");
		}
	}
}
