package fibonacciheap;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class FibHeapInsertTest extends AbstractTest{
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
	    int actual = heap.minKey();
	    str = heap.toString();
	    Assert.assertEquals(actual, expected);
	    if (actual != expected) {
		Common.error(String.valueOf(actual) + str);
	    }
	    System.out.println(lastInput());
	}
    };

    public FibHeapInsertTest(int[] input1, int expected1) {
	super("FibonacciHeap");
	this.input = input1;
	this.expected = expected1;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
	return Arrays.asList(new Object[][]{
		{new int[] {1, 2}, 1},
		{new int[] {5, 2, 3}, 2}, // returns minKey() after minNode was extracted
		{new int[] {100, 2, 3}, 2},
		{new int[] {10, 20, 3, 4, 0, 9}, 0},
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
