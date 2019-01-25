package a10lib.compiler.syntax;

import java.util.LinkedList;

import javax.swing.tree.DefaultMutableTreeNode;

import a10lib.util.Strings;

/**
 * A class representing a block which will contains list of statement and blocks
 * 
 * @author Athensclub
 *
 */
public class Block extends Statement {

    private LinkedList<Statement> subStatement = new LinkedList<>();

    private Block superBlock;

    private BlockProvider provider;

    /**
     * Create new instance of block
     */
    public Block() {
    }
    
   @Override
    public void addTo(DefaultMutableTreeNode treeNode,String name) {
	int i = 0;
	DefaultMutableTreeNode result = new DefaultMutableTreeNode("Block_" + name);
	for(Statement st : subStatement) {
	   st.addTo(result, "Substatement_" + i++);
	}
	treeNode.add(result);
    }

    /**
     * Used by parser not to be used by user
     * 
     * @param provider
     */
    protected void setProvider(BlockProvider provider) {
	this.provider = provider;
    }

    /**
     * 
     * @return The provider that created this Block
     */
    protected BlockProvider getProvider() {
	return provider;
    }

    /**
     * Get a block that this block is in
     * 
     * @return a block that this block is in
     */
    public Block getSuperBlock() {
	return superBlock;
    }

    /**
     * Add statement or block to this block
     * 
     * @param statement:
     *            the statement or block to be added to this block
     */
    public void addStatement(Statement statement) {
	if (statement instanceof Block) {
	    if(statement == this) {
		throw new IllegalArgumentException("Cannot add block to itself: " + statement);
	    }
	    ((Block) statement).superBlock = this;
	}
	subStatement.add(statement);
	getStatement().addAll(statement.getStatement());
    }

    /**
     * Get a list of all statements inside this block
     * 
     * @return a list of all statements inside this block
     */
    public LinkedList<Statement> getSubStatement() {
	return subStatement;
    }
    
    @Override
    public String toString() {
	return "Block:\n" + Strings.addTabInFrontEveryLine(Strings.toStringNewLine(subStatement));
    }

}
