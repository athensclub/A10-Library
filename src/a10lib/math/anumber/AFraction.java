package a10lib.math.anumber;

import java.math.BigDecimal;
import java.math.RoundingMode;

import a10lib.math.Maths;

/**
 * A class that represent one number divided by other
 * 
 * @author Athensclub
 *
 */
public class AFraction extends ANumber {

    /**
     * Note this class only implemented for AFraction and ADecimal
     */
    
    /**
     * Represent Fraction 1/2
     */
    public static final AFraction ONE_HALF = new AFraction(ADecimal.ONE, ADecimal.TWO);

    /**
     * 
     */
    private static final long serialVersionUID = -5834860691871099314L;

    private ANumber numerator, denominator;

    /**
     * Convert ADecimal value to AFraction value
     * 
     * @param val
     * @return
     */
    public static AFraction fromDecimal(ADecimal val) {
	return new AFraction(val, ADecimal.ONE);
    }

    public AFraction(ANumber numerator, ANumber denominator) {
	if (numerator == null || denominator == null) {
	    throw new NullPointerException();
	}
	if (numerator.isNegative() && denominator.isNegative()) {
	    this.numerator = numerator.negate();
	    this.denominator = denominator.negate();
	} else {
	    this.numerator = numerator;
	    this.denominator = denominator;
	}
    }

    /**
     * Getter for this fraction denominator
     * 
     * @return
     */
    public ANumber getDenominator() {
	return denominator;
    }

    /**
     * Getter for this fraction numerator
     * 
     * @return
     */
    public ANumber getNumerator() {
	return numerator;
    }

    /**
     * Create AFraction from this fraction where the greatest common divisor of
     * numerator and denominator is 1.This only apply when both numerator and
     * denominator are instance of ADecimal.Otherwise return itself.
     * 
     * @return
     */
    public AFraction toLowestTermWhenPossible() {
	if (numerator instanceof ADecimal && denominator instanceof ADecimal) {
	    BigDecimal num = numerator.bigDecimalValue();
	    BigDecimal denom = denominator.bigDecimalValue();
	    BigDecimal gcd = Maths.gcd(num, denom);
	    return new AFraction(new ADecimal(num.divide(gcd)), new ADecimal(denom.divide(gcd)));

	}
	return this;
    }

    @Override
    public ANumber add(ANumber other) {
	if (other instanceof AFraction) {
	    AFraction o = (AFraction) other;
	    // (a/b) + (c/d) = (ad+bc)/bd
	    return new AFraction(numerator.multiply(o.denominator).add(o.numerator.multiply(denominator)),
		    denominator.multiply(o.denominator));
	}
	if (other instanceof ADecimal) {
	    return add(fromDecimal((ADecimal) other));
	}
	return other.add(this);
    }

    @Override
    public ANumber multiply(ANumber other) {
	if (other instanceof AFraction) {
	    AFraction o = (AFraction) other;
	    // (a/b) * (c/d) = (ab/cd)
	    return new AFraction(numerator.multiply(o.numerator), denominator.multiply(o.denominator));
	}
	if (other instanceof ADecimal) {
	    return multiply(fromDecimal((ADecimal) other));
	}
	return other.add(this);
    }

    @Override
    public boolean isNegative() {
	return numerator.isNegative() ^ denominator.isNegative(); // either but not both
    }

    @Override
    public boolean isZero() {
	return numerator.isZero();
    }

    @Override
    public int compareTo(ANumber other) {
	if (other instanceof AFraction) {
	    ANumber diff = subtract(other);
	    if (diff.isZero()) {
		return 0;
	    } else if (diff.isNegative()) {
		return -1;
	    } else {
		return 1;
	    }
	}
	if (other instanceof ADecimal) {
	    return compareTo(fromDecimal((ADecimal) other));
	}
	return other.compareTo(this);
    }

    @Override
    public ANumber reciprocal() {
	return new AFraction(denominator, numerator);
    }

    @Override
    public String toString() {
	return numerator + "/" + denominator;
    }

    @Override
    public ANumber pow(ANumber other) {
	return new AFraction(numerator.pow(other), denominator.pow(denominator));
    }

    @Override
    protected BigDecimal calculateBigDecimalValue() {
	BigDecimal num = numerator.bigDecimalValue();
	BigDecimal denom = denominator.bigDecimalValue();
	try {
	    return num.divide(denom);
	} catch (ArithmeticException e) {
	    return num.divide(denom, 20 + num.scale() + denom.scale(), RoundingMode.HALF_UP);
	}
    }

}
