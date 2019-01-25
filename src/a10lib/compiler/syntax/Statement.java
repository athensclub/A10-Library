package a10lib.compiler.syntax;


import java.util.LinkedList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import a10lib.compiler.token.Token;

/**
 * A class representing a statement
 * 
 * @author Athensclub
 *
 */
public class Statement {

    private LinkedList<Token> statement = new LinkedList<>();

    public Statement() {
    }

    public Statement(LinkedList<Token> statement) {
	this.statement = statement;
    }

    /**
     * Add this block to a swing's tree node for easier viewing using given
     * name.Statement will automatically not add the statement node layer over token
     * layer if the token length is <= 1
     * 
     * @param treeNode
     */
    public void addTo(DefaultMutableTreeNode treeNode, String name) {
	if (statement.size() > 1) {
	    DefaultMutableTreeNode result = new DefaultMutableTreeNode(name);
	    for (Token t : statement) {
		result.add(new DefaultMutableTreeNode(t));
	    }
	    treeNode.add(result);
	}else if(statement.size() == 1) {
	    treeNode.add(new DefaultMutableTreeNode(statement.getFirst()));
	}
    }

    /**
     * Get all the token that made up to be this statement
     * 
     * @return all the token that made up to be this statement
     */
    public LinkedList<Token> getStatement() {
	return statement;
    }

    @Override
    public String toString() {
	return statement.toString();
    }

}
