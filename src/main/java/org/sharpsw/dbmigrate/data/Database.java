package org.sharpsw.dbmigrate.data;

import java.util.*;

public class Database {
    private String productName;
    private String productVersion;
    private int majorVersion;
    private int minorVersion;
    private int majorJDBCVersion;
    private int minorJDBCVersion;
    private String schema;
    private List<Table> tables;

    public Database(final String schema) {
        this.schema = schema;
        this.tables = new ArrayList<Table>();
    }

    public void setSchema(String name) {
        this.schema = name;
    }

    public String getSchema() {
        return this.schema;
    }

    public void setProductName(String name) {
        this.productName = name;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductVersion(String version) {
        this.productVersion = version;
    }

    public String getProductVersion() {
        return this.productVersion;
    }

    public void setMajorVersion(int version) {
        this.majorVersion = version;
    }

    public int getMajorVersion() {
        return this.majorVersion;
    }

    public void setMinorVersion(int version) {
        this.minorVersion = version;
    }

    public int getMinorVersion() {
        return this.minorVersion;
    }

    public void setMajorJDBCVersion(int version) {
        this.majorJDBCVersion = version;
    }

    public int getMajorJDBCVersion() {
        return this.majorJDBCVersion;
    }

    public void setMinorJDBCVersion(int version) {
        this.minorJDBCVersion = version;
    }

    public int getMinorJDBCVersion() {
        return this.minorJDBCVersion;
    }

    public void add(Table table) {
        if (!this.tables.contains(table)) {
            this.tables.add(table);
        }
    }

    public void remove(Table table) {
        this.tables.remove(table);
    }

    public List<Table> getTables() {
        return this.tables;
    }

}
