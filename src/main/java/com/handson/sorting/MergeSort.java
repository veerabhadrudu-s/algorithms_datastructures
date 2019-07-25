/**
 * 
 */
package com.handson.sorting;

/**
 * @author sveera
 *
 */
public class MergeSort {

	public void sort(Integer[] valuesTobeSorted) {
		if (valuesTobeSorted == null)
			return;
		doMergeSort(valuesTobeSorted, 0, valuesTobeSorted.length - 1);
	}

	private void doMergeSort(Integer[] valuesTobeSorted, int lowerIndex, int upperIndex) {
		if (lowerIndex >= upperIndex)
			return;
		int midIndex = (lowerIndex + upperIndex) / 2;
		doMergeSort(valuesTobeSorted, lowerIndex, midIndex);
		doMergeSort(valuesTobeSorted, midIndex + 1, upperIndex);
		mergeByComparingValues(valuesTobeSorted, lowerIndex, midIndex, midIndex + 1, upperIndex);
	}

	private void mergeByComparingValues(Integer[] valuesTobeSorted, int leftArrayLowerIndex, int leftArrayUpperIndex,
			int rightArrayLowerIndex, int rightArrayUpperIndex) {
		int sortedValueCurrentIndex = 0, i = leftArrayLowerIndex, j = rightArrayLowerIndex;
		Integer[] sortedValues = new Integer[(rightArrayUpperIndex - leftArrayLowerIndex) + 1];
		while (i <= leftArrayUpperIndex && j <= rightArrayUpperIndex)
			if (valuesTobeSorted[i] > valuesTobeSorted[j])
				sortedValues[sortedValueCurrentIndex++] = valuesTobeSorted[j++];
			else
				sortedValues[sortedValueCurrentIndex++] = valuesTobeSorted[i++];
		while (i <= leftArrayUpperIndex)
			sortedValues[sortedValueCurrentIndex++] = valuesTobeSorted[i++];
		while (j <= rightArrayUpperIndex)
			sortedValues[sortedValueCurrentIndex++] = valuesTobeSorted[j++];
		for (int k = 0; k < sortedValues.length; k++)
			valuesTobeSorted[leftArrayLowerIndex++] = sortedValues[k];
	}

}
