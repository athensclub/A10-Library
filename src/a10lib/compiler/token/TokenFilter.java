package a10lib.compiler.token;

import a10lib.util.Strings;

public interface TokenFilter {
    
    public static final TokenFilter DO_NOTHING = token -> {};
    
    public static final TokenFilter REMOVE_WHITESPACES = token -> Strings.removeBlankSpace(token);

    /**
     * Filter the given token ie. remove white spaces,etc.
     * @param token
     */
    public void filter(StringBuilder token);
    
}
