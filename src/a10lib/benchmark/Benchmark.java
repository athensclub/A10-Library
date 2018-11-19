package a10lib.benchmark;

public class Benchmark {

    /**
     * Run the given runnable and return the time taken to run it
     * 
     * @param r
     * @return
     */
    public static double test(BenchmarkRun r) {
	long last = System.currentTimeMillis();
	r.run(0);
	long now = System.currentTimeMillis();
	return (now - last) / 1000.0;
    }

    /**
     * Run the given runnable up to the amount of times given and return the time
     * taken to run it
     * 
     * @param r
     * @param count:
     *            the times that the runnable is going to be run
     * @return
     */
    public static double test(BenchmarkRun r, int count) {
	if(count <= 0) {
	    throw new IllegalArgumentException("Invalid count of runnable test: " + count);
	}
	long last = System.currentTimeMillis();
	for (int i = 0; i < count; i++) {
	    r.run(i);
	}
	long now = System.currentTimeMillis();
	return (now - last) / 1000.0;
    }
    
    /**
     * Run the given runnable up to the amount of times given and print the time
     * taken to run it
     * 
     * @param r
     * @param count:
     *            the times that the runnable is going to be run
     * @return
     */
    public static void printTestResult(BenchmarkRun r, int count) {
	double result = test(r,count);
	System.out.println("Time Spent: " + result + " seconds");
    }
    
    /**
     * Run the given runnable up to the amount of times given and print the time
     * taken to run it with it name
     * 
     * @param r
     * @param count:
     *            the times that the runnable is going to be run
     * @return
     */
    public static void printTestResult(String name,BenchmarkRun r, int count) {
	double result = test(r,count);
	System.out.println("Time Spent Running " + name + ": " + result + " seconds");
    }
    
    
    
    /**
     * Run the given runnable up to the amount of times given and return the  average time
     * taken to run each of the runnable
     * 
     * @param r
     * @param count:
     *            the times that the runnable is going to be run
     * @return
     */
    public static double testAVG(BenchmarkRun r, int count) {
	return test(r,count) / count;
    }

}
