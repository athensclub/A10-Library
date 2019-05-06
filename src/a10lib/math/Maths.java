package a10lib.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * A utilities class for mathematical calculation
 * 
 * @author Athensclub
 *
 */
public final class Maths {

    /**
     * First 32 digits of mathematical constant pi
     */
    public static final BigDecimal PI = new BigDecimal("3.14159265358979323846264338327950");

    /**
     * 32 digits of mathematical constant e
     */
    public static final BigDecimal E = new BigDecimal("2.71828182845904523536028747135266");

    public static final BigDecimal TWO = BigDecimal.valueOf(2);

    public static final BigDecimal TWO_PI = PI.multiply(TWO);

    public static final BigDecimal PI_OVER_180 = PI.divide(BigDecimal.valueOf(180), MathContext.DECIMAL128);

    public static final BigDecimal N180_OVER_PI = BigDecimal.valueOf(180).divide(PI, MathContext.DECIMAL128);

    private Maths() {
    }

    public static boolean isNegative(BigDecimal val) {
	return val.signum() < 0;
    }

    /**
     * Convert degrees to radians
     * 
     * @param deg
     * @return
     */
    public static BigDecimal toRadian(BigDecimal deg) {
	return deg.multiply(PI_OVER_180);
    }

    /**
     * Convert radians to degrees
     * 
     * @param rad
     * @return
     */
    public static BigDecimal toDegree(BigDecimal rad) {
	return rad.multiply(N180_OVER_PI);
    }

    /**
     * Get integer part of number to the left of the decimal point
     * 
     * @param val
     * @return
     */
    public static BigDecimal integerPart(BigDecimal val) {
	return val.setScale(0, RoundingMode.DOWN);
    }

    /**
     * Get the fractional part of big decimal to the right of decimal point
     * 
     * @param val
     * @return
     */
    public static BigDecimal decimalPart(BigDecimal val) {
	return val.subtract(integerPart(val));
    }

    /**
     * Check if the given value is integer or not.
     * 
     * @param val
     * @return
     */
    public static boolean isInteger(BigDecimal val) {
	return val.signum() == 0 || val.scale() <= 0 || val.stripTrailingZeros().scale() <= 0;
    }

    private  static BigDecimal powInt(BigDecimal base,BigDecimal exp) {
	if(exp.signum() < 0) {
	    return BigDecimal.ONE.divide(powInt(base,exp.negate()),MathContext.DECIMAL128);
	}
	if(exp.signum() == 0) {
	    return BigDecimal.ONE;
	}
	BigInteger n = exp.toBigIntegerExact();
	BigDecimal result = BigDecimal.ONE;
	while(n.signum() != 0) {
	    if(n.and(BigInteger.ONE).signum() != 0) {
		result = result.multiply(base);
		n = n.subtract(BigInteger.ONE);
	    }
	    base = base.multiply(base);
	    n = n.shiftRight(1);
	}
	return result;
    }
    
    /**
     * Return natural exponent of value e^val.
     * 
     * @param val
     * @return
     */
    public static BigDecimal exp(BigDecimal val) {
	if (val.signum() < 0) {
	    return BigDecimal.ONE.divide(exp(val.negate()), MathContext.DECIMAL128);
	}
	BigDecimal integer = integerPart(val);
	BigDecimal decimal = val.subtract(integer);
	if (integer.signum() == 0) {
	    return expTaylor(decimal);
	}
	// e^decimal part, use taylor series
	BigDecimal expdec = expTaylor(decimal);
	// e^integer part use basic exponentiation
	BigDecimal expint = powInt(E, integer);
	return expdec.multiply(expint).round(MathContext.DECIMAL128); // e^dec part * e^int part = e^dec + int = e^val
    }

    /**
     * Find e^x for |x| < 1 using taylor series
     * 
     * @param val
     * @return
     */
    private static BigDecimal expTaylor(BigDecimal val) {
	if (val.abs().compareTo(BigDecimal.ONE) > 0) {
	    throw new IllegalArgumentException("expTaylor(x) | |x| > 1");
	}
	BigDecimal sum = BigDecimal.ONE;
	BigDecimal fact = BigDecimal.ONE;
	BigDecimal factc = BigDecimal.ONE;
	BigDecimal expt = val;
	for (int i = 0; i < 20; i++) {
	    sum = sum.add(expt.divide(fact, MathContext.DECIMAL128));
	    factc = factc.add(BigDecimal.ONE);
	    fact = fact.multiply(factc);
	    expt = expt.multiply(val);
	}
	return sum.round(MathContext.DECIMAL128);
    }

    /**
     * Find the tan of the value using sin and cos which use taylor series
     * 
     * @param val
     * @return
     */
    public static BigDecimal tan(BigDecimal val) {
	return sin(val).divide(cos(val), MathContext.DECIMAL128);
    }

    /**
     * Calculate cos of big decimal using taylor series
     * 
     * @param val
     * @return
     */
    public static BigDecimal cos(BigDecimal val) {
	if (val.abs().compareTo(TWO_PI) > 0) {
	    return cos(val.remainder(TWO_PI));
	}
	BigDecimal expt = BigDecimal.ONE;
	BigDecimal sum = BigDecimal.ZERO;
	BigDecimal fact = BigDecimal.ONE;
	BigDecimal factc = BigDecimal.ZERO;
	BigDecimal valsq = val.pow(2);
	for (int i = 0; i < 20; i++) {
	    sum = sum.add(expt.divide(fact, MathContext.DECIMAL128));
	    // increase x exponent and negate
	    expt = expt.multiply(valsq).negate();
	    // increase factorial
	    factc = factc.add(BigDecimal.ONE);
	    fact = fact.multiply(factc);
	    factc = factc.add(BigDecimal.ONE);
	    fact = fact.multiply(factc);
	}
	return sum.round(MathContext.DECIMAL128);
    }

    /**
     * Calculate sin of big decimal using taylor series
     * 
     * @param val
     * @return
     */
    public static BigDecimal sin(BigDecimal val) {
	if (val.abs().compareTo(TWO_PI) > 0) {
	    return sin(val.remainder(TWO_PI));
	}
	BigDecimal sum = BigDecimal.ZERO;
	BigDecimal fact = BigDecimal.ONE;
	BigDecimal factc = BigDecimal.ONE;
	BigDecimal expt = val;
	BigDecimal valsq = val.multiply(val);
	for (int i = 0; i < 20; i++) {
	    sum = sum.add(expt.divide(fact, MathContext.DECIMAL128));
	    // increase x exponent and negate
	    expt = expt.multiply(valsq).negate();
	    // increase factorial
	    factc = factc.add(BigDecimal.ONE);
	    fact = fact.multiply(factc);
	    factc = factc.add(BigDecimal.ONE);
	    fact = fact.multiply(factc);
	}
	return sum.round(MathContext.DECIMAL128);
    }

    /**
     * clamping is the process of limiting a position to an area. Unlike wrapping,
     * clamping merely moves the point to the nearest available value.
     * <p>
     * To put clamping into perspective, pseudocode for clamping is as follows:
     * Pseudocode (clamping):
     * </p>
     * <code>
     * function clamp(x, min, max): <br/>
     * if (x < min) then <br/>
     * x = min;<br/>
     * else if (x > max) then<br/>
     *   x = max;<br/>
     * return x;<br/>
     * end clamp<br/>
     * </code>
     * 
     * @see https://en.wikipedia.org/wiki/Clamping_(graphics)
     * 
     * @param val
     * @param min
     * @param max
     * @return
     */
    public static int clamp(int val, int min, int max) {
	return val < min ? min : val > max ? max : val;
    }

}
