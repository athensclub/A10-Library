package a10lib.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

import a10lib.event.beansevt.ArrayElementChangedEvent;
import a10lib.event.beansevt.ArrayResizeEvent;
import a10lib.event.beansevt.BooleanArrayElementChangedEvent;
import a10lib.event.beansevt.BooleanArrayFillEvent;
import a10lib.event.beansevt.BooleanArrayResizeEvent;

public class BooleanArrayProperty extends Property<boolean[]> {

    private ArrayList<Consumer<BooleanArrayResizeEvent>> resizeListeners = new ArrayList<>();

    private ArrayList<Consumer<BooleanArrayFillEvent>> fillListeners = new ArrayList<>();

    private ArrayList<Consumer<BooleanArrayElementChangedEvent>> elementChangedListeners = new ArrayList<>();

    /**
     * Create a boolean array property with the given size
     * 
     * @param size
     *            the size of this array property
     */
    public BooleanArrayProperty(int size) {
	set(new boolean[size]);
    }

    /**
     * add listener that trigger when this array property is resized.
     * <p>
     * lambda recommended.
     * </p>
     * 
     * @param listener
     *            the listener that trigger when this array property is resized
     */
    public void addResizeListener(Consumer<BooleanArrayResizeEvent> listener) {
	resizeListeners.add(listener);
    }

    /**
     * remove a listener that trigger when this array property is resized
     * 
     * @param listener
     *            the listener to be removed
     */
    public void removeResizeListener(Consumer<ArrayResizeEvent<Boolean>> listener) {
	resizeListeners.remove(listener);
    }

    /**
     * resize the array to the given size
     * 
     * @param newSize
     *            the new size of the array
     */
    public void resize(int newSize) {
	if (newSize < 0) {
	    throw new ArrayStoreException("new size:" + newSize);
	}
	boolean[] oldArray = get();
	boolean[] newArray = new boolean[newSize];
	System.arraycopy(oldArray, 0, newArray, 0, (oldArray.length < newSize ? oldArray.length : newSize));
	set(newArray);
	BooleanArrayResizeEvent event = new BooleanArrayResizeEvent(oldArray, newArray, System.currentTimeMillis());
	resizeListeners.forEach(l -> l.accept(event));
    }

    /**
     * Add a fill listener that triggers when part of this array get filled with a
     * value.
     * <p>
     * lambda recommended.
     * </p>
     * 
     * @param listener
     *            a fill listener that triggers when part of this array get filled
     *            with a value
     */
    public void addFillListener(Consumer<BooleanArrayFillEvent> listener) {
	fillListeners.add(listener);
    }

    /**
     * Remove a fill listener that triggers when part of this array get filled with
     * a value
     * 
     * @param listener
     *            a listener to be removed
     */
    public void removeFillListener(Consumer<BooleanArrayFillEvent> listener) {
	fillListeners.remove(listener);
    }

    /**
     * Fill the array with the given value
     * 
     * 
     * @param object
     *            the value that will fill the array
     * @param fromIndex
     *            the starting index(inclusive) of array that going to get filled
     * @param toIndex
     *            the ending index(exclusive) of array that going to get filled
     */
    public void fill(boolean object, int fromIndex, int toIndex) {
	Arrays.fill(get(), object);
	BooleanArrayFillEvent event = new BooleanArrayFillEvent(get(), object, fromIndex, toIndex,
		System.currentTimeMillis());
	fillListeners.forEach(l -> l.accept(event));
    }

    /**
     * Fill the whole array with the given value
     * 
     * @param value
     *            the value to fill the array
     */
    public void fill(boolean value) {
	fill(value, 0, get().length);
    }

    /**
     * Add the listener that trigger the element changed listener that trigger when
     * the element value of the array is changed.
     * <p>
     * lambda recommended.
     * </p>
     * 
     * @param listener
     *            the element changed listener that trigger when the element value
     *            of the array is changed
     */
    public void addElementChangedListener(Consumer<BooleanArrayElementChangedEvent> listener) {
	elementChangedListeners.add(listener);
    }

    /**
     * Remove the listener that trigger when the element changed listener that
     * trigger when the element value of the array is changed
     * 
     * @param listener
     *            the listener to be removed
     */
    public void removeElementChangedListener(Consumer<BooleanArrayElementChangedEvent> listener) {
	elementChangedListeners.remove(listener);
    }

    /**
     * Set the given index with the given value
     * 
     * @param index
     *            the index of array
     * @param value
     *            the value to set the element of array to
     */
    public void set(int index, boolean value) {
	boolean old = get()[index];
	if (old != value) {
	    get()[index] = value;
	    BooleanArrayElementChangedEvent event = new BooleanArrayElementChangedEvent(index, get(), old, value,
		    System.currentTimeMillis());
	    elementChangedListeners.forEach(l -> l.accept(event));
	}
    }
    
    /**
     * Set the given index with the given value
     * 
     * @param index
     *            the index of array
     * @param value
     *            the value to set the element of array to
     */
    public void set(HasIndex index, boolean value) {
	set(index.getIndex(),value);
    }

    /**
     * Perform the consumer action on every element of this array
     * 
     * @param consumer
     *            the consumer to perform action on every element of this array
     */
    public void forEach(Consumer<Boolean> consumer) {
	for (int i = 0; i < get().length; i++) {
	    consumer.accept(get()[i]);
	}
    }

    /**
     * Get the element of this array property at the given index
     * 
     * @param index
     *            the index of element that wanted
     * @return the element of this array property at the given index
     */
    public boolean get(int index) {
	return get()[index];
    }
    
    /**
     * Get the element of this array property at the given index
     * 
     * @param index
     *            the index of element that wanted
     * @return the element of this array property at the given index
     */
    public boolean get(HasIndex index) {
	return get()[index.getIndex()];
    }

    /**
     * Get the length of the array in this array property
     * 
     * @return the length of the array in this array property
     */
    public int length() {
	return get().length;
    }
    
    @Override
    public String toString() {
	return Arrays.toString(get());
    }
    
}
