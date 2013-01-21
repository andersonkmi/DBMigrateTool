package org.sharpsw.dbmt.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sharpsw.dbmt.base.Database;
import org.sharpsw.dbmt.config.IDatabaseConfiguration;
import org.sharpsw.dbmt.support.IDMLGenerator;
import org.sharpsw.dbmt.support.OracleDDLVisitor;
import org.sharpsw.dbmt.support.OracleDMLGeneratorImpl;
import org.sharpsw.dbmt.support.TableVisitorProtocol;

class MigrationDestinationOracleService extends MigrationDestinationBaseService {
	private List<String> tablesToDrop;
	
	MigrationDestinationOracleService(IDatabaseConfiguration configuration) {
		super(configuration);
		this.tablesToDrop = new ArrayList<String>();
	}
	
	@Override
	protected void generateStatements(Database database) throws SQLException {
		TableVisitorProtocol.Visitor visitor = new OracleDDLVisitor();
		IDMLGenerator dmlGenerator = new OracleDMLGeneratorImpl();		
		database.accept(visitor);
		this.tableCreateStatements = visitor.getTableCreateStatements();
		this.fkStatements = visitor.getForeignKeyCreateStatements();
		this.pkStatements = visitor.getPrimaryKeyCreateStatements();
		this.autoIncrementStatements = visitor.getAutoIncrementCreateStatements();
		
		dmlGenerator.parse(database);
		this.selectStatements = dmlGenerator.getSelectStatements();
	}

	@Override
	protected void undoTableCreation() throws SQLException {
		StringBuffer select = new StringBuffer();
		select.append("SELECT TABLE_NAME FROM USER_TABLES");
		Statement statement = this.connection.createStatement();
		ResultSet result = statement.executeQuery(select.toString());
		
		while(result.next()) {
			String tableName = result.getString("TABLE_NAME");
			this.tablesToDrop.add(tableName);
		}
		result.close();
		
		for(String table: this.tablesToDrop) {
			try {
				StringBuffer dropStatementString = new StringBuffer();
				dropStatementString.append("DROP TABLE ").append(table);
				Statement dropStatement = this.connection.createStatement();
				dropStatement.execute(dropStatementString.toString());
				dropStatement.close();
			} catch (SQLException dropExc) {
				// Nothing is performed here.
			}
		}
	}

}
