package org.codecraftlabs.neptune.data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.codecraftlabs.neptune.config.DatabaseConfig;
import org.codecraftlabs.neptune.connectivity.ConnectionFactoryException;
import org.codecraftlabs.neptune.connectivity.ConnectionFactory;
import org.codecraftlabs.neptune.connectivity.DatabaseDriverLoadException;

import javax.annotation.Nonnull;

public class DatabaseSchemaParser {
	private static final Logger logger = Logger.getLogger(DatabaseSchemaParser.class);
    private final ConnectionFactory dbConnectionCreator;
		
	public DatabaseSchemaParser(final ConnectionFactory dbConnectionCreator) {
		this.dbConnectionCreator = dbConnectionCreator;
	}
	
	public Database load(final DatabaseConfig configuration) throws DatabaseSchemaParseException {
		if(configuration == null) {
			logger.error("The configuration information is null");
			throw new DatabaseSchemaParseException("Database configuration provided is null");
		}

		logger.info("Starting the schema parsing process");
		try (Connection connection = createDatabaseConnection(configuration)) {
			try {
				Database database = configureDatabaseInfo(configuration, connection.getMetaData());
				List<String> schemas = generateSchemaList(connection.getMetaData());
				Map<String, List<String>> tablesPerSchema = new HashMap<>();
				for (String schema : schemas) {
					List<String> tables = generateTableList(connection.getMetaData(), schema);
					tablesPerSchema.put(schema, tables);
				}
				processTables(database, tablesPerSchema, connection.getMetaData());
				return database;							
			} catch (SQLException exception) {
				throw new DatabaseSchemaParseException(String.format("Error when loading database information: %s", exception.getMessage()), exception);
			}
		} catch (SQLException exception) {
			logger.error(String.format("Error when closing the database connection: %s", exception.getMessage()), exception);
			throw new DatabaseSchemaParseException(String.format("Error when closing database connection: %s", exception.getMessage()), exception);
		}
    }
	
	private Connection createDatabaseConnection(final DatabaseConfig config) throws DatabaseSchemaParseException {
		logger.info("Creating database connection");

		try {
			if(this.dbConnectionCreator == null) {
				throw new DatabaseSchemaParseException("Database connection factory instance is null");
			}
			return this.dbConnectionCreator.getConnection(config);
		} catch (ConnectionFactoryException exception) {
			throw new DatabaseSchemaParseException(String.format("Error when connecting to the database: %s", exception.getMessage()), exception);
		} catch (DatabaseDriverLoadException exception) {
			throw new DatabaseSchemaParseException(String.format("Error when loading the database driver: %s", exception.getMessage()), exception);
		}
	}
	
	private Database configureDatabaseInfo(final DatabaseConfig config, final DatabaseMetaData metadata) throws DatabaseSchemaParseException {
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

	@Nonnull
	private List<String> generateSchemaList(@Nonnull final DatabaseMetaData metadata) throws DatabaseSchemaParseException {
		logger.info("Generating schema list");

		List<String> schemas = new ArrayList<>();
		try (ResultSet rs = metadata.getSchemas()) {
			while(rs.next()) {
				String schemaName = rs.getString("TABLE_SCHEM");
				schemas.add(schemaName);
			}
			return schemas;
		} catch (SQLException exception) {
			throw new DatabaseSchemaParseException("Error when listing schemas", exception);
		}
	}
	
	private List<String> generateTableList(final DatabaseMetaData metadata, @Nonnull final String schema) throws DatabaseSchemaParseException {
		logger.info("Generating tables list");

		List<String> tables = new ArrayList<>();
		String[] types = { "TABLE" };
		try (ResultSet rs = metadata.getTables(null, schema, "%", types)) {
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
	
	private void processTables(final Database database, final Map<String, List<String>> tablesPerSchema, final DatabaseMetaData metadata) throws DatabaseSchemaParseException {
		logger.info("Processing tables per schema");

		for (Map.Entry<String, List<String>> item : tablesPerSchema.entrySet()) {
			Schema schema = new Schema(item.getKey());
			for(String tableName : item.getValue()) {
				Table table = new Table(tableName);
				generateColumnList(table, metadata);
				schema.add(table);
			}
			database.add(schema);
		}
	}
	
	private void generateColumnList(final Table table, final DatabaseMetaData metadata) throws DatabaseSchemaParseException {
		logger.info("Generating column list");

		try (ResultSet rs = metadata.getColumns(null, null, table.getName(), "%")){			
			Map<String, PrimaryKey> primaryKeys = this.getPrimaryKeys(table, metadata);
			Map<String, ForeignKey> foreignKeys = this.getForeignKeys(table, metadata);
						
			while(rs.next()) {				
				Column column = new Column("");
				configureColumnName(column, rs);
				configureColumnDataType(column, rs);
				configureColumnSize(column, rs);
				configureColumnDecimalDigits(column, rs);								
				configureNullableColumn(column, rs);
				configureColumnDefaultValue(column, rs);
				configureColumnPosition(column, rs);
				configureColumnAutoIncrement(column, rs);
				configureColumnPrimaryKey(column, primaryKeys);
				configureColumnForeignKey(column, foreignKeys);
				table.add(column);
			}
		} catch (SQLException exception) {
			throw new DatabaseSchemaParseException("Error when listing the columns for table: '" + table + "'", exception);
		}
	}
	
	private void configureColumnName(Column column, ResultSet rs) throws SQLException {
		String name = rs.getString("COLUMN_NAME");
		column.setName(name);
	}
	
	private void configureColumnDataType(Column column, ResultSet rs) throws SQLException {
		int type = rs.getInt("DATA_TYPE");
		column.setDataType(JDBCType.valueOf(type));
	}
	
	private void configureColumnSize(Column column, ResultSet rs) throws SQLException {
		int size = rs.getInt("COLUMN_SIZE");
		column.setLength(size);		
	}
	
	private void configureColumnDecimalDigits(Column column, ResultSet rs) throws SQLException {
		BigDecimal decimalDigits = rs.getBigDecimal("DECIMAL_DIGITS");		
		if(decimalDigits != null) {
			column.setPrecision(decimalDigits.intValue());
		}
	}
	
	private void configureNullableColumn(Column column, ResultSet rs) throws SQLException {
		String nullableValue = rs.getString("IS_NULLABLE");
		int nullable = rs.getInt("NULLABLE");

		column.setIsNullable(nullable == DatabaseMetaData.columnNullable || nullableValue.equalsIgnoreCase("yes"));
	}
	
	private void configureColumnDefaultValue(Column column, ResultSet rs) throws SQLException {
		String defaultValue = rs.getString("COLUMN_DEF");
		column.setDefaultValue(Objects.requireNonNullElse(defaultValue, "null"));
	}
	
	private void configureColumnPosition(Column column, ResultSet rs) throws SQLException {
		int position = rs.getInt("ORDINAL_POSITION");
		column.setPosition(position);		
	}
		
	private void configureColumnAutoIncrement(Column column, ResultSet rs) throws SQLException {
		String typeName = rs.getString("TYPE_NAME");
		String isAutoIncrement = rs.getString("IS_AUTOINCREMENT");
		
		if (isAutoIncrement != null && isAutoIncrement.equals("YES")) {
			column.setIsAutoIncrement(true);
		} else column.setIsAutoIncrement(typeName != null && (typeName.contains("identity") || typeName.contains("COUNTER")));
	}
	
	private void configureColumnPrimaryKey(Column column, Map<String, PrimaryKey> primaryKeys) {
		if(primaryKeys.containsKey(column.getName())) {
			PrimaryKey primaryKey = primaryKeys.get(column.getName());
			column.setIsPrimaryKey(true);
			column.setPrimaryKeyPosition(primaryKey.getPosition());
		}		
	}
	
	private void configureColumnForeignKey(Column column, Map<String, ForeignKey> foreignKeys) {
		if(foreignKeys.containsKey(column.getName())) {
			ForeignKey fk = foreignKeys.get(column.getName());
			column.setIsForeignKey(true);
			column.setForeignKey(fk);
		}		
	}
	
	private Map<String, PrimaryKey> getPrimaryKeys(Table table, final DatabaseMetaData metadata) throws SQLException {
		Map<String, PrimaryKey> keys = new LinkedHashMap<>();
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
		Map<String, ForeignKey> keys = new LinkedHashMap<>();
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
				fk.setName(table.getName() + "_FK_" + counter);
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
