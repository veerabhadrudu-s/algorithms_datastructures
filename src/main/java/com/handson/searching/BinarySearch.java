/**
 * 
 */
package com.handson.searching;

import com.handson.comparator.Comparator;

/**
 * @author sveera
 *
 */
public class BinarySearch {

	public Integer search(Integer numberToBeSearched, Integer... inputArray) {
		return recursiveSearch(numberToBeSearched, 0, inputArray.length - 1, inputArray);
	}

	private Integer recursiveSearch(Integer numberToBeSearched, Integer lowderIndex, Integer higherIndex,
			Integer[] inputArray) {
		if (lowderIndex > higherIndex)
			return -1;
		Integer midIndex = (lowderIndex + higherIndex) / 2;
		if (numberToBeSearched == inputArray[midIndex])
			return midIndex;
		else if (numberToBeSearched < inputArray[midIndex])
			return recursiveSearch(numberToBeSearched, lowderIndex, midIndex - 1, inputArray);
		else
			return recursiveSearch(numberToBeSearched, midIndex + 1, higherIndex, inputArray);
	}

	public Integer searchInAscended(Integer numberToBeSearched, Integer... inputArray) {
		return recursiveSearch((value1, value2) -> value1 < value2, numberToBeSearched, 0, inputArray.length - 1,
				inputArray);
	}

	public Integer searchInDescended(Integer numberToBeSearched, Integer... inputArray) {
		return recursiveSearch((value1, value2) -> value1 > value2, numberToBeSearched, 0, inputArray.length - 1,
				inputArray);
	}

	private Integer recursiveSearch(Comparator comparator, Integer numberToBeSearched, Integer lowderIndex,
			Integer higherIndex, Integer[] inputArray) {
		if (lowderIndex > higherIndex)
			return -1;
		Integer midIndex = (lowderIndex + higherIndex) / 2;
		if (numberToBeSearched == inputArray[midIndex])
			return midIndex;
		else if (comparator.compare(numberToBeSearched, inputArray[midIndex]))
			return recursiveSearch(comparator, numberToBeSearched, lowderIndex, midIndex - 1, inputArray);
		else
			return recursiveSearch(comparator, numberToBeSearched, midIndex + 1, higherIndex, inputArray);
	}

}
