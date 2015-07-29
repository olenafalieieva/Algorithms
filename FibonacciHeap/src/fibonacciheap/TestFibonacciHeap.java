package fibonacciheap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import fibonacciheap.FibonacciHeap.Node;

public class TestFibonacciHeap {
   
    @SuppressWarnings("rawtypes")
    private final FibonacciHeap heap= new FibonacciHeap();;

    @Before
    public void init() {
        heap.clear();
    }
    
    @Test
    public void TestisEmty() {
	assertTrue(heap.isEmpty());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void TestOneInsert() {
	
	heap.insert(1);
	assertEquals(1, heap.minKey());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void TestManyInsert() {
	heap.insert(0);
	heap.insert(3);
	heap.insert(5);
	heap.insert(1);
	heap.insert(1);
	
	assertEquals(0, heap.minKey());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void TestExtractMin() {
	heap.insert(0);
	heap.insert(3);
	heap.insert(5);
	heap.insert(1);
	heap.insert(1);
	
	assertEquals(0, heap.extractMin().getKey());
    } 

    @SuppressWarnings("unchecked")
    @Test
    public void TestOrderAfterExtractMin() {
	heap.insert(0);
	heap.insert(3);
	heap.insert(5);
	heap.insert(1);
	heap.insert(1);
	heap.extractMin();
	heap.extractMin();
	heap.extractMin();
	
	assertEquals(2, heap.minRank());
	assertEquals(2, heap.size());
	assertEquals(1, heap.minKey());
    } 
    
    @SuppressWarnings("unchecked")
    @Test
    public void TestNodeRankAfterExtractMin() {
	heap.insert(0);
	heap.insert(3);
	heap.insert(5);
	heap.insert(1);
	heap.insert(1);
	heap.extractMin();
	heap.extractMin();
	Node<Integer> node = heap.getMinNode();
	assertEquals(2, heap.minRank());
	assertEquals(1, (int)node.getKey());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void TestSingleNodeExtractMin() {
	heap.insert(0);
	Node<Integer> node = heap.extractMin();
	
	assertEquals(0, (int)node.getKey());
	assertEquals(0, heap.size());
	assertEquals(null, heap.minKey());
    } 
    
    @Test
    public void TestEmptyHeapExtractMin() {
	assertEquals(0, heap.size());
	assertEquals(null, heap.minKey());
    } 
    
    @SuppressWarnings("unchecked")
    @Test
    public void TestDecreaseKey() {
	heap.insert(3);
	heap.insert(1);
	heap.insert(1);
	Node<Integer> node = heap.insert(5);
	heap.extractMin();
	heap.extractMin();
	heap.decreaseKey(node, 0);
	
	assertEquals(0, heap.minKey());
    } 
    
    @Test
    public void TestDecreaseKeyForSingleNodeInHeap() {
	Node<Integer> node = heap.insert(100);
	heap.decreaseKey(node, 0);
	
	assertEquals(1, heap.size());
	assertEquals(0, heap.minKey());
    }  
  
    @Test
    public void TestDelete() {
	heap.insert(3);
	heap.insert(1);
	heap.insert(1);
	heap.extractMin();
	Node<Integer> node = heap.insert(0);
	heap.insert(3);
	heap.insert(5);
	heap.delete(node);
	
	assertEquals(4, heap.size());
	assertEquals(1, heap.minKey()); 
    }  
}
