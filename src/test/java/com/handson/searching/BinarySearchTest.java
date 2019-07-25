/**
 * 
 */
package com.handson.searching;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import com.handson.junit.IntegerArrayConverter;

/**
 * @author sveera
 *
 */
public class BinarySearchTest {

	private BinarySearch binarySearch;

	@BeforeEach
	public void setUp() throws Exception {
		binarySearch = new BinarySearch();
	}

	/*
	 * The worst case complexity of this algorithm is O(log n). However this
	 * algorithm expects numbers to be in sorted order.
	 */
	@DisplayName("test Binary Search")
	@ParameterizedTest(name = " with input array of [{0}] for {1} and expect index as {2}")
	@CsvSource({ "'1',2,-1", "'1,2,3,4,5',-1,-1", "'1,2,3,4,5',2,1", "'1,2,4,5,6,7,9,10',2,1" })
	public void testBinarySearch(@ConvertWith(IntegerArrayConverter.class) Integer[] inputArray, int numberToBeSearched,
			Integer expectedIndex) {
		assertEquals(expectedIndex, binarySearch.search(numberToBeSearched, inputArray));
	}

	@DisplayName("test Binary Search in Ascended Values ")
	@ParameterizedTest(name = " with input array of [{0}] for {1} and expect index as {2}")
	@CsvSource({ "'1',2,-1", "'1,2,3,4,5',-1,-1", "'1,2,3,4,5',2,1", "'1,2,4,5,6,7,9,10',2,1" })
	public void testBinarySearchInAscendedValues(@ConvertWith(IntegerArrayConverter.class) Integer[] inputArray,
			Integer numberToBeSearched, Integer expectedIndex) {
		assertEquals(expectedIndex, binarySearch.searchInAscended(numberToBeSearched, inputArray));
	}

	@DisplayName("test Binary Search in Descended Values ")
	@ParameterizedTest(name = " with input array of [{0}] for {1} and expect index as {2}")
	@CsvSource({ "'1',2,-1", "'5,4,3,2,1',-1,-1", "'5,4,3,2,1',2,3", "'10,9,7,6,5,4,2,1',2,6" })
	public void testBinarySearchInDescendedValues(@ConvertWith(IntegerArrayConverter.class) Integer[] inputArray,
			Integer numberToBeSearched, Integer expectedIndex) {
		assertEquals(expectedIndex, binarySearch.searchInDescended(numberToBeSearched, inputArray));
	}

}
