package a10lib.util;

import java.util.function.Consumer;

/**
 * A utilities class that deal with java arrays
 * 
 * @author Athensclub
 *
 */
public final class ArrayUtils {

    private ArrayUtils() {
    }

    /**
     * Check whether all elements of the given array is null or not
     * 
     * @param array:
     *            the array to test
     * @return whether all elements of the given array is null or not
     */
    public static <T> boolean isEmpty(T[] array) {
	for (int i = 0; i < array.length; i++) {
	    if (array[i] != null) {
		return false;
	    }
	}
	return true;
    }

    /**
     * Count the number of null element in the given array
     * 
     * @param array
     *            the array to test
     * @return the number of null element in the given array
     */
    public static <T> int nullCount(T[] array) {
	int total = 0;
	for (int i = 0; i < array.length; i++) {
	    if (array[i] == null) {
		total++;
	    }
	}
	return total;
    }

    /**
     * Count the number of non-null element in the given array
     * 
     * @param array
     *            the array to test
     * @return the number of non-null element in the given array
     */
    public static <T> int nonNullCount(T[] array) {
	return array.length - nullCount(array);
    }

    /**
     * Check whether all of element in the given array is false or not
     * 
     * @param array
     *            the array to test
     * @return whether all of element in the given array is false or not
     */
    public static boolean isAllFalse(boolean[] array) {
	for (int i = 0; i < array.length; i++) {
	    if (array[i] == true) {
		return false;
	    }
	}
	return true;
    }

    /**
     * Perform consumer action on every element of the given array.<br>
     * <br>
     * This method use {@code for(int i = 0;i<array.length;i++)} to do for-each
     * iteration
     * 
     * @param array
     *            the array to get consumer accepted every element
     * @param consumer
     *            the consumer that will accept every elemenet of the array
     */
    public static <T> void forEach(T[] array, Consumer<T> consumer) {
	for (int i = 0; i < array.length; i++) {
	    consumer.accept(array[i]);
	}
    }

}
