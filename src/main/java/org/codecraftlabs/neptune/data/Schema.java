package org.codecraftlabs.neptune.data;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Schema {
    private String name;
    private Set<Table> tables;

    public Schema(String name) {
        this.name = name;
        tables = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void add(@Nonnull Table table) {
        tables.add(table);
    }

    public Set<Table> getTables() {
        return Collections.unmodifiableSet(tables);
    }
}
