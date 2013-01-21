package org.sharpsw.dbmt.connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ConnectionManager {
	private Map<Integer, Connection> availableConns;
	private Map<Integer, Boolean> connectionStatus;
	private int size;
	
	public ConnectionManager() {
		this.availableConns = new HashMap<Integer, Connection>();
		this.connectionStatus = new HashMap<Integer, Boolean>();
		this.size = 10;
	}
	
	public void initialize(String url, int size) throws SQLException {
		if(this.availableConns.isEmpty()) {
			this.size = size;
			for(int i = 0; i < size; i++) {
				Connection connection = DriverManager.getConnection(url);
				this.availableConns.put(new Integer(connection.hashCode()), connection);
				this.connectionStatus.put(new Integer(connection.hashCode()), Boolean.TRUE);
			}
		}
	}
	
	public synchronized Connection getConnection() {
		Connection connection = null;
		
		Set<Integer> keys = this.connectionStatus.keySet();
		Iterator<Integer> iterator = keys.iterator();
		Integer availableKey = null;
		while(iterator.hasNext()) {
			Integer key = iterator.next();
			if(this.connectionStatus.get(key)) {
				availableKey = key;
				break;
			}
		}
		
		if(availableKey != null) {
			connection = this.availableConns.get(availableKey);
			this.connectionStatus.put(availableKey, Boolean.FALSE);
		}
		return connection;
	}
	
	public synchronized void returnConnection(Connection connection) {
		int hashCode = connection.hashCode();
		Integer key = new Integer(hashCode);
		this.connectionStatus.put(key, Boolean.TRUE);
	}
	
	public int getPoolSize() {
		return this.size;
	}
	
	public synchronized int getAvailableConnectionCount() {
		int counter = 0;
		Set<Integer> keys = this.connectionStatus.keySet();
		Iterator<Integer> iterator = keys.iterator();
		while(iterator.hasNext()) {
			if(this.connectionStatus.get(iterator.next())) {
				++counter;
			}
		}
		return counter;
	}
	
	public synchronized int getInUseConnectionCount() {
		int counter = 0;
		Set<Integer> keys = this.connectionStatus.keySet();
		Iterator<Integer> iterator = keys.iterator();
		while(iterator.hasNext()) {
			if(!this.connectionStatus.get(iterator.next())) {
				++counter;
			}
		}
		return counter;
		
	}
}
