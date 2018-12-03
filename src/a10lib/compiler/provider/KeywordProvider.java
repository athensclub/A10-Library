package a10lib.compiler.provider;

import java.util.HashMap;
import java.util.Map;

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

    public static final TokenCreator DEFAULT_NON_KEYWORD_TOKEN_CREATOR = new TokenCreator() {
	@Override
	public Token createToken(StringBuilder str) {
	    return new Token(str.toString());
	}
    };

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

    private Map<String, TokenCreator> keywords = new HashMap<>();

    private TokenCreator nextKeyword;

    private TokenProvider nonKeywordProvider;

    /**
     * Add a new keyword to this provider dictionary and when it matches the keyword
     * it will create token using given creator
     * 
     * @param keyword:
     *            the new keyword
     * @param creator:
     *            the creator that create token for given keyword
     */
    public void addKeyword(String keyword, TokenCreator creator) {
	if (creator == null) {
	    throw new IllegalArgumentException("Token Creator of keyword is null");
	}
	keywords.put(keyword, creator);
    }

    /**
     * Add keyword to this provider dictionary and will create token using
     * {@code DefaultKeywordToken} when matches keyword
     * 
     * @param keyword:
     *            the new keyword
     */
    public void addKeyword(String keyword) {
	addKeyword(keyword, DEFAULT_KEYWORD_TOKEN_CREATOR);
    }

    /**
     * Add a array of keywords to this provider dictionary and will create token
     * using given token when matches any of keywords
     * 
     * @param creator:
     *            the creator that create token for given keywords
     * @param keywords:
     *            the new keywords
     */
    public void addKeywords(TokenCreator creator, String... keywords) {
	for (String key : keywords) {
	    addKeyword(key, creator);
	}
    }

    /**
     * Add a array of keywords to this provider dictionary and will create token
     * using {@code DefaultKeywordToken} when matches any of keywords
     * 
     * @param keywords:
     *            the new keywords
     */
    public void addKeywords(String... keywords) {
	addKeywords(DEFAULT_KEYWORD_TOKEN_CREATOR, keywords);
    }

    /**
     * Set the provider that provide token for non-keyword tokens
     * 
     * @param nonKeywordProvider:
     *            the provider that provide token for non-keyword tokens
     */
    public void setNonKeywordProvider(TokenProvider nonKeywordProvider) {
	if (nonKeywordProvider instanceof KeywordProvider) {
	    throw new IllegalArgumentException("Non keyword provider can not be instance of KeywordProvider");
	}
	this.nonKeywordProvider = nonKeywordProvider;
    }

    @Override
    public boolean matchToken(Tokenizer tokenizer, StringBuilder string) {
	if ((nextKeyword = keywords.get(string.toString())) != null) {
	    return true;
	}
	for (String str : keywords.keySet()) {
	    if (Strings.endsWith(string, str)) {
		for (int i = 0; i < str.length(); i++) {
		    try {
			tokenizer.previousChar();
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		}
		return true;
	    }
	}
	return nonKeywordProvider == null ? false : nonKeywordProvider.matchToken(tokenizer, string);
    }

    @Override
    public Token createToken(StringBuilder string) {
	if (nextKeyword != null) {
	    Token result = nextKeyword.createToken(string);
	    nextKeyword = null;
	    return result;
	}
	if (nonKeywordProvider != null) {
	    return nonKeywordProvider.createToken(string);
	}
	return DEFAULT_NON_KEYWORD_TOKEN_CREATOR.createToken(string);
    }

}
