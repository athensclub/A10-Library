package a10lib.beans;

/**
 * Represent a class or enum that has index representation.
 * <p>
 * Used with ArrayProperty to get index from enum
 * </p>
 * 
 * @author Athensclub
 *
 */
public interface HasIndex {

    /**
     * Get the index that the object representing.Must be constant for enum
     * 
     * @return the index that the object representing
     */
    public int getIndex();

}
