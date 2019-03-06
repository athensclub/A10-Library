package a10lib.util;

/**
 * Character utilities
 * 
 * @author Athensclub
 *
 */
public final class Characters {

    private Characters() {
    }

    /**
     * Check whether the given character is alphabet(in regex: [a-zA-Z])
     * 
     * @param c
     * @return
     */
    public static boolean isAlphabet(char c) {
	return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    /**
     * Get java's escaped value of that character for example: 'n' will be return
     * '\n'
     * 
     * @param c
     * @return
     */
    public static char getEscape(char c) {
	switch (c) {
	case 'b':
	    return ('\b');
	case 'n':
	    return ('\n');
	case 't':
	    return ('\t');
	case 'r':
	    return ('\r');
	case 'f':
	    return ('\f');
	case '"':
	    return ('"');
	case '\\':
	    return '\\';
	default:
	    throw new IllegalArgumentException("Illegal escape character: \\" + c);
	}
    }

}
