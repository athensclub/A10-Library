package a10lib.event.beansevt;

/**
 * A class representing event of object changing value
 * 
 * @author Athensclub
 *
 * @param <T>
 *            the type of object that is changed
 */
public class ValueChangedEvent<T> {
    
    private T oldValue,newValue;
    
    private long when;
    
    /**
     * Create new value changed event
     * @param when the time that this event occurs
     * @param oldValue the value before the change occurs
     * @param newValue the value after the change occurs
     */
    public ValueChangedEvent(long when,T oldValue,T newValue) {
	if(oldValue == newValue) {
	    throw new IllegalArgumentException("oldValue:" + oldValue + " = newValue:" + newValue);
	}
	this.when = when;
	this.oldValue = oldValue;
	this.newValue = newValue;
    }
    
    /**
     * 
     * @return The value after the change occurs
     */
    public T getNewValue() {
	return newValue;
    }
    
    /**
     * 
     * @return The value before the change occurs
     */
    public T getOldValue() {
	return oldValue;
    }
    
    /**
     * 
     * @return The time that this event occurs
     */
    public long getWhen() {
	return when;
    }

}
