package a10lib.util.ref;

/**
 * A simple class wrapping a reference to another value
 * 
 * @author Athensclub
 *
 */
public class IntReference {

    public int value;

    /**
     * Create a reference to null value
     */
    public IntReference() {
    }

    /**
     * Create reference to the given initial value
     * 
     * @param initialValue
     *                         the value that will be referenced to
     */
    public IntReference(int initialValue) {
	value = initialValue;
    }

}
