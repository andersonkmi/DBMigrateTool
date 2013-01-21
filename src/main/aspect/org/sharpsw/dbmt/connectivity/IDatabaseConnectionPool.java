package org.sharpsw.dbmt.connectivity;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabaseConnectionPool {
	public Connection getConnection(String url) throws SQLException;	
	public void surrenderConnection(Connection connection);
}
