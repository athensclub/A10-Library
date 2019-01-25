package a10lib.event.beansevt;

/**
 * A class representing change of boolean value
 * 
 * @author Athensclub
 *
 */
public class BooleanChangedEvent {

    private long when;

    private boolean oldValue, newValue;

    /**
     * Create new instance of boolean changed event
     * 
     * @param when
     *            the time that this event occur
     * @param newValue
     *            the new value after the change occur
     */
    public BooleanChangedEvent(long when, boolean newValue) {
	oldValue = !newValue;
	this.when = when;
	this.newValue = newValue;
    }

    public long getWhen() {
	return when;
    }

    /**
     * Get the old value before the changed occur.<br>
     * <br>
     * Same as <code>!{@link #getNewValue()}</code>.
     * 
     * @return the old value before the changed occur
     */
    public boolean getOldValue() {
	return oldValue;
    }

    /**
     * Get the new value after the change occur.<br>
     * <br>
     * Same as <code>!{@link #getOldValue()}</code>.
     * 
     * @return the new value after the change occur
     */
    public boolean getNewValue() {
	return newValue;
    }

}
