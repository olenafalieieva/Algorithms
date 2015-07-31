package fibonacciheap;

import java.util.HashMap;
import java.util.Map;

public class FibonacciHeap<T extends Comparable<T>> {

    Node<T> minNode;
    int size;

    public FibonacciHeap() {
	minNode = null;
	size = 0;
    }

    public Node<T> getMinNode() {
	return minNode;
    }

    public int getSize(){
	return size;
    }

    public boolean isEmpty() {
	return size == 0;
    }

    public void clear() {
	minNode = null;
	size = 0;
    }

    public T minKey(){
	if (isEmpty()) {
	    return null;  
	}
	return minNode.key;
    }

    public int minRank(){
	if (isEmpty()) {
	    return -1;  
	}
	return minNode.rank;
    }

    public Node<T> insert(T key) {
	Node<T> node = new Node<T>(key);
	minNode = unionRootsLists(minNode, node);
	size++;

	return node;
    }

    public Node<T> extractMin() {
	if (isEmpty()) {
	    return null;  
	}
	Node<T> min = minNode;
	Node<T> extractedMin = min;
	Node<T> newMin;
	if(min == min.next && min.child == null) { 
	    size--; // should return size = 0
	    minNode = null;

	    return extractedMin;

	} else if (min != min.next && min.child == null) { 
	    newMin = min.next;
	    removeNode(min);
	    minNode = findNewMin(newMin);
	    size--;
	    consolidate(); 

	    return extractedMin;
	}
	Node<T> start = min.child; // need children's LinkedList
	newMin = min;
	//if(min.child != null) { 
	do {
	    newMin = unionRootsLists(newMin, start);
	    start.parent = null;
	} while(min.child != start);
	//} 
	min.child = null;
	newMin = min.next;
	removeNode(min);
	minNode = findNewMin(newMin);
	size--;
	consolidate();

	return extractedMin;
    }

    public void decreaseKey(Node<T> node, T newKey) {
	if (newKey.compareTo(node.key) > 0) {
	    throw new IllegalArgumentException("New key is lager then current one");
	}
	node.key = newKey;
	Node<T> parentNode = node.parent;
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

    public void delete(Node<T> node) {
	Node<T> leastNode = new Node(Integer.MIN_VALUE);
	decreaseKey(node, leastNode.getKey());
	extractMin();
    }

    private Node<T> findNewMin(Node<T> newMin) {
	Node<T> start = newMin;
	Node<T> current = start.next;
	do { 
	    if(current.compareTo(newMin) < 0) {
		newMin.isMinimum = false;
		newMin = current;
	    } 
	    current = current.next;
	} while (current != start);
	newMin.isMinimum = true;	
	return newMin;	
    }

    private void cut (Node<T> node, Node<T> parent) {
	removeNode(node);
	parent.rank--;
	//parent.isMarked = true;
	unionRootsLists(node, minNode);
	//node.parent = null;
	node.isMarked = false;
    }

    private void cascadingCut (Node<T> parentNode) {
	Node<T> parent = parentNode.parent;
	if (parent != null) {
	    if (!parentNode.isMarked) {
		parent.isMarked = true;
	    } else {
		cut(parentNode, parent);
		cascadingCut(parent);
	    }
	}
    }

    private Node<T> unionRootsLists(Node<T> min, Node<T> max) {
	if (min == null) {
	    return max;
	}
	if(max == null) {
	    return min;
	}
	union(min, max);

	Node<T> minimNode; 
	if (min.compareTo(max) <= 0) {
	    minimNode = min;  
	} else {
	    minimNode = max;
	    max.isMinimum = true;
	    min.isMinimum = false;
	}

	return minimNode;
    }

    private void removeNode(Node<T> node) {
	node.next.prev = node.prev;
	node.prev.next = node.next;
	node.next = node;
	node.prev = node;
    }

    private void union(Node<T> min, Node<T> max) {
	max.next = min;
	max.prev = min.prev;
	min.prev.next = max;
	min.prev = max;
    }

    private void consolidate() {
	Map<Integer, Node<T>> rankMap = new HashMap<Integer, Node<T>>();
	Node<T> start = minNode;
	Node<T> current = start;
	Node<T> rankNode = null;
	int index;
	do { 
	    index = current.rank;
	    if (rankMap.containsKey(index)){
		rankNode = rankMap.get(index);
		current = linkHeaps(current, rankNode);
		rankMap.remove(index);
	    } else {
		rankMap.put(index, current);
		current = current.next;
	    } 
	} while (current != start);

    }

    private Node<T> linkHeaps(Node<T> min, Node<T> max) {
	if (min.compareTo(max) > 0) {
	    Node<T> temp = min;
	    min = max;
	    max = temp;
	}
	removeNode(max);
	max.parent = min;
	if (min.child == null) {
	    min.child = max;
	} else {
	    union(min.child, max); 
	}
	max.isMarked = false;
	max.isMinimum = false;
	min.rank++;
	return min;
    }

    static class Node <T extends Comparable<T>>
    implements Comparable<Node<T>> {

	private T key;
	int rank;
	private Node<T> prev;
	private Node<T> next;
	private Node<T> parent;
	private Node<T> child;

	boolean isMarked;
	boolean isMinimum;

	public Node() {
	    this.key = null;
	}

	public Node(T key) {
	    this.key = key;
	    this.next = this;
	    this.prev = this;
	    rank = 0;
	}

	public T getKey() {
	    return key;
	}

	public void setKey(T key) {
	    this.key = key;
	}

	public int compareTo(Node<T> that) {
	    return this.key.compareTo(that.key);
	}
    }
}
