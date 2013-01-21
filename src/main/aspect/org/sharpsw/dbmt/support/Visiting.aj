package org.sharpsw.dbmt.support;
import org.sharpsw.dbmt.base.Database;
import org.sharpsw.dbmt.base.Table;
import org.sharpsw.dbmt.base.Column;

public aspect Visiting extends TableVisitorProtocol {
    declare parents: Visitable implements VisitableNode;

	declare parents: Database implements Root;
	declare parents: Table implements Node;
	declare parents: Column implements Leaf;

}
