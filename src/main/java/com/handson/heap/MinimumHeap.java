/**
 * 
 */
package com.handson.heap;

import com.handson.comparator.Comparator;

/**
 * @author sveera
 *
 */
public class MinimumHeap<V> extends Heap<V> {

	public MinimumHeap(Comparator comparator, int capacity) {
		super(comparator, capacity);
	}

	public MinimumHeap() {
		super((value1, value2) -> value1 > value2 ? true : false, 100);
	}

	public HeapNode<V> minimum() {
		return super.rootElement();
	}

	public HeapNode<V> delete_minimum() {
		return super.delete_rootElement();
	}

}
