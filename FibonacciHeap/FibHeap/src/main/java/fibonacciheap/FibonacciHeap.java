package fibonacciheap;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class FibonacciHeap {

    public FibNode minNode;
    int size;

    public FibonacciHeap() {
	minNode = null;
	size = 0;
    }

    public FibNode getMinNode() {
	return minNode;
    }

    public int getSize() {
	return size;
    }

    public boolean isEmpty() {
	return minNode == null;
    }

    public void clear() {
	minNode = null;
	size = 0;
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

    public FibNode insert(int key) {
	FibNode node = new FibNode(key);
	minNode = unionRootsLists(minNode, node);
	size++;

	return node;
    }

    public void decreaseKey(FibNode node, int newKey) {
	if (newKey > node.key) {
	    throw new IllegalArgumentException(
		    "New key is lager then current one");
	} else if (newKey == node.key) {
	    return;
	}
	node.key = newKey;
	FibNode parentNode = node.parent;
	if (parentNode != null && node.key < parentNode.key) {
	    cut(node, parentNode);
	    cascadingCut(parentNode);
	}
	if (minNode.key > node.key) {
	    minNode.isMinimum = false;
	    node.isMinimum = true;
	    minNode = node;
	}
    }

    public void delete(FibNode node) {
	FibNode leastNode = new FibNode(Integer.MIN_VALUE);
	decreaseKey(node, leastNode.getKey());
	FibHeapExtractMin.extractMin(this);
    }

    private void cut(FibNode node, FibNode parent) {
	removeNode(node);
	parent.rank--;
	unionRootsLists(minNode, node);
	node.isMarked = false;
    }

    private void cascadingCut(FibNode parentNode) {
	FibNode parent = parentNode.parent;
	if (parent != null) {
	    if (!parentNode.isMarked) {
		parent.isMarked = true;
	    } else {
		cut(parentNode, parent);
		cascadingCut(parent);
	    }
	}
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

    protected void removeNode(FibNode node) {
	node.next.prev = node.prev;
	node.prev.next = node.next;
	node.next = node;
	node.prev = node;
	node = null;
    }

    protected void union(FibNode min, FibNode max) {
	max.next = min;
	max.prev = min.prev;
	min.prev.next = max;
	min.prev = max;
    }

    protected FibNode linkHeaps(FibNode min, FibNode max) {
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
	    return  list.peek() != null;
	}

	public FibNode next() {
	    return list.poll();
	}

	public void remove() {
	    throw new UnsupportedOperationException();
	}
    }

    public String toString() { 
	StringBuilder sb = new StringBuilder();
	if (minNode != null) {
	    sb.append(minNode.printNode(0));
	}
	return sb.toString();
    }
}