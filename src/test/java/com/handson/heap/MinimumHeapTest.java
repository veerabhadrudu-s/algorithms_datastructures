/**
 * 
 */
package com.handson.heap;

import static com.handson.junit.RandomUtil.generateRandomShuffledNumbers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.handson.sorting.QuickSorting;

/**
 * @author sveera
 *
 */
public class MinimumHeapTest {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private MinimumHeap<Integer> mimimumHeap;

	@BeforeEach
	public void setUp() throws Exception {
		mimimumHeap = new MinimumHeap<>();
	}

	@Test
	public void testInEmptyHeap_getMinimum_ExpectNull() {
		assertNull(mimimumHeap.minimum());
	}

	@Test
	public void testInSingleElementHeap_getMinimum_ExpectSingleValue() {
		mimimumHeap.insert(2, 2);
		assertEquals(2, mimimumHeap.minimum().priority);
	}

	@Test
	public void testInTwoElementHeap_getMinimum() {
		mimimumHeap.insert(2, 2);
		mimimumHeap.insert(3, 3);
		assertEquals(2, mimimumHeap.minimum().priority);
	}

	@Test
	public void testInEmptyHeap_delete_Minimum_ExpectNull() {
		assertNull(mimimumHeap.delete_minimum());
	}

	@Test
	public void testInTwoElementHeap_deleteMinimum() {
		mimimumHeap.insert(2, 2);
		mimimumHeap.insert(3, 3);
		assertEquals(2, mimimumHeap.delete_minimum().priority);
		assertEquals(3, mimimumHeap.delete_minimum().priority);
		assertNull(mimimumHeap.minimum());
	}

	@Test
	public void testInThreeElementHeap_deleteMinimum() {
		mimimumHeap.insert(2, 2);
		mimimumHeap.insert(3, 3);
		mimimumHeap.insert(4, 4);
		assertEquals(2, mimimumHeap.delete_minimum().priority);
		assertEquals(3, mimimumHeap.delete_minimum().priority);
		assertEquals(4, mimimumHeap.delete_minimum().priority);
		assertNull(mimimumHeap.minimum());
	}

	@Test
	public void testInsertArrayOfDecreasingOrderAndPerformDeleteMinimumElementOfAllElements() {
		Integer[] heapElements = new Integer[] { 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8,
				7, 6, 5, 4, 3, 2, 1, 0 };

		for (int i = 0; i < heapElements.length; i++) {
			mimimumHeap.insert(heapElements[i], heapElements[i]);
			assertEquals(heapElements[i].intValue(), mimimumHeap.minimum().priority);
		}
		for (int i = heapElements.length - 1; i >= 0; i--)
			assertEquals(heapElements[i].intValue(), mimimumHeap.delete_minimum().priority);
	}

	@RepeatedTest(1)
	public void test_Random_Insert_getMinimum(RepetitionInfo repetitionInfo) {
		QuickSorting quickSorting = new QuickSorting();
		Integer[] keysInserted = generateRandomShuffledNumbers(repetitionInfo, 5000);
		for (int i = 0; i < keysInserted.length; i++) {
			mimimumHeap.insert(keysInserted[i], keysInserted[i]);
			Integer[] insertedListArray = Arrays.copyOfRange(keysInserted, 0, i + 1);
			quickSorting.sort(insertedListArray);
			assertEquals(insertedListArray[0].intValue(), mimimumHeap.minimum().priority);
		}
	}

	@RepeatedTest(1)
	public void test_Random_Insert_DeleteMinimum(RepetitionInfo repetitionInfo) {
		QuickSorting quickSorting = new QuickSorting();
		Integer[] keysInserted = generateRandomShuffledNumbers(repetitionInfo, 5000);
		for (int i = 0; i < keysInserted.length; i++)
			mimimumHeap.insert(keysInserted[i], keysInserted[i]);
		logger.debug("Sorted elements " + Arrays.deepToString(keysInserted));
		quickSorting.sort(keysInserted);

		for (int i = 0; i < keysInserted.length; i++)
			assertEquals(keysInserted[i].intValue(), mimimumHeap.delete_minimum().priority);
		assertNull(mimimumHeap.minimum());
	}

}
