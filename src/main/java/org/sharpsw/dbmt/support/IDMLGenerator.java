package org.sharpsw.dbmt.support;

import java.util.List;

import org.sharpsw.dbmigrate.base.Database;

public interface IDMLGenerator {
	public void parse(final Database database);
	public List<String> getSelectStatements();
}
