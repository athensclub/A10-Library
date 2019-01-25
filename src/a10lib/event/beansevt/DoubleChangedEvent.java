package a10lib.event.beansevt;

/**
 * A class representing a double variable value change event
 * 
 * @author Athensclub
 *
 */
public class DoubleChangedEvent {
    
    private long when;
    
    private double oldValue,newValue;
    
    /**
     * Create new double changed event
     * @param when the time that this event occur
     * @param oldValue the old value before changing
     * @param newValue new value after changing
     */
    public DoubleChangedEvent(long when,double oldValue,double newValue) {
	if(oldValue == newValue) {
	    throw new IllegalArgumentException("oldValue:" + oldValue + " = newValue:" + newValue);
	}
	this.when = when;
	this.oldValue = oldValue;
	this.newValue = newValue;
    }
    
    /**
     * 
     * @return New value after this event occur
     */
    public double getNewValue() {
	return newValue;
    }
    
    /**
     * 
     * @return Old value before this event occur
     */
    public double getOldValue() {
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
