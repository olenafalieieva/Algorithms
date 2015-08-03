package fibonacciheap;

import static org.junit.Assert.assertEquals;

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
    public void TestInsert() {
	for (Integer i = 0; i < 6400; i++) {
	    heap.insert(i);
	}
	final long delta = 5;
	assertEquals(5L, stopwatch.runtime(TimeUnit.MILLISECONDS), delta);
    }

    @Test
    public void TestExtractMin() {
	long total = 0;
	for (Integer i = 0; i < 1600; i++) {
	    heap.insert(i);
	    total++;
	}
	for (long i = 0; i < total; i++) {
	    heap.extractMin();
	}
	final long delta = 5;
	assertEquals(35L, stopwatch.runtime(TimeUnit.MILLISECONDS), delta);
    }
}