package org.codecraftlabs.neptune.data;

import java.sql.JDBCType;

public class Column implements Cloneable {
	private String name;
	private boolean isAutoIncrement;
	private boolean isNullable;
	private JDBCType dataType;
	private String defaultValue;
	private int length;
	private int precision;
	private int position;
	private boolean isPrimaryKey;
	private short pkPosition;
	private boolean isForeignKey;
	private ForeignKey foreignKey;
	
	public Column(String name) {
		this.name = name;
		this.isAutoIncrement = false;
		this.isNullable = false;
		this.defaultValue = "";
		this.length = 0;
		this.precision = 0;
		this.position = 0;
		this.isPrimaryKey = false;
		this.pkPosition = 0;
		this.isForeignKey = false;
	}
		
	public Column(final Column column) {
		this.name = column.getName();
		this.isAutoIncrement = column.isAutoIncrement();
		this.isNullable = column.isNullable();
		this.dataType = column.getDataType();
		this.defaultValue = column.getDefaultValue();
		this.length = column.getLength();
		this.precision = column.getPrecision();
		this.position = column.getPosition();	
		this.isPrimaryKey = column.isPrimaryKey();
		this.pkPosition = column.getPrimaryKeyPosition();
		this.isForeignKey = column.isForeignKey();
		this.foreignKey = column.getForeignKey();
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
			
	public boolean isAutoIncrement() {
		return this.isAutoIncrement;
	}
	
	public void setIsAutoIncrement(boolean flag) {
		this.isAutoIncrement = flag;
	}
	
	public void setIsNullable(boolean flag) {
		this.isNullable = flag;
	}
	
	public boolean isNullable() {
		return this.isNullable;
	}
	
	public JDBCType getDataType() {
		return this.dataType;
	}
	
	public void setDataType(JDBCType type) {
		this.dataType = type;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public String getDefaultValue() {
		return this.defaultValue;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public int getPrecision() {
		return this.precision;
	}
	
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getPosition() {
		return this.position;
	}
	
	public void setIsPrimaryKey(boolean flag) {
		this.isPrimaryKey = flag;
	}
	
	public boolean isPrimaryKey() {
		return this.isPrimaryKey;
	}
	
	public void setPrimaryKeyPosition(short position) {
		this.pkPosition = position;
	}
	
	public short getPrimaryKeyPosition() {
		return this.pkPosition;
	}
	
	public void setIsForeignKey(boolean flag) {
		this.isForeignKey = flag;
	}
	
	public boolean isForeignKey() {
		return this.isForeignKey;
	}
	
	public void setForeignKey(final ForeignKey fk) {
		this.foreignKey = fk;
	}
	
	public ForeignKey getForeignKey() {
		return this.foreignKey;
	}

	@Override
	public boolean equals(Object other) {
		if(this == other) {
			return true;
		}
		
		if(!other.getClass().equals(this.getClass())) {
			return false;
		}
		
		Column instance = (Column) other;
		return this.name.equals(instance.name) &&
		       this.isAutoIncrement == instance.isAutoIncrement &&
		       this.isNullable == instance.isNullable &&
		       this.dataType == instance.dataType &&
		       this.defaultValue.equals(instance.defaultValue) &&
		       this.length == instance.length &&
		       this.precision == instance.precision &&
		       this.position == instance.position;
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}
