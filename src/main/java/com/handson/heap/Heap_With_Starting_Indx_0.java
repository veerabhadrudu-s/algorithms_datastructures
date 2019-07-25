/**
 * 
 */
package com.handson.heap;

import com.handson.comparator.Comparator;

/**
 * @author sveera
 *
 */
@Deprecated
public class Heap_With_Starting_Indx_0<V> {

	protected HeapInternalNode[] heapNodes;
	protected int heapPointer;
	private Comparator comparator;

	public Heap_With_Starting_Indx_0(Comparator comparator) {
		this(comparator, 100);
	}

	public Heap_With_Starting_Indx_0(Comparator comparator, int capacity) {
		heapNodes = new HeapInternalNode[capacity];
		this.comparator = comparator;
	}

	public void insert(int priority, V value) {
		if (heapPointer == heapNodes.length)
			reInitializeHeap();
		// logger.debug("Elements before insertion " + Arrays.deepToString(heapNodes));
		insertNewNode(priority, value);
		for (int childNodeIndex = heapPointer - 1, parentNodeIndex = calculateParentIndex(
				childNodeIndex); parentNodeIndex >= 0; childNodeIndex = parentNodeIndex, parentNodeIndex = calculateParentIndex(
						childNodeIndex)) {
			if (comparator.compare(heapNodes[parentNodeIndex].priority, heapNodes[childNodeIndex].priority))
				swapParentChild(heapNodes, parentNodeIndex, childNodeIndex);
			else
				break;
		}
	}

	protected int calculateParentIndex(int childNodeIndex) {
		return childNodeIndex % 2 == 0 ? childNodeIndex / 2 - 1 : childNodeIndex / 2;
	}

	public HeapNode<V> rootElement() {
		if (heapPointer == 0)
			return null;
		HeapInternalNode heapInternalNode = heapNodes[0];
		HeapNode<V> heapNode = new HeapNode<>();
		heapNode.priority = heapInternalNode.priority;
		heapNode.value = (V) heapInternalNode.value;
		return heapNode;
	}

	public HeapNode<V> delete_rootElement() {
		if (heapPointer == 0)
			return null;
		// logger.debug("Elements before deletion " + Arrays.deepToString(heapNodes));
		HeapInternalNode heapInternalNode = heapNodes[0];
		HeapNode<V> heapNode = new HeapNode<>();
		heapNode.priority = heapInternalNode.priority;
		heapNode.value = (V) heapInternalNode.value;
		heapNodes[0] = heapNodes[heapPointer - 1];
		heapNodes[--heapPointer] = null;
		heapify(1, heapPointer, heapNodes);
		return heapNode;
	}

	protected void heapify(int index, int lastElementIndexInHeapArray, HeapInternalNode[] heapNodes) {
		if (index * 2 > lastElementIndexInHeapArray)
			return;
		HeapInternalNode parentNode = heapNodes[index - 1];
		HeapInternalNode leftChild = heapNodes[index * 2 - 1];
		HeapInternalNode rightChild = heapNodes[index * 2];
		HeapInternalNode selectedChild = leftChild != null && rightChild != null
				? comparator.compare(leftChild.priority, rightChild.priority) ? rightChild : leftChild
				: leftChild == null ? rightChild : leftChild;
		int selectedIndex = selectedChild == leftChild ? index * 2 - 1 : index * 2;
		if (comparator.compare(parentNode.priority, selectedChild.priority)) {
			swapParentChild(heapNodes, index - 1, selectedIndex);
			heapify(selectedIndex + 1, lastElementIndexInHeapArray, heapNodes);
		}
	}

	private void swapParentChild(HeapInternalNode[] heapInternalNodes, int parentNodeIndex, int childNodeIndex) {
		HeapInternalNode tempIntenalNode = heapInternalNodes[childNodeIndex];
		heapInternalNodes[childNodeIndex] = heapInternalNodes[parentNodeIndex];
		heapInternalNodes[parentNodeIndex] = tempIntenalNode;
	}

	private void insertNewNode(int priority, V value) {
		HeapInternalNode heapInternalNode = new HeapInternalNode();
		heapInternalNode.priority = priority;
		heapInternalNode.value = value;
		heapNodes[heapPointer++] = heapInternalNode;
	}

	private void reInitializeHeap() {
		final HeapInternalNode[] newHeapNodes = new HeapInternalNode[heapNodes.length * 2];
		for (int i = 0; i < heapNodes.length; i++)
			newHeapNodes[i] = heapNodes[i];
		heapNodes = newHeapNodes;
	}

}
