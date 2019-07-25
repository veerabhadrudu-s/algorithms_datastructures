/**
 * 
 */
package com.handson.sorting;

/**
 * @author sveera
 *
 */
public class QuickSorting {

	public void sort(Integer[] valuesTobeSorted) {
		sort(valuesTobeSorted, 0, valuesTobeSorted.length - 1);
	}

	public void sortUsingRandomPivot(Integer[] valuesTobeSorted) {
		sortUsingRandomPivot(valuesTobeSorted, 0, valuesTobeSorted.length - 1);
	}

	private void sort(Integer[] valuesTobeSorted, int leftIndex, int rightIndex) {
		if (leftIndex < rightIndex) {
			int partitionIndex = partition(valuesTobeSorted, leftIndex, rightIndex, rightIndex);
			sort(valuesTobeSorted, leftIndex, partitionIndex);
			sort(valuesTobeSorted, partitionIndex + 1, rightIndex);
		}

	}

	private void sortUsingRandomPivot(Integer[] valuesTobeSorted, int leftIndex, int rightIndex) {
		if (leftIndex < rightIndex) {
			int randomPivotIndex = randomPivot(leftIndex, rightIndex);
			int partitionIndex = partition(valuesTobeSorted, leftIndex, rightIndex, randomPivotIndex);
			sort(valuesTobeSorted, leftIndex, partitionIndex);
			sort(valuesTobeSorted, partitionIndex + 1, rightIndex);
		}

	}

	// This random pivot algorithm function should return value between leftIndex
	// and rightIndex inclusive. In this case we are simply considering midIndex
	// value between leftIndex and rightIndex.
	private int randomPivot(int leftIndex, int rightIndex) {
		return (leftIndex + rightIndex) / 2;
	}

	private int partition(Integer[] valuesTobeSorted, int leftIndex, int rightIndex, int pivotIndex) {
		int pivotElement = valuesTobeSorted[pivotIndex];
		leftIndex--;
		rightIndex++;
		while (true) {
			do
				leftIndex++;
			while (isIndexInBound(valuesTobeSorted, leftIndex) && valuesTobeSorted[leftIndex] < pivotElement);
			do
				rightIndex--;
			while (isIndexInBound(valuesTobeSorted, leftIndex) && valuesTobeSorted[rightIndex] > pivotElement);
			if (leftIndex > rightIndex)
				return rightIndex;
			else
				swapTwoElements(valuesTobeSorted, leftIndex, rightIndex);
		}

	}

	private boolean isIndexInBound(Integer[] valuesTobeSorted, int index) {
		return index > -1 && index < valuesTobeSorted.length;
	}

	private void swapTwoElements(Integer[] valuesTobeSorted, int leftIndex, int rightIndex) {
		Integer tmp = valuesTobeSorted[rightIndex];
		valuesTobeSorted[rightIndex] = valuesTobeSorted[leftIndex];
		valuesTobeSorted[leftIndex] = tmp;
	}

}
