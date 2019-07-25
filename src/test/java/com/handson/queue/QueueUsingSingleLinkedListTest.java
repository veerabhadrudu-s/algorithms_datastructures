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
public class QueueUsingSingleLinkedListTest {

	@DisplayName("test queue Enqueue and dequeue ")
	@ParameterizedTest(name = "using enqueued array {1} and expected dequeued order is {0}")
	@CsvSource({ "'1','1'", "'1,2','1,2'", "'1,2,3','1,2,3'", "'1,2,3,4','1,2,3,4'", "'1,2,3,4,5','1,2,3,4,5'", })
	public void testQueueUsingArrayWithEnqueueAndDequeueOperations(
			@ConvertWith(IntegerArrayConverter.class) Integer[] expectedDequeuedValues,
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesTobeEnqueued) {
		QueueUsingSingleLinkedList<Integer> queueUsingSingleLinkedList = new QueueUsingSingleLinkedList<>();
		for (int i = 0; i < valuesTobeEnqueued.length; i++)
			queueUsingSingleLinkedList.enqeue(valuesTobeEnqueued[i]);
		for (int i = 0; i < expectedDequeuedValues.length; i++)
			assertEquals(new Integer(expectedDequeuedValues[i]), queueUsingSingleLinkedList.dequeue());
	}

	@Test
	@DisplayName("test Dequeue Operation On Empty Queue And Expect Exception")
	public void testDequeueOperationOnEmptyQueue() {
		QueueUsingSingleLinkedList<Integer> queueUsingSingleLinkedList = new QueueUsingSingleLinkedList<>();
		assertThrows(EmptyQueueException.class, queueUsingSingleLinkedList::dequeue);
	}

	@Test
	@DisplayName("test Enqueue and Dequeue Randomly")
	public void testEnqueueAndDequeueOperationsRandomly() {
		QueueUsingSingleLinkedList<Integer> queueUsingSingleLinkedList = new QueueUsingSingleLinkedList<>();
		queueUsingSingleLinkedList.enqeue(1);
		queueUsingSingleLinkedList.enqeue(2);
		assertEquals(new Integer(1), queueUsingSingleLinkedList.dequeue());
		assertEquals(new Integer(2), queueUsingSingleLinkedList.dequeue());
		assertThrows(EmptyQueueException.class, queueUsingSingleLinkedList::dequeue);
		queueUsingSingleLinkedList.enqeue(3);
		queueUsingSingleLinkedList.enqeue(4);
		queueUsingSingleLinkedList.enqeue(5);
		queueUsingSingleLinkedList.enqeue(6);
		assertEquals(new Integer(3), queueUsingSingleLinkedList.dequeue());
		assertEquals(new Integer(4), queueUsingSingleLinkedList.dequeue());
		assertEquals(new Integer(5), queueUsingSingleLinkedList.dequeue());
		assertEquals(new Integer(6), queueUsingSingleLinkedList.dequeue());
	}

}
