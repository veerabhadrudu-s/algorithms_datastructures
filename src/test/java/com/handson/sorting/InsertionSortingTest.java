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
import java.util.Random;

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
public class InsertionSortingTest {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private InsertionSorting insertionSorting;

	@BeforeEach
	public void setUp() throws Exception {
		insertionSorting = new InsertionSorting();
	}

	@Test
	@DisplayName("is InsertionSorting not null")
	public void testInsertionSorting() {
		assertNotNull(insertionSorting);
	}

	/*
	 * If numbers are already in expected sorted order (best case) complexity of
	 * this algorithm is O(n). If numbers are in reverse order (worst case)
	 * complexity of this algorithm is n^2/2=O(n^2).
	 */
	@DisplayName("test sort numbers")
	@ParameterizedTest(name = "for {1} and expected order is {0}")
	@CsvSource({ "'1','1'", "'1,2,3,4','1,2,3,4'", "'1,2','2,1'", "'1,2,3','2,1,3'", "'1,2,3','3,2,1'",
			"'1,2,3,4,5,6,7,8,9,10','10,9,8,7,6,5,4,3,2,1'", "'1,1,2,3,4,5,6,7,8,9,10','5,4,3,2,1,6,7,8,9,10,1'" })
	public void testSort(@ConvertWith(IntegerArrayConverter.class) Integer[] expectedSortedValues,
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesTobeSorted) {
		logger.debug("\n\n\n");
		insertionSorting.sort(valuesTobeSorted);
		assertArrayEquals(expectedSortedValues, valuesTobeSorted);
	}

	@DisplayName("test sort numbers in ascending order")
	@ParameterizedTest(name = "for {1} and expected order is {0}")
	@CsvSource({ "'1','1'", "'1,2,3,4','1,2,3,4'", "'1,2','2,1'", "'1,2,3','2,1,3'", "'1,2,3','3,2,1'",
			"'1,2,3,4,5,6,7,8,9,10','10,9,8,7,6,5,4,3,2,1'", "'1,1,2,3,4,5,6,7,8,9,10','5,4,3,2,1,6,7,8,9,10,1'" })
	public void testSortAscending(@ConvertWith(IntegerArrayConverter.class) Integer[] expectedSortedValues,
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesTobeSorted) {
		logger.debug("\n\n\n");
		insertionSorting.sortAscending(valuesTobeSorted);
		assertArrayEquals(expectedSortedValues, valuesTobeSorted);
	}

	@DisplayName("test sort numbers in descending order")
	@ParameterizedTest(name = "for {1} and expected order is {0}")
	@CsvSource({ "'1','1'", "'4,3,2,1','1,2,3,4'", "'2,1','1,2'", "'3,2,1','2,1,3'", "'3,2,1','3,2,1'",
			"'10,9,8,7,6,5,4,3,2,1','1,2,3,4,5,6,7,8,9,10'", "'10,9,8,7,6,5,4,3,2,1,1','5,4,3,2,1,6,7,8,9,10,1'" })
	public void testSortDescending(@ConvertWith(IntegerArrayConverter.class) Integer[] expectedSortedValues,
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesTobeSorted) {
		logger.debug("\n\n\n");
		insertionSorting.sortDescending(valuesTobeSorted);
		assertArrayEquals(expectedSortedValues, valuesTobeSorted);
	}

	@DisplayName("test sorting randomly")
	@RepeatedTest(1)
	public void testSortingRandomly(RepetitionInfo repetitionInfo) {
		logger.debug("Repeating index " + repetitionInfo.getCurrentRepetition());
		Integer[] arrayToBeSorted = generateRandomShuffledNumbers(repetitionInfo, 20000);
		Integer[] expectedSortedArray = Arrays.copyOf(arrayToBeSorted, arrayToBeSorted.length);
		insertionSorting.sort(arrayToBeSorted);
		Collections.sort(asList(expectedSortedArray));
		assertArrayEquals(expectedSortedArray, arrayToBeSorted);
	}

}
