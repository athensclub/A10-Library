package a10lib.compiler.token;

import java.util.ArrayList;
import java.util.LinkedList;

import a10lib.compiler.ATokenizingException;
import a10lib.util.Strings;

public abstract class Tokenizer {

    protected TokenFilter filter = TokenFilter.DO_NOTHING;

    protected ArrayList<TokenProvider> providers = new ArrayList<>();

    private LinkedList<Character> pushBackBuffer = new LinkedList<>();

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
     * @throws Exception 
     */
    protected abstract char nextCharInStream() throws Exception;

    /**
     * push the current token{@code Tokenizer} class's current {@code StringBuilder}
     * object) back 1 character and append it later
     * 
     */
    public void previousChar() {
	pushBackBuffer.addLast(Strings.removeLastChar(current));
    }

    /**
     * append the next character to the current token({@code Tokenizer} class's
     * current {@code StringBuilder} object)
     */
    public void nextChar() {
	if (!pushBackBuffer.isEmpty()) {
	    current.append(pushBackBuffer.removeLast());
	} else {
	    try {
		current.append(nextCharInStream());
	    } catch (Exception e) {
		e.printStackTrace();
	    }
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
     * @throws ATokenizingException:
     *             If found unexpected token
     */
    public Token nextToken() throws ATokenizingException {
	current = new StringBuilder();
	while (true) {
	    if(eof() && pushBackBuffer.isEmpty()) {
		    break;
	    }
	    nextChar();
	    filter.filter(current);
	    for (TokenProvider provider : providers) {
		if (provider.matchToken(this, current)) {
		    return provider.createToken(current);
		}
	    }
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
