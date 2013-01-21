package org.sharpsw.dbmt.connectivity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SimpleDatabaseConnectionPoolImpl implements IDatabaseConnectionPool {
	private Map<String, ConnectionManager> connectionManager;
	private Map<Integer, String> connectionURL;
	
	public SimpleDatabaseConnectionPoolImpl() {
		this.connectionManager = new HashMap<String, ConnectionManager>();
		this.connectionURL = new HashMap<Integer, String>();
	}
	
	public synchronized Connection getConnection(String url) throws SQLException {
		Connection connection = null;
		ConnectionManager manager = this.connectionManager.get(url);
		if(manager != null) {
			connection = manager.getConnection();
			this.connectionURL.put(new Integer(connection.hashCode()), url);
		} else {
			manager = new ConnectionManager();
			manager.initialize(url, 30);
			connection = manager.getConnection();
			this.connectionURL.put(new Integer(connection.hashCode()), url);
			this.connectionManager.put(url, manager);
		}
		return connection;
	}
	
	public synchronized void surrenderConnection(Connection connection) {
		int hash = connection.hashCode();
		Integer hashCode = new Integer(hash);
		String url = this.connectionURL.get(hashCode);
		ConnectionManager manager = this.connectionManager.get(url);
		manager.returnConnection(connection);
	}	
}
