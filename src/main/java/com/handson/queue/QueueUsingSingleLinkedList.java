/**
 * 
 */
package com.handson.queue;

import com.handson.node.SingleLinkNode;

/**
 * @author sveera
 *
 */
public class QueueUsingSingleLinkedList<T> {

	private SingleLinkNode<T> headNode;
	private SingleLinkNode<T> tailNode;

	public void enqeue(T enqueuedValue) {
		SingleLinkNode<T> newNode = new SingleLinkNode<>();
		newNode.nodeValue = enqueuedValue;
		if (headNode == null)
			headNode = newNode;
		else
			tailNode.nextNode = newNode;
		tailNode = newNode;

	}

	public T dequeue() {
		if (headNode == null)
			throw new EmptyQueueException();
		T dequedValue = headNode.nodeValue;
		headNode = headNode.nextNode;
		return dequedValue;

	}

}
