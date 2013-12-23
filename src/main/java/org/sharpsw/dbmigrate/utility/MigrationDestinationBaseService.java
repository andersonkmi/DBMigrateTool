package org.sharpsw.dbmigrate.utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.sharpsw.dbmigrate.config.DatabaseConfig;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionCreateException;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionCreator;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionDriverLoadException;
import org.sharpsw.dbmigrate.data.Database;

abstract class MigrationDestinationBaseService {
	protected DatabaseConfig configuration;
	protected List<String> tableCreateStatements;
	protected List<String> fkStatements;
	protected List<String> pkStatements;
	protected List<String> autoIncrementStatements;
	protected List<String> selectStatements;
	protected Connection connection;

	@SuppressWarnings("unused")
	private MigrationDestinationBaseService() {
		
	}
	
	MigrationDestinationBaseService(DatabaseConfig configuration) {
		this.configuration = configuration;
	}
	
	public void migrate(final Database database) throws DBMigrationException {
		boolean exceptionRaised = true;
		try {
			connect();			
			generateStatements(database);
			createTables();
			exceptionRaised = false;
		} catch (DatabaseConnectionCreateException exception) {
			StringBuffer message = new StringBuffer();
			message.append("Connection creation exception: ").append(exception.getMessage());
			throw new DBMigrationException(message.toString(), exception);
		} catch (DatabaseConnectionDriverLoadException exception) {
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
	
	protected void connect() throws SQLException, DatabaseConnectionDriverLoadException, DatabaseConnectionCreateException {
		DatabaseConnectionCreator destination = new DatabaseConnectionCreator(this.configuration);
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
