/**
 * 
 */
package com.handson.heap;

import com.handson.comparator.Comparator;

/**
 * @author sveera
 *
 */
public class MaximumHeap<V> extends Heap<V> {

	public MaximumHeap(Comparator comparator, int capacity) {
		super(comparator, capacity);
	}

	public MaximumHeap() {
		super((value1, value2) -> value1 < value2 ? true : false, 100);
	}

	public HeapNode<V> maximum() {
		return super.rootElement();
	}

	public HeapNode<V> delete_maximum() {
		return super.delete_rootElement();
	}
}
