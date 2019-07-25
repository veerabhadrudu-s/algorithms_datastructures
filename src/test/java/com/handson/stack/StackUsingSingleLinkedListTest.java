/**
 * 
 */
package com.handson.stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author sveera
 *
 */
public class StackUsingSingleLinkedListTest {

	@Test
	@DisplayName("test One Push And One Pop Operation")
	public void testPushAndPopOperation() {
		StackUsingSingleLinkedList<Integer> stackUsingSingleLinkedList = new StackUsingSingleLinkedList<>();
		stackUsingSingleLinkedList.push(1);
		assertEquals(new Integer(1), stackUsingSingleLinkedList.pop());
	}

	@Test
	@DisplayName("test Two Push And Two Pop Operations")
	public void testTwoPushAndTwoPopOperations() {
		StackUsingSingleLinkedList<Integer> stackUsingSingleLinkedList = new StackUsingSingleLinkedList<>();
		stackUsingSingleLinkedList.push(1);
		stackUsingSingleLinkedList.push(3);
		assertEquals(new Integer(3), stackUsingSingleLinkedList.pop());
		assertEquals(new Integer(1), stackUsingSingleLinkedList.pop());
	}

	@Test
	@DisplayName("test Two Push And Three Pop Operations")
	public void testTwoPushAndThreePopOperations() {
		StackUsingSingleLinkedList<Integer> stackUsingSingleLinkedList = new StackUsingSingleLinkedList<>();
		stackUsingSingleLinkedList.push(1);
		stackUsingSingleLinkedList.push(3);
		assertEquals(new Integer(3), stackUsingSingleLinkedList.pop());
		assertEquals(new Integer(1), stackUsingSingleLinkedList.pop());
		assertThrows(StackUnderFlowException.class, stackUsingSingleLinkedList::pop);
	}

	@Test
	@DisplayName("test Push And Pop Operations Randomly")
	public void testPushAndPopOperationsRandomly() {
		StackUsingSingleLinkedList<Integer> stackUsingSingleLinkedList = new StackUsingSingleLinkedList<>();
		stackUsingSingleLinkedList.push(1);
		stackUsingSingleLinkedList.push(3);
		assertEquals(new Integer(3), stackUsingSingleLinkedList.pop());
		assertEquals(new Integer(1), stackUsingSingleLinkedList.pop());
		assertThrows(StackUnderFlowException.class, stackUsingSingleLinkedList::pop);
		stackUsingSingleLinkedList.push(4);
		assertEquals(new Integer(4), stackUsingSingleLinkedList.pop());
	}

	@Test
	@DisplayName("test stack of  initial capacity 2 with 3 Push operations")
	public void testStackOfInitialCapacity2WithThreePushOperations() {
		StackUsingSingleLinkedList<Integer> stackUsingSingleLinkedList = new StackUsingSingleLinkedList<>();
		stackUsingSingleLinkedList.push(1);
		stackUsingSingleLinkedList.push(3);
		stackUsingSingleLinkedList.push(4);
		assertEquals(new Integer(4), stackUsingSingleLinkedList.pop());
		assertEquals(new Integer(3), stackUsingSingleLinkedList.pop());
		assertEquals(new Integer(1), stackUsingSingleLinkedList.pop());
	}

}
