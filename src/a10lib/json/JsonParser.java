package a10lib.json;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import a10lib.compiler.provider.DecimalNumberProvider;
import a10lib.compiler.provider.KeywordProvider;
import a10lib.compiler.provider.StringProvider;
import a10lib.compiler.provider.WhitespaceProvider;
import a10lib.compiler.syntax.Block;
import a10lib.compiler.syntax.BlockProvider;
import a10lib.compiler.syntax.Parser;
import a10lib.compiler.syntax.Statement;
import a10lib.compiler.syntax.StatementProvider;
import a10lib.compiler.token.StreamTokenizer;
import a10lib.compiler.token.StringTokenizer;
import a10lib.compiler.token.Token;
import a10lib.compiler.token.TokenCreator;
import a10lib.compiler.token.Tokenizer;

/**
 * A parser for JSON file format.A value from JSON are converted to Java Values.
 * <p>
 * The parsed JSON value will return a list of value that are separated by
 * comma, mapped to java value by this parser's mapping rule.
 * </p>
 * <p>
 * <code>
 * The parser will convert following json value (left) to java value (right) </br>
 * int -> Integer</br>
 * boolean -> Boolean</br>
 * String -> String</br>
 * null -> null</br>
 * Array -> List{@literal <Object>} (Objects type are mapped to java value by this mapping)</br>
 * JSON Object -> Map{@literal <String,Object>} (Objects type are mapped to java value by this mapping)</br>
 * </code>
 * </p>
 * 
 * @author Athensclub
 *
 */
public class JsonParser {

    private static final String BRACKETS = "[]{}";

    private Tokenizer tokenizer;

    private Parser parser;

    public static void main(String[] args) throws Exception {
	System.out.println(new JsonParser(new StringReader("{\r\n" + "    \"quiz\": {\r\n" + "        \"sport\": {\r\n"
		+ "            \"q1\": {\r\n"
		+ "                \"question\": \"Which one is correct team name in NBA?\",\r\n"
		+ "                \"options\": [\r\n" + "                    \"New York Bulls\",\r\n"
		+ "                    \"Los Angeles Kings\",\r\n"
		+ "                    \"Golden State Warriros\",\r\n" + "                    \"Huston Rocket\"\r\n"
		+ "                ],\r\n" + "                \"answer\": \"Huston Rocket\"\r\n" + "            }\r\n"
		+ "        },\r\n" + "        \"maths\": {\r\n" + "            \"q1\": {\r\n"
		+ "                \"question\": \"5 + 7 = ?\",\r\n" + "                \"options\": [\r\n"
		+ "                    \"10\",\r\n" + "                    \"11\",\r\n"
		+ "                    \"12\",\r\n" + "                    \"13\"\r\n" + "                ],\r\n"
		+ "                \"answer\": \"12\"\r\n" + "            },\r\n" + "            \"q2\": {\r\n"
		+ "                \"question\": \"12 - 8 = ?\",\r\n" + "                \"options\": [\r\n"
		+ "                    \"1\",\r\n" + "                    \"2\",\r\n" + "                    \"3\",\r\n"
		+ "                    \"4\"\r\n" + "                ],\r\n" + "                \"answer\": \"4\"\r\n"
		+ "            }\r\n" + "        }\r\n" + "    }\r\n" + "}")).parseObjects());
    }

    public JsonParser(Reader stream) {
	tokenizer = new StreamTokenizer(stream);
	parser = new Parser(tokenizer);
	setupTokenizer(tokenizer);
	setupParser(parser);
    }

    public JsonParser(String str) {
	tokenizer = new StringTokenizer(str);
	parser = new Parser(tokenizer);
	setupTokenizer(tokenizer);
	setupParser(parser);
    }

    /**
     * Parser everything from the stream and return as list of array mapped to java
     * values.
     * 
     * @return
     * @throws Exception
     */
    public List<Object> parseObjects() throws Exception {
	Block block = parser.parse();
	ArrayList<Object> result = new ArrayList<>();
	boolean expectComma = false;
	for (Statement stm : block.getSubStatement()) {
	    if (expectComma) {
		if (stm.getStatement().getFirst().getString().equals(",")) {
		    expectComma = false;
		} else {
		    throw new IllegalArgumentException("Expected comma,found: " + stm);
		}
	    } else {
		result.add(parse(stm));
		expectComma = true;
	    }
	}
	return Collections.unmodifiableList(result);
    }

    private Object parse(Statement stm) {
	if (stm instanceof JsonBlock) {
	    return parse((JsonBlock) stm);
	}
	Token t = stm.getStatement().getFirst();
	if (t instanceof StringProvider.Token) {
	    return ((StringProvider.Token) t).getLiteralValue();
	}
	try {
	    return Integer.parseInt(t.getString());
	} catch (NumberFormatException e) {
	}
	switch (t.getString()) {
	case "true":
	    return true;
	case "false":
	    return false;
	case "null":
	    return null;
	}
	throw new IllegalArgumentException("Unknown token: " + t);
    }

    private Object parse(JsonBlock block) {
	if (block.isArray) {
	    boolean expectComma = false;
	    ArrayList<Object> result = new ArrayList<>();
	    for (Statement stm : block.getSubStatement()) {
		if (expectComma) {
		    if (stm.getStatement().getFirst().getString().equals(",")) {
			expectComma = false;
		    } else {
			throw new IllegalArgumentException("Expected comma,found: " + stm);
		    }
		} else {
		    result.add(parse(stm));
		    expectComma = true;
		}
	    }
	    if (!expectComma) {
		throw new IllegalArgumentException("Unexpected comma");
	    }
	    return Collections.unmodifiableList(result);
	} else {
	    Iterator<Statement> it = block.getSubStatement().iterator();
	    HashMap<String, Object> result = new HashMap<>();
	    boolean expectComma = false;
	    while (it.hasNext()) {
		Statement stm = it.next();
		if (expectComma) {
		    if (stm.getStatement().getFirst().getString().equals(",")) {
			expectComma = false;
		    } else {
			throw new IllegalArgumentException("Expected comma,found: " + stm);
		    }
		} else {
		    Token begin = stm.getStatement().getFirst();
		    if (begin instanceof StringProvider.Token) {
			if (it.hasNext()) {
			    Token colon = it.next().getStatement().getFirst();
			    if (colon.getString().equals(":")) {
				if (it.hasNext()) {
				    Statement value = it.next();
				    String key = ((StringProvider.Token) begin).getLiteralValue();
				    if (!result.containsKey(key)) {
					result.put(key, parse(value));
					expectComma = true;
				    } else {
					throw new IllegalArgumentException("Duplicate Object key: \"" + key + "\"");
				    }
				} else {
				    throw new IllegalArgumentException("Expected value after object key: " + begin);
				}
			    } else {
				throw new IllegalArgumentException("Expected ':',found: " + colon);
			    }
			} else {
			    throw new IllegalArgumentException("Unexpected String: " + begin);
			}
		    } else {
			throw new IllegalArgumentException("Expected String in object key, found: " + begin);
		    }
		}
	    }
	    return Collections.unmodifiableMap(result);
	}
    }

    private void setupParser(Parser parser) {
	parser.addBlockProvider(new BlockProvider() {

	    @Override
	    public boolean matchEndBlock(Parser parser, LinkedList<Token> statement) {
		if (statement.isEmpty()) {
		    return false;
		}
		Token t = statement.getFirst();
		if (t instanceof BracketToken) {
		    BracketToken b = (BracketToken) t;
		    return b.isArray && !b.isOpen;
		}
		return false;
	    }

	    @Override
	    public Block createBlock(LinkedList<Token> blockBegin) {
		return new JsonBlock(true);
	    }

	    @Override
	    public boolean matchBeginBlock(Parser parser, LinkedList<Token> statement) {
		if (statement.isEmpty()) {
		    return false;
		}
		Token t = statement.getFirst();
		if (t instanceof BracketToken) {
		    BracketToken b = (BracketToken) t;
		    return b.isArray && b.isOpen;
		}
		return false;
	    }
	});
	parser.addBlockProvider(new BlockProvider() {

	    @Override
	    public boolean matchEndBlock(Parser parser, LinkedList<Token> statement) {
		if (statement.isEmpty()) {
		    return false;
		}
		Token t = statement.getFirst();
		if (t instanceof BracketToken) {
		    BracketToken b = (BracketToken) t;
		    return !b.isArray && !b.isOpen;
		}
		return false;
	    }

	    @Override
	    public Block createBlock(LinkedList<Token> blockBegin) {
		return new JsonBlock(false);
	    }

	    @Override
	    public boolean matchBeginBlock(Parser parser, LinkedList<Token> statement) {
		if (statement.isEmpty()) {
		    return false;
		}
		Token t = statement.getFirst();
		if (t instanceof BracketToken) {
		    BracketToken b = (BracketToken) t;
		    return !b.isArray && b.isOpen;
		}
		return false;
	    }
	});
	parser.addStatementProvider(new StatementProvider() {

	    @Override
	    public boolean matchStatement(LinkedList<Token> tokens) {
		return !(tokens.getFirst() instanceof BracketToken);
	    }
	});
    }

    private void setupTokenizer(Tokenizer tokenizer) {
	KeywordProvider provider = new KeywordProvider();
	provider.addKeywords(":", "{", "}", "[", "]", ",");
	provider.setTokenCreator(new TokenCreator() {
	    @Override
	    public Token createToken(StringBuilder string) {
		if (BRACKETS.contains(string)) {
		    return new BracketToken(string.charAt(0));
		}
		return TokenCreator.super.createToken(string);
	    }
	});
	tokenizer.addProviders(new WhitespaceProvider(), StringProvider.INSTANCE, DecimalNumberProvider.INSTANCE,
		provider);
    }

    private static class JsonBlock extends Block {

	private boolean isArray;

	public JsonBlock(boolean arr) {
	    isArray = arr;
	}

	public boolean isArray() {
	    return isArray;
	}

    }

    /**
     * A token of bracket.
     * 
     * @author Athensclub
     *
     */
    private static class BracketToken extends Token {

	private boolean isArray, isOpen;

	public BracketToken(char c) {
	    super();
	    if (c == '[' || c == ']') {
		isArray = true;
	    }
	    if (c == '[' || c == '{') {
		isOpen = true;
	    }
	}

	public boolean isArray() {
	    return isArray;
	}

	public boolean isOpen() {
	    return isOpen;
	}

    }

}
