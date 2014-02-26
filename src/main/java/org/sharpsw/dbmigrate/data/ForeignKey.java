package org.sharpsw.dbmigrate.data;

public class ForeignKey {
	private String name;
	private String primaryKeyTableName;
	private String primaryKeyColumnName;
	private String foreignKeyColumnName;
	private ForeignKeyUpdateRule updateRule;
	private ForeignKeyDeleteRule deleteRule;
	private int keySequence;
	
	public ForeignKey() {
		this.name = "";
		this.primaryKeyColumnName = "";
		this.primaryKeyTableName = "";
		this.foreignKeyColumnName = "";
		this.updateRule = ForeignKeyUpdateRule.IMPORTED_KEY_NO_ACTION;
		this.deleteRule = ForeignKeyDeleteRule.IMPORTED_KEY_NO_ACTION;
		this.keySequence = 0;
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
	
	public void setDeleteRule(ForeignKeyDeleteRule rule) {
		this.deleteRule = rule;
	}
	
	public void setUpdateRule(ForeignKeyUpdateRule rule) {
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
	
	public ForeignKeyDeleteRule getDeleteRule() {
		return this.deleteRule;
	}
	
	public ForeignKeyUpdateRule getUpdateRule() {
		return this.updateRule;
	}
	
	public void setKeySequence(int sequence) {
		this.keySequence = sequence;
	}
	
	public int getKeySequence() {
		return this.keySequence;
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
