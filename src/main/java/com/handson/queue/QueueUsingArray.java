/**
 * 
 */
package com.handson.queue;

/**
 * @author sveera
 *
 */
public class QueueUsingArray<T> {

	private int frontIndex;
	private int rearIndex;
	private Object[] queueDataArray;

	public QueueUsingArray(int i) {
		queueDataArray = new Object[i];
	}

	public void enqeue(T value) {
		if (isQueueFull())
			throw new QueueFullException();
		this.queueDataArray[rearIndex % queueDataArray.length] = value;
		rearIndex++;
	}

	public T dequeue() {
		if (frontIndex - rearIndex == 0)
			throw new EmptyQueueException();
		T dequeuedValue = (T) this.queueDataArray[frontIndex % queueDataArray.length];
		frontIndex++;
		return dequeuedValue;
	}

	private boolean isQueueFull() {
		return queueDataArray.length - (rearIndex - frontIndex) == 0;
	}

}
