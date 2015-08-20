package fibonacciheap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class FibonacciHeap<E extends Comparable<E>> {

    Node<E> minNode;
    int size;

    public FibonacciHeap() {
	minNode = null;
	size = 0;
    }

    public Node<E> getMinNode() {
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

    public E minKey() {
	if (isEmpty()) {
	    return null;
	}
	return minNode.key;
    }

    public int minRank() {
	if (isEmpty()) {
	    return -1;
	}
	return minNode.rank;
    }

    public Node<E> insert(E key) {
	Node<E> node = new Node<E>(key);
	minNode = unionRootsLists(minNode, node);
	size++;

	return node;
    }

    public Node<E> extractMin() {
	if (isEmpty()) {
	    return null;
	}
	Node<E> min = minNode;
	Node<E> extractedMin = minNode;
	Node<E> newMin;
	if (min == min.next && min.child == null) {
	    size--; // should return size = 0
	    minNode = null;

	    return extractedMin;

	} else if (min != min.next && min.child == null) {
	    minNode = minNode.next;
	    removeNode(min);
	    consolidate();
	    size--;

	    return extractedMin;
	}

	Node<E> startChild = minNode.child;
	newMin = minNode.next;
	removeNode(minNode);
	Node<E> start = startChild; // children's LinkedList is needed
	NodeIterator<E> it = new NodeIterator<E>(start);

	do {
	    union(newMin, start);
	    start.parent = null;
	    if (it.next() != null) {
		newMin = start;
		start = it.next();
	    }
	} while (it.hasNext());

	minNode = newMin;
	consolidate();
	size--;

	return extractedMin;
    }

    public void decreaseKey(Node<E> node, E newKey) {
	if (newKey.compareTo(node.key) > 0) {
	    throw new IllegalArgumentException(
		    "New key is lager then current one");
	}
	node.key = newKey;
	Node<E> parentNode = node.parent;
	if (parentNode != null && node.key.compareTo(parentNode.key) < 0) {
	    cut(node, parentNode);
	    cascadingCut(parentNode);
	}
	if (minNode.compareTo(node) > 0) {
	    minNode.isMinimum = false;
	    node.isMinimum = true;
	    minNode = node;
	}
    }

    public void delete(Node<E> node) {
	@SuppressWarnings("unchecked")
	Node<E> leastNode = new Node(Integer.MIN_VALUE);
	decreaseKey(node, leastNode.getKey());
	extractMin();
    }

    private void cut(Node<E> node, Node<E> parent) {
	removeNode(node);
	parent.rank--;
	unionRootsLists(minNode, node);
	node.isMarked = false;
    }

    private void cascadingCut(Node<E> parentNode) {
	Node<E> parent = parentNode.parent;
	if (parent != null) {
	    if (!parentNode.isMarked) {
		parent.isMarked = true;
	    } else {
		cut(parentNode, parent);
		cascadingCut(parent);
	    }
	}
    }

    private Node<E> unionRootsLists(Node<E> min, Node<E> max) {
	if (min == null) {
	    return max;
	}
	if (max == null) {
	    return min;
	}
	union(min, max);

	Node<E> minimNode;
	if (min.compareTo(max) <= 0) {
	    minimNode = min;
	} else {
	    minimNode = max;
	    max.isMinimum = true;
	    min.isMinimum = false;
	}

	return minimNode;
    }

    private void removeNode(Node<E> node) {
	node.next.prev = node.prev;
	node.prev.next = node.next;
	node.next = node;
	node.prev = node;
	node = null;
    }

    private void union(Node<E> min, Node<E> max) {
	if (min == null) {
	    return;
	}
	if (max == null) {
	    return;
	}
	max.next = min;
	max.prev = min.prev;
	min.prev.next = max;
	min.prev = max;
    }

    private void consolidate() {
	Node<E> start = minNode;
	Node<E> current = minNode;
	Node<E> rankNode = null;
	int index = current.rank;
	NodeIterator<E> it = new NodeIterator<E>(start);
	Map<Integer, Node<E>> rankMap = new HashMap<Integer, Node<E>>();
	do {
	    if (rankMap.containsKey(index)) {
		rankNode = rankMap.get(index);
		current = linkHeaps(current, rankNode);
		rankMap.remove(index);
		index = current.rank;
	    } else {
		rankMap.put(index, current);
		if (it.hasNext()) {
		    current = it.next();
		}
		index = current.rank;
	    }
	    if (current.compareTo(minNode) < 0) {
		minNode = current;
	    }
	} while (it.hasNext());
    }

    private Node<E> linkHeaps(Node<E> min, Node<E> max) {
	if (min.compareTo(max) >= 0) {
	    Node<E> temp = min;
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

    static class Node<E extends Comparable<E>> implements Comparable<Node<E>> {

	private E key;
	int rank;
	private Node<E> prev;
	private Node<E> next;
	private Node<E> parent;
	private Node<E> child;

	boolean isMarked;
	boolean isMinimum;

	public Node() {
	    this.key = null;
	}

	public Node(E key) {
	    this.key = key;
	    this.next = this;
	    this.prev = this;
	    rank = 0;
	}

	public E getKey() {
	    return key;
	}

	public void setKey(E key) {
	    this.key = key;
	}

	public int compareTo(Node<E> that) {
	    return this.key.compareTo(that.key);
	}
    }

    private static class NodeIterator<E extends Comparable<E>> implements
    Iterator<Node<E>> {

	private Queue<Node<E>> list = new LinkedList<Node<E>>();

	public NodeIterator(Node<E> start) {
	    if (start == null) {
		return;
	    }
	    Node<E> current = start;
	    do {
		list.add(current);
		current = current.next;
	    } while (current != start);
	    this.next(); //sets pointer to head
	}

	public boolean hasNext() {
	    return (!list.isEmpty());
	}

	public Node<E> next() {
	    Node<E> node = list.poll();
	    return node;
	}

	public void remove() {
	    throw new UnsupportedOperationException();
	}
    }
}