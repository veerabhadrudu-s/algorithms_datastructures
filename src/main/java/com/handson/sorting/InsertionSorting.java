/**
 * 
 */
package com.handson.sorting;

import com.handson.comparator.Comparator;

/**
 * @author sveera
 *
 */
public class InsertionSorting {

	public void sort(Integer... valuesTobeSorted) {
		for (Integer i = 0; i < valuesTobeSorted.length; i++) {
			for (Integer j = i; j > 0; j--)
				if (valuesTobeSorted[j] < valuesTobeSorted[j - 1])
					swapTwoValues(j - 1, j, valuesTobeSorted);
				else
					break;
		}
	}

	public void sortAscending(Integer... valuesTobeSorted) {
		sortWithComparator((value1, value2) -> value1 < value2, valuesTobeSorted);
	}

	public void sortDescending(Integer... valuesTobeSorted) {
		sortWithComparator((value1, value2) -> value1 > value2, valuesTobeSorted);
	}

	private void sortWithComparator(Comparator comparator, Integer... valuesTobeSorted) {
		for (Integer i = 0; i < valuesTobeSorted.length; i++)
			for (Integer j = i; j > 0; j--)
				if (comparator.compare(valuesTobeSorted[j], valuesTobeSorted[j - 1]))
					swapTwoValues(j - 1, j, valuesTobeSorted);
				else
					break;
	}

	private void swapTwoValues(Integer fromIndex, Integer toIndex, Integer... valuesTobeSorted) {
		valuesTobeSorted[toIndex] += valuesTobeSorted[fromIndex];
		valuesTobeSorted[fromIndex] = valuesTobeSorted[toIndex] - valuesTobeSorted[fromIndex];
		valuesTobeSorted[toIndex] = valuesTobeSorted[toIndex] - valuesTobeSorted[fromIndex];
	}

}
