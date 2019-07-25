/**
 * 
 */
package com.handson.sorting;

import static com.handson.junit.RandomUtil.generateRandomShuffledNumbers;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.handson.junit.IntegerArrayConverter;

/**
 * @author sveera
 *
 */
public class QuickSortingTest {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private QuickSorting quickSorting;

	@BeforeEach
	public void setUp() throws Exception {
		quickSorting = new QuickSorting();
	}

	@Test
	@DisplayName("is InsertionSorting not null")
	public void testInsertionSorting() {
		assertNotNull(quickSorting);
	}

	/*
	 * Quick sorting is a in place sorting algorithm i.e It gives a space complexity
	 * of O(n). Quick sorting works on the mechanism of divide and concur paradigm
	 * using selected pivot element. Generally, quick sorting algorithm gives a
	 * average complexity of O(n*log(n)). If numbers are in already sorted order it
	 * gives a worst complexity of O(n^2). To over comes this, we are selecting
	 * pivot element using random algorithm to reach average complexity of
	 * (O(n*log(n))) in all cases.As long as random algorithm select's appropriate
	 * pivot element we will get complexity of (O(n*log(n))) but still it might give
	 * complexity of O(n^2) for same input, as we are using random algorithm of
	 * pivot selection.
	 * 
	 */
	@DisplayName("test sort numbers using random pivot ")
	@ParameterizedTest(name = "for {1} and expected order is {0}")
	@CsvSource({ "'1','1'", "'1,2,3,4','1,2,3,4'", "'1,2','2,1'", "'1,2,3','2,1,3'", "'1,2,3','3,2,1'",
			"'1,2,3,4,5,6,7,8,9,10','1,2,3,4,5,6,7,8,9,10'", "'1,2,3,4,5,6,7,8,9,10','10,9,8,7,6,5,4,3,2,1'",
			"'1,1,2,3,4,5,6,7,8,9,10','5,4,3,2,1,6,7,8,9,10,1'",
			"'0,0,0,0,0,0,0,0,0,0,0,0,0,0,0','0,0,0,0,0,0,0,0,0,0,0,0,0,0,0'",
			"'0,0,0,0,0,0,0,0,1,1,1,1,1,1,1','0,1,0,1,0,1,0,1,0,1,0,1,0,1,0'" })
	public void testSortUsingRandomPivot(@ConvertWith(IntegerArrayConverter.class) Integer[] expectedSortedValues,
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesTobeSorted) {
		quickSorting.sortUsingRandomPivot(valuesTobeSorted);
		assertArrayEquals(expectedSortedValues, valuesTobeSorted);
	}

	@DisplayName("test sorting using random numbers with random pivot")
	@RepeatedTest(100)
	public void testSortingUsingRandomNumbersWithRandomPivot(RepetitionInfo repetitionInfo) {
		logger.debug("Repeating index " + repetitionInfo.getCurrentRepetition());
		Integer[] arrayToBeSorted = generateRandomShuffledNumbers(repetitionInfo, 20000);
		Integer[] expectedSortedArray = Arrays.copyOf(arrayToBeSorted, arrayToBeSorted.length);
		quickSorting.sortUsingRandomPivot(arrayToBeSorted);
		Collections.sort(asList(expectedSortedArray));
		assertArrayEquals(expectedSortedArray, arrayToBeSorted);
	}

	@DisplayName("test sort numbers")
	@ParameterizedTest(name = "for {1} and expected order is {0}")
	@CsvSource({ "'1','1'", "'1,2,3,4','1,2,3,4'", "'1,2','2,1'", "'1,2,3','2,1,3'", "'1,2,3','3,2,1'",
			"'1,2,3,4,5,6,7,8,9,10','1,2,3,4,5,6,7,8,9,10'", "'1,2,3,4,5,6,7,8,9,10','10,9,8,7,6,5,4,3,2,1'",
			"'1,1,2,3,4,5,6,7,8,9,10','5,4,3,2,1,6,7,8,9,10,1'",
			"'0,0,0,0,0,0,0,0,0,0,0,0,0,0,0','0,0,0,0,0,0,0,0,0,0,0,0,0,0,0'",
			"'0,0,0,0,0,0,0,0,1,1,1,1,1,1,1','0,1,0,1,0,1,0,1,0,1,0,1,0,1,0',",
			"'0,0,0,0,0,0,0,0,0,0,0,0,0,1,1','1,0,0,0,0,0,0,0,0,0,0,0,0,0,1'" })
	public void testSort(@ConvertWith(IntegerArrayConverter.class) Integer[] expectedSortedValues,
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesTobeSorted) {
		quickSorting.sort(valuesTobeSorted);
		assertArrayEquals(expectedSortedValues, valuesTobeSorted);
	}

	@DisplayName("test sorting using random numbers")
	@RepeatedTest(100)
	public void testSortingUsingRandomNumbers(RepetitionInfo repetitionInfo) {
		logger.debug("Repeating index " + repetitionInfo.getCurrentRepetition());
		Integer[] arrayToBeSorted = generateRandomShuffledNumbers(repetitionInfo, 20000);
		Integer[] expectedSortedArray = Arrays.copyOf(arrayToBeSorted, arrayToBeSorted.length);
		// logger.debug(Arrays.deepToString(arrayToBeSorted));
		quickSorting.sort(arrayToBeSorted);
		Collections.sort(asList(expectedSortedArray));
		assertArrayEquals(expectedSortedArray, arrayToBeSorted);
	}

}
