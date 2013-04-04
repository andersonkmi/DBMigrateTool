package org.sharpsw.dbmt.config;

public enum DatabaseVendor {
	ORACLE_11G("Oracle 11G"),
	MSSQLSERVER_2012("Microsoft SQL Server 2012"),
	MSACCESS("Microsoft Access"),
	POSTGRESQL("PostgreSQL 9"),
	HYPERSQL("Hypersonic Database "),
	MYSQL("MySQL Server"),
	H2("H2 database server"),
	XML("Flat XML");
	
	private String description;
	
	DatabaseVendor(final String descr) {
		this.description = descr;
	}
	
	public String getDescription() {
		return this.description;
	}
}
