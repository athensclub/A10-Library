package a10lib.util.ref;

/**
 * A simple class wrapping a reference to another value
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
