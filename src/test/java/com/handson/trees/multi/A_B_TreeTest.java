/**
 * 
 */
package com.handson.trees.multi;

import static com.handson.junit.RandomUtil.generateRandomShuffledNumbers;
import static java.util.Arrays.copyOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
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
public class A_B_TreeTest {

	@Nested
	class A_B_Tree_Invalid_ConfigurationTest {

		@Test
		public void test_A_B_TreeWithInvalidMinimumChildLimitAndExpectException() {
			assertThrows(MinChildrenSizeShouldBeTwo.class,
					() -> new A_B_Tree<Integer, Integer>((key1, key2) -> key1.equals(key2) ? 0 : key1 > key2 ? -1 : 1,
							1, 3));
		}

		@Test
		public void test_A_B_TreeWithInvalidMinMaxChildRealtionLimitsAndExpectException() {
			assertThrows(InvalidMinMaxChildrenLimits.class,
					() -> new A_B_Tree<Integer, Integer>((key1, key2) -> key1.equals(key2) ? 0 : key1 > key2 ? -1 : 1,
							10, 15));
		}

	}

	@Nested
	class Two_Three_TreeTest extends Nested_A_B_TreeTest {
		public Two_Three_TreeTest() {
			super(2, 3);
		}
	}

	@Nested
	@Disabled
	class Two_Four_TreeTest extends Nested_A_B_TreeTest {
		public Two_Four_TreeTest() {
			super(2, 4);
		}
	}

	@Nested
	class Two_Five_TreeTest extends Nested_A_B_TreeTest {
		public Two_Five_TreeTest() {
			super(2, 5);
		}
	}

	@Nested
	class Three_Six_TreeTest extends Nested_A_B_TreeTest {
		public Three_Six_TreeTest() {
			super(3, 6);
		}
	}

	@Nested
	class Fifty_Hundred_TreeTest extends Nested_A_B_TreeTest {
		public Fifty_Hundred_TreeTest() {
			super(50, 100);
		}
	}

	@Nested
	class Two_Hundred_Six_Hundred_TreeTest extends Nested_A_B_TreeTest {
		public Two_Hundred_Six_Hundred_TreeTest() {
			super(200, 600);
		}
	}

	@Nested
	class A_B_TreeComparePerformanceOfDiffrentConfigurationsTest {
		private final Logger logger = LoggerFactory.getLogger(getClass());
		private QuickSorting quickSorting = new QuickSorting();

		@RepeatedTest(3)
		public void testPerformance(RepetitionInfo repetitionInfo) {
			Integer[] keysToBeInserted = generateRandomShuffledNumbers(repetitionInfo, 50000);
			// logger.debug(Arrays.deepToString(keysToBeInserted));
			Integer[] sortedInsertedKeys = copyOf(keysToBeInserted, keysToBeInserted.length);
			quickSorting.sort(sortedInsertedKeys);
			String sortedInsertedKeysString = createCsvString(sortedInsertedKeys);
			A_B_TreeSpy<Integer, String> two_Four_Tree = new A_B_TreeSpy<>(
					(key1, key2) -> key1.equals(key2) ? 0 : key1 > key2 ? -1 : 1, 2, 4);
			A_B_TreeSpy<Integer, String> fifty_Hundred_Tree = new A_B_TreeSpy<>(
					(key1, key2) -> key1.equals(key2) ? 0 : key1 > key2 ? -1 : 1, 50, 100);
			A_B_TreeSpy<Integer, String> two_Hundred_Five_Hundred_Tree = new A_B_TreeSpy<>(
					(key1, key2) -> key1.equals(key2) ? 0 : key1 > key2 ? -1 : 1, 200, 500);
			long startTime = System.nanoTime();
			insertAndAssert(two_Four_Tree, keysToBeInserted, sortedInsertedKeysString);
			deleteKeys(two_Four_Tree, keysToBeInserted);
			long two_Four_Tree_RunningTime = System.nanoTime() - startTime;
			startTime = System.nanoTime();
			insertAndAssert(fifty_Hundred_Tree, keysToBeInserted, sortedInsertedKeysString);
			deleteKeys(fifty_Hundred_Tree, keysToBeInserted);
			long fifty_Hundred_Tree_RunningTime = System.nanoTime() - startTime;
			startTime = System.nanoTime();
			insertAndAssert(two_Hundred_Five_Hundred_Tree, keysToBeInserted, sortedInsertedKeysString);
			deleteKeys(two_Hundred_Five_Hundred_Tree, keysToBeInserted);
			long two_Hundred_Five_Hundred_Tree_RunningTime = System.nanoTime() - startTime;
			logger.debug(
					"Running time of " + two_Four_Tree + " is " + (double) two_Four_Tree_RunningTime / 1000000000.0);
			logger.debug("Running time of " + fifty_Hundred_Tree + " is "
					+ (double) fifty_Hundred_Tree_RunningTime / 1000000000.0);
			logger.debug("Running time of " + two_Hundred_Five_Hundred_Tree + " is "
					+ (double) two_Hundred_Five_Hundred_Tree_RunningTime / 1000000000.0);

		}

		private void insertAndAssert(A_B_TreeSpy<Integer, String> a_b_Tree, Integer[] keysToBeInserted,
				String sortedInsertedKeysString) {
			insertKeys(a_b_Tree, keysToBeInserted);
			searchKeys(a_b_Tree, keysToBeInserted);
			assertEquals(sortedInsertedKeysString, a_b_Tree.getInOrderTraversalValues());
		}

		private void insertKeys(A_B_TreeSpy<Integer, String> a_b_Tree, Integer[] keysToBeInserted) {
			for (Integer keyToBeInserted : keysToBeInserted)
				a_b_Tree.insert(keyToBeInserted, String.valueOf(keyToBeInserted));
		}

		private void searchKeys(A_B_TreeSpy<Integer, String> a_b_Tree, Integer[] keysToBeInserted) {
			for (Integer keyToBeInserted : keysToBeInserted)
				assertEquals(String.valueOf(keyToBeInserted), a_b_Tree.search(keyToBeInserted));
		}

		private void deleteKeys(A_B_TreeSpy<Integer, String> a_b_Tree, Integer[] keysToBeDeleted) {
			for (Integer keyToBeDeleted : keysToBeDeleted)
				assertEquals(String.valueOf(keyToBeDeleted), a_b_Tree.delete(keyToBeDeleted));
		}

		private String createCsvString(Integer[] sortedValues) {
			Set<Integer> distinctValues = new LinkedHashSet<>();
			for (Integer keyToBeInserted : sortedValues)
				distinctValues.add(keyToBeInserted);
			String csvString = "";
			for (Integer distinctValue : distinctValues)
				csvString += distinctValue + ",";
			return !csvString.isEmpty() ? csvString.substring(0, csvString.length() - 1) : null;
		}

	}

}
