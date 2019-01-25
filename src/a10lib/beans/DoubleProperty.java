package a10lib.beans;

import java.util.ArrayList;
import java.util.function.Consumer;

import a10lib.event.beansevt.DoubleChangedEvent;
import a10lib.event.beansevt.ValueChangedEvent;
import javafx.beans.value.ChangeListener;

/**
 * A bean class for double values.<br>
 * <br>
 * Base on javafx beans.
 * 
 * @author Athensclub
 *
 */
public class DoubleProperty implements Changable<Number>{

    private double value;

    private double min = -Double.MAX_VALUE, max = Double.MAX_VALUE;

    private ArrayList<Consumer<DoubleChangedEvent>> changedListeners = new ArrayList<>();
    
    public DoubleProperty() {

    }

    public DoubleProperty(double initialValue) {
	set(initialValue);
    }

    /**
     * @return the value of this double property
     */
    public double get() {
	return value;
    }

    /**
     * Set maximum value that this double value can be
     * 
     * @param max
     *            the maximum value that this max value can be
     */
    public void setMax(double max) {
	if (max < min) {
	    throw new IllegalArgumentException("max:" + max + " < min:" + min);
	}
	this.max = max;
	// update the value
	set(value);
    }

    /**
     * Set the minimum value that this value can be
     * 
     * @param min
     *            the minimum value that this value can be
     */
    public void setMin(double min) {
	if (min > max) {
	    throw new IllegalArgumentException("min:" + min + " > max:" + max);
	}
	this.min = min;
	// update the value
	set(value);
    }

    /**
     * Set limit of minimum and maximum value of this property
     * 
     * @param min
     *            minimum value this property can be
     * @param max
     *            maximum value this property can be
     */
    public void setBounds(double min, double max) {
	if (min > max) {
	    throw new IllegalArgumentException("min:" + min + " > max:" + max);
	}
    }

    /**
     * Set the new value of this double property
     * 
     * @param value
     *            the new value of this double property
     */
    public void set(double value) {
	double old = this.value;
	if (value > max) {
	    value = max;
	}
	if (value < min) {
	    value = min;
	}
	if (old != value) {
	    this.value = value;
	    DoubleChangedEvent event = new DoubleChangedEvent(System.currentTimeMillis(), old, value);
	    changedListeners.forEach(l -> l.accept(event));

	}
    }

    /**
     * Add a listener that trigger when the value of this property is changed
     * 
     * @param e
     *            the listener that trigger when value of this property is changed
     */
    public void addChangedListener(Consumer<DoubleChangedEvent> e) {
	changedListeners.add(e);
    }

    /**
     * Remove a listener that trigger when the value of this property is changed
     * 
     * @param listener
     *            the listener to be removed
     */
    public void removeChangedListener(Consumer<DoubleChangedEvent> listener) {
	changedListeners.remove(listener);
    }

    @Override
    public void set(Number value) {
	set(value.doubleValue());
    }

}
