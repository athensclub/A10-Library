package a10lib.util;

/**
 * Character utilities
 * 
 * @author Athensclub
 *
 */
public final class Characters {

    private Characters() {}
    
    /**
     * Check whether the given character is alphabet(in regex: [a-zA-Z])
     * @param c
     * @return
     */
    public static boolean isAlphabet(char c) {
	return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
    
}
