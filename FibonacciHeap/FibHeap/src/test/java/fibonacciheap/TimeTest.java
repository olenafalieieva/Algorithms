package fibonacciheap;

//import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

public class TimeTest {
    private final FibonacciHeap<Integer> heap = new FibonacciHeap<Integer>();;

    @Before
    public void init() {
	heap.clear();
    }

    private static final Logger logger = Logger.getLogger("");
    private static void logInfo(Description description, String status, long nanos) {
	String testName = description.getMethodName();
	logger.info(String.format("Test %s %s, spent %d milliseconds",
		testName, status, TimeUnit.NANOSECONDS.toMillis(nanos)));
    }

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {

	@Override
	protected void succeeded(long nanos, Description description) {
	    logInfo(description, "succeeded", nanos);
	}
    };

    @Test
    public void testInsert() {
	System.out.println("Insert");
	for (int j = 2; j <= Math.pow(2, 15); j*=2) {
	    insert(j);

	    System.out.println("N = "+j+" Time = "+ stopwatch.runtime(TimeUnit.MILLISECONDS));
	}
    } 

    @Test
    public void testExtractMin() {
	System.out.println("Extracted Min");
	for (int j = 2; j <= Math.pow(2, 15); j*=2) {
	    extractMin(j);

	    System.out.println("N = "+j+" Time = "+ stopwatch.runtime(TimeUnit.MILLISECONDS));
	}
    }

    private void insert(Integer j) {
	for (Integer i = 0; i < j; i++) {
	    heap.insert(i);
	}
    }

    private void extractMin(long j) {
	for (Integer i = 0; i < j; i++) {
	    heap.insert(i);
	}
	for (long i = 0; i < j; i++) {
	    heap.extractMin();
	}
    }
}