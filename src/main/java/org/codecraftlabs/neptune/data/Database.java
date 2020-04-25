package org.codecraftlabs.neptune.data;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Database {
    private String name;
    private String productName;
    private String productVersion;
    private int majorVersion;
    private int minorVersion;
    private int majorJDBCVersion;
    private int minorJDBCVersion;
    private Set<Schema> schemas = new HashSet<>();

    public Database(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

    public void add(@Nonnull final Schema schema) {
        schemas.add(schema);
    }

    public Set<Schema> getSchemas() {
        return Collections.unmodifiableSet(schemas);
    }
}
