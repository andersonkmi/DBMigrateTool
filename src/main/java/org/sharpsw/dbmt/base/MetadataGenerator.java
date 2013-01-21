package org.sharpsw.dbmt.base;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MetadataGenerator {
	private Connection connection;
	private DatabaseMetaData meta;
	private boolean isMSAccess;
	
	@SuppressWarnings("unused")
	private MetadataGenerator() {
		
	}
	
	public MetadataGenerator(Connection connection) {
		this.connection = connection;
	}
	
	public Database generate() throws MetadataGenException {
		try {
			Database database = new Database("");
			this.meta = this.connection.getMetaData();
			String databaseProductName = this.meta.getDatabaseProductName();
			String databaseProductVersion = this.meta.getDatabaseProductVersion();
			int majorJDBCVersion = this.meta.getJDBCMajorVersion();
			int minorJDBCVersion = this.meta.getJDBCMinorVersion();
			
			database.setMajorJDBCVersion(majorJDBCVersion);
			database.setMinorJDBCVersion(minorJDBCVersion);
			try {
				int majorVersion = this.meta.getDatabaseMajorVersion();
				int minorVersion = this.meta.getDatabaseMinorVersion();
				database.setMajorVersion(majorVersion);
				database.setMinorVersion(minorVersion);
			} catch (UnsupportedOperationException opExc) {
				// TODO: Ignore such exception, but it will provide some kind of treatment later on.
			}			
			database.setProductName(databaseProductName);
			database.setProductVersion(databaseProductVersion);
			
			checkDatabaseProduct(database);
			generateTableList(database);
			return database;
		} catch (SQLException exception) {
			StringBuffer message = new StringBuffer();
			message.append("SQL exception raised when generating database metada.");
			throw new MetadataGenException(message.toString(), exception);
		} finally {
			if(this.connection != null) {
				try {
					this.connection.close();					
				} catch (SQLException exception) {
					StringBuffer message = new StringBuffer();
					message.append("Error when closing the database connection.");
					throw new MetadataGenException(message.toString(), exception);
				}
			}
		}
	}
	
	protected DatabaseMetaData getMetadata() {
		return this.meta;
	}
		
	private void checkDatabaseProduct(Database database) {
		if(database.getProductName().equalsIgnoreCase("Access")) {
			this.isMSAccess = true;
		}
	}
	
	private void generateTableList(Database database) throws MetadataGenException {
		String types[] = { "TABLE" };
		ResultSet rs = null;
		try {
			rs = this.meta.getTables(null, null, "%", types);
			while(rs.next()) {
				String name = rs.getString("TABLE_NAME");
				if(isTableNameValid(name)) {
					Table table = new Table(name);
					generateColumnList(table);
					getForeignKeys(table);
					database.add(table);
				} else {
					StringBuffer message = new StringBuffer();
					message.append("The table '").append(name).append("' is not valid.");
					throw new MetadataGenException(message.toString());
				}
			}
		} catch (SQLException exception) {
			StringBuffer message = new StringBuffer();
			message.append("Error when listing the tables.");
			throw new MetadataGenException(message.toString(), exception);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				} 
			} catch (SQLException exception) {
				StringBuffer message = new StringBuffer();
				message.append("Error when trying to close the table result set.");
				throw new MetadataGenException(message.toString(), exception);
			}
		}
	}
	
	private void generateColumnList(Table table) throws MetadataGenException {
		ResultSet rs = null;
		try {			
			this.getPrimaryKeys(table);
			
			rs = this.meta.getColumns(null, null, table.getName(), "%");
			
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
				table.add(column);
			}
		} catch (SQLException exception) {
			StringBuffer message = new StringBuffer();
			message.append("Error when listing the columns for table: '").append(table).append("'");
			throw new MetadataGenException(message.toString(), exception);
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException exception) {
					StringBuffer message = new StringBuffer();
					message.append("Error when trying to close the column result set.");
					throw new MetadataGenException(message.toString(), exception);
				}
			}
		}
	}
	
	private void getPrimaryKeys(Table table) throws SQLException {
		if (this.isMSAccess) {			
			String columnName = "";
			short position = -1;
			String tableName = table.getName();
			
			ResultSet rset = this.meta.getIndexInfo(null, null, table.getName(), true, true);
			while (rset.next()) {
				String idx = rset.getString("INDEX_NAME");
				if (idx != null) {
					if (idx.equalsIgnoreCase("PrimaryKey") || 
					    idx.contains(tableName)) {
						columnName = rset.getString("COLUMN_NAME");
						position = rset.getShort("ORDINAL_POSITION");						
						PrimaryKey key = new PrimaryKey(position, columnName);
						table.add(key);
					}
				}
			}
			rset.close();
		} else {			
			ResultSet pkResultSet = this.meta.getPrimaryKeys(null, null, table.getName());
			while(pkResultSet.next()) {
				String name = pkResultSet.getString("COLUMN_NAME");
				short position = pkResultSet.getShort("KEY_SEQ");				
				PrimaryKey key = new PrimaryKey(position, name);
				table.add(key);
			}
			pkResultSet.close();
		}
	}
	
	private void getForeignKeys(Table table) throws SQLException {
		if(this.isMSAccess) {
			Connection connection = this.meta.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT szColumn, szReferencedObject, szReferencedColumn, szRelationship from MSysRelationships WHERE szObject = '").append(table.getName()).append("'");
			try {
				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery(buffer.toString());
				while(result.next()) {
					String columnName = result.getString("szColumn");
					String referencedTable = result.getString("szReferencedObject");
					String referencedColumn = result.getString("szReferencedColumn");
					String fkName = result.getString("szRelationship");
					
					ForeignKey fk = new ForeignKey();
					fk.setName(fkName);
					fk.setForeignKeyColumnName(columnName);
					fk.setPrimaryKeyColumnName(referencedColumn);
					fk.setPrimaryKeyTableName(referencedTable);
					fk.setDeleteRule(DatabaseMetaData.importedKeyCascade);
					fk.setUpdateRule(DatabaseMetaData.importedKeyCascade);					
					table.add(fk);
				}
				result.close();
			} catch (SQLException exception) {
				// TODO: handle this exception later
			}
		} else {
			ResultSet rs = this.meta.getImportedKeys(null, null, table.getName());
			int counter = 0;
			while(rs.next()) {
				String pkTableName = rs.getString("PKTABLE_NAME");
				String pkColumnName = rs.getString("PKCOLUMN_NAME");
				String fkColumnName = rs.getString("FKCOLUMN_NAME");
				String fkName = rs.getString("FK_NAME");
				short updateRule = rs.getShort("UPDATE_RULE");
				short deleteRule = rs.getShort("DELETE_RULE");
				
				ForeignKey fk = new ForeignKey();
				if(fkName != null && !fkName.isEmpty()) {
					fk.setName(fkName);
				} else {
					StringBuffer buffer = new StringBuffer();
					buffer.append("Fake_FK").append(counter);
					fk.setName(buffer.toString());
					counter++;
				}
				
				fk.setPrimaryKeyTableName(pkTableName);
				fk.setPrimaryKeyColumnName(pkColumnName);
				fk.setForeignKeyColumnName(fkColumnName);
				fk.setDeleteRule(deleteRule);
				fk.setUpdateRule(updateRule);			
				table.add(fk);
			}
			rs.close();
		}
	}
	
	boolean isTableNameValid(String name) {
		boolean result = true;
		if(this.isMSAccess) {
			if(name.startsWith("MSys")) {
				result = false;
			}
		}
		if(name.contains(" ")) {
			result = false;
		}
		
		return result;
	}
}
