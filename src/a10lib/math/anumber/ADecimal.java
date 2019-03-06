package a10lib.math.anumber;

import java.math.BigDecimal;

import a10lib.math.Maths;

/**
 * A class that represent a single decimal number
 * 
 * @author Athensclub
 *
 */
public class ADecimal extends ANumber {

    public static final ADecimal TWO = new ADecimal(BigDecimal.valueOf(2));
    
    public static final ADecimal ONE = new ADecimal(BigDecimal.valueOf(1));

    public static final ADecimal ZERO = new ADecimal(BigDecimal.valueOf(0));

    public static final ADecimal NEGATIVE_ONE = new ADecimal(BigDecimal.valueOf(-1));

    /**
     * Note that this class implemented operation with ADecimal only
     */

    private BigDecimal value;

    public ADecimal(String value) {
	this.value = new BigDecimal(value);
    }

    public ADecimal(BigDecimal value) {
	if (value == null) {
	    throw new NullPointerException();
	}
	this.value = value;
    }

    @Override
    public ANumber add(ANumber other) {
	if (other instanceof ADecimal) {
	    return new ADecimal(value.add(((ADecimal) other).value));
	}
	return other.add(this);
    }

    @Override
    public ANumber multiply(ANumber other) {
	if (other instanceof ADecimal) {
	    return new ADecimal(value.multiply(((ADecimal) other).value));
	}
	return other.multiply(this);
    }

    @Override
    public boolean isNegative() {
	return value.compareTo(BigDecimal.ZERO) < 0;
    }

    @Override
    public boolean isZero() {
	return value.compareTo(BigDecimal.ZERO) == 0;
    }

    @Override
    public ANumber pow(ANumber other) {
	if (other instanceof ADecimal || other instanceof AFraction) {
	    return new ADecimal(Maths.pow(value, other.bigDecimalValue()));
	}
	throw new IllegalArgumentException();
    }

    @Override
    protected BigDecimal calculateBigDecimalValue() {
	return value;
    }

}
