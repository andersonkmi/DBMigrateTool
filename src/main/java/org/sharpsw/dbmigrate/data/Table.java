package org.sharpsw.dbmigrate.data;

import java.util.LinkedHashSet;
import java.util.Set;

public class Table implements Comparable<Table> {
	private String name;
	private Set<Column> columns;
	
	public Table() {
		this.name = "";
		this.columns = new LinkedHashSet<Column>();
	}
	
	public Table(String name) {
		this.name = name;
		this.columns = new LinkedHashSet<Column>();
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
			
	public Set<Column> getColumns() {
		return this.columns;
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
