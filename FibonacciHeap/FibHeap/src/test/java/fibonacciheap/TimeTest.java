package fibonacciheap;

//import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import fibonacciheap.FibonacciHeap.Node;

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
	    insertTest(j);
	    System.out.println("N = "+j+" Time = "+ stopwatch.runtime(TimeUnit.MILLISECONDS));
	}
    } 

    @Test
    public void testExtractMin() {
	System.out.println("Extracted Min");
	for (int j = 2; j <= Math.pow(2, 15); j*=2) {
	    extractMinTest(j);
	    System.out.println("N = "+j+" Time = "+ stopwatch.runtime(TimeUnit.MILLISECONDS));
	}
    }

    @Test
    public void testDecreaseKey() {
	System.out.println("Decrease Key");
	for (int j = 2; j <= Math.pow(2, 15); j*=2) {
	    Node<Integer> node = heap.insert(j+1);
	    insertTest(j);
	    heap.decreaseKey(node, 0);
	    System.out.println("N = "+j+" Time = "+ stopwatch.runtime(TimeUnit.MILLISECONDS));
	}
    }

    @Test
    public void testDelete() {
	System.out.println("Delete");
	for (int j = 2; j <= Math.pow(2, 15); j*=2) {
	    insertTest(j);
	    Node<Integer> node = heap.insert(j+1);
	    heap.delete(node);
	    System.out.println("N = "+j+" Time = "+ stopwatch.runtime(TimeUnit.MILLISECONDS));
	}
    }

    
    private void insertTest(Integer j) {
	for (Integer i = 0; i < j; i++) {
	    heap.insert(i);
	}
    }

    private void extractMinTest(long j) {
	for (Integer i = 0; i < j; i++) {
	    heap.insert(i);
	}
	for (long i = 0; i < j; i++) {
	    heap.extractMin();
	}
    }
}