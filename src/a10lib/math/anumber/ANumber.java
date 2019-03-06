package a10lib.math.anumber;

import java.math.BigDecimal;

import a10lib.compiler.Regex;

/**
 * A base class of all number in anumber package
 * 
 * @author Athensclub
 *
 */
public abstract class ANumber extends Number implements Comparable<ANumber> {

    /**
     * ORDER OF IMPLEMENTATION: ADecimal -> AFraction
     */

    /**
     * 
     */
    private static final long serialVersionUID = 4078167201408747426L;

    private BigDecimal value;

    /**
     * Create racket number according to its string(any CharSequence)
     * representation.Number string rules can be found at
     * {@link a10lib.compiler.Regex} for pattern of numbers.This support all number
     * in that class.
     * 
     * @param str
     * @return
     */
    public static ANumber valueOf(CharSequence cs) {
	if (Regex.matches(cs, Regex.FRACTIONABLE_NUMBER_PATTERN)) {
	    String str = cs.toString();
	    if (str.contains("/")) {
		String[] parts = str.split("/");
		return new AFraction(new ADecimal(parts[0]), new ADecimal(parts[1]));
	    } else {
		return new ADecimal(str);
	    }
	}
	throw new NumberFormatException(cs.toString());
    }

    /**
     * Return if this < 0
     * 
     * @return
     */
    public abstract boolean isNegative();

    /**
     * return if this == 0
     * 
     * @return
     */
    public abstract boolean isZero();

    /**
     * Return if this >= 0
     * 
     * @return
     */
    public boolean isPositive() {
	return !isNegative();
    }

    /**
     * Add this number by other number
     * 
     * @param other
     */
    public abstract ANumber add(ANumber other);

    /**
     * Subtract this number by other number
     * 
     * @param other
     */
    public ANumber subtract(ANumber other) {
	return other.negate().add(this);
    }

    /**
     * Multiply this number by other number
     * 
     * @param other
     */
    public abstract ANumber multiply(ANumber other);

    /**
     * Divide this number by other number
     * 
     * @param other
     */
    public ANumber divide(ANumber other) {
	return other.reciprocal().multiply(this);
    }

    public ANumber reciprocal() {
	return new AFraction(ADecimal.ONE, this);
    }

    /**
     * Return the negated version of this value(this value multiplied by -1)
     * 
     * @return
     */
    public ANumber negate() {
	return multiply(ADecimal.NEGATIVE_ONE);
    }

    /**
     * Raise this number by the other number.returned as the result
     * 
     * @param other
     * @return
     */
    public abstract ANumber pow(ANumber other);

    /**
     * Calculate the approximate big decimal value of this number.the value will
     * then be cached by ANumber for later bigDecimalValue() uses.
     * 
     * @return
     */
    protected abstract BigDecimal calculateBigDecimalValue();

    /**
     * Return BigDecimal value that is or approximately equal to this number
     * 
     * @return
     */
    public BigDecimal bigDecimalValue() {
	if (value == null) {
	    value = calculateBigDecimalValue();
	}
	return value;
    }

    @Override
    public double doubleValue() {
        return bigDecimalValue().doubleValue();
    }
    
    @Override
    public float floatValue() {
	return (float) doubleValue();
    }

    @Override
    public int intValue() {
	return (int) doubleValue();
    }

    @Override
    public long longValue() {
	return (long) doubleValue();
    }

    @Override
    public byte byteValue() {
	return (byte) doubleValue();
    }

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof ANumber) {
	    return compareTo((ANumber) obj) == 0;
	} else if (obj instanceof Number) {
	    return doubleValue() == ((Number) obj).doubleValue();
	}
	return false;
    }

    @Override
    public int compareTo(ANumber o) {
	return bigDecimalValue().compareTo(o.bigDecimalValue());
    }

    @Override
    public String toString() {
	return bigDecimalValue().toPlainString();
    }

}
