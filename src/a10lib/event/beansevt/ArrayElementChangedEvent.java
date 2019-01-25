package a10lib.event.beansevt;

/**
 * An event class representing a change in array element
 * 
 * @param <T>
 *            the type of element of the array that get its element changed
 * 
 * @author Athensclub
 *
 */
public class ArrayElementChangedEvent<T> {

    private T[] array;

    private T oldValue, newValue;

    private int index;

    private long when;

    /**
     * Create new instance of {@linkplain ArrayElemenetChangedEvent}
     * 
     * @param index
     *            the index of array that its changed element is store
     * @param arr
     *            the array that its element changed value
     * @param oldVal
     *            the old value of the element
     * @param newVal
     *            the new value of the element
     * @param when
     *            the time this change occurs
     */
    public ArrayElementChangedEvent(int index, T[] arr, T oldVal, T newVal, long when) {
	this.index = index;
	array = arr;
	oldValue = oldVal;
	newValue = newVal;
	this.when = when;
    }

    /**
     * Get the array that get its element changed
     * 
     * @return the array that get its element changed
     */
    public T[] getArray() {
	return array;
    }

    /**
     * Get the index of the element of the array that get changed
     * 
     * @return the index of the element of the array that get changed
     */
    public int getIndex() {
	return index;
    }

    /**
     * Get the new value of the element after its changed
     * 
     * @return the new value of the element after its changed
     */
    public T getNewValue() {
	return newValue;
    }

    /**
     * Get the old value of the element before its changed
     * 
     * @return the old value of the element before its changed
     */
    public T getOldValue() {
	return oldValue;
    }

    /**
     * The time this change occurs
     * 
     * @return time this change occurs
     */
    public long getWhen() {
	return when;
    }

}
