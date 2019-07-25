/**
 * 
 */
package com.handson.junit;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.RepetitionInfo;

/**
 * @author sveera
 *
 */
public class RandomUtil {

	public static Integer[] generateRandomShuffledNumbers(RepetitionInfo repetitionInfo, int noOfElements) {
		Random random = ThreadLocalRandom.current();
		int randomLengthCount = noOfElements + repetitionInfo.getCurrentRepetition();
		LinkedHashSet<Integer> distinctSet = new LinkedHashSet<>();
		for (int i = 0; i < randomLengthCount; i++)
			distinctSet.add(random.nextInt());
		Integer[] keysToBeInserted = new Integer[distinctSet.size()];
		int i = 0;
		for (Iterator<Integer> iterator = distinctSet.iterator(); iterator.hasNext(); i++)
			keysToBeInserted[i] = iterator.next();
		shuffleArray(keysToBeInserted);
		return keysToBeInserted;

	}

	// Implementation of Fisher–Yates shuffle
	private static void shuffleArray(Integer[] ar) {
		Random rnd = ThreadLocalRandom.current();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

}
