/**
 * 
 */
package com.handson.stack;

import com.handson.node.SingleLinkNode;

/**
 * @author sveera
 *
 */
public class StackUsingSingleLinkedList<T> {

	private SingleLinkNode<T> head;

	public StackUsingSingleLinkedList() {
		super();
		head = new SingleLinkNode<>();
	}

	public void push(T value) {
		SingleLinkNode<T> newNode = new SingleLinkNode<>();
		newNode.nodeValue = value;
		newNode.nextNode = head;
		head = newNode;
	}

	public T pop() {
		if (head.nextNode == null)
			throw new StackUnderFlowException();
		SingleLinkNode<T> topNode = head;
		head = head.nextNode;
		return topNode.nodeValue;
	}

}
