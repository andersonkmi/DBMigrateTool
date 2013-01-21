package org.sharpsw.dbmt.utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.sharpsw.dbmt.base.Database;
import org.sharpsw.dbmt.config.IDatabaseConfiguration;
import org.sharpsw.dbmt.connectivity.DBConnectionCreateException;
import org.sharpsw.dbmt.connectivity.DBConnectionCreator;
import org.sharpsw.dbmt.connectivity.DBConnectionDriverLoadException;

abstract class MigrationDestinationBaseService {
	protected IDatabaseConfiguration configuration;
	protected List<String> tableCreateStatements;
	protected List<String> fkStatements;
	protected List<String> pkStatements;
	protected List<String> autoIncrementStatements;
	protected List<String> selectStatements;
	protected Connection connection;

	@SuppressWarnings("unused")
	private MigrationDestinationBaseService() {
		
	}
	
	MigrationDestinationBaseService(IDatabaseConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public void migrate(final Database database) throws DBMigrationException {
		boolean exceptionRaised = true;
		try {
			connect();			
			generateStatements(database);
			createTables();
			exceptionRaised = false;
		} catch (DBConnectionCreateException exception) {
			StringBuffer message = new StringBuffer();
			message.append("Connection creation exception: ").append(exception.getMessage());
			throw new DBMigrationException(message.toString(), exception);
		} catch (DBConnectionDriverLoadException exception) {
			StringBuffer message = new StringBuffer();
			message.append("DB driver loading exception: ").append(exception.getMessage());
			throw new DBMigrationException(message.toString(), exception);
		} catch (SQLException exception) {
			StringBuffer message = new StringBuffer();
			message.append("SQL exception: ").append(exception.getMessage());
			throw new DBMigrationException(message.toString(), exception);
		} finally {
			try {
				if(this.connection != null) {
					if(exceptionRaised) {
						this.connection.rollback();
						undoTableCreation();
					} else { 
						this.connection.commit();
					}
					this.connection.close();
				}
			} catch (SQLException exception) {
				// TODO: ignore this exception and see what you can do later.
			}
		}		
	}
	
	protected void connect() throws SQLException, DBConnectionDriverLoadException, DBConnectionCreateException {
		DBConnectionCreator destination = new DBConnectionCreator(this.configuration);
		this.connection = destination.getConnection();
		this.connection.setAutoCommit(false);
	}
	
	protected void createTables() throws SQLException {
		for(String element: this.tableCreateStatements) {
			Statement statement = this.connection.createStatement();
			statement.execute(element);	
			statement.close();
		}
	}
	abstract protected void generateStatements(final Database database) throws SQLException;
	abstract protected void undoTableCreation() throws SQLException;
}
