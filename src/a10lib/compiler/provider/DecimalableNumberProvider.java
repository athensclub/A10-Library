package a10lib.compiler.provider;

import a10lib.compiler.token.TokenProvider;
import a10lib.compiler.token.Tokenizer;
import a10lib.compiler.token.Visitor;

/**
 * A provider that help tokenizing decimalable number.
 * 
 * <p>
 * A decimalable number is a number that can be positive or negative, can has
 * decimal point or not have one.
 * </p>
 * 
 * @author Athensclub
 *
 */
public class DecimalableNumberProvider extends TokenProvider {

    /**
     * A cache instance of {@link DecimalNumberProvider}
     */
    public static final DecimalableNumberProvider INSTANCE = new DecimalableNumberProvider();

    public static class Token extends a10lib.compiler.token.Token {

	public Token(String str) {
	    super(str);
	}
	
    }

    public static class DecimalVisitor implements Visitor {

	private StringBuilder string;

	private boolean hasDot;

	private boolean hasFloatingPoint;

	@Override
	public void onBegin(Tokenizer tokenizer, StringBuilder string) {
	    this.string = string;
	    if(tokenizer.eof()) {
		tokenizer.endVisit();
	    }
	}

	@Override
	public void onEnter(Tokenizer tokenizer, a10lib.compiler.token.Token token) {
	    throw new IllegalStateException("Decimal Visitor should not be exited to another visitor.");
	}

	@Override
	public void nextChar(Tokenizer tokenizer, char next) {
	    if (Character.isDigit(next)) {
		if (hasDot) {
		    hasFloatingPoint = true;
		}
		string.append(next);
		if(tokenizer.eof()) {
		    tokenizer.endVisit();
		}
	    } else if (next == '.') {
		if (!hasDot) {
		    hasDot = true;
		    string.append(next);
		} else {
		    tokenizer.pushPreviousChar('.');
		    tokenizer.endVisit();
		}
	    } else {
		tokenizer.pushPreviousChar(next);
		tokenizer.endVisit();
	    }
	    
	}

	@Override
	public a10lib.compiler.token.Token createToken() {
	    if (hasDot && !hasFloatingPoint) {
		throw new IllegalStateException("Decimal number with dot but without floating point: " + string);
	    }
	    return new Token(string.toString());
	}

    }

    @Override
    public boolean matchToken(Tokenizer tokenizer, StringBuilder string) {
	if ((string.length() == 1 && Character.isDigit(string.charAt(0)))
		|| (string.length() == 2 && string.charAt(0) == '-' && Character.isDigit(string.charAt(1)))) {
	    tokenizer.visit(new DecimalVisitor());
	}
	return false;
    }

    @Override
    public Token createToken(StringBuilder string) {
	throw new IllegalArgumentException(
		"Decimal Number provider should not create token by itself,Insteead it should create token from its visitor");
    }

}
