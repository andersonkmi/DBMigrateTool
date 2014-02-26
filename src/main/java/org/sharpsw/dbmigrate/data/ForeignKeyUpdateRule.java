package org.sharpsw.dbmigrate.data;

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
	
	public static ForeignKeyUpdateRule findById(short id) {
		Integer ruleId = new Integer(id);
		if(ruleId.equals(IMPORTED_KEY_NO_ACTION.getCode())) {
			return IMPORTED_KEY_NO_ACTION;
		} else if(ruleId.equals(IMPORTED_KEY_CASCADE.getCode())) {
			return IMPORTED_KEY_CASCADE;
		} else if(ruleId.equals(IMPORTED_KEY_RESTRICT.getCode())) {
			return IMPORTED_KEY_RESTRICT;
		} else if(ruleId.equals(IMPORTED_KEY_SET_DEFAULT.getCode())) {
			return IMPORTED_KEY_SET_DEFAULT;
		} else if(ruleId.equals(IMPORTED_KEY_SET_NULL.getCode())) {
			return IMPORTED_KEY_SET_NULL;
		} else {
			return IMPORTED_KEY_NO_ACTION;
		}
	}
}
