package org.sharpsw.dbmigrate.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.sharpsw.dbmigrate.data.Column;
import org.sharpsw.dbmigrate.data.Database;
import org.sharpsw.dbmigrate.data.Table;

public class OracleDMLGeneratorImpl implements IDMLGenerator {
	public static final int TABLE_NAME_MAX_SIZE = 30;
	private List<String> selectStatements;
	
	public OracleDMLGeneratorImpl() {
		this.selectStatements = new ArrayList<String>();
	}
	
	public List<String> getSelectStatements() {
		return this.selectStatements;
	}
	
	public void parse(final Database database) {
		List<Table> tables = database.getTables();
		for(Table table: tables) {
			parse(table);
		}
	}
	
	private void parse(final Table table) {
		if(table.getName().length() <= OracleDMLGeneratorImpl.TABLE_NAME_MAX_SIZE) {
			List<Column> columns = table.getColumns();
			int size = columns.size();
			int counter = 0;
			
			StringBuffer buffer = new StringBuffer();
			for(Column column: columns) {
				counter++;
				buffer.append(column.getName());
				if(counter < size) {
					buffer.append(", ");
				}
			}
			
			StringBuffer statement = new StringBuffer();
			statement.append("SELECT ").append(buffer).append(" FROM ").append(table.getName());
			this.selectStatements.add(statement.toString());
		}
	}
}
