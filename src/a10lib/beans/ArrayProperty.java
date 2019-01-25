package a10lib.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

import a10lib.event.beansevt.ArrayElementChangedEvent;
import a10lib.event.beansevt.ArrayFillEvent;
import a10lib.event.beansevt.ArrayResizeEvent;

/**
 * A beans class representing property of an array.<br>
 * <br>
 * Should not use {@link #get()} to change it's element values.As it will not
 * fire events
 * 
 * @author Athensclub
 *
 * @param <T>
 *            the type of element of this array property
 */
public class ArrayProperty<T> extends Property<T[]> {

    private ArrayList<Consumer<ArrayResizeEvent<T>>> resizeListeners = new ArrayList<>();

    private ArrayList<Consumer<ArrayFillEvent<T>>> fillListeners = new ArrayList<>();

    private ArrayList<Consumer<ArrayElementChangedEvent<T>>> elementChangedListeners = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public ArrayProperty(int size) {
	set((T[]) new Object[size]);
    }

    /**
     * add listener that trigger when this array property is resized
     * 
     * @param listener
     *            the listener that trigger when this array property is resized
     */
    public void addResizeListener(Consumer<ArrayResizeEvent<T>> listener) {
	resizeListeners.add(listener);
    }

    /**
     * remove a listener that trigger when this array property is resized
     * 
     * @param listener
     *            the listener to be removed
     */
    public void removeResizeListener(Consumer<ArrayResizeEvent<T>> listener) {
	resizeListeners.remove(listener);
    }

    /**
     * resize the array to the given size
     * 
     * @param newSize
     *            the new size of the array
     */
    @SuppressWarnings("unchecked")
    public void resize(int newSize) {
	if (newSize < 0) {
	    throw new ArrayStoreException("new size:" + newSize);
	}
	T[] oldArray = get();
	T[] newArray = (T[]) new Object[newSize];
	System.arraycopy(oldArray, 0, newArray, 0, (oldArray.length < newSize ? oldArray.length : newSize));
	set(newArray);
	ArrayResizeEvent<T> event = new ArrayResizeEvent<>(oldArray, newArray, System.currentTimeMillis());
	resizeListeners.forEach(l -> l.accept(event));
    }

    /**
     * Add a fill listener that triggers when part of this array get filled with a
     * value
     * 
     * @param listener
     *            a fill listener that triggers when part of this array get filled
     *            with a value
     */
    public void addFillListener(Consumer<ArrayFillEvent<T>> listener) {
	fillListeners.add(listener);
    }

    /**
     * Remove a fill listener that triggers when part of this array get filled with
     * a value
     * 
     * @param listener
     *            a listener to be removed
     */
    public void removeFillListener(Consumer<ArrayFillEvent<T>> listener) {
	fillListeners.remove(listener);
    }

    /**
     * Fill the array with the given value
     * 
     * @param object
     *            the value that will fill the array
     * @param fromIndex
     *            the starting index(inclusive) of array that going to get filled
     * @param toIndex
     *            the ending index(exclusive) of array that going to get filled
     */
    public void fill(T object, int fromIndex, int toIndex) {
	Arrays.fill(get(), fromIndex,toIndex,object);
	ArrayFillEvent<T> event = new ArrayFillEvent<>(get(), object, fromIndex, toIndex, System.currentTimeMillis());
	fillListeners.forEach(l -> l.accept(event));
    }

    /**
     * Fill the whole array with the given value
     * 
     * @param value
     *            the value to fill the array
     */
    public void fill(T value) {
	fill(value, 0, get().length);
    }

    /**
     * Add the listener that trigger the element changed listener that trigger when
     * the element value of the array is changed
     * 
     * @param listener
     *            the element changed listener that trigger when the element value
     *            of the array is changed
     */
    public void addElementChangedListener(Consumer<ArrayElementChangedEvent<T>> listener) {
	elementChangedListeners.add(listener);
    }

    /**
     * Remove the listener that trigger when the element changed listener that
     * trigger when the element value of the array is changed
     * 
     * @param listener
     *            the listener to be removed
     */
    public void removeElementChangedListener(Consumer<ArrayElementChangedEvent<T>> listener) {
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
    public void set(int index, T value) {
	T old = get()[index];
	if (old != value) {
	    get()[index] = value;
	    ArrayElementChangedEvent<T> event = new ArrayElementChangedEvent<T>(index, get(), old, value,
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
    public void set(HasIndex index, T value) {
	set(index.getIndex(),value);
    }

    /**
     * Perform the consumer action on every element of this array
     * 
     * @param consumer
     *            the consumer to perform action on every element of this array
     */
    public void forEach(Consumer<T> consumer) {
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
    public T get(int index) {
	return get()[index];
    }

    /**
     * Get the element of this array property at the given index
     * 
     * @param index
     *            the index of element that wanted
     * @return the element of this array property at the given index
     */
    public T get(HasIndex index) {
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
