package a10lib.math.anumber;

import java.math.BigDecimal;

import libs.BigFunctions.BigFunctions;

/**
 * Represent a complex number consist of real number added by imaginary number
 * 
 * @author Athensclub
 *
 */
public class AComplex extends ANumber {

    /**
     * Implement only for AComplex,AFraction,ADecimal (and real-number based
     * ANumber)
     */

    /**
     * 
     */
    private static final long serialVersionUID = -6889921093493258982L;

    private ANumber real, imaginary;

    public AComplex(ANumber real, ANumber imaginary) {
	if (real instanceof AComplex) {
	    AComplex r = (AComplex) real;
	    // take real part of complex number as real part of this number if it does not
	    // contain imaginary number
	    if (r.imaginary.isZero()) {
		this.real = r.real;
	    } else {
		throw new IllegalArgumentException("Real number parts contains imaginary number: " + r);
	    }
	} else {
	    this.real = real;
	}
	if (imaginary instanceof AComplex) {
	    AComplex i = (AComplex) imaginary;
	    // take real part of complex number as real part of this number if it does not
	    // contain imaginary number
	    if (i.imaginary.isZero()) {
		this.real = i.real;
	    } else {
		throw new IllegalArgumentException("Imaginary number parts contains imaginary number: " + i);
	    }
	} else {
	    this.imaginary = imaginary;
	}
	this.imaginary = imaginary;
    }

    /**
     * Create complex number from given fraction value
     * 
     * @param fraction
     * @return
     */
    public static AComplex fromFraction(AFraction fraction) {
	return new AComplex(fraction, ADecimal.ZERO);
    }

    /**
     * Create complex number from given decimal value
     * 
     * @param decimal
     * @return
     */
    public static AComplex fromDecimal(ADecimal decimal) {
	return fromFraction(AFraction.fromDecimal(decimal));
    }

    @Override
    public boolean isNegative() {
	throw new IllegalArgumentException("Check for negative of imaginary number: " + this);
    }

    @Override
    public boolean isZero() {
	return real.isZero() && imaginary.isZero();
    }

    @Override
    public ANumber add(ANumber other) {
	if (other instanceof AComplex) {
	    return new AComplex(real.add(((AComplex) other).real), imaginary.add(((AComplex) other).imaginary));
	} else {
	    // assume the rest of class are real numbers
	    return new AComplex(real.add(other), imaginary);
	}
    }

    @Override
    public ANumber multiply(ANumber other) {
	if (other instanceof AComplex) {
	    ANumber c = ((AComplex) other).real;
	    ANumber d = ((AComplex) other).imaginary;
	    // (a+bi)(c+di) = ac - bd + (ad + bc)i
	    return new AComplex(real.multiply(c).subtract(imaginary.multiply(d)),
		    real.multiply(d).add(imaginary.multiply(c)));
	} else {
	    // assume the rest of class are real numbers
	    // (a+bi) * c = ac +bci
	    return new AComplex(real.multiply(other), imaginary.multiply(other));
	}
    }

    /**
     * Get the real number part of this complex number
     * 
     * @return
     */
    public ANumber getReal() {
	return real;
    }

    /**
     * Get the imaginary part of this complex number
     * 
     * @return
     */
    public ANumber getImaginary() {
	return imaginary;
    }

    @Override
    public ANumber reciprocal() {
	// (a-bi)/(a^2+b^2)
	return new AFraction(new AComplex(real, imaginary.negate()),
		imaginary.pow(ADecimal.TWO).add(real.pow(ADecimal.TWO)));
    }

    @Override
    public ANumber pow(ANumber other) {
	ANumber r = real.pow(ADecimal.TWO).add(imaginary.pow(ADecimal.TWO)).pow(AFraction.ONE_HALF);
	ADecimal theta = new ADecimal(BigFunctions.arctan(imaginary.divide(real).bigDecimalValue(), 30));
	
    }
    
    @Override
    public String toString() {
        return real + "+" + imaginary + "i";
    }

    @Override
    protected BigDecimal calculateBigDecimalValue() {
	if (imaginary.isZero()) {
	    return real.bigDecimalValue();
	}
	throw new IllegalArgumentException("Calculate decimal value for complex number: " + this);
    }

}
