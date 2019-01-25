package a10lib.beans;

/**
 * A class that can change its beans value
 * 
 * @author Athensclub
 *
 * @param <T>
 *            the type of beans value that it can change
 */
public interface Changable<T> {

    public void set(T value);
    
}
