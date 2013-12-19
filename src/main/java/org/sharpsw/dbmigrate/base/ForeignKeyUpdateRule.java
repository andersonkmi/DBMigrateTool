package org.sharpsw.dbmigrate.base;

public enum ForeignKeyUpdateRule {
	IMPORTED_KEY_NO_ACTION(0, "No action"),
	IMPORTED_KEY_CASCADE(1, "Cascade"),
	IMPORTED_KEY_SET_NULL(2, "Set null"),
	IMPORTED_KEY_SET_DEFAULT(3, "Set default"),
	IMPORTED_KEY_RESTRICT(4, "Restrict");
	
	private Integer code;
	private String description;
	
	private ForeignKeyUpdateRule(final Integer code, final String description) {
		this.code = code;
		this.description = description;
	}
	
	public Integer getCode() {
		return this.code;
	}
	
	public String getDescription() {
		return this.description;
	}
}
