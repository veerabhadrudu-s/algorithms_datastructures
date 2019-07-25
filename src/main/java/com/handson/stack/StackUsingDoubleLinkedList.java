/**
 * 
 */
package com.handson.stack;

import com.handson.node.DoubleLinkNode;

/**
 * @author sveera
 *
 */
public class StackUsingDoubleLinkedList<T> {

	private DoubleLinkNode<T> sentinalHeadNode;
	private DoubleLinkNode<T> sentinalTailNode;

	public StackUsingDoubleLinkedList() {
		super();
		sentinalTailNode = new DoubleLinkNode<T>();
		sentinalHeadNode = new DoubleLinkNode<T>();
		sentinalHeadNode.nextNode = sentinalTailNode;
		sentinalTailNode.previousNode = sentinalHeadNode;
	}

	public void push(T value) {
		DoubleLinkNode<T> newDoubleLinkNode = new DoubleLinkNode<T>();
		newDoubleLinkNode.nodeValue = value;
		newDoubleLinkNode.previousNode = sentinalTailNode.previousNode;
		newDoubleLinkNode.nextNode = sentinalTailNode;
		sentinalTailNode.previousNode.nextNode = newDoubleLinkNode;
		sentinalTailNode.previousNode = newDoubleLinkNode;
	}

	public T pop() {
		if (isStackEmpty())
			throw new StackUnderFlowException();
		DoubleLinkNode<T> lastNode = sentinalTailNode.previousNode;
		lastNode.previousNode.nextNode = sentinalTailNode;
		sentinalTailNode.previousNode = lastNode.previousNode;
		return lastNode.nodeValue;
	}

	private boolean isStackEmpty() {
		return sentinalTailNode.previousNode == sentinalHeadNode || sentinalHeadNode.nextNode == sentinalTailNode;
	}

}
