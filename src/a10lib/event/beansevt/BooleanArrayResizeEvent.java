package a10lib.event.beansevt;

/**
 * An event representing resizing of an array.<br>
 * <br>
 * As resizing array requires creating new array, this event also contain old
 * array and new array reference.
 * 
 * @author Athensclub
 *
 */
public class BooleanArrayResizeEvent {
    
    private boolean[] oldArray, newArray;

    private int oldSize, newSize;

    private long when;

    /**
     * Create new instance of array resize event
     * 
     * @param oldarr
     *            the old array before resizing
     * @param newarr
     *            the new array after resizing
     * @param when
     *            the time this event occurs
     */
    public BooleanArrayResizeEvent(boolean[] oldarr, boolean[] newarr, long when) {
	oldArray = oldarr;
	newArray = newarr;
	oldSize = oldArray.length;
	newSize = newArray.length;
	this.when = when;
    }

    /**
     * Get the new array before the resize occurs
     * 
     * @return the new array before the resize occurs
     * 
     */
    public boolean[] getNewArray() {
	return newArray;
    }

    /**
     * The length of the array after the resize occurs.<br>
     * Same as <code>{@link #getNewArray()}.length</code>
     * 
     * @return
     */
    public int getNewSize() {
	return newSize;
    }

    /**
     * Get the old array before the resize occurs
     * 
     * @return the old array before the resize occurs
     */
    public boolean[] getOldArray() {
	return oldArray;
    }

    /**
     * The length of the old array before the resize occurs.<br>
     * Same as <code>{@link #getOldArray()}.length</code>
     * 
     * @return The length of the old array before the resize occurs
     */
    public int getOldSize() {
	return oldSize;
    }

    /**
     * Get the time that this resizing occurs
     * 
     * @return the time that this resizing occurs
     */
    public long getWhen() {
	return when;
    }

}
