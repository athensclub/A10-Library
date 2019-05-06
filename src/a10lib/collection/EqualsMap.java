package a10lib.collection;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Map class that uses only equals for comparing key to values without using
 * hash code.Not recommend because of low performance.This map uses ArrayList to
 * store all the entries of this map
 * 
 * @author Athensclub
 *
 * @param <K>:
 *            The key class type for this map
 * @param <V>:
 *            THe value class type for this map
 */
public class EqualsMap<K, V> extends AbstractMap<K, V> {

    private ArrayList<Entry<K, V>> entries = new ArrayList<>();

    private Set<Entry<K, V>> entrySet;

    @Override
    public Set<Entry<K, V>> entrySet() {

	if (entrySet == null) {
	    entrySet = new AbstractSet<Entry<K, V>>() {

		@Override
		public Iterator<Entry<K, V>> iterator() {
		    return entries.iterator();
		}

		@Override
		public int size() {
		    return entries.size();
		}

	    };
	}

	return entrySet;
    }

    @Override
    public V put(K key, V value) {
	V previous = get(key);
	if (previous != null) {
	    for (Entry<K, V> e : entries) {
		if (e.getKey().equals(key)) {
		    e.setValue(value);
		    break;
		}
	    }
	} else {
	    entries.add(new AbstractMap.SimpleEntry<K, V>(key, value));
	}
	return previous;
    }

}
