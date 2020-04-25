package org.codecraftlabs.neptune.data;

public class PrimaryKey {
	private String columnName;
	private short position;
	
	public void setColumnName(final String name) {
		columnName = name;
	}
	
	public String getColumnName() {
		return this.columnName;
	}
	
	public void setPosition(short position) {
		this.position = position;
	}
	
	public short getPosition() {
		return position;
	}
}
