package fibonacciheap;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestFibonacciHeap {
   
    @SuppressWarnings("rawtypes")
    private final FibonacciHeap heap= new FibonacciHeap();;

    @Before
    public void init() {
        heap.clear();
    }
    
//    @Test
//    public void TestisEmty() {
//	assertTrue(heap.isEmpty());
//    }
//    
//    @SuppressWarnings("unchecked")
//    @Test
//    public void TestOneInsert() {
//	
//	heap.insert(1);
//	assertEquals(1, heap.minKey());
//    }
//    
//    @SuppressWarnings("unchecked")
//    @Test
//    public void TestManyInsert() {
//	
//	heap.insert(0);
//	heap.insert(3);
//	heap.insert(5);
//	heap.insert(1);
//	heap.insert(1);
//	assertEquals(0, heap.minKey());
//	
//    }
//    
//    @SuppressWarnings("unchecked")
//    @Test
//    public void TestExtractMin() {
//	
//	heap.insert(0);
//	heap.insert(3);
//	heap.insert(5);
//	heap.insert(1);
//	heap.insert(1);
//	
//	assertEquals(0, heap.extractMin().getKey());
//    } 

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
	assertEquals(2, heap.size());
	assertEquals(3, heap.minKey());
    } 
    
    @Test
    public void TestEmptyHeapExtractMin() {
	
	assertEquals(0, heap.size());
	assertEquals(null, heap.minKey());
    } 
}
