package a10lib.util;

import java.math.BigDecimal;

/**
 * A utilitie class for numbers
 * 
 * @author Athensclub
 *
 */
public final class Numbers {

    private Numbers() {
    }

    /**
     * Check if given big decimal is an integer
     * 
     * @param decimal
     * @return
     */
    public static boolean isInteger(BigDecimal decimal) {
	return decimal.signum() == 0 || decimal.scale() <= 0 || decimal.stripTrailingZeros().scale() <= 0;
    }

}
