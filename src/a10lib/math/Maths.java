package a10lib.math;

import java.math.BigDecimal;
import java.math.BigInteger;

import libs.BigFunctions.BigFunctions;

/**
 * A utilities class for mathematics calculations (mostly algebra)
 * 
 * @author Athensclub
 *
 */
public final class Maths {

    public static final BigDecimal DOUBLE_MAX_VALUE = BigDecimal.valueOf(Double.MAX_VALUE);

    /**
     * A minimum value possible for double. Same as DOUBLE_MAX_VALUE.negate()
     */
    public static final BigDecimal DOUBLE_MIN_VALUE = BigDecimal.valueOf(-Double.MAX_VALUE);

    public static final BigDecimal INT_MAX_VALUE = BigDecimal.valueOf(Integer.MAX_VALUE);

    public static final BigDecimal INT_MIN_VALUE = BigDecimal.valueOf(Integer.MIN_VALUE);

    private Maths() {
    }

    /**
     * Check whether the given BIgInteger is zero or not
     * 
     * @param val
     * @return
     */
    public static boolean isZero(BigInteger val) {
	return val.compareTo(BigInteger.ZERO) == 0;
    }

    public static boolean isOdd(BigInteger val) {
	return !isZero(val.and(BigInteger.ONE));
    }

    public static boolean isEven(BigInteger val) {
	return isZero(val.and(BigInteger.ONE));
    }

    /**
     * Get the greatest common divisor for both decimal.Determined by converting
     * both number to integer by multiplying with power of ten then find gcd of
     * those number then divide the gcd with the previous power of ten.
     * 
     * 
     * @param first
     * @param second
     * @return
     */
    public static BigDecimal gcd(BigDecimal first, BigDecimal second) {
	int dp = Math.max(numberOfDecimalPlaces(first), numberOfDecimalPlaces(second));
	BigDecimal power = BigDecimal.TEN.pow(dp);
	return new BigDecimal(
		gcd(first.multiply(power).toBigIntegerExact(), second.multiply(power).toBigIntegerExact()))
			.divide(power);
    }

    /**
     * Get the number of digits to the right of decimal point of the given
     * BigDecimal value(excluding zeroes that come after the actual value)
     * 
     * @param val
     * @return
     */
    public static int numberOfDecimalPlaces(BigDecimal val) {
	return Math.max(0, val.stripTrailingZeros().scale());
    }

    /**
     * Get the greatest common divisor for both integers
     * 
     * @param first
     * @param second
     * @return
     */
    public static BigInteger gcd(BigInteger first, BigInteger second) {

	BigInteger p = first.abs();
	BigInteger q = second.abs();
	if (isZero(q)) {
	    if (isZero(p)) {
		return BigInteger.ONE;
	    }
	    return p;
	}
	if (isZero(p)) {
	    return q;
	}
	if (isEven(p) && isEven(q)) {
	    return gcd(p.shiftRight(1), q.shiftRight(1)).shiftLeft(1);
	} else if (isEven(p)) {
	    return gcd(p.shiftRight(1), q);
	} else if (isEven(q)) {
	    return gcd(p, q.shiftRight(1));
	} else if (p.compareTo(q) >= 0) {
	    return gcd(p.subtract(q).shiftRight(1), q);
	} else {
	    return gcd(p, q.subtract(p).shiftRight(1));
	}
    }

    /**
     * Check to see whether the given number is representing a integer(whole number)
     * or not
     * 
     * @param number
     * @return
     */
    public static boolean isInteger(BigDecimal number) {
	return number.signum() == 0 || number.scale() <= 0 || number.stripTrailingZeros().scale() <= 0;
    }

    /**
     * Check if given number is even or odd
     * 
     * @param number
     * @return
     */
    public static boolean isEven(BigDecimal number) {
	if (!isInteger(number)) {
	    throw new IllegalArgumentException("Check non-integer number for even?");
	}
	return number.remainder(BigDecimal.valueOf(2)).compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * Raise the given number to the given exponent.Loses precision quickly, Not
     * recommended
     * 
     * @param number
     * @param exponent
     * @return
     */
    public static BigDecimal pow(BigDecimal number, BigDecimal exponent) {
	//when number is < 0
	if (number.compareTo(BigDecimal.ZERO) < 0) {
	    if (isInteger(exponent)) {
		if (isEven(exponent)) {
		    return pow(number.abs(), exponent);
		} else {
		    return pow(number.abs(), exponent).negate();
		}
	    } else {
		throw new IllegalArgumentException("Power result in imaginary number: " + number + " ^ " + exponent);
	    }
	}
	// best case: use big decimal standard pow method
	if (isInteger(exponent) && exponent.compareTo(INT_MAX_VALUE) <= 0 && exponent.compareTo(INT_MIN_VALUE) >= 0) {
	    return number.pow(exponent.intValueExact());
	}
	// when best case not possible: try to use java standard Math.pow when possible
	if (number.compareTo(DOUBLE_MAX_VALUE) <= 0 && number.compareTo(DOUBLE_MIN_VALUE) >= 0
		&& exponent.compareTo(DOUBLE_MAX_VALUE) <= 0 && exponent.compareTo(DOUBLE_MIN_VALUE) >= 0) {
	    double result = Math.pow(number.doubleValue(), exponent.doubleValue());
	    if (!Double.isNaN(result) && Double.isFinite(result)) {
		return new BigDecimal(String.format("%.0f", result));
	    }
	}
	// worst case: use inaccurate (because of scaling) e^(ln(x*n))
	return BigFunctions.exp(BigFunctions.ln(number, 30 + number.scale()).multiply(exponent), 30 + number.scale());
    }

}
