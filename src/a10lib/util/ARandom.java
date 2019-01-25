package a10lib.util;

import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * A wrapper class for java's default {@code Random} class that add extra
 * functionality
 * 
 * @author Athensclub
 *
 */
@SuppressWarnings("serial")
public class ARandom extends Random {

    private static final ARandom defaultRandom = new ARandom();

    /**
     * 
     * @return The ARandom object that is static and default
     */
    public static ARandom defaultRandom() {
	return defaultRandom;
    }

    /**
     * Create new instance of ARandom
     */
    public ARandom() {
	super();
    }

    /**
     * Create ARandom with the given seed
     * 
     * @param seed
     *            the seed of this ARandom
     */
    public ARandom(long seed) {
	super(seed);
    }

    /**
     * Randomize integer between the given bounds
     * 
     * @param min
     *            the minimum bounds of this randomization(inclusive)
     * @param max
     *            the maximum bounds of this randomization(exclusive)
     * @return random integer between the given bounds
     */
    public int nextInt(int min, int max) {
	if (min > max) {
	    throw new IllegalArgumentException("min:" + min + " > max:" + max);
	}
	return nextInt(max - min) + min;
    }

    /**
     * Randomize long between the given bounds
     * 
     * @param min
     *            the minimum bounds of this randomization(inclusive)
     * @param max
     *            the maximum bounds of this randomization(exclusive)
     * @return random long between the given bounds
     */
    public long nextLong(long min, long max) {
	if (min > max) {
	    throw new IllegalArgumentException("min:" + min + " > max:" + max);
	}
	long r = nextLong();
	long n = max - min, m = n - 1;
	if ((n & m) == 0L) // power of two
	    r = (r & m) + min;
	else if (n > 0L) { // reject over-represented candidates
	    for (long u = r >>> 1; // ensure nonnegative
		    u + m - (r = u % n) < 0L; // rejection check
		    u = nextLong() >>> 1) // retry
		;
	    r += min;
	} else { // range not representable as long
	    while (r < min || r >= max)
		r = nextLong();
	}
	return r;
    }

    /**
     * Randomize double between the given bounds
     * 
     * @param min
     *            the minimum bounds of this randomization(inclusive)
     * @param max
     *            the maximum bounds of this randomization(exclusive)
     * @return random double between the given bounds
     */
    public double nextDouble(double min, double max) {
	if (min > max) {
	    throw new IllegalArgumentException("min:" + min + " > max:" + max);
	}
	double r = nextDouble();
	r = r * (max - min) + min;
	if (r >= max) // correct for rounding
	    r = Math.nextDown(max);
	return r;
    }

    /**
     * Randomly get 1 element from the given array
     * 
     * @param array
     *            array to get random element from
     * @return random element from given array
     */
    public <T> T get(T[] array) {
	return array[nextInt(array.length)];
    }

    /**
     * Randomly get 1 element from the given list
     * 
     * @param list
     *            list to get random element from
     * @return random element from given list
     */
    public <T> T get(List<T> list) {
	return list.get(nextInt(list.size()));
    }

    /**
     * Randomly remove 1 element from the given list
     * 
     * @param list
     *            the list to remove random element from
     * @return random element removed from the given list
     */
    public <T> T remove(List<T> list) {
	return list.remove(nextInt(list.size()));
    }
}
