package org.sharpsw.dbmt.support;

import java.util.List;

import org.sharpsw.dbmt.support.TableVisitorProtocol.VisitableNode;

public class SQLServerVisitor implements TableVisitorProtocol.Visitor {

	@Override
	public void visitDatabase(VisitableNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitTable(VisitableNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitColumn(VisitableNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getTableCreateStatements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getPrimaryKeyCreateStatements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getForeignKeyCreateStatements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAutoIncrementCreateStatements() {
		// TODO Auto-generated method stub
		return null;
	}

}
