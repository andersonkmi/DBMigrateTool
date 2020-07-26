package org.codecraftlabs.neptune.data;

public enum ForeignKeyDeleteRule {
	IMPORTED_KEY_NO_ACTION((short) 0, "No action"),
	IMPORTED_KEY_CASCADE((short) 1, "Cascade"),
	IMPORTED_KEY_SET_NULL((short) 2, "Set null"),
	IMPORTED_KEY_SET_DEFAULT((short) 3, "Set default"),
	IMPORTED_KEY_RESTRICT((short) 4, "Restrict");
	
	private final Short code;
	private final String description;
	
	ForeignKeyDeleteRule(final Short code, final String description) {
		this.code = code;
		this.description = description;
	}
	
	public Short getCode() {
		return this.code;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public static ForeignKeyDeleteRule findById(short id) {
		if(id == IMPORTED_KEY_NO_ACTION.getCode()) {
			return IMPORTED_KEY_NO_ACTION;
		} else if(id == IMPORTED_KEY_CASCADE.getCode()) {
			return IMPORTED_KEY_CASCADE;
		} else if(id == IMPORTED_KEY_RESTRICT.getCode()) {
			return IMPORTED_KEY_RESTRICT;
		} else if(id == IMPORTED_KEY_SET_DEFAULT.getCode()) {
			return IMPORTED_KEY_SET_DEFAULT;
		} else if(id == IMPORTED_KEY_SET_NULL.getCode()) {
			return IMPORTED_KEY_SET_NULL;
		} else {
			return IMPORTED_KEY_NO_ACTION;
		}
	}
}
