package a10lib.beans;

import java.util.ArrayList;
import java.util.function.Consumer;

import a10lib.event.beansevt.ValueChangedEvent;

public class Property<T> implements Changable<T>{

    private T value;

    private ArrayList<Consumer<ValueChangedEvent<T>>> changedListeners = new ArrayList<>();

    /**
     * Create new instance of property
     */
    public Property() {
	// do nothing
    }

    /**
     * Create the property with the given value
     * 
     * @param initialValue
     *            the value of this property
     */
    public Property(T initialValue) {
	set(initialValue);
    }

    /**
     * @return the value of this property
     */
    public T get() {
	return value;
    }

    /**
     * Set the new value of this property
     * 
     * @param value
     *            the new value of this property
     */
    public void set(T value) {
	T old = this.value;
	if (value != old) {
	    this.value = value;
	    ValueChangedEvent<T> event = new ValueChangedEvent<>(System.currentTimeMillis(), old, value);
	    changedListeners.forEach(l -> l.accept(event));
	}
    }

    /**
     * Add a listener that trigger when the value of this property is changed
     * 
     * @param listener
     *            the listener that trigger when value of this property is changed
     */
    public void addChangedListener(Consumer<ValueChangedEvent<T>> listener) {
	changedListeners.add(listener);
    }

    /**
     * Remove a listener that trigger when the value of this property is changed
     * 
     * @param listener
     *            the listener to be removed
     */
    public void removeChangedListener(Consumer<ValueChangedEvent<T>> listener) {
	changedListeners.remove(listener);
    }

}
