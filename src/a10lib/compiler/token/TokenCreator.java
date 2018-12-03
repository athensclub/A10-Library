package a10lib.compiler.token;

public interface TokenCreator {
        
    /**
     * Create token from the given string
     * @param string
     * @return
     */
    public default Token createToken(StringBuilder string) {
	return new Token(string.toString());
    }
}
