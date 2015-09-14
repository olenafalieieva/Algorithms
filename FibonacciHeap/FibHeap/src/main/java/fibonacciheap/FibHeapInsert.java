package fibonacciheap;
import fibonacciheap.FibonacciHeap;
/**
 * public class FibonacciHeap {
    public FibNode minNode;
    int size;

    public FibonacciHeap() {
	minNode = null;
	size = 0;
    }
   public boolean isEmpty() {
	return minNode == null;
    }
    public int minKey() {
	if (isEmpty()) {
	    return -1;
	}
	return minNode.key;
    }
    public int minRank() {
	if (isEmpty()) {
	    return -1;
	}
	return minNode.rank;
    }
 */
/**
 * public class FibNode {
    int key;
    int rank;
    FibNode prev;
    FibNode next;
    FibNode parent;
    FibNode child;
    boolean isMarked;
    boolean isMinimum;

    public FibNode() {
	this.key = 0;
    }
    public FibNode(int key) {
	this.key = key;
	this.next = this;
	this.prev = this;
	rank = 0;
    }
    
 Problem
 Implement method for Fibonacci Heap data structure:
 FibNode insert(int key), method must update minNode pointer if necessary. 
  *
 */
public class FibHeapInsert {
    FibonacciHeap heap;

    public FibHeapInsert(FibonacciHeap heap) {
	this.heap = heap;
    }
    FibNode minNode = heap.minNode;
    
    public FibNode insert(int key) {

	FibNode node = new FibNode(key);
	minNode = unionRootsLists(minNode, node);
	heap.size++;

	return node;
    }

    private FibNode unionRootsLists(FibNode min, FibNode max) {
	if (min == null) {
	    return max;
	}
	if (max == null) {
	    return min;
	}
	union(min, max);

	FibNode minimNode;
	if (min.key < max.key) {
	    minimNode = min;
	} else {
	    minimNode = max;
	    max.isMinimum = true;
	    min.isMinimum = false;
	}

	return minimNode;
    }

    private void union(FibNode min, FibNode max) {
	max.next = min;
	max.prev = min.prev;
	min.prev.next = max;
	min.prev = max;
    }
}
