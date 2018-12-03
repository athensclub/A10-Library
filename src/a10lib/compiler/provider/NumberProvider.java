package a10lib.compiler.provider;

import java.math.BigInteger;

import a10lib.compiler.token.Token;
import a10lib.compiler.token.TokenCreator;
import a10lib.compiler.token.TokenProvider;
import a10lib.compiler.token.Tokenizer;
import a10lib.util.Strings;

public class NumberProvider extends TokenProvider {

    private TokenProvider nonNumberProvider;

    private TokenCreator numberTokenCreator;

    /**
     * A default class that is created when user does not provide number token
     * creator
     * 
     * @author Athensclub
     *
     */
    public static class DefaultNumberToken extends Token {

	public DefaultNumberToken(String str) {
	    super(str);
	}

	/**
	 * Get a int value of this token
	 * 
	 * @return a int value of this token
	 */
	public int intValue() {
	    return Integer.parseInt(getString());
	}

	/**
	 * Get a BigInteger value of this token
	 * 
	 * @return a BigInteger value of this token
	 */
	public BigInteger bigIntValue() {
	    return new BigInteger(getString());
	}

    }

    /**
     * Set a provider that provide token for non-number tokens
     * 
     * @param nonNumberProvider:
     *            a provider that provide token for non-number tokens
     */
    public void setNonNumberProvider(TokenProvider nonNumberProvider) {
	this.nonNumberProvider = nonNumberProvider;
    }

    /**
     * Set a token creator that create token for number token default is creating
     * {@code DefaultNumberToken}
     * 
     * @param numberTokenCreator:
     *            a token creator that create token for number token
     */
    public void setNumberTokenCreator(TokenCreator numberTokenCreator) {
	this.numberTokenCreator = numberTokenCreator;
    }

    @Override
    public boolean matchToken(Tokenizer tokenizer, StringBuilder string) {
	if (tokenizer.eof()) {
	    if (Strings.isInteger(string)) {
		return true;
	    }
	}
	if (string.length() > 1) {
	    if (!Character.isDigit(string.charAt(0)) && Character.isDigit(string.length() - 1)) {
		try {
		    tokenizer.previousChar();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return true;
	    } else if (Character.isDigit(string.charAt(0)) && !Character.isDigit(string.length() - 1)) {
		try {
		    tokenizer.previousChar();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return true;
	    }
	}
	return nonNumberProvider == null ? false : nonNumberProvider.matchToken(tokenizer, string);
    }

    @Override
    public Token createToken(StringBuilder string) {
	return Strings.isInteger(string) ? 
		(numberTokenCreator == null ? new DefaultNumberToken(string.toString()) : numberTokenCreator.createToken(string))
	     :	(nonNumberProvider == null ? super.createToken(string) : nonNumberProvider.createToken(string));
    }

}
