package fibonacciheap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import fibonacciheap.FibonacciHeap.Node;

public class TestFibonacciHeap {

	@SuppressWarnings("rawtypes")
	private final FibonacciHeap heap = new FibonacciHeap();

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
		heap.insert(3);
		heap.insert(5);
		heap.insert(0);
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

		assertEquals(3, heap.minKey());
		assertEquals(1, heap.minRank());
		assertEquals(2, heap.getSize());

	} 

	@SuppressWarnings("unchecked")
	@Test
	public void TestNodeRankAfterExtractMin() {
		heap.insert(0);
		heap.insert(3);
		heap.insert(5);
		heap.insert(1);
		heap.insert(2);
		heap.extractMin();
		heap.extractMin();
		Node<Integer> node = heap.getMinNode();

		assertEquals(3, heap.getSize());
		assertEquals(2, (int)node.getKey());
		assertEquals(0, node.rank);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void TestSingleNodeExtractMin() {
		heap.insert(0);
		Node<Integer> node = heap.extractMin();

		assertEquals(0, (int)node.getKey());
		assertEquals(0, heap.getSize());
		assertEquals(null, heap.minKey());
	} 

	@Test
	public void TestEmptyHeap() {
		assertEquals(0, heap.getSize());
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
		assertEquals(2, heap.getSize());
	} 

	@Test
	public void TestDecreaseKeyForSingleNodeInHeap() {
		Node<Integer> node = heap.insert(100);
		heap.decreaseKey(node, 0);

		assertEquals(1, heap.getSize());
		assertEquals(0, heap.minKey());
	}  

	@Test
	public void TestDelete() {
		heap.insert(3);
		heap.insert(1);
		heap.insert(1);
		heap.extractMin();
		Node<Integer> node1 = heap.insert(0);
		Node<Integer> node2 = heap.insert(3);
		heap.insert(5);
		heap.delete(node1);
		heap.delete(node2);

		assertEquals(3, heap.getSize());
		assertEquals(1, heap.minKey()); 
	}  

	@Test
	public void TestTheSameNodes() {
		heap.insert(3);
		heap.insert(3);
		heap.insert(3);
		heap.insert(3);
		heap.insert(3);
		heap.insert(3);
		heap.insert(3);
		heap.extractMin();

		assertEquals(2, heap.minRank());
		assertEquals(6, heap.getSize());
		assertEquals(3, heap.minKey()); 
	}  
}