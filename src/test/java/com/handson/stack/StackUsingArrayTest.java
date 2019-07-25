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
public class StackUsingArrayTest {

	@Test
	@DisplayName("test One Push And One Pop Operation")
	public void testPushAndPopOperation() {
		StackUsingArray<Integer> stackUsingArray = new StackUsingArray<>(5);
		stackUsingArray.push(1);
		assertEquals(new Integer(1), stackUsingArray.pop());
	}

	@Test
	@DisplayName("test Two Push And Two Pop Operations")
	public void testTwoPushAndTwoPopOperations() {
		StackUsingArray<Integer> stackUsingArray = new StackUsingArray<>(5);
		stackUsingArray.push(1);
		stackUsingArray.push(3);
		assertEquals(new Integer(3), stackUsingArray.pop());
		assertEquals(new Integer(1), stackUsingArray.pop());
	}

	@Test
	@DisplayName("test Two Push And Three Pop Operations")
	public void testTwoPushAndThreePopOperations() {
		StackUsingArray<Integer> stackUsingArray = new StackUsingArray<>(5);
		stackUsingArray.push(1);
		stackUsingArray.push(3);
		assertEquals(new Integer(3), stackUsingArray.pop());
		assertEquals(new Integer(1), stackUsingArray.pop());
		assertThrows(StackUnderFlowException.class, stackUsingArray::pop);
	}

	@Test
	@DisplayName("test stack of  initial capacity 2 with 3 Push operations")
	public void testStackOfInitialCapacity2WithThreePushOperations() {
		StackUsingArray<Integer> stackUsingArray = new StackUsingArray<>(2);
		stackUsingArray.push(1);
		stackUsingArray.push(3);
		stackUsingArray.push(4);
		assertEquals(new Integer(4), stackUsingArray.pop());
		assertEquals(new Integer(3), stackUsingArray.pop());
		assertEquals(new Integer(1), stackUsingArray.pop());
	}

}
