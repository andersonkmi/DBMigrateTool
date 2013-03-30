package org.sharpsw.dbmt.connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sharpsw.dbmt.config.IDatabaseConfiguration;

public class DBConnectionCreator {
	private IDatabaseConfiguration configuration;
	
	@SuppressWarnings("unused")
	private DBConnectionCreator() {}
	
	public DBConnectionCreator(IDatabaseConfiguration configuration) throws DBConnectionDriverLoadException {
		this.configuration = configuration;
		String driverClass = this.configuration.getDriverClassName();
		
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException classNotFoundExc) {
			StringBuffer message = new StringBuffer();
			message.append("Error when loading the driver: '").append(driverClass).append("'");
			throw new DBConnectionDriverLoadException(message.toString(), classNotFoundExc);
		} catch (ExceptionInInitializerError error) {
			StringBuffer message = new StringBuffer();
			message.append("Error when loading the driver: '").append(driverClass).append("'");
	
			throw new DBConnectionDriverLoadException(message.toString(), error);
		} catch (LinkageError linkageErr) {
			StringBuffer message = new StringBuffer();
			message.append("Error when loading the driver: '").append(driverClass).append("'");
			throw new DBConnectionDriverLoadException(message.toString(), linkageErr);
		} 
	}
	
	public Connection getConnection() throws DBConnectionCreateException {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.configuration.getConnectionString());
			return connection;
		} catch (SQLException exception) {
			StringBuffer message = new StringBuffer();
			message.append("Error when creating a new connection to the database.");
			throw new DBConnectionCreateException(message.toString(), exception);
		}
	}
}