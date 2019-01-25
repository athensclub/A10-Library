package a10lib.beans;

import java.util.ArrayList;
import java.util.function.Consumer;

import a10lib.event.beansevt.BooleanChangedEvent;

public class BooleanProperty implements Changable<Boolean>{

    private boolean value;

    private ArrayList<Consumer<BooleanChangedEvent>> changedListeners = new ArrayList<>();
    
    public BooleanProperty() {

    }

    public BooleanProperty(boolean initialValue) {
	set(initialValue);
    }

    /**
     * @return the value of this boolean property
     */
    public boolean get() {
	return value;
    }

    /**
     * Set the new value of this boolean property
     * 
     * @param value
     *            the new value of this boolean property
     */
    public void set(boolean value) {
	boolean old = this.value;
	if (old != value) {
	    this.value = value;
	    BooleanChangedEvent event = new BooleanChangedEvent(System.currentTimeMillis(), value);
	    changedListeners.forEach(l -> l.accept(event));

	}
    }

    /**
     * Add a listener that trigger when the value of this property is changed
     * 
     * @param e
     *            the listener that trigger when value of this property is changed
     */
    public void addChangedListener(Consumer<BooleanChangedEvent> e) {
	changedListeners.add(e);
    }

    /**
     * Remove a listener that trigger when the value of this property is changed
     * 
     * @param listener
     *            the listener to be removed
     */
    public void removeChangedListener(Consumer<BooleanChangedEvent> listener) {
	changedListeners.remove(listener);
    }

    @Override
    public void set(Boolean value) {
	set(value.booleanValue());
    }
    
}
