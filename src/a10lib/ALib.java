package a10lib;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ALib {

	/**
	 * Creates sub array from an existing array
	 * 
	 * @param list
	 *            An existing array that is used to created array
	 * @param begin
	 *            The index of the existing array that is used as beginning of the
	 *            subarray
	 * @param end
	 *            The index of the existing array that is used as end of the
	 *            subarray
	 *
	 * @return The subarray which contains element of exist array from begin index
	 *         to end index
	 */
	public static <T> T[] subArray(T[] list, int begin, int end) {
		// T[] result = Array.newInstance(arg0, arg1);
		return Arrays.copyOfRange(list, begin, end + 1);
	}

	/**
	 * 
	 * @param num
	 *            the number to check whether it is prime or not
	 * 
	 * @return whether the given number is prime or not
	 */
	public static boolean isPrime(int num) {

		// make the input absolute to make it easier to check
		num = Math.abs(num);

		// now that the number is absolute, any number below 2 is not prime
		if (num < 2) {
			return false;
		}

		// check by dividing every integer from 2 to input / 2 to check if it is
		// divisible by any of it
		for (int i = 2; i <= num / 2; i++) {

			// return fakse immediately after knowing that the input can be divided by other
			// number other than 1 and itself
			if (num % i == 0) {
				return false;
			}

		}

		// return the holding variable containing the value whether the input is prime
		// or not
		return true;
	}

	/**
	 * 
	 * @param num
	 * 			number to factorize
	 * @return list of all prime number multiplied to get the given number
	 */
	public static ArrayList<Integer> factorize(int num) {

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

}
