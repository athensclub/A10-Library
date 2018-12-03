package a10lib.collection;

import java.util.*;

public interface MathSet<T> extends Set<T>,Cloneable {

    public static <E> MathSet<E> from(Set<E> from){
	return new MathSet<E>() {

	    @Override
	    public boolean add(E arg0) {
		return from.add(arg0);
	    }

	    @Override
	    public boolean addAll(Collection<? extends E> arg0) {
		return from.addAll(arg0);
	    }

	    @Override
	    public void clear() {
		from.clear();
	    }

	    @Override
	    public boolean contains(Object arg0) {
		return from.contains(arg0);
	    }

	    @Override
	    public boolean containsAll(Collection<?> arg0) {
		return from.containsAll(arg0);
	    }

	    @Override
	    public boolean isEmpty() {
		return from.isEmpty();
	    }

	    @Override
	    public Iterator<E> iterator() {
		return from.iterator();
	    }

	    @Override
	    public boolean remove(Object arg0) {
		return from.remove(arg0);
	    }

	    @Override
	    public boolean removeAll(Collection<?> arg0) {
		return from.removeAll(arg0);
	    }

	    @Override
	    public boolean retainAll(Collection<?> arg0) {
		return from.removeAll(arg0);
	    }

	    @Override
	    public int size() {
		return from.size();
	    }

	    @Override
	    public Object[] toArray() {
		return from.toArray();
	    }

	    @Override
	    public <T> T[] toArray(T[] arg0) {
		return from.toArray(arg0);
	    }
	    
	    @Override
	    public String toString() {
		return from.toString();
	    }
	    
	    @SuppressWarnings("unchecked")
	    @Override
	    public Object clone() {
		try {
		    return from((Set<E>) from.getClass().getMethod("clone").invoke(from));
		} catch (Exception e) {
		    try {
			throw e;
		    } catch (Exception e1) {
			e1.printStackTrace();
		    }
		}
		throw new UnsupportedOperationException();
	    }
	    
	};
	    
	    
    }
    
    public default void intersect(Set<? extends T> other) {
	removeIf(obj->!contains(obj) || !other.contains(obj));
    }

    public default void union(Set<? extends T> other) {
	addAll(other);
    }

    public default void subtract(Set<? extends T> other) {
	removeAll(other);
    }
    
    public default Set<? extends T> complement(Set<? extends T> other) {
	MathSet<? extends T> copy = (MathSet<? extends T>)clone();
	copy.removeAll(other);
	return copy;
    }

    public Object clone();

}
