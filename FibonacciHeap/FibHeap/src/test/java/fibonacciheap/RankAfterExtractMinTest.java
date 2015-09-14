package fibonacciheap;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class RankAfterExtractMinTest extends AbstractTest {

    int[] input;
    int expected;
    String str; 
    Runnable task = new Runnable() {
	@Override
	public void run() {
	    FibonacciHeap heap = new FibonacciHeap();

	    for (int num : input) {
		heap.insert(num);
	    }

	    FibHeapExtractMin.extractMin(heap);

	    str = heap.toString();
	    int actual = heap.minRank();
	    Assert.assertEquals(actual, expected);
	    if (actual != expected) {
		Common.error(String.valueOf(actual) + str);
	    }

	    System.out.println(lastInput());

	}
    };

    public RankAfterExtractMinTest (int[] input, int expected) {
	super("FibonacciHeap");
	this.input = input;
	this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
	return Arrays.asList(new Object[][]{
		{new int[] {1, 2}, 0},
		{new int[] {2, 5, 1}, 1},
		{new int[] {7, 2, 3, 30, 5, 15, 20, 4}, 2},
	});
    }

    @Override
    protected Runnable getTask() {
	return task;
    }

    @Override
    protected String lastInput() {
	return new StringBuilder()
	.append("\n")
	.append("Input: ")
	.append(Common.printArray(input))
	.append("\n")
	.append(str)
	.append("\nExpected: ")
	.append(expected)
	.toString();

    }
}