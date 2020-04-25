package org.codecraftlabs.neptune.support;

import java.util.List;

import org.codecraftlabs.neptune.data.Database;

public interface IDMLGenerator {
	public void parse(final Database database);
	public List<String> getSelectStatements();
}
