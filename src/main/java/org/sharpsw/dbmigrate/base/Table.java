package org.sharpsw.dbmigrate.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table implements Comparable<Table> {
	private String name;
	private List<Column> columns;
	private Map<String, List<ForeignKey>> foreignKeys;
	
	public Table() {
		this.name = "";
		this.columns = new ArrayList<Column>();
		this.foreignKeys = new HashMap<String, List<ForeignKey>>();
	}
	
	public Table(String name) {
		this.name = name;
		this.columns = new ArrayList<Column>();
		this.foreignKeys = new HashMap<String, List<ForeignKey>>();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	
	public void add(Column column) {		
		if(!this.columns.contains(column)) {
			this.columns.add(column);
		}
	}
	
	public void remove(Column column) {
		this.columns.remove(column);
	}
		
	public void add(ForeignKey fk) {
		List<ForeignKey> keys = null;
		if(this.foreignKeys.containsKey(fk.getPrimaryKeyTableName())) {
			keys = this.foreignKeys.get(fk.getPrimaryKeyTableName());
			keys.add(fk);
			this.foreignKeys.put(fk.getPrimaryKeyTableName(), keys);
		} else {
			keys = new ArrayList<ForeignKey>();
			keys.add(fk);
			this.foreignKeys.put(fk.getPrimaryKeyTableName(), keys);
		}
	}
	
	public List<Column> getColumns() {
		return this.columns;
	}
	
	public Map<String, List<ForeignKey>> getForeignKeys() {
		return this.foreignKeys;
	}
		
	public boolean hasForeignKeys() {
		if(this.foreignKeys.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public boolean isPrimaryKey(final String name) {
		boolean result = false;
		return result;
	}
			
	public boolean equals(Object other) {
		if(this == other) {
			return true;
		}
		
		if(!other.getClass().equals(this.getClass())) {
			return false;
		}
		
		Table instance = (Table) other;
		return this.name.equals(instance.name);
	}

	@Override
	public int compareTo(Table other) {
		return this.getName().compareTo(other.getName());
	}
}
