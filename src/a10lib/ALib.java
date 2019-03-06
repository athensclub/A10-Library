package a10lib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ALib {

    private ALib() {
    }

    /**
     * Map a value that goes between min1 and max1 to a value that goes between min2
     * and max2
     * 
     * @param value:
     *            a value to be mapped
     * @param min1:
     *            minimum value of the given value
     * @param max1:
     *            maximum value of the given value
     * @param min2:
     *            minimum value of result value
     * @param max2:
     *            maximum value of result value
     * @return a value that goes between min2 and max2 that come from mapping from
     *         value that goes from min1 to max1
     */
    public static final double map(double value, double min1, double max1, double min2, double max2) {
	return min2 + (max2 - min2) * ((value - min1) / (max1 - min1));
    }

    /**
     * Clone the array and clone all the element inside the array if cloneable
     * 
     * @param clazz:
     *            the Class object of the element inside the array's class
     * @param arr:
     *            the array that is going to be cloned
     * @return the array that contains the clone of all the element inside existing
     *         array
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("unchecked")
    public static final <T extends Cloneable> T[] cloneElement(Class<T> clazz, T[] arr) throws NoSuchMethodException,
	    SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	T[] result = arr.clone();
	Method clone = clazz.getMethod("clone");
	for (int i = 0; i < arr.length; i++) {
	    result[i] = (T) clone.invoke(arr[i]);
	}
	return result;
    }

    /**
     * Map a value that goes between min1 and max1 to a value that goes between min2
     * and max2
     * 
     * @param value:
     *            a value to be mapped
     * @param min1:
     *            minimum value of the given value
     * @param max1:
     *            maximum value of the given value
     * @param min2:
     *            minimum value of result value
     * @param max2:
     *            maximum value of result value
     * @return a value that goes between min2 and max2 that come from mapping from
     *         value that goes from min1 to max1
     */
    public static final int map(int value, int min1, int max1, int min2, int max2) {
	return (int) map((double) value, (double) min1, (double) max1, (double) min2, (double) max2);
    }

    /**
     * Check if the given string contains only ASCII characters
     * 
     * @param str:
     *            string to check if it contains only ASCII characters
     * @return true if the given string contains only ASCII characters
     */
    public static final boolean isASCII(String str) {
	return str.matches("\\A\\p{ASCII}*\\z");
    }

    /**
     * Check if the given character is ASCII character
     * 
     * @param c:
     *            character to check if it is ASCII character
     * @return true if the given character is ASCII character
     */
    public static final boolean isASCII(char c) {
	return Character.toString(c).matches("\\A\\p{ASCII}*\\z");
    }

    public static long sqrt(long x) {
	if (x < 0)
	    throw new IllegalArgumentException("Square root of negative number");
	long y = 0;
	for (long i = 1L << 31; i != 0; i >>>= 1) {
	    y |= i;
	    if (y > 3037000499L || y * y > x)
		y ^= i;
	}
	return y;
    }

    /**
     * 
     * @param num
     *            the number to check whether it is prime or not
     * 
     * @return whether the given number is prime or not
     */
    public static final boolean isPrime(int x) {
	return isPrime((long) x);
    }

    public static final boolean isPrime(long x) {

	if (x < 0)
	    throw new IllegalArgumentException("Negative number");
	if (x == 0 || x == 1)
	    return false;
	else if (x == 2)
	    return true;
	else {
	    if (x % 2 == 0)
		return false;
	    for (int i = 3, end = (int) sqrt(x); i <= end; i += 2) {
		if (x % i == 0)
		    return false;
	    }
	    return true;
	}
    }

    public static boolean isParlindrome(String str) {
	return new StringBuilder(str).reverse().toString().equals(str);
    }

    /**
     * 
     * @param num
     *            number to factorize
     * @return list of all prime number multiplied to get the given number
     */
    public static final ArrayList<Integer> factorize(int num) {

	// creating array to hold the factorized numbers
	ArrayList<Integer> result = new ArrayList<>();

	// if the input is less than zero,then it is negative so we will make it
	// positive by multiplying -1 to the value and adding -1 to the result
	if (num < 0) {
	    result.add(-1);
	}

	// make the number absolute since we already checked whether the number is
	// negative
	num = Math.abs(num);

	// if the number is prime then there is no factor for this number so this
	// function will return an empty set
	if (isPrime(num)) {
	    return null;
	}

	// creating variable to holding the starting point of every iteration
	int start = 2;

	// keep looping until the number is prime
	while (!isPrime(num)) {

	    // looping the number to get the factor for each number
	    for (int i = start; i < num / 2; i++) {

		// if the number is divisible by another number then it must be factor of it so
		// we add it to the list and divide from the input
		if (num % i == 0) {
		    result.add(i);

		    // dividing the input so that it is factored out by the found factor
		    num /= i;
		    break;
		}

		// checking if the starting point of loop is high enough so that it take less
		// computation
		if (i > start) {
		    start = i;
		}

	    }

	}

	// add the remaining prime number into the result
	result.add(num);

	return result;

    }

    public static final ArrayList<Long> factorize(long num) {

	// creating array to hold the factorized numbers
	ArrayList<Long> result = new ArrayList<>();

	// if the input is less than zero,then it is negative so we will make it
	// positive by multiplying -1 to the value and adding -1 to the result
	if (num < 0) {
	    result.add(-1L);
	}

	// make the number absolute since we already checked whether the number is
	// negative
	num = Math.abs(num);

	// if the number is prime then there is no factor for this number so this
	// function will return an empty set
	if (isPrime(num)) {
	    return null;
	}

	// creating variable to holding the starting point of every iteration
	long start = 2;

	// keep looping until the number is prime
	while (!isPrime(num)) {

	    // looping the number to get the factor for each number
	    for (long i = start; i < num / 2; i++) {

		// if the number is divisible by another number then it must be factor of it so
		// we add it to the list and divide from the input
		if (num % i == 0) {
		    result.add(i);

		    // dividing the input so that it is factored out by the found factor
		    num /= i;
		    break;
		}

		// checking if the starting point of loop is high enough so that it take less
		// computation
		if (i > start) {
		    start = i;
		}

	    }

	}

	// add the remaining prime number into the result
	result.add(num);

	return result;

    }

}
