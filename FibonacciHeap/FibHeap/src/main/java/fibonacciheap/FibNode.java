package fibonacciheap;

public class FibNode {
    int key;
    int rank;
    FibNode prev;
    FibNode next;
    FibNode parent = null;
    FibNode child = null;

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

    public int getKey() {
	return key;
    }

    public void setKey(int key) {
	this.key = key;
    }
     
    StringBuilder sb = new StringBuilder();
    
    public String printNode(int level) { 
	
	FibNode current = this; 
	do { 
	    sb.append("Node: ")
	    .append(current.key)
	    .append("  Lev: ")
	    .append(level)
	    .append(" ");
	    if (current.child != null) {
		sb.append("\n")
		.append(current.child.printNode(++level));
		level--;
	    }
	    current = current.next;
	} while (current != this);
	return sb.toString();
    }
}