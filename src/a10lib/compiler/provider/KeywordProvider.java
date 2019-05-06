package a10lib.compiler.provider;

import java.util.ArrayList;

import a10lib.compiler.token.Token;
import a10lib.compiler.token.TokenCreator;
import a10lib.compiler.token.TokenProvider;
import a10lib.compiler.token.Tokenizer;
import a10lib.util.Strings;

/**
 * A {@code a10lib.compiler.token.TokenProvider} pre-defined class that helps
 * tokenize all the keywords.You can set an Array of strings to be a keywords
 * and this {@code a10lib.compiler.KeywordProvider} will automatically tokenize
 * all the keywords correctly
 * 
 * @author Athensclub
 *
 */
public class KeywordProvider extends TokenProvider {

    public static final TokenCreator DEFAULT_KEYWORD_TOKEN_CREATOR = new TokenCreator() {
	@Override
	public Token createToken(StringBuilder str) {
	    return new DefaultKeywordToken(str.toString());
	}
    };

    public static class DefaultKeywordToken extends Token {

	public DefaultKeywordToken(String str) {
	    super(str);
	}

    }

    private TokenCreator tokenCreator;

    private ArrayList<String> keywords = new ArrayList<>();

    /**
     * Add a new keyword to this provider dictionary and when it matches the keyword
     * it will create token using given creator
     * 
     * @param keyword:
     *                     the new keyword
     */
    public void addKeyword(String keyword) {
	keywords.add(keyword);
    }

    /**
     * Set the token creator that will create token created by this provider.If
     * null,this keyword provider will use its default
     * {@link #DEFAULT_KEYWORD_TOKEN_CREATOR}
     * 
     * @param tokenCreator
     */
    public void setTokenCreator(TokenCreator tokenCreator) {
	this.tokenCreator = tokenCreator;
    }

    /**
     * Add a array of keywords to this provider dictionary and will create token
     * using given token when matches any of keywords
     * 
     * @param keywords:
     *                      the new keywords
     */
    public void addKeywords(String... keywords) {
	for (String key : keywords) {
	    addKeyword(key);
	}
    }
    
    int i= 0;

    @Override
    public boolean matchToken(Tokenizer tokenizer, StringBuilder string) {
	for (String str : keywords) {
	    if (Strings.endsWith(string, str)) {
		if (str.length() != string.length()) {
		    for (int i = 0; i < str.length(); i++) {
			try {
			    tokenizer.previousChar();
			} catch (Exception e) {
			    e.printStackTrace();
			}
		    }
		}
		return true;
	    }
	}
	return false;
    }

    @Override
    public Token createToken(StringBuilder string) {
	return tokenCreator == null ? DEFAULT_KEYWORD_TOKEN_CREATOR.createToken(string) : tokenCreator.createToken(string);
    }

}
