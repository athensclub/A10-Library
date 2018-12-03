package a10lib.compiler.token;

/**
 * a {@code Tokenizer} that tokenizes a string
 * 
 * @author Athensclub
 *
 */
public class StringTokenizer extends Tokenizer {

    private String string;

    private int index;

    /**
     * Create new string tokenizer with the given string to be tokenized
     * 
     * @param str:
     *            the string to be tokenized
     */
    public StringTokenizer(String str) {
	string = str;
    }

    @Override
    public boolean eof() {
	return index == string.length();
    }

    @Override
    public void previousChar() {
	index--;
	current.deleteCharAt(current.length() - 1);
    }

    @Override
    protected void nextChar() {
	current.append(string.charAt(index++));
    }

}
