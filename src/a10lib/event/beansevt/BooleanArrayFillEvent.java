package a10lib.event.beansevt;

/**
 * A event representing a fill of an array
 * 
 * @author Athensclub
 *
 */
public class BooleanArrayFillEvent {
    private boolean[] array;
    private boolean fillValue;

    private int fromIndex, toIndex;

    private long when;

    /**
     * Create new instance of {@link ArrayFillEvent}
     * 
     * @param arr
     *            the array that get filled
     * @param fill
     *            the value that fill the array
     * @param from
     *            the start index(inclusive)
     * @param to
     *            the end index(exclusive)
     * @param when
     *            the time that this fill occurs
     */
    public BooleanArrayFillEvent(boolean[] arr, boolean fill, int from, int to, long when) {
	array = arr;
	fillValue = fill;
	fromIndex = from;
	toIndex = to;
	this.when = when;
    }

    /**
     * Get the array that get filled
     * 
     * @return the array that get filled
     */
    public boolean[] getArray() {
	return array;
    }

    /**
     * Get the value that fill the array
     * 
     * @return the value that fill the array
     */
    public boolean getFillValue() {
	return fillValue;
    }

    /**
     * Get the beginning index(inclusive) of the array part that get filled
     * 
     * @return the beginning index(inclusive) of the array part that get filled
     */
    public int getFromIndex() {
	return fromIndex;
    }

    /**
     * Get the ending index(exclusive) of the array part that get filled
     * 
     * @return the ending index(exclusive) of the array part that get filled
     */
    public int getToIndex() {
	return toIndex;
    }

    /**
     * Get the time that this fill occurs
     * 
     * @return the time that this fill occurs
     */
    public long getWhen() {
	return when;
    }

}
