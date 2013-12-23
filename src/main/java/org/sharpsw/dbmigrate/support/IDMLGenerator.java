package org.sharpsw.dbmigrate.support;

import java.util.List;

import org.sharpsw.dbmigrate.data.Database;

public interface IDMLGenerator {
	public void parse(final Database database);
	public List<String> getSelectStatements();
}
