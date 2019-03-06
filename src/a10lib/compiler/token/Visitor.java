package a10lib.compiler.token;

/**
 * Visitor object visit a token in a token then create the result token
 * 
 * @author Athensclub
 *
 */
public interface Visitor {

    /**
     * Called when this visitor begin visiting
     * 
     * @param string
     */
    public void onBegin(StringBuilder string);

    /**
     * Receive next character in the stream
     * 
     * @param next
     */
    public void nextChar(Tokenizer tokenizer,char next);

    /**
     * Create token from the characters stream that this visitor received
     * @return
     */
    public Token createToken();

}
