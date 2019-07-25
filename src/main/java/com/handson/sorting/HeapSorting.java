/**
 * 
 */
package com.handson.sorting;

import com.handson.heap.Heap;
import com.handson.heap.HeapInternalNode;

/**
 * @author sveera
 *
 */
public class HeapSorting extends Heap<Integer> {

	public HeapSorting() {
		super((value1, value2) -> value1 > value2 ? true : false, 1);
	}

	public void sort(Integer[] valuesToBeSorted) {
		convertToHeapInternalNodes(valuesToBeSorted);
		constructHeap();
		for (int i = 0; i < valuesToBeSorted.length; i++)
			valuesToBeSorted[i] = delete_rootElement().priority;
	}

	private void convertToHeapInternalNodes(Integer[] valuesToBeSorted) {
		HeapInternalNode[] heapInternalNodes = new HeapInternalNode[valuesToBeSorted.length + 2];
		for (int i = 0; i < valuesToBeSorted.length; i++) {
			HeapInternalNode heapInternalNode = new HeapInternalNode();
			heapInternalNode.priority = valuesToBeSorted[i];
			heapInternalNode.value = valuesToBeSorted[i];
			heapInternalNodes[i + 1] = heapInternalNode;
		}
		this.heapInternalNodes = heapInternalNodes;
		this.heapPointer = heapInternalNodes.length - 2;
	}

	private void constructHeap() {
		for (int parentIndex = heapPointer / 2; parentIndex >= 1; parentIndex--)
			downWardHeapify(heapInternalNodes, heapPointer, parentIndex);
	}

}
