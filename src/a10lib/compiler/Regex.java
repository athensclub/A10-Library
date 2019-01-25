package a10lib.compiler;

/**
 * A class that has regular expression for common matching
 * 
 * @author Athensclub
 *
 */
public class Regex {

    /**
     * A regex that test for a number.
     * <p>
     * A number can be negative which has negative sign in front of it then any
     * number of digits then optionally has a floating point with any number of
     * digits.Should be used to match entire number string
     * </p>
     */
    public static final String NUMBER_REGEX = "-?[0-9]+(\\.[0-9]+)?";

    /**
     * A regex that test for racket identifier
     * <p>
     * A racket identifier is a token that can be used in racket to declare a
     * variable of any types (function,number,string etc.)
     * </p>
     */
    public static final String RACKET_IDENTIFIER_REGEX = "[a-zA-Z\\+\\-\\*/_=?][a-zA-Z\\+\\-\\*/0-9_=?]*";

}
