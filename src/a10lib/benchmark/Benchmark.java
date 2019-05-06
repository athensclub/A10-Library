package a10lib.benchmark;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * A class that test a run for an amount of count and test the amount of time
 * that run used.
 * <p>
 * All time unit ran in this benchmark are returned in seconds.
 * <p/>
 * 
 * @author Athensclub
 *
 */
public class Benchmark {

    /**
     * The clock used for stopwatching benchmark
     */
    public static Clock CLOCK = Clock.systemDefaultZone();

    /**
     * Run the given runnable and return the time taken to run it
     * 
     * @param r
     * @return
     */
    public static double test(BenchmarkRun r) {
	Instant last = CLOCK.instant();
	r.run(0);
	Instant now = CLOCK.instant();
	return last.until(now, ChronoUnit.NANOS) / 1000000000.0;
    }

    /**
     * Run the given runnable up to the amount of times given and return the time
     * taken to run it
     * 
     * @param r
     * @param   count:
     *              the times that the runnable is going to be run
     * @return
     */
    public static double test(BenchmarkRun r, int count) {
	if (count <= 0) {
	    throw new IllegalArgumentException("Invalid count of runnable test: " + count);
	}
	Instant last = CLOCK.instant();
	for (int i = 0; i < count; i++) {
	    r.run(i);
	}
	Instant now = CLOCK.instant();
	return last.until(now, ChronoUnit.NANOS) / 1000000000.0;
    }

    /**
     * Run the given runnable up to the amount of times given and print the time
     * taken to run it
     * 
     * @param r
     * @param   count:
     *              the times that the runnable is going to be run
     * @return
     */
    public static void printTestResult(BenchmarkRun r, int count) {
	double result = test(r, count);
	System.out.println("Time Spent: " + result + " seconds");
    }

    /**
     * Run the given runnable up to the amount of times given and print the time
     * taken to run it with it name
     * 
     * @param r
     * @param   count:
     *              the times that the runnable is going to be run
     * @return
     */
    public static void printTestResult(String name, BenchmarkRun r, int count) {
	double result = test(r, count);
	System.out.println("Time Spent Running " + name + ": " + result + " seconds");
    }

    /**
     * Run the given runnable up to the amount of times given and return the average
     * time taken to run each of the runnable
     * 
     * @param r
     * @param   count:
     *              the times that the runnable is going to be run
     * @return
     */
    public static double testAVG(BenchmarkRun r, int count) {
	return test(r, count) / count;
    }

}
