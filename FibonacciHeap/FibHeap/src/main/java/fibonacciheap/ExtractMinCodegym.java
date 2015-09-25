package fibonacciheap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ExtractMinCodegym {

    FibNode	minNode;
    int size = 0;

    public FibNode insert(int key) {

	FibNode node = new FibNode(key);
	minNode = unionRootsLists(minNode, node);
	size++;

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

    public FibNode extractMin() {
	FibNode extractedMin = null;

	if (minNode != minNode.next && minNode.child == null) {
	    FibNode min = minNode.next;
	    removeNode(minNode);
	    minNode = min;
	   minNode =  consolidate(minNode);
	    size--;
	    return extractedMin;
	}
	return extractedMin;

    }
    public  FibNode consolidate(FibNode minNode) {
	if (minNode == minNode.next && minNode.child == null) {
	    size--; 
	    //minNode = minNode;
	    return minNode;
	}  
	FibNode start = minNode;
	FibNode curr = start;
	List<FibNode> list = new LinkedList<FibNode>();
	do {
	    list.add(curr);
	    curr = curr.next;
	} while (curr != start);
	Iterator<FibNode> it = list.iterator(); 
	FibNode current;
	current = it.next();
	FibNode rankNode = null;
	int index = current.rank;
	if (current.key < minNode.key) {
	    minNode = current;
	}
	Map<Integer, FibNode> rankMap = new HashMap<Integer, FibNode>();
	do {
	    if (rankMap.containsKey(index)) {
		rankNode = rankMap.get(index);
		current = linkHeaps(current, rankNode);
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
	} while (it.hasNext());

	while (rankMap.containsKey(index)) {
	    rankNode = rankMap.get(index);
	    current = linkHeaps(current, rankNode);
	    rankMap.remove(index);
	    index = current.rank;
	    if (current.key <= minNode.key) {
		minNode = current;
	    }
	}
	return minNode;
    }
    
    protected void removeNode(FibNode node) {
	node.next.prev = node.prev;
	node.prev.next = node.next;
	node.next = node;
	node.prev = node;
    }

    private FibNode linkHeaps(FibNode min, FibNode max) {
	if (min.key > max.key) {
	    FibNode tmp;
	    tmp = min;
	    min = max;
	    max = tmp;
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

    public FibNode getMinNode() {

	return minNode;
    }
    public String toString() { 
	StringBuilder sb = new StringBuilder();
	if (minNode != null) {
	    sb.append(minNode.printNode(0));
	}
	return sb.toString();
    }
}
