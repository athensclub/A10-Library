package a10lib.compiler.token;

import java.util.ArrayList;

import a10lib.compiler.ATokenizingException;

public abstract class Tokenizer {

    protected TokenFilter filter = TokenFilter.DO_NOTHING;

    protected ArrayList<TokenProvider> providers = new ArrayList<>();

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
     * push the current token back 1 character and append it later
     * 
     * @throws Exception:
     *             If failed to push back character
     */
    public abstract void previousChar() throws Exception;

    /**
     * append the next character to the current token({@code Tokenizer} class's
     * current {@code StringBuilder} object)
     * 
     * @throws Exception:
     *             if failed to append next character
     */
    protected abstract void nextChar() throws Exception;

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
	while (!eof()) {
	    try {
		nextChar();
	    } catch (Exception e) {
		e.printStackTrace();
		return null;
	    }
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
