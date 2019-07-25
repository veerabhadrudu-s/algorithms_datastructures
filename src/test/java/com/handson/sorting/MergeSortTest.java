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
public class MergeSortTest {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private MergeSort mergeSort;

	@BeforeEach
	public void setUp() throws Exception {
		mergeSort = new MergeSort();
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
		mergeSort.sort(valuesTobeSorted);
		assertArrayEquals(expectedSortedValues, valuesTobeSorted);
	}

	@DisplayName("test sorting using random numbers")
	@RepeatedTest(100)
	public void testSortingUsingRandomNumbers(RepetitionInfo repetitionInfo) {
		logger.debug("Repeating index " + repetitionInfo.getCurrentRepetition());
		Integer[] arrayToBeSorted = generateRandomShuffledNumbers(repetitionInfo, 20000);
		Integer[] expectedSortedArray = Arrays.copyOf(arrayToBeSorted, arrayToBeSorted.length);
		// logger.debug(Arrays.deepToString(arrayToBeSorted));
		mergeSort.sort(arrayToBeSorted);
		Collections.sort(asList(expectedSortedArray));
		assertArrayEquals(expectedSortedArray, arrayToBeSorted);
	}

}
