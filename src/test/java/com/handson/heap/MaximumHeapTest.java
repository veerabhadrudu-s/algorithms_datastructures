/**
 * 
 */
package com.handson.heap;

import static com.handson.junit.RandomUtil.generateRandomShuffledNumbers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import com.handson.junit.IntegerArrayConverter;
import com.handson.sorting.QuickSorting;

/**
 * @author sveera
 *
 */
public class MaximumHeapTest {

	private MaximumHeap<Integer> maximumHeap;

	@BeforeEach
	public void setUp() throws Exception {
		maximumHeap = new MaximumHeap<>();
	}

	@Test
	public void testInEmptyHeap_getMaximum_ExpectNull() {
		assertNull(maximumHeap.maximum());
	}

	@Test
	public void testInSingleElementHeap_getMaximum_ExpectSingleValue() {
		maximumHeap.insert(2, 2);
		assertEquals(2, maximumHeap.maximum().priority);
	}

	@Test
	public void testInTwoElementHeap_getMaximum() {
		maximumHeap.insert(3, 3);
		maximumHeap.insert(2, 2);
		assertEquals(3, maximumHeap.maximum().priority);
	}

	@Test
	public void testInEmptyHeap_delete_Maximum_ExpectNull() {
		assertNull(maximumHeap.delete_maximum());
	}

	@Test
	public void testInTwoElementHeap_deleteMaximum() {
		maximumHeap.insert(2, 2);
		maximumHeap.insert(3, 3);
		assertEquals(3, maximumHeap.delete_maximum().priority);
		assertEquals(2, maximumHeap.delete_maximum().priority);
		assertNull(maximumHeap.maximum());
	}

	@Test
	public void testInThreeElementHeap_deleteMaximum() {
		maximumHeap.insert(4, 4);
		maximumHeap.insert(3, 3);
		maximumHeap.insert(2, 2);
		assertEquals(4, maximumHeap.delete_maximum().priority);
		assertEquals(3, maximumHeap.delete_maximum().priority);
		assertEquals(2, maximumHeap.delete_maximum().priority);
		assertNull(maximumHeap.maximum());
	}

	@Test
	public void testDecreaseKeyInSingleElementHeap() {
		maximumHeap.insert(2, 2);
		assertEquals(new Integer(2), maximumHeap.decreaseKey(1));
		assertNull(maximumHeap.maximum());
	}

	@DisplayName("test decrease key In two element heap")
	@ParameterizedTest(name = "test in two element heap inserted in order - [{0}] decrease Key at index {1} ")
	@CsvSource({ "'1,2',1,2,1", "'1,2',2,1,2" })
	public void testDeleteKeyInTwoElementHeap(@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted,
			int indexOfKeyToBeDecreased, Integer removedValue, Integer remainingValue) {
		for (Integer integer : valuesToBeInserted)
			maximumHeap.insert(integer, integer);
		assertEquals(removedValue, maximumHeap.decreaseKey(indexOfKeyToBeDecreased));
		assertEquals(remainingValue, maximumHeap.delete_maximum().value);
	}

	@DisplayName("test decrease key in multi element heap")
	@ParameterizedTest(name = "test in multi element heap inserted in order - [{0}] decrease Key at index {1} and "
			+ "expect delete maximum of remaining elements in this decreasing order {3}")
	@CsvSource({ "'50,49,10,48,47,9,8,46,45,44,43,7,6,5,4,42',16,42,'50,49,48,47,46,45,44,43,10,9,8,7,6,5,4'",
			"'50,49,10,48,47,9,8,46,45,44,43,7,6,5,4,42',12,7,'50,49,48,47,46,45,44,43,42,10,9,8,6,5,4'",
			"'50,49,10,48,47,9,8,46,45,44,43,7,6,5,4,42',1,50,'49,48,47,46,45,44,43,42,10,9,8,7,6,5,4'",
			"'50,49,10,48,47,9,8,46,45,44,43,7,6,5,4,42',3,10,'50,49,48,47,46,45,44,43,42,9,8,7,6,5,4'",
			"'50,49,10,48,47,9,8,46,45,44,43,7,6,5,4,42',2,49,'50,48,47,46,45,44,43,42,10,9,8,7,6,5,4'" })
	public void testDeleteKeyInMultiElementHeap(@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted,
			int indexOfKeyToBeDecreased, Integer removedValue, String expectedRemainingValuesInDecreasingOrder) {
		for (Integer integer : valuesToBeInserted)
			maximumHeap.insert(integer, integer);
		assertEquals(removedValue, maximumHeap.decreaseKey(indexOfKeyToBeDecreased));
		String actualRemainingValuesInDecreasingOrder = "";
		for (int i = 0; i < valuesToBeInserted.length - 1; i++)
			actualRemainingValuesInDecreasingOrder += maximumHeap.delete_maximum().value + ",";
		assertEquals(expectedRemainingValuesInDecreasingOrder, actualRemainingValuesInDecreasingOrder.substring(0,
				actualRemainingValuesInDecreasingOrder.length() - 1));
	}

	@Test
	public void testInsertArrayOfIncreasingOrderAndPerformDeleteMaximumElementOfAllElements() {
		Integer[] heapElements = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
				20, 21, 22, 23, 24, 25 };
		for (int i = 0; i < heapElements.length; i++) {
			maximumHeap.insert(heapElements[i], heapElements[i]);
			assertEquals(heapElements[i].intValue(), maximumHeap.maximum().priority);
		}
		for (int i = heapElements.length - 1; i >= 0; i--)
			assertEquals(heapElements[i].intValue(), maximumHeap.delete_maximum().priority);
	}

	@Test
	public void testInsertArrayWithDuplicatesAndPerformDeleteMaximumElementOfAllElements() {
		QuickSorting quickSorting = new QuickSorting();
		Integer[] heapElements = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
				20, 21, 22, 23, 24, 25, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
				22, 23, 24, 25 };
		for (int i = 0; i < heapElements.length; i++) {
			maximumHeap.insert(heapElements[i], heapElements[i]);
			assertEquals(heapElements[i > heapElements.length / 2 - 1 ? heapElements.length / 2 - 1 : i].intValue(),
					maximumHeap.maximum().priority);
		}
		quickSorting.sort(heapElements);
		for (int i = heapElements.length - 1; i >= 0; i--)
			assertEquals(heapElements[i].intValue(), maximumHeap.delete_maximum().priority);
	}

	@RepeatedTest(5)
	public void test_Random_Insert_getMaximum(RepetitionInfo repetitionInfo) {
		QuickSorting quickSorting = new QuickSorting();
		Integer[] keysInserted = generateRandomShuffledNumbers(repetitionInfo, 5000);
		for (int i = 0; i < keysInserted.length; i++) {
			maximumHeap.insert(keysInserted[i], keysInserted[i]);
			Integer[] insertedListArray = Arrays.copyOfRange(keysInserted, 0, i + 1);
			quickSorting.sort(insertedListArray);
			assertEquals(insertedListArray[insertedListArray.length - 1].intValue(), maximumHeap.maximum().priority);
		}
	}

	@RepeatedTest(100)
	public void test_Random_Insert_DeleteMaximum(RepetitionInfo repetitionInfo) {
		QuickSorting quickSorting = new QuickSorting();
		Integer[] keysInserted = generateRandomShuffledNumbers(repetitionInfo, 5000);
		for (int i = 0; i < keysInserted.length; i++)
			maximumHeap.insert(keysInserted[i], keysInserted[i]);
		quickSorting.sort(keysInserted);
		// logger.debug("Sorted elements " + Arrays.deepToString(keysInserted));
		for (int i = keysInserted.length - 1; i >= 0; i--)
			assertEquals(keysInserted[i].intValue(), maximumHeap.delete_maximum().priority);
		assertNull(maximumHeap.maximum());
	}

	@RepeatedTest(100)
	public void test_Random_DecreaseKey(RepetitionInfo repetitionInfo) {
		Random random = new Random();
		int randomLengthCount = 5000 + repetitionInfo.getCurrentRepetition();
		List<Integer> list = new ArrayList<>();
		for (int i = 0, randomValue; i < randomLengthCount; i++) {
			randomValue = random.nextInt();
			list.add(randomValue);
			maximumHeap.insert(randomValue, randomValue);
		}
		list.remove(maximumHeap.decreaseKey(list.size() / 2));
		Collections.sort(list);
		for (int i = randomLengthCount - 2; i >= 0; i--)
			assertEquals(list.get(i), maximumHeap.delete_maximum().value);
		assertNull(maximumHeap.maximum());
	}
}
