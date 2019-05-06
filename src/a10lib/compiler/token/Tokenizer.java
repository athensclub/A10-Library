package a10lib.compiler.token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import a10lib.compiler.ATokenizingException;
import a10lib.compiler.provider.DecimalNumberProvider;
import a10lib.util.Strings;

public abstract class Tokenizer {

    private ArrayList<TokenProvider> providers = new ArrayList<>();

    private LinkedList<Character> pushBackBuffer = new LinkedList<>();

    private LinkedList<Visitor> visitors = new LinkedList<>();

    private Token next;

    protected StringBuilder current;

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
     * Set the current string builder of the current token.This should not be called
     * unless neccessary.
     * 
     * @param str
     */
    public void setCurrent(StringBuilder str) {
	current = str;
    }

    /**
     * get the next character by implementation of each tokenizer's char stream
     * 
     * @throws Exception
     */
    protected abstract char nextCharInStream() throws Exception;

    /**
     * push the current token {@code Tokenizer} class's current
     * {@code StringBuilder} object) back 1 character and append it later.
     * 
     */
    public void previousChar() {
	pushBackBuffer.addLast(Strings.removeLastChar(current));
    }

    /**
     * Push the given character into same buffer as previousChar() buffer to append
     * it later.The character that is appended last will be used first.
     * 
     * @param c
     */
    public void pushPreviousChar(char c) {
	pushBackBuffer.addLast(c);
    }

    /**
     * Make the given visitor visit this tokenizer's char stream
     * 
     * @param visitor
     */
    public void visit(Visitor visitor) {
	visitors.addLast(visitor);
	visitor.onBegin(this, current);
	current = new StringBuilder();
    }

    /**
     * End the visit of the most recent visitor that is still not ended visiting.
     */
    public void endVisit() {
	Visitor last = visitors.removeLast();
	Token token = last.createToken();
	if (visitors.isEmpty()) {
	    if (next == null) {
		next = token;
	    } else {
		next = null;
		throw new IllegalStateException("next != null, endVisit() called");
	    }
	} else {
	    visitors.getLast().onEnter(this, token);
	}
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
	while (!(eof() && pushBackBuffer.isEmpty())) {
	    char c = (char) -1;
	    try {
		c = nextChar();
	    } catch (IOException e) {
		continue;
	    }
	    if (!visitors.isEmpty()) {
		visitors.getLast().nextChar(this, c);
		if (next != null) {
		    Token t = next;
		    next = null;
		    return t;
		}
	    } else {
		current.append(c);
		for (TokenProvider provider : providers) {
		    if (provider.matchToken(this, current)) {
			return provider.createToken(current);
		    }
		}
	    }
	}
	if (next != null) {
	    Token t = next;
	    next = null;
	    return t;
	}
	if (!visitors.isEmpty()) {
	    @SuppressWarnings("unchecked")
	    List<Visitor> v = (List<Visitor>) visitors.clone();
	    visitors.clear(); // recover from exception
	    throw new IllegalArgumentException("Visitor does not end when reach eof: " + v);
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
