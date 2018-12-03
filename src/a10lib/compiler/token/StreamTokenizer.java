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

    /**
     * Default size of maximum token size that can be marked to get previous
     * character as this stream tokenizer use Reader.mark() to perform
     * previousChar()
     */
    public static final int DEFAULT_MAX_TOKEN_SIZE = 100;

    private boolean eof;

    private int index;

    private int maxTokenSize;

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
     * Get size of maximum token size that can be marked to get previous character
     * as this stream tokenizer use Reader.mark() to perform previousChar()
     * 
     * @return size of maximum token size that can be marked to get previous
     *         character as this stream tokenizer use Reader.mark() to perform
     *         previousChar()
     */
    public int getMaxTokenSize() {
	return maxTokenSize;
    }

    /**
     * Set size of maximum token size that can be marked to get previous character
     * as this stream tokenizer use Reader.mark() to perform previousChar()
     * 
     * @param maxTokenSize:
     *            the maximum size
     */
    public void setMaxTokenSize(int maxTokenSize) {
	this.maxTokenSize = maxTokenSize;
    }

    @Override
    public void previousChar() throws OperationNotSupportedException, IOException {
	if (!input.markSupported()) {
	    throw new OperationNotSupportedException("Input stream does not support mark(int)");
	} else if (current.length() < 1) {
	    throw new OperationNotSupportedException("Current Token String Stream length is 0");
	} else {
	    eof = false;
	    index--;
	    current.deleteCharAt(current.length() - 1);
	    input.reset();
	    for (int j = 0; j < index; j++) {
		input.read();
	    }
	}
    }

    @Override
    protected void nextChar() throws IOException {
	if (current.length() < 1) {
	    if (input.markSupported()) {
		index = 0;
		input.mark(maxTokenSize);
	    }
	}
	int read = input.read();
	if (read != -1) {
	    current.append((char) read);
	} else {
	    eof = true;
	}
	// System.out.println(current + " -> " + index);
	index++;
    }

    @Override
    public boolean eof() {
	return eof;
    }

}
