/**
 * 
 */
package com.handson.stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author sveera
 *
 */
public class StackUsingDoubleLinkedListTest {

	private StackUsingDoubleLinkedList<Integer> stackUsingDoubleLinkedList;

	@BeforeEach
	public void setUp() throws Exception {
		stackUsingDoubleLinkedList = new StackUsingDoubleLinkedList<>();
	}

	@Test
	@DisplayName("test One Push And One Pop Operation")
	public void testPushAndPopOperation() {
		stackUsingDoubleLinkedList.push(1);
		assertEquals(new Integer(1), stackUsingDoubleLinkedList.pop());
	}

	@Test
	@DisplayName("test Two Push And Two Pop Operations")
	public void testTwoPushAndTwoPopOperations() {
		stackUsingDoubleLinkedList.push(1);
		stackUsingDoubleLinkedList.push(3);
		assertEquals(new Integer(3), stackUsingDoubleLinkedList.pop());
		assertEquals(new Integer(1), stackUsingDoubleLinkedList.pop());
	}

	@Test
	@DisplayName("test Two Push And Three Pop Operations")
	public void testTwoPushAndThreePopOperations() {
		stackUsingDoubleLinkedList.push(1);
		stackUsingDoubleLinkedList.push(3);
		assertEquals(new Integer(3), stackUsingDoubleLinkedList.pop());
		assertEquals(new Integer(1), stackUsingDoubleLinkedList.pop());
		assertThrows(StackUnderFlowException.class, stackUsingDoubleLinkedList::pop);
	}

	@Test
	@DisplayName("test stack with Three Push And Three Pop Operations")
	public void testThreePushAndThreePopOperations() {
		stackUsingDoubleLinkedList.push(1);
		stackUsingDoubleLinkedList.push(3);
		stackUsingDoubleLinkedList.push(4);
		assertEquals(new Integer(4), stackUsingDoubleLinkedList.pop());
		assertEquals(new Integer(3), stackUsingDoubleLinkedList.pop());
		assertEquals(new Integer(1), stackUsingDoubleLinkedList.pop());
	}

}
