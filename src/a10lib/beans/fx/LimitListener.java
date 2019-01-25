package a10lib.beans.fx;

import a10lib.beans.Changable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * A listener that automatically limit a beans number between its min and max
 * value if its value changed
 * 
 * @author Athensclub
 *
 */
public class LimitListener implements ChangeListener<Number> {

    private double min = -Double.MAX_VALUE, max = Double.MAX_VALUE;

    /**
     * Get the maximum value of this limit bounds
     * 
     * @return the maximum value of this limit bounds
     */
    public double getMax() {
	return max;
    }

    /**
     * Get the minimum value of this limit bounds
     * 
     * @return the minimum value of this limit bounds
     */
    public double getMin() {
	return min;
    }

    /**
     * Set the maximum value of this limit bounds
     * 
     * @param max
     *            the maximum value of this limit bounds
     */
    public void setMax(double max) {
	if (min > max) {
	    throw new IllegalArgumentException("min:" + min + " > max:" + max);
	}
	this.max = max;
    }

    /**
     * Set the minimum value of this limit bounds
     * 
     * @param min
     *            the minimum value of this limit bounds
     */
    public void setMin(double min) {
	if (min > max) {
	    throw new IllegalArgumentException("min:" + min + " > max:" + max);
	}
	this.min = min;
    }

    /**
     * Set the limit bounds of this limit listener
     * 
     * @param min
     *            the minimum limit bounds of this limit listener
     * @param max
     *            the maximum limit bounds of this limit listener
     */
    public void setBounds(double min, double max) {
	if (min > max) {
	    throw new IllegalArgumentException("min:" + min + " > max:" + max);
	}
	this.min = min;
	this.max = max;
    }

    /**
     * Perform a limit to the given value using this limit listener's limit bounds
     * and return the limited value
     * 
     * @param value
     *            the value to get limited
     * @return the limited value
     */
    public double limit(double value) {
	return value > max ? max : value < min ? min : value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void changed(ObservableValue<? extends Number> obj, Number oldv, Number newv) {
	double limited = limit(newv.doubleValue());
	if (limited != newv.doubleValue()) {
	    if (obj instanceof DoubleProperty) {
		((DoubleProperty) obj).set(limited);
	    } else if (obj instanceof FloatProperty) {
		((FloatProperty) obj).set((float) limited);
	    } else if (obj instanceof IntegerProperty) {
		((IntegerProperty) obj).set((int) limited);
	    } else if (obj instanceof LongProperty) {
		((LongProperty) obj).set((long) limited);
	    } else if (obj instanceof Changable) {
		((Changable<Number>) obj).set(limited);
	    } else {
		throw new IllegalStateException("Add listener to unknown or unchangable property:" + obj);
	    }
	}
    }
}
