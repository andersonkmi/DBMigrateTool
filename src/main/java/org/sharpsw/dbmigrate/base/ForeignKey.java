package org.sharpsw.dbmigrate.base;

public class ForeignKey {
	private String name;
	private String primaryKeyTableName;
	private String primaryKeyColumnName;
	private String foreignKeyColumnName;
	private int updateRule;
	private int deleteRule;
	
	public ForeignKey() {
		this.name = "";
		this.primaryKeyColumnName = "";
		this.primaryKeyTableName = "";
		this.foreignKeyColumnName = "";
		this.updateRule = 0;
		this.deleteRule = 0;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPrimaryKeyTableName(String name) {
		this.primaryKeyTableName = name;
	}
	
	public void setPrimaryKeyColumnName(String name) {
		this.primaryKeyColumnName = name;
	}
	
	public void setForeignKeyColumnName(String name) {
		this.foreignKeyColumnName = name;
	}
	
	public void setDeleteRule(int rule) {
		this.deleteRule = rule;
	}
	
	public void setUpdateRule(int rule) {
		this.updateRule = rule;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getPrimaryKeyTableName() {
		return this.primaryKeyTableName;
	}
	
	public String getPrimaryKeyColumnName() {
		return this.primaryKeyColumnName;
	}
	
	public String getForeignKeyColumnName() {
		return this.foreignKeyColumnName;
	}
	
	public int getDeleteRule() {
		return this.deleteRule;
	}
	
	public int getUpdateRule() {
		return this.updateRule;
	}
	
	public boolean equals(Object other) {
		if(this == other) {
			return true;
		}
		
		if(!other.getClass().equals(this.getClass())) {
			return false;
		}
		
		ForeignKey instance = (ForeignKey) other;
		return this.name.equals(instance.name);
	}	
	
	public int hashCode() {
		int value = this.name.hashCode();
		return value;
	}
}
