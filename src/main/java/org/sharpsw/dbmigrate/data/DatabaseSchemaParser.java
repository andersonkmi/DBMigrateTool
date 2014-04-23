package org.sharpsw.dbmigrate.data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.sharpsw.dbmigrate.config.DatabaseConfig;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionFactoryException;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionFactory;
import org.sharpsw.dbmigrate.connectivity.DatabaseConnectionDriverLoadException;

public class DatabaseSchemaParser {
    private DatabaseConnectionFactory dbConnectionCreator;
	
	public DatabaseSchemaParser() {
		this.dbConnectionCreator = new DatabaseConnectionFactory();
	}
	
	public DatabaseSchemaParser(final DatabaseConnectionFactory dbConnectionCreator) {
		this.dbConnectionCreator = dbConnectionCreator;
	}
	
	public Database load(final DatabaseConfig configuration) throws DatabaseSchemaParseException {
		try (Connection connection = this.createDatabaseConnection(configuration)) {
			try {
				Database database = this.configureDatabaseInfo(configuration, connection.getMetaData());
				List<String> tables = generateTableList(connection.getMetaData());
				this.processTables(database, tables, connection.getMetaData());
				return database;							
			} catch (SQLException exception) {
				throw new DatabaseSchemaParseException(String.format("Error when loading database information: %s", exception.getMessage()), exception);
			}
		} catch (SQLException exception) {
			throw new DatabaseSchemaParseException(String.format("Error when closing database connection: %s", exception.getMessage()), exception);
		}
    }
	
	private Connection createDatabaseConnection(final DatabaseConfig config) throws DatabaseSchemaParseException {
		try {
			Connection connection = this.dbConnectionCreator.getConnection(config);
			return connection;
		} catch (DatabaseConnectionFactoryException exception) {
			throw new DatabaseSchemaParseException(String.format("Error when connecting to the database: %s", exception.getMessage()), exception);
		} catch (DatabaseConnectionDriverLoadException exception) {
			throw new DatabaseSchemaParseException(String.format("Error when loading the database driver: %s", exception.getMessage()), exception);
		}
	}
	
	private Database configureDatabaseInfo(final DatabaseConfig config, DatabaseMetaData metadata) throws DatabaseSchemaParseException {
		try {
			Database database = new Database(config.getDatabase());
			String databaseProductName = metadata.getDatabaseProductName();
			String databaseProductVersion = metadata.getDatabaseProductVersion();
			int majorJDBCVersion = metadata.getJDBCMajorVersion();
			int minorJDBCVersion = metadata.getJDBCMinorVersion();
			
			database.setMajorJDBCVersion(majorJDBCVersion);
			database.setMinorJDBCVersion(minorJDBCVersion);
			int majorVersion = metadata.getDatabaseMajorVersion();
			int minorVersion = metadata.getDatabaseMinorVersion();
			database.setMajorVersion(majorVersion);
			database.setMinorVersion(minorVersion);
			database.setProductName(databaseProductName);
			database.setProductVersion(databaseProductVersion);
			return database;			
		} catch (SQLException exception) {
			throw new DatabaseSchemaParseException(String.format("Error when loading basic database information: %s", exception.getMessage()), exception);
		}
	}
	
	private List<String> generateTableList(final DatabaseMetaData metadata) throws DatabaseSchemaParseException {
		List<String> tables = new ArrayList<String>();
		String types[] = { "TABLE" };
		try (ResultSet rs = metadata.getTables(null, null, "%", types)) {
			while(rs.next()) {
				String name = rs.getString("TABLE_NAME");
				if(isTableNameValid(name)) {
					tables.add(name);
				} else {
					throw new DatabaseSchemaParseException(String.format("The table '%s' is not valid", name));
				}
			}
		} catch (SQLException exception) {
			throw new DatabaseSchemaParseException("Error when listing the tables", exception);
		}
		return tables;
	}
	
	private void processTables(final Database database, final List<String> tables, final DatabaseMetaData metadata) throws DatabaseSchemaParseException {
		for(String table : tables) {
			Table item = new Table(table);
			database.add(item);
			generateColumnList(item, metadata);
		}
	}
	
	private void generateColumnList(final Table table, final DatabaseMetaData metadata) throws DatabaseSchemaParseException {
		try (ResultSet rs = metadata.getColumns(null, null, table.getName(), "%")){			
			Map<String, PrimaryKey> primaryKeys = this.getPrimaryKeys(table, metadata);
			Map<String, ForeignKey> foreignKeys = this.getForeignKeys(table, metadata);
						
			while(rs.next()) {				
				String name = rs.getString("COLUMN_NAME");
				int type = rs.getInt("DATA_TYPE");
				int size = rs.getInt("COLUMN_SIZE");
				BigDecimal decimalDigits = rs.getBigDecimal("DECIMAL_DIGITS");
				int nullable = rs.getInt("NULLABLE");
				String defaultValue = rs.getString("COLUMN_DEF");
				int position = rs.getInt("ORDINAL_POSITION");
				String typeName = rs.getString("TYPE_NAME");
				String isNullable = rs.getString("IS_NULLABLE");
				
				String isAutoIncrement = "";
				try {
					isAutoIncrement = rs.getString("IS_AUTOINCREMENT");
				} catch (SQLException sqlExc) {
					StringBuffer message = new StringBuffer();
					message.append("Error when trying to read column 'IS_AUTOINCREMENT': ").append(sqlExc.getMessage());
				}
				
				Column column = new Column(name);
				column.setDataType(type);
				column.setLength(size);
				if(decimalDigits != null) {
					column.setPrecision(decimalDigits.intValue());
				}
				if(nullable == DatabaseMetaData.columnNullable || isNullable.equalsIgnoreCase("yes")) {
					column.setIsNullable(true);
				} else {
					column.setIsNullable(false);
				}
				
				if(defaultValue == null) {
					column.setDefaultValue("null");
				} else {
					column.setDefaultValue(defaultValue);
				}
				
				column.setPosition(position);
				
				if(isAutoIncrement.equals("YES")) {
					column.setIsAutoIncrement(true);
				} else if(typeName.contains("identity") || typeName.contains("COUNTER")) {
					column.setIsAutoIncrement(true);
				} else {
					column.setIsAutoIncrement(false);
				}

				if(primaryKeys.containsKey(name)) {
					PrimaryKey primaryKey = primaryKeys.get(name);
					column.setIsPrimaryKey(true);
					column.setPrimaryKeyPosition(primaryKey.getPosition());
				}
				
				if(foreignKeys.containsKey(name)) {
					ForeignKey fk = foreignKeys.get(name);
					column.setIsForeignKey(true);
					column.setForeignKey(fk);
				}
				table.add(column);
			}
		} catch (SQLException exception) {
			StringBuffer message = new StringBuffer();
			message.append("Error when listing the columns for table: '").append(table).append("'");
			throw new DatabaseSchemaParseException(message.toString(), exception);
		}
	}
	
	private Map<String, PrimaryKey> getPrimaryKeys(Table table, final DatabaseMetaData metadata) throws SQLException {
		Map<String, PrimaryKey> keys = new LinkedHashMap<String, PrimaryKey>();
		ResultSet pkResultSet = metadata.getPrimaryKeys(null, null, table.getName());
		while(pkResultSet.next()) {
			String name = pkResultSet.getString("COLUMN_NAME");
			short position = pkResultSet.getShort("KEY_SEQ");
			PrimaryKey key = new PrimaryKey();
			key.setColumnName(name);
			key.setPosition(position);
			keys.put(name, key);
		}
		pkResultSet.close();		
		return keys;
	}
	
	private Map<String, ForeignKey> getForeignKeys(final Table table, final DatabaseMetaData metadata) throws SQLException {
		Map<String, ForeignKey> keys = new LinkedHashMap<String, ForeignKey>();
		ResultSet rs = metadata.getImportedKeys(null, null, table.getName());
		int counter = 0;
		while(rs.next()) {
			String pkTableName = rs.getString("PKTABLE_NAME");
			String pkColumnName = rs.getString("PKCOLUMN_NAME");
			String fkColumnName = rs.getString("FKCOLUMN_NAME");
			String fkName = rs.getString("FK_NAME");
			short updateRule = rs.getShort("UPDATE_RULE");
			short deleteRule = rs.getShort("DELETE_RULE");
			short keySequence = rs.getShort("KEY_SEQ");
			
			ForeignKey fk = new ForeignKey();
			if(fkName != null && !fkName.isEmpty()) {
				fk.setName(fkName);
			} else {
				StringBuffer buffer = new StringBuffer();
				buffer.append(table.getName()).append("_FK_").append(counter);
				fk.setName(buffer.toString());
				counter++;
			}
			
			fk.setPrimaryKeyTableName(pkTableName);
			fk.setPrimaryKeyColumnName(pkColumnName);
			fk.setForeignKeyColumnName(fkColumnName);
			fk.setKeySequence(keySequence);
			fk.setDeleteRule(ForeignKeyDeleteRule.findById(deleteRule));
			fk.setUpdateRule(ForeignKeyUpdateRule.findById(updateRule));
			
			keys.put(fkName, fk);
		}
		rs.close();
		
		return keys;
	}
	
	boolean isTableNameValid(String name) {
		boolean result = true;
		if(name.contains(" ")) {
			result = false;
		}		
		return result;
	}
}
