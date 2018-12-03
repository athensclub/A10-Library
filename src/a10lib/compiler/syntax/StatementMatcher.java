package a10lib.compiler.syntax;

import java.util.*;

import a10lib.compiler.token.*;

/**
 * Interface responsible for making classes be able to match statement with
 * their wanted statement type
 * 
 * @author Athensclub
 *
 */
public interface StatementMatcher {

    /**
     * Return whether the given list of tokens matches with this statement type's
     * syntax
     * 
     * @param tokens:
     *            the list of token to be matched
     * @return whether the given list of tokens matches with this statement type's
     *         syntax
     */
    public boolean matchStatement(LinkedList<Token> tokens);

}
