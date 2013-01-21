package org.sharpsw.dbmt.support;
import java.util.List;

/**
 * Implements the abstracted Visitor design pattern.<p> 
 *
 * Intent: <i>Represents an operation to be performed on the elements of an 
 * object structure. Visitor lets you define a new operation without changing
 * the classes of the elements on which it operates</i><p>
 *
 * Note that this implementation only deals with two different kind of nodes:
 * terminal and non-terminal nodes. In cases where the aggregate structure  
 * contains more types of nodes, this aspect cannot be used without 
 * modifications. <p> 
 *
 * Note further that whenever the aggregate structure is unimportant, the
 * additional functionality can be added in a much sipmler using 
 * AspectJ's open classes mechanism (i.e., by using inter-type declarations
 * to implement the desired functionality).
 *
 * @author  Jan Hannemann
 * @author  Gregor Kiczales
 * @version 1.1, 02/17/04
 */

public abstract aspect TableVisitorProtocol {
    public interface VisitableNode {}
    protected interface Root extends VisitableNode {}
	protected interface Node extends VisitableNode {}
	protected interface Leaf extends VisitableNode {} 
	
	public interface Visitor {	    
		public void visitDatabase(VisitableNode node);
		public void visitTable(VisitableNode node);
		public void visitColumn(VisitableNode node);
		public List<String> getTableCreateStatements();
		public List<String> getPrimaryKeyCreateStatements();
		public List<String> getForeignKeyCreateStatements();
		public List<String> getAutoIncrementCreateStatements();
	}
	
	public void VisitableNode.accept(Visitor visitor) {}
	   
	public void Root.accept(Visitor visitor) {
		visitor.visitDatabase(this);
	}

	public void Node.accept(Visitor visitor) { 
	    visitor.visitTable(this); 
	}
	
	public void Leaf.accept(Visitor visitor) { 
	    visitor.visitColumn(this); 
	}
}
