package org.sharpsw.dbmigrate.base;

public class PrimaryKey {
	private short position;
	private String column;
	
	@SuppressWarnings("unused")
	private PrimaryKey() {
		
	}
	public PrimaryKey(String column) {
		this.column = column;
		this.position = 0;
	}
	
	public PrimaryKey(short position, String column) {
		this.position = position;
		this.column = column;
	}
	
	public short getPosition() {
		return this.position;
	}
	
	public String getColumn() {
		return this.column;
	}
	
	public int hashCode() {
		return this.column.hashCode();
	}
	
	public boolean equals(Object other) {
		if(this == other) {
			return true;
		}
		
		if(!other.getClass().equals(this.getClass())) {
			return false;
		}
		
		PrimaryKey instance = (PrimaryKey) other;
		return this.column.equals(instance.column);
	}	
}
