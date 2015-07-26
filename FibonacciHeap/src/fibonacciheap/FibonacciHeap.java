package fibonacciheap;

import java.util.ArrayList;
import java.util.List;

public class FibonacciHeap<T extends Comparable<T>> {

    Node<T> minNode;
    int size;

    public FibonacciHeap() {
	minNode = null;
	size = 0;
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

    public int size(){
	return size;
    }

    public Node<T> insert(T key) {
	Node<T> node = new Node<T>(key);
	minNode = unionRootsLists(minNode, node);
	minNode.isMinimum = true;
	size++;

	return minNode;
    }

    public Node<T> extractMin() {

	if (isEmpty()) {
	    return null;  
	}

	Node<T> min = minNode;
	Node<T> extractedMin = min;
	Node<T> newMin = min;


	if(min == min.next && min.child == null) { 
	    size--; // should return size = 0
	    minNode = null;

	    return extractedMin;

	} else if (min != min.next && min.child == null) { //present another nodes, don't need consolidate
	    newMin = min.next;
	    removeNode(min);
	    minNode = findNewMin(newMin);
	    size--;
	    return extractedMin;
	}


	Node<T> start = min.child; //need child's LinkedList
	if(min != null && min.child != null) { 
	    do {
		newMin = unionRootsLists(newMin, min.child);
		min.child.parent = null;
		min.child = min.child.next;
	    } while(min.child != start);
	} 
	min.child = null;
	removeNode(min);

	minNode = newMin;
	size--;
	consolidate();

	return extractedMin;
    }

    private Node<T> findNewMin(Node<T> newMin) {
	Node<T> start = newMin;
	Node<T> current = start.next;
	do { 
	    if(current.compareTo(current.next) < 0) {
		newMin = current;
	    } 
	    current = current.next;
	} while (current != start);
	return newMin;	
    }

    private Node<T> unionRootsLists(Node<T> min, Node<T> max) {
	if (min == null) {
	    return max;
	}
	if(max == null) {
	    return min;
	}
	max.next = min;
	max.prev = min.prev;
	min.prev.next = max;
	min.prev = max;
	Node<T> minimNode = min.compareTo(max) < 0 ? min : max;

	return minimNode;
    }

    private void consolidate() {
	List<Node<T>> rankList = new ArrayList<Node<T>>();
	Node<T> start = minNode;
	Node<T> current = start;
	Node<T> rankNode;
	int index;
	do { 
	    current = current.next;
	    index = current.rank;
	    rankNode = rankList.get(index);
	    if (rankNode == null){
		rankList.add(index, current);
	    } else {
		current = linkHeaps(current, rankNode);
		rankList.add(current.rank, current);
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
	    unionChildrenList(min.child, max); 
	}
	max.isMarked = false;
	max.parent = min;
	min.rank++;
	return min;
    }

    private void unionChildrenList(Node<T> rootChild, Node<T> newChild) { //insert newChild to ChildrenList
	newChild.next = rootChild;
	newChild.prev = rootChild.prev;
	rootChild.prev = newChild;
	if (rootChild.next == rootChild) {
	    rootChild.next = newChild;
	}
    }

    private void removeNode(Node node) {
	node.next.prev = node.prev;
	node.prev.next = node.next;
	node.next = node;
	node.prev = node;
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
	 if (minNode.key.compareTo(node.key) > 0) {
	     minNode = node;
	 }
    }
    
    private void cut (Node<T> node, Node<T> parentNode) {
	removeNode(node);
	unionRootsLists(node, minNode);
	node.parent = null;
	node.isMarked = false;
    }

   private void cascadingCut (Node<T> parentNode) {
       Node<T> parent = parentNode;
       if (parent != null) {
	   if (!parentNode.isMarked) {
	       parent.isMarked = true;
	   } else {
	       cut(parentNode, parent);
	       cascadingCut(parent);
	   }
       }
   }

    public void delete(Node<T> node) {
	Node<T> leastNode = new Node(Integer.MIN_VALUE);
	decreaseKey(node, (T) leastNode);
	extractMin();
    }

    static class Node <T extends Comparable<T>>
    implements Comparable<Node<T>> {

	private T key;
	private int rank;
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
	    this.parent = null;
	    this.child = null;
	    isMarked = false;
	    isMinimum = false;
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
