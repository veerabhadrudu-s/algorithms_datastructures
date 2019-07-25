/**
 * 
 */
package com.handson.heap;

import com.handson.comparator.Comparator;

/**
 * @author sveera
 *
 */
public class Heap<V> {

	protected HeapInternalNode[] heapInternalNodes;
	private Comparator comparator;
	protected int heapPointer;

	public Heap(Comparator comparator) {
		this(comparator, 100);
	}

	public Heap(Comparator comparator, int capacity) {
		this.heapInternalNodes = new HeapInternalNode[capacity];
		this.comparator = comparator;
	}

	public void insert(int priority, V value) {
		if (heapPointer == heapInternalNodes.length - 1)
			increaseHeapSize();
		HeapInternalNode heapInternalNode = new HeapInternalNode();
		heapInternalNode.priority = priority;
		heapInternalNode.value = value;
		this.heapInternalNodes[++heapPointer] = heapInternalNode;
		if (heapPointer > 1)
			upWardHeapify(heapInternalNodes, heapPointer);
	}

	public HeapNode<V> rootElement() {
		if (heapPointer == 0)
			return null;
		return createHeapNode(heapInternalNodes[1].priority, (V) heapInternalNodes[1].value);
	}

	public HeapNode<V> delete_rootElement() {
		if (heapPointer == 0)
			return null;
		HeapNode<V> heapNode = rootElement();
		this.heapInternalNodes[1] = this.heapInternalNodes[heapPointer];
		this.heapInternalNodes[heapPointer--] = null;
		downWardHeapify(this.heapInternalNodes, heapPointer, 1);
		return heapNode;
	}

	public V decreaseKey(int index) {
		if (heapPointer == 0 || index > heapPointer)
			return null;
		HeapNode<V> heapNode = createHeapNode(this.heapInternalNodes[index].priority,
				(V) this.heapInternalNodes[index].value);
		this.heapInternalNodes[index] = this.heapInternalNodes[heapPointer];
		this.heapInternalNodes[heapPointer--] = null;
		balanceHeap(heapInternalNodes, heapPointer, index);
		return heapNode.value;
	}

	private void balanceHeap(HeapInternalNode[] heapInternalNodes, int heapPointer, int index) {
		if (heapPointer <= 1 || index > heapPointer)
			return;
		if (index / 2 >= 1
				&& comparator.compare(heapInternalNodes[index / 2].priority, heapInternalNodes[index].priority))
			upWardHeapify(heapInternalNodes, index);
		else
			downWardHeapify(heapInternalNodes, heapPointer, index);
	}

	private HeapNode<V> createHeapNode(int priority, V value) {
		HeapNode<V> heapNode = new HeapNode<>();
		heapNode.priority = priority;
		heapNode.value = value;
		return heapNode;
	}

	private void upWardHeapify(HeapInternalNode[] heapInternalNodes, int childIndex) {
		for (int parentIndex = childIndex / 2; parentIndex >= 1
				&& comparator.compare(heapInternalNodes[parentIndex].priority,
						heapInternalNodes[childIndex].priority); childIndex = parentIndex, parentIndex /= 2)
			swapElementsBetweenTwoIndexes(heapInternalNodes, parentIndex, childIndex);
	}

	protected void downWardHeapify(HeapInternalNode[] heapInternalNodes, int heapPointer, int heapViolatedIndex) {
		if (heapPointer < heapViolatedIndex * 2)
			return;
		HeapInternalNode leftChild = heapInternalNodes[heapViolatedIndex * 2];
		HeapInternalNode rightChild = heapInternalNodes[heapViolatedIndex * 2 + 1];
		int selectedIndex = leftChild != null && rightChild != null
				? comparator.compare(leftChild.priority, rightChild.priority) ? heapViolatedIndex * 2 + 1
						: heapViolatedIndex * 2
				: rightChild == null ? heapViolatedIndex * 2 : heapViolatedIndex * 2 + 1;
		if (comparator.compare(heapInternalNodes[heapViolatedIndex].priority,
				heapInternalNodes[selectedIndex].priority)) {
			swapElementsBetweenTwoIndexes(heapInternalNodes, heapViolatedIndex, selectedIndex);
			downWardHeapify(heapInternalNodes, heapPointer, selectedIndex);
		}
	}

	private void increaseHeapSize() {
		HeapInternalNode[] newHeapInternalNodes = new HeapInternalNode[heapInternalNodes.length * 2];
		for (int i = 0; i < heapInternalNodes.length; i++)
			newHeapInternalNodes[i] = heapInternalNodes[i];
		heapInternalNodes = newHeapInternalNodes;
	}

	private void swapElementsBetweenTwoIndexes(HeapInternalNode[] heapInternalNodes, int index1, int index2) {
		HeapInternalNode tempHeapInternalNode = heapInternalNodes[index1];
		heapInternalNodes[index1] = heapInternalNodes[index2];
		heapInternalNodes[index2] = tempHeapInternalNode;
	}
}
