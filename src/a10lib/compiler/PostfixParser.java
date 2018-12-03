package a10lib.compiler;

import java.io.Reader;
import java.util.LinkedList;

import a10lib.compiler.provider.KeywordProvider;
import a10lib.compiler.provider.NumberProvider;
import a10lib.compiler.provider.WhitespaceProvider;
import a10lib.compiler.syntax.Block;
import a10lib.compiler.syntax.BlockCreator;
import a10lib.compiler.syntax.BlockProvider;
import a10lib.compiler.syntax.Parser;
import a10lib.compiler.syntax.Statement;
import a10lib.compiler.syntax.StatementProvider;
import a10lib.compiler.token.StreamTokenizer;
import a10lib.compiler.token.StringTokenizer;
import a10lib.compiler.token.Token;
import a10lib.compiler.token.Tokenizer;
import a10lib.util.Strings;

/**
 * A {@code Parser} that parses the given string or stream and create a postfix
 * block example: "(1+2)*4+(2-3)"
 * 
 * @author Athensclub
 *
 */
public class PostfixParser extends Parser {

    /**
     * Set up the postfix syntax
     */
    {
	setGlobalBlockCreator(BlockCreator.defaultInstanceOf(PostfixBlock.class));
	addBlockProvider(new BlockProvider() {

	    @Override
	    public boolean matchBeginBlock(Parser parser, LinkedList<Token> statement) {
		return statement.getFirst().getString().equals("(");
	    }

	    @Override
	    public boolean matchEndBlock(Parser parser, LinkedList<Token> statement) {
		return statement.getFirst().getString().equals(")");
	    }

	    @Override
	    public Block createBlock(LinkedList<Token> begin) {
		return new PostfixBlock();
	    }

	});
	addStatementProvider(new StatementProvider() {

	    @Override
	    public boolean matchStatement(LinkedList<Token> tokens) {
		if (Strings.isInteger(tokens.getFirst().getString())) {
		    return true;
		}
		return Strings.equalsAny(tokens.getFirst().getString(), "+", "-", "*", "/") != null;
	    }

	});
    }

    /**
     * Parses the expression and calculate the result ignoring order of operations
     * 
     * @return the result of expression ignoring order of operations
     * @throws AParsingException:
     *             If failed to parse
     */
    public double evaluate() throws AParsingException {
	return evaluate(((PostfixBlock) parse()).evaluate());
    }

    /**
     * Calculate the result from the given postfix expression represented by strings
     * 
     * @param expr:
     *            the postfix expression represented by strings
     * @return the result from the given postfix expression represented by strings
     * @throws AParsingException:
     *             If failed to parse
     */
    public static double evaluate(LinkedList<String> expr) throws AParsingException {
	LinkedList<Double> stack = new LinkedList<>();
	for (String stm : expr) {
	    if (Strings.isInteger(stm)) {
		stack.push(Double.parseDouble(stm));
	    } else if (Strings.equalsAny(stm, "+", "-", "*", "/") != null) {
		double second = stack.pop();
		double first = stack.pop();
		switch (stm) {
		case "+":
		    stack.push(first + second);
		    break;
		case "-":
		    stack.push(first - second);
		    break;
		case "*":
		    stack.push(first * second);
		    break;
		case "/":
		    stack.push(first / second);
		    break;
		}
	    } else {
		throw new AParsingException("Unknown statement while evaluating postfix expression: " + stm);
	    }
	}
	if (stack.size() != 1) {
	    throw new AParsingException(
		    "Expression operators count does not match with numbers count: " + stack.size());
	}
	return stack.getLast();
    }

    /**
     * Parse the current postfix expression and return {@code PostfixBlock} same as
     * calling {@code (PostfixBlock)parse();}
     * 
     * @return the {@code PostfixBlock} gloabl block after parsing the current
     *         postfix expression
     * @throws AParsingException:
     *             If failed to parse
     */
    public PostfixBlock parsePostfix() throws AParsingException {
	return (PostfixBlock) parse();
    }

    /**
     * A {@code Block} class representing a postfix block statement aka. brackets
     * 
     * @author Athensclub
     *
     */
    public static class PostfixBlock extends Block {

	/**
	 * Evaluate the list of statement to postfix expression represented by list of
	 * strings
	 * 
	 * @return the list of statement to postfix expression represented by list of
	 *         strings
	 * @throws AParsingException:
	 *             If failed to parse
	 */
	public LinkedList<String> evaluate() throws AParsingException {
	    LinkedList<String> result = new LinkedList<>();
	    LinkedList<String> operators = new LinkedList<>();
	    boolean expectNumber = true;
	    for (Statement stm : getSubStatement()) {
		if (stm instanceof Block) {
		    if (expectNumber) {
			result.addAll(((PostfixBlock) stm).evaluate());
			expectNumber = false;
		    } else {
			throw new AParsingException(
				"Unexpected block expression while parsing postfix statement: " + stm.getStatement());
		    }
		} else {
		    String token = stm.getStatement().getFirst().getString();
		    if (Strings.isInteger(token)) {
			if (expectNumber) {
			    result.add(token);
			    expectNumber = false;
			} else {
			    throw new AParsingException("Unexpected number while parsing postfix statement: " + token);
			}
		    } else if (Strings.equalsAny(token, "+", "-", "*", "/") != null) {
			if (!expectNumber) {
			    if (!operators.isEmpty()) {
				result.add(operators.pop());
			    }
			    operators.push(token);
			    expectNumber = true;
			} else {
			    throw new AParsingException(
				    "Unexpected operator while parsing postfix statement: " + token);
			}
		    } else {
			throw new AParsingException("Unexpected statement while parsing postfix statement: " + token);
		    }
		}
	    }
	    while (!operators.isEmpty()) {
		result.add(operators.pop());
	    }
	    return result;
	}

    }

    private static Tokenizer setTokenizer(Tokenizer tokenizer) {
	tokenizer.getProviders().clear();
	WhitespaceProvider whitespace = new WhitespaceProvider();
	KeywordProvider keyword = new KeywordProvider();
	keyword.addKeywords("+", "-", "*", "/", "(", ")");
	whitespace.setTokenProvider(keyword);
	keyword.setNonKeywordProvider(new NumberProvider());
	tokenizer.addProvider(whitespace);
	return tokenizer;
    }

    /**
     * Create a postfix parser that parse the given stream
     * 
     * @param stream:
     *            a postfix statement stream that is going to be parsed
     */
    public PostfixParser(Reader stream) {
	super(setTokenizer(new StreamTokenizer(stream)));
    }

    /**
     * Create a postfix parser that parse the given string
     * 
     * @param expr:
     *            a postfix statement string that is going to be parsed
     */
    public PostfixParser(String expr) {
	super(setTokenizer(new StringTokenizer(expr)));
    }

    /**
     * Create a postfix parser that parse through the given tokenizer
     * 
     * @param tokenizer:
     *            The tokenizer that provide postfix expression token
     */
    public PostfixParser(Tokenizer tokenizer) {
	super(setTokenizer(tokenizer));
    }

}
