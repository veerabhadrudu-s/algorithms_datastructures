/**
 * 
 */
package com.handson.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import com.handson.junit.IntegerArrayConverter;

/**
 * @author sveera
 *
 */
public class QueueUsingArrayTest {

	@DisplayName("test queue Enqueue and dequeue ")
	@ParameterizedTest(name = "using enqueued array {1} and expected dequeued order is {0}")
	@CsvSource({ "'1','1'", "'1,2','1,2'", "'1,2,3','1,2,3'", "'1,2,3,4','1,2,3,4'", "'1,2,3,4,5','1,2,3,4,5'", })
	public void testQueueUsingArrayWithEnqueueAndDequeueOperations(
			@ConvertWith(IntegerArrayConverter.class) Integer[] expectedDequeuedValues,
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesTobeEnqueued) {
		QueueUsingArray<Integer> queueUsingArray = new QueueUsingArray<>(valuesTobeEnqueued.length);
		for (int i = 0; i < valuesTobeEnqueued.length; i++)
			queueUsingArray.enqeue(valuesTobeEnqueued[i]);
		for (int i = 0; i < expectedDequeuedValues.length; i++)
			assertEquals(new Integer(expectedDequeuedValues[i]), queueUsingArray.dequeue());
	}

	@Test
	@DisplayName("test Dequeue Operation On Empty Queue And Expect Exception")
	public void testDequeueOperationOnEmptyQueue() {
		QueueUsingArray<Integer> queueUsingArray = new QueueUsingArray<>(10);
		assertThrows(EmptyQueueException.class, queueUsingArray::dequeue);
	}

	@Test
	@DisplayName("test Enqueue and Dequeue Operations For Circular Rotation Case")
	public void testEnqueueAndDequeueOperationsRandomly() {
		QueueUsingArray<Integer> queueUsingArray = new QueueUsingArray<>(4);
		queueUsingArray.enqeue(1);
		queueUsingArray.enqeue(2);
		assertEquals(new Integer(1), queueUsingArray.dequeue());
		assertEquals(new Integer(2), queueUsingArray.dequeue());
		queueUsingArray.enqeue(3);
		queueUsingArray.enqeue(4);
		queueUsingArray.enqeue(5);
		queueUsingArray.enqeue(6);
		assertEquals(new Integer(3), queueUsingArray.dequeue());
		assertEquals(new Integer(4), queueUsingArray.dequeue());
		assertEquals(new Integer(5), queueUsingArray.dequeue());
		assertEquals(new Integer(6), queueUsingArray.dequeue());
	}

}
