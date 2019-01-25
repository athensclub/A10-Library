package a10lib.util;

/**
 * A simple class wrapping a reference to another value.<br>
 * <br>
 * The implementation of this class is simply <br>
 * <br>
 * <code>
 * public class Reference{@literal <T>}{<br>
 * <br>
 * 	public T value;<br>
 * <br>
 * 	public Reference() {<br>
 * 	<br>
 * 	}<br>
 * <br>
 * 	public Reference(T initialValue) { <br>
 * 		value = initialValue; <br>
 * 	} <br>
 * <br>
 * }<br>
 * </code>
 * 
 * @author Athensclub
 *
 * @param <T>
 *            a type of value this reference is holding
 */
public class Reference<T> {

    public T value;

    /**
     * Create a reference to null value
     */
    public Reference() {
    }

    /**
     * Create reference to the given initial value
     * 
     * @param initialValue
     *            the value that will be referenced to
     */
    public Reference(T initialValue) {
	value = initialValue;
    }

}
