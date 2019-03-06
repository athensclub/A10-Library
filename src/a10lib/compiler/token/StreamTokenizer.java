package a10lib.compiler.token;

import java.io.IOException;
import java.io.Reader;

import javax.naming.OperationNotSupportedException;

/**
 * A {@code Tokenizer} class that tokenizes a stream of strings from
 * {@code Reader}
 * 
 * @author Athensclub
 *
 */
public class StreamTokenizer extends Tokenizer {

    private boolean eof;

    private Reader input;

    /**
     * Create tokenizer that tokenize the string from the stream
     * 
     * @param stream:
     *            the stream that is going to get tokenized
     */
    public StreamTokenizer(Reader stream) {
	input = stream;
    }

    /**
     * Reset the stream to be tokenized to be the given stream
     * 
     * @param reader
     */
    public void reset(Reader reader) {
	input = reader;
    }

    @Override
    protected char nextCharInStream() throws IOException {
	return (char) input.read();
    }

    @Override
    public boolean eof() {
	return eof;
    }

}
