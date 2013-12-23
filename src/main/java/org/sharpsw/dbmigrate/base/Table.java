package org.sharpsw.dbmigrate.base;

import java.util.LinkedList;
import java.util.List;

public class Table implements Comparable<Table> {
	private String name;
	private List<Column> columns;
	
	public Table() {
		this.name = "";
		this.columns = new LinkedList<Column>();
	}
	
	public Table(String name) {
		this.name = name;
		this.columns = new LinkedList<Column>();
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
			
	public List<Column> getColumns() {
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
