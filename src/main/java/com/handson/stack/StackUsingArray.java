/**
 * 
 */
package com.handson.stack;

/**
 * @author sveera
 *
 */
public class StackUsingArray<T> {

	private Object[] stackArray;
	private int stackPointer = -1;

	public StackUsingArray(int initialCapacity) {
		stackArray = new Object[initialCapacity];
	}

	public void push(T value) {
		if (size() == stackArray.length)
			increaseStackSizeWithGrowthStrategy();
		this.stackArray[++stackPointer] = value;
	}

	public T pop() {
		if (size() == 0)
			throw new StackUnderFlowException();
		return (T) stackArray[stackPointer--];
	}

	private int size() {
		return stackPointer + 1;
	}

	private void increaseStackSizeWithGrowthStrategy() {
		Object[] sizeIncreasedArray = new Object[stackArray.length * 2];
		for (int i = 0; i < stackArray.length; i++)
			sizeIncreasedArray[i] = stackArray[i];
		stackArray = sizeIncreasedArray;
	}

}
