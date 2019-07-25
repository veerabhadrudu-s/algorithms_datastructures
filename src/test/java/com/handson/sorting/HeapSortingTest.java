/**
 * 
 */
package com.handson.sorting;

import static com.handson.junit.RandomUtil.generateRandomShuffledNumbers;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

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
public class HeapSortingTest {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private HeapSorting heapSorting;

	@BeforeEach
	public void setUp() throws Exception {
		heapSorting = new HeapSorting();
	}

	@DisplayName("test sort numbers ")
	@ParameterizedTest(name = "for {1} and expected order is {0}")
	@CsvSource({ "'1','1'", "'1,2,3,4','1,2,3,4'", "'1,2','2,1'", "'1,2,3','2,1,3'", "'1,2,3','3,2,1'",
			"'1,2,3,4,5,6,7,8,9,10','1,2,3,4,5,6,7,8,9,10'", "'1,2,3,4,5,6,7,8,9,10','10,9,8,7,6,5,4,3,2,1'",
			"'1,1,2,3,4,5,6,7,8,9,10','5,4,3,2,1,6,7,8,9,10,1'",
			"'0,0,0,0,0,0,0,0,0,0,0,0,0,0,0','0,0,0,0,0,0,0,0,0,0,0,0,0,0,0'",
			"'0,0,0,0,0,0,0,0,1,1,1,1,1,1,1','0,1,0,1,0,1,0,1,0,1,0,1,0,1,0'" })
	public void testSort(@ConvertWith(IntegerArrayConverter.class) Integer[] expectedSortedValues,
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesTobeSorted) {
		heapSorting.sort(valuesTobeSorted);
		assertArrayEquals(expectedSortedValues, valuesTobeSorted);
	}

	@Test
	public void testSortElementsWithDuplicates() {
		Integer[] heapElements = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
				20, 21, 22, 23, 24, 25 };
		Integer[] expectedElements = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
				19, 20, 21, 22, 23, 24, 25 };
		heapSorting.sort(heapElements);
		Collections.sort(asList(expectedElements));
		assertArrayEquals(expectedElements, heapElements);
	}

	@DisplayName("test sorting using random numbers")
	@RepeatedTest(100)
	public void testSortingUsingRandomNumbers(RepetitionInfo repetitionInfo) {
		logger.debug("Repeating index " + repetitionInfo.getCurrentRepetition());
		Integer[] arrayToBeSorted = generateRandomShuffledNumbers(repetitionInfo, 20000);
		Integer[] expectedSortedArray = Arrays.copyOf(arrayToBeSorted, arrayToBeSorted.length);
		// logger.debug(Arrays.deepToString(arrayToBeSorted));
		heapSorting.sort(arrayToBeSorted);
		Collections.sort(asList(expectedSortedArray));
		assertArrayEquals(expectedSortedArray, arrayToBeSorted);
	}
}
