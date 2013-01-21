package org.sharpsw.dbmt.connectivity;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public aspect DatabaseConnectionPoolingAspect {
	IDatabaseConnectionPool connPool = new SimpleDatabaseConnectionPoolImpl();
	
	pointcut connectionCreation(String url) : 
		call(public static Connection DriverManager.getConnection(String)) && 
		args(url) && 
		!within(org.sharpsw.dbmt.connectivity.ConnectionManager);
	pointcut connectionRelease(Connection connection) : call(public void Connection.close()) && target(connection);
	
	Connection around(String url) throws SQLException : connectionCreation(url) {
		Connection connection = this.connPool.getConnection(url);
		if(connection == null) {
			throw new SQLException("No more connections available in the pool (AJ).");
		}
		return connection;
	}
	
	void around(Connection connection) : connectionRelease(connection) {
		this.connPool.surrenderConnection(connection);
	}
}
