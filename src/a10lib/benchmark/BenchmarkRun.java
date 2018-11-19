package a10lib.benchmark;

public interface BenchmarkRun {

    /**
     * Run this benchmark given the order of this benchmark in a loop as index
     * 
     * @param count
     */
    public void run(int index);

}
