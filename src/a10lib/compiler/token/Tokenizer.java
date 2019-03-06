package a10lib.compiler.token;

import java.util.ArrayList;
import java.util.LinkedList;

import a10lib.compiler.ATokenizingException;
import a10lib.util.Strings;

public abstract class Tokenizer {

    private TokenFilter filter = TokenFilter.DO_NOTHING;

    private ArrayList<TokenProvider> providers = new ArrayList<>();

    private LinkedList<Character> pushBackBuffer = new LinkedList<>();

    private Visitor visitor;

    private Token next;

    private boolean returnVisit;

    protected StringBuilder current;

    public void setFilter(TokenFilter filter) {
	this.filter = filter;
    }

    public void addProvider(TokenProvider p) {
	providers.add(p);
    }

    public ArrayList<TokenProvider> getProviders() {
	return providers;
    }

    public void addProviders(TokenProvider... provider) {
	for (TokenProvider tp : provider) {
	    providers.add(tp);
	}
    }

    public void addProvider(TokenMatcher matcher, TokenCreator creator) {
	addProvider(new TokenProvider() {

	    @Override
	    public boolean matchToken(Tokenizer tokenizer, StringBuilder string) {
		return matcher.matchToken(tokenizer, string);
	    }

	    @Override
	    public Token createToken(StringBuilder string) {
		return creator.createToken(string);
	    }

	});
    }

    public void addProvider(TokenMatcher matcher) {
	addProvider(new TokenProvider() {

	    @Override
	    public boolean matchToken(Tokenizer tokenizer, StringBuilder string) {
		return matcher.matchToken(tokenizer, string);
	    }
	});
    }

    /**
     * get the next character by implementation of each tokenizer's char stream
     * 
     * @throws Exception
     */
    protected abstract char nextCharInStream() throws Exception;

    /**
     * push the current token {@code Tokenizer} class's current
     * {@code StringBuilder} object) back 1 character and append it later
     * 
     */
    public void previousChar() {
	pushBackBuffer.addLast(Strings.removeLastChar(current));
    }

    /**
     * Make the given visitor visit this tokenizer's char stream
     * 
     * @param visitor
     */
    public void visit(Visitor visitor) {
	this.visitor = visitor;
	visitor.onBegin(current);
	current = new StringBuilder();
    }

    public void endVisit() {
	returnVisit = true;
	next = visitor.createToken();
	visitor = null;
    }

    /**
     * get the next character in the stream
     * 
     * @throws Exception
     */
    public char nextChar() throws Exception {
	if (!pushBackBuffer.isEmpty()) {
	    return pushBackBuffer.removeLast();
	} else {
	    return nextCharInStream();
	}
    }

    /**
     * Return whether this tokenizer reaches end of file yet
     * 
     * @return whether this tokenizer reaches end of file yet
     */
    public abstract boolean eof();

    /**
     * Get the next token tokenized by given token providers
     * 
     * @return the next token tokenized by given token providers
     * @throws Exception
     */
    public Token nextToken() throws Exception {
	current = new StringBuilder();
	while (true) {
	    if (returnVisit || (eof() && pushBackBuffer.isEmpty())) {
		break;
	    }
	    char c = nextChar();
	    if (visitor != null) {
		visitor.nextChar(this, c);
	    } else {
		current.append(c);
	    }
	    filter.filter(current);
	    for (TokenProvider provider : providers) {
		if (visitor == null) {
		    if (provider.matchToken(this, current)) {
			return provider.createToken(current);
		    }
		}
	    }
	    if (visitor != null && !returnVisit && eof()) {
		break;
	    }
	}
	if (returnVisit) {
	    returnVisit = false;
	    Token n = next;
	    next = null;
	    return n;
	}
	if (visitor != null) {
	    visitor = null; //recover from exception
	    throw new IllegalArgumentException("Visitor does not end when reach eof->" + visitor.createToken());
	}
	if (current.length() > 0) {
	    for (TokenProvider provider : providers) {
		if (provider.matchToken(this, current)) {
		    return provider.createToken(current);
		}
	    }
	}
	if (current.length() > 0) {
	    throw new ATokenizingException("Unknown Token Type: " + current);
	}
	return null;
    }

}
