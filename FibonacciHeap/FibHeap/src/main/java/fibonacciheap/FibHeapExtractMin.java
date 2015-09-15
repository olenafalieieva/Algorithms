package fibonacciheap;

import java.util.HashMap;
import java.util.Map;

import fibonacciheap.FibonacciHeap.NodeIterator;

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
    int key, rank;
    FibNode prev, next, parent, child;
    boolean isMarked, isMinimum;

    public FibNode() {
	this.key = 0;
    }
    public FibNode(int key) {
	this.key = key;
	this.next = this;
	this.prev = this;
	rank = 0;
    }

Problem:
 Implement methods for Fibonacci Heap data structure:
 FibNode extractMin(FibonacciHeap heap) and 
 void consolidate(FibNode minNode, FibonacciHeap heap)
 Helper: for simplification you can use these methods(see below): void removeNode(FibNode node), 
 FibNode linkHeaps(FibNode min, FibNode max) and class NodeIterator() to iterate throw the roots' or children's Linked List 
 */
public class FibHeapExtractMin {

    public static FibNode extractMin(FibonacciHeap heap) {
	FibNode minNode = heap.minNode;
	if (heap.isEmpty()) {
	    return null;
	}
	FibNode extractedMin = minNode;

	if (minNode == minNode.next && minNode.child == null) {
	    heap.size--; // should return size = 0
	    minNode = null;
	    heap.minNode = minNode;

	    return extractedMin;

	} else if (minNode != minNode.next && minNode.child == null) {
	    FibNode min = minNode.next;
	    heap.removeNode(minNode);
	    minNode = min;
	    consolidate(minNode, heap);
	    heap.size--;

	    return extractedMin;
	}

	FibNode startChild = minNode.child;
	FibNode newMin;
	newMin = minNode.next;
	heap.removeNode(minNode);

	NodeIterator it = new NodeIterator(startChild); // do children's LinkedList for iterating
	FibNode current = startChild;
	while (it.hasNext()) {
	    current = it.next();
	    current.parent = null;
	    heap.union(newMin, current);
	} 
	minNode = newMin;
	consolidate(minNode, heap);
	heap.size--;

	return extractedMin;
    }

    public static void consolidate(FibNode minNode, FibonacciHeap heap) {
	if (minNode == minNode.next && minNode.child == null) {
	    heap.size--; 
	    heap.minNode = minNode;
	    return;
	}  
	FibNode start = minNode;
	NodeIterator it = new NodeIterator(start);
	FibNode current = it.next();
	FibNode rankNode = null;
	int index = current.rank;

	Map<Integer, FibNode> rankMap = new HashMap<Integer, FibNode>();
	while (it.hasNext()) {
	    if (rankMap.containsKey(index)) {
		rankNode = rankMap.get(index);
		current = heap.linkHeaps(current, rankNode);
		rankMap.remove(index);
		index = current.rank;
	    } else {
		rankMap.put(index, current);
		current = it.next();
		index = current.rank;
	    }
	    if (current.key < minNode.key) {
		minNode = current;
	    }
	} 

	if (rankMap.containsKey(index)) {
	    rankNode = rankMap.get(index);
	    current = heap.linkHeaps(current, rankNode);
	    rankMap.remove(index);
	    index = current.rank;
	} 
	if (current.key < minNode.key) {
	    minNode = current;
	}

	heap.minNode = minNode;	
    }
}
/**
  public void removeNode(FibNode node) {
	node.next.prev = node.prev;
	node.prev.next = node.next;
	node.next = node;
	node.prev = node;
	node = null;
    }

    public FibNode linkHeaps(FibNode min, FibNode max) {
	if (min.key > max.key) {
	    FibNode temp = min;
	    min = max;
	    max = temp;
	}
	removeNode(max);
	max.parent = min;
	if (min.child == null) {
	    min.child = max;
	} else {
	    union(max, min.child);
	}
	max.isMarked = false;
	max.isMinimum = false;
	min.rank++;
	return min;
    }

    private void union(FibNode max, FibNode min) {
		max.next = min;
		max.prev = min.prev;
		min.prev.next = max;
		min.prev = max;
    	}
    }

    public static class NodeIterator implements
    Iterator<FibNode> {
	private Queue<FibNode> list = new LinkedList<FibNode>();

	public NodeIterator(FibNode start) {
	    if (start == null) {
		return;
	    }
	    FibNode current = start;
	    do {
		list.add(current);
		current = current.next;
	    } while (current != start);
	   
	}
	public boolean hasNext() {
	    return (!list.isEmpty());
	}
	public FibNode next() {
	    FibNode node = list.poll();
	    return node;
	}
    }
 */
