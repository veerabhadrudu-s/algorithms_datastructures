/**
 * 
 */
package com.handson.trees.balanced;

import static com.handson.junit.RandomUtil.generateRandomShuffledNumbers;
import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import com.handson.junit.IntegerArrayConverter;
import com.handson.sorting.QuickSorting;
import com.handson.trees.Comparator;
import com.handson.trees.EularTourTreeTraversal;

/**
 * @author sveera
 *
 */
public class RedBlackTreeTest {

	private RedBlackTreeSpy<Integer, String> redBlackTree;

	@BeforeEach
	public void setUp() throws Exception {
		redBlackTree = new RedBlackTreeSpy<>((nodeKey, key) -> nodeKey.equals(key) ? 0 : nodeKey > key ? -1 : 1);
	}

	@Test
	@DisplayName("test search Key in Empty Tree")
	public void testSearchKeyInEmptyTree() {
		redBlackTree.search(new Integer(400));
		assertEquals(null, redBlackTree.readKeysInOrderTraversal());
		redBlackTree.validateBlackHeightOfTree(0);
	}

	@Test
	@DisplayName("test insert Key in empty Tree")
	public void testInsertKeyInEmptyTree() {
		insertAndAssert(400);
		assertEquals("400", redBlackTree.readKeysInOrderTraversal());
		redBlackTree.validateBlackHeightOfTree(1);
	}

	@Test
	@DisplayName("test insert Key in root node Tree")
	public void testInsertKeyInRootNodeTree() {
		insertAndAssert(400, 600, 200);
		assertEquals("200,400,600", redBlackTree.readKeysInOrderTraversal());
		redBlackTree.validateBlackHeightOfTree(1);
	}

	@DisplayName("test insert Key in complete binary tree of height 2 to create double red problem")
	@ParameterizedTest(name = " for input inserted in order {0} and expect change in black height of tree")
	@CsvSource({ "'400,600,200,500','200,400,500,600',2", "'400,600,200,100','100,200,400,600',2",
			"'400,600,200,300','200,300,400,600',2", "'400,600,200,700','200,400,600,700',2" })
	public void testInsertKeyInCompleteBinaryTreeOfHeight2ToCreateDoubleRedProblem(
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted, String sortedOrder,
			int expectedBlackHeight) {
		insertAndAssert(valuesToBeInserted);
		assertEquals(sortedOrder, redBlackTree.readKeysInOrderTraversal());
		redBlackTree.validateBlackHeightOfTree(expectedBlackHeight);
	}

	@DisplayName("test insert Key in tree of height 2 to create double red problem")
	@ParameterizedTest(name = "for input inserted in order {0} and expect {3} rotation")
	@CsvSource({ "'400,600,700','400,600,700',1,'L'", "'400,600,500','400,500,600',1,'RL'",
			"'400,200,100','100,200,400',1,'R'", "'400,200,300','200,300,400',1,'LR'" })
	public void testInsertKeyInTreeOfHeight2ToCreateDoubleRedProblem(
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted, String sortedOrder,
			int expectedBlackHeight, String expectedRotationType) {
		insertAndAssert(valuesToBeInserted);
		assertEquals(sortedOrder, redBlackTree.readKeysInOrderTraversal());
		redBlackTree.validateBlackHeightOfTree(expectedBlackHeight);
	}

	@DisplayName("test insert Key in complete binary tree of height 3 to create double red problem")
	@ParameterizedTest(name = "for input inserted in order {0} and expect change in black height of tree")
	@CsvSource({ "'400,600,200,500,700,750','200,400,500,600,700,750',2",
			"'400,600,200,500,700,650','200,400,500,600,650,700',2",
			"'400,600,200,500,700,50','50,200,400,500,600,700',2",
			"'400,600,200,500,700,150','150,200,400,500,600,700',2" })
	public void testInsertKeyInCompleteBinaryTreeOfHeight3ToCreateDoubleRedProblem(
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted, String sortedOrder,
			int expectedBlackHeight) {
		insertAndAssert(valuesToBeInserted);
		assertEquals(sortedOrder, redBlackTree.readKeysInOrderTraversal());
		redBlackTree.validateBlackHeightOfTree(expectedBlackHeight);
	}

	@DisplayName("test insert Key in tree of height 4 to create double red problem")
	@ParameterizedTest(name = "for input inserted in order {0} and expect color change and {3} rotation ")
	@CsvSource({
			"'400,200,600,100,300,500,700,450,550,650,750,775','100,200,300,400,450,500,550,600,650,700,750,775',2,'L'",
			"'400,200,600,100,300,500,700,450,550,650,750,425','100,200,300,400,425,450,500,550,600,650,700,750',2,'RL'",
			"'400,200,600,100,300,500,700,50,150,250,350,25','25,50,100,150,200,250,300,350,400,500,600,700',2,'R'",
			"'400,200,600,100,300,500,700,50,150,250,350,375','50,100,150,200,250,300,350,375,400,500,600,700',2,'LR'" })
	public void testInsertKeyInTreeOfHeight4ToCreateDoubleRedProblem(
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted, String sortedOrder,
			int expectedBlackHeight, String expectedRotationType) {
		insertAndAssert(valuesToBeInserted);
		assertEquals(sortedOrder, redBlackTree.readKeysInOrderTraversal());
		redBlackTree.validateBlackHeightOfTree(expectedBlackHeight);
	}

	@DisplayName("test insert Key in complete binary tree of height 4 to create double red problem")
	@ParameterizedTest(name = "for input inserted in order {0} and expect multiple color changes and increase in black height of tree")
	@CsvSource({
			"'400,200,600,100,300,500,700,50,150,250,350,450,550,650,750,775','50,100,150,200,250,300,350,400,450,500,550,600,650,700,750,775',3",
			"'400,200,600,100,300,500,700,50,150,250,350,450,550,650,750,25','25,50,100,150,200,250,300,350,400,450,500,550,600,650,700,750',3",
			"'400,200,600,100,300,500,700,50,150,250,350,450,550,650,750,275','50,100,150,200,250,275,300,350,400,450,500,550,600,650,700,750',3",
			"'400,200,600,100,300,500,700,50,150,250,350,450,550,650,750,325','50,100,150,200,250,300,325,350,400,450,500,550,600,650,700,750',3" })
	public void testInsertKeyInCompleteBinaryTreeOfHeight4ToCreateDoubleRedProblem(
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted, String sortedOrder,
			int expectedBlackHeight) {
		insertAndAssert(valuesToBeInserted);
		assertEquals(sortedOrder, redBlackTree.readKeysInOrderTraversal());
		redBlackTree.validateBlackHeightOfTree(expectedBlackHeight);
	}

	@DisplayName("test find minimum key in red black tree created using input sequance")
	@ParameterizedTest(name = "{0}")
	@CsvSource({ ",,", "'400','400',400", "'400,200','200,400',200",
			"'400,600,200,500,700,750','200,400,500,600,700,750',200",
			"'400,200,600,100,300,500,700,50,150,250,350,450,550,650,750,775','50,100,150,200,250,300,350,400,450,500,550,600,650,700,750,775',50" })
	public void testFindMinimumKeyInRedBlackTree(@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted,
			String sortedOrder, Integer minimumKey) {
		insertAndAssert(valuesToBeInserted);
		assertEquals(sortedOrder, redBlackTree.readKeysInOrderTraversal());
		assertEquals(minimumKey, redBlackTree.findMinimumKey());
	}

	@DisplayName("test find maximum key in red black tree created using input sequance")
	@ParameterizedTest(name = "{0}")
	@CsvSource({ ",,", "'400','400',400", "'400,200','200,400',400",
			"'400,600,200,500,700,750','200,400,500,600,700,750',750",
			"'400,200,600,100,300,500,700,50,150,250,350,450,550,650,750,775','50,100,150,200,250,300,350,400,450,500,550,600,650,700,750,775',775" })
	public void testFindMaximumKeyInRedBlackTree(@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted,
			String sortedOrder, Integer maximumKey) {
		insertAndAssert(valuesToBeInserted);
		assertEquals(sortedOrder, redBlackTree.readKeysInOrderTraversal());
		assertEquals(maximumKey, redBlackTree.findMaximumKey());
	}

	@DisplayName("test find successor key in red black tree")
	@ParameterizedTest(name = "find successor key of {2} in red black tree created using input sequance {0}")
	@CsvSource({ ",,400,", "'400','400',400,", "'400,200','200,400',400,",
			"'400,600,200,500,700,750','200,400,500,600,700,750',400,500",
			"'400,200,600,100,300,500,700,50,150,250,350,450,550,650,750,775','50,100,150,200,250,300,350,400,450,500,550,600,650,700,750,775',400,450",
			"'400,200,600,100,300,500,700,50,150,250,350,450,550,650,750,775','50,100,150,200,250,300,350,400,450,500,550,600,650,700,750,775',775," })
	public void testFindSuccessorKeyInRedBlackTree(
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted, String sortedOrder,
			Integer successorForKeyToBeFound, Integer successorKey) {
		insertAndAssert(valuesToBeInserted);
		assertEquals(sortedOrder, redBlackTree.readKeysInOrderTraversal());
		RedBlackNode<Integer, String> successorNode = redBlackTree.findSuccessor(successorForKeyToBeFound);
		Integer actualSuccessorKey = successorNode != null ? successorNode.key : null;
		assertEquals(successorKey, actualSuccessorKey);
	}

	@Test
	@DisplayName("test delete Key in empty Tree")
	public void testDeleteKeyInEmptyTree() {
		redBlackTree.delete(400);
		assertNull(redBlackTree.readKeysInOrderTraversal());
		redBlackTree.validateBlackHeightOfTree(0);
	}

	@Test
	@DisplayName("test delete Key in root node tree")
	public void testDeleteKeyInRootNodeTree() {
		insertValues(400);
		redBlackTree.delete(400);
		assertNull(redBlackTree.readKeysInOrderTraversal());
		redBlackTree.validateBlackHeightOfTree(0);
	}

	@DisplayName("test delete key in tree height of 2")
	@ParameterizedTest(name = "test delete key {1} in tree height of 2 created using {0}")
	@CsvSource({ "'400,200',200,'400',1", "'400,600',600,'400',1", "'400,600',400,'600',1", "'400,200',400,'200',1",
			"'400,200,600',400,'200,600',1" })
	public void testDeleteKeyInTreeHeightOf2(@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted,
			Integer keyToBeDeleted, String sortedOrderAfterDeletion, Integer expectedBlackHeight) {
		insertValues(valuesToBeInserted);
		redBlackTree.delete(keyToBeDeleted);
		assertEquals(sortedOrderAfterDeletion, redBlackTree.readKeysInOrderTraversal());
		redBlackTree.validateBlackHeightOfTree(expectedBlackHeight);
	}

	@DisplayName("test delete key in red black tree of height 2 to make BBB case")
	@ParameterizedTest(name = "test delete keys {1} in red black tree of height 2 created using {0} to make BBB case")
	@CsvSource({ "'400,200,600,700','700,200','400,600',1", "'400,200,600,700','700,600','200,400',1",
			"'400,200,600,700','700,400','200,600',1" })
	public void testDeleteKeyInRedBlackTreeOfHeight2ToMakeBBBCase(
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted,
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeDeleted, String sortedOrderAfterDeletion,
			Integer expectedBlackHeight) {
		insertValues(valuesToBeInserted);
		for (Integer keyToBeDeleted : keysToBeDeleted)
			redBlackTree.delete(keyToBeDeleted);
		assertEquals(sortedOrderAfterDeletion, redBlackTree.readKeysInOrderTraversal());
		redBlackTree.validateBlackHeightOfTree(expectedBlackHeight);
	}

	@DisplayName("test delete key in red black tree of height 3 to make BBR case")
	@ParameterizedTest(name = "test delete keys {1} in red black tree of height 2 created using {0} to make BBR case")
	@CsvSource({ "'400,200,600,700','200','400,600,700',2", "'400,200,600,500,700','200','400,500,600,700',2",
			"'400,200,600,100','600','100,200,400',2", "'400,200,600,300','600','200,300,400',2",
			"'400,200,600,100,300','600','100,200,300,400',2" })
	public void testDeleteKeyInRedBlackTreeOfHeight3ToMakeBBRCase(
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted,
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeDeleted, String sortedOrderAfterDeletion,
			Integer expectedBlackHeight) {
		insertValues(valuesToBeInserted);
		for (Integer keyToBeDeleted : keysToBeDeleted)
			redBlackTree.delete(keyToBeDeleted);
		assertEquals(sortedOrderAfterDeletion, redBlackTree.readKeysInOrderTraversal());
		redBlackTree.validateBlackHeightOfTree(expectedBlackHeight);
	}

	@DisplayName("test delete key in red black tree to make RBR case")
	@ParameterizedTest(name = "test delete keys {1} in red black tree of height 2 created using {0} to make RBR case")
	@CsvSource({ "'400,200,600,100,300,500,700,750','500','100,200,300,400,600,700,750',2",
			"'400,200,600,100,300,500,700,650','500','100,200,300,400,600,650,700',2",
			"'400,200,600,100,300,500,700,650,750','500','100,200,300,400,600,650,700,750',2",
			"'400,200,600,100,300,500,700,50','300','50,100,200,400,500,600,700',2",
			"'400,200,600,100,300,500,700,150','300','100,150,200,400,500,600,700',2",
			"'400,200,600,100,300,500,700,50,150','300','50,100,150,200,400,500,600,700',2" })
	public void testDeleteKeyInRedBlackTreeToMakeRBRCase(
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted,
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeDeleted, String sortedOrderAfterDeletion,
			Integer expectedBlackHeight) {
		insertValues(valuesToBeInserted);
		for (Integer keyToBeDeleted : keysToBeDeleted)
			redBlackTree.delete(keyToBeDeleted);
		assertEquals(sortedOrderAfterDeletion, redBlackTree.readKeysInOrderTraversal());
		redBlackTree.validateBlackHeightOfTree(expectedBlackHeight);
	}

	@DisplayName("test delete key in red black tree to make RBB case")
	@ParameterizedTest(name = "test delete keys {1} in red black tree of height 2 created using {0} to make RBB case")
	@CsvSource({ "'400,200,600,100,300,500,700,750','750,500','100,200,300,400,600,700',2",
			"'400,200,600,100,300,500,700,50','50,300','100,200,400,500,600,700',2" })
	public void testDeleteKeyInRedBlackTreeToMakeRBBCase(
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted,
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeDeleted, String sortedOrderAfterDeletion,
			Integer expectedBlackHeight) {
		insertValues(valuesToBeInserted);
		for (Integer keyToBeDeleted : keysToBeDeleted)
			redBlackTree.delete(keyToBeDeleted);
		assertEquals(sortedOrderAfterDeletion, redBlackTree.readKeysInOrderTraversal());
		redBlackTree.validateBlackHeightOfTree(expectedBlackHeight);
	}

	@DisplayName("test delete key in red black tree to make BRBR case")
	@ParameterizedTest(name = "test delete keys {1} in red black tree created using {0} to make BRBR case and rotate in {4}")
	@CsvSource({
			"'400,200,600,100,300,500,700,50,650,750,625,775','500','50,100,200,300,400,600,625,650,700,750,775',3,'RLL'",
			"'400,200,600,100,300,500,700,50,650,750,675,775','500','50,100,200,300,400,600,650,675,700,750,775',3,'RLR'",
			"'400,200,600,100,300,500,700,50,650,750,625,675,775','500','50,100,200,300,400,600,625,650,675,700,750,775',3,'RLL'",
			"'400,200,600,100,300,500,700,50,150,750,25,125','300','25,50,100,125,150,200,400,500,600,700,750',3,'LRL'",
			"'400,200,600,100,300,500,700,50,150,750,25,175','300','25,50,100,150,175,200,400,500,600,700,750',3,'LRR'",
			"'400,200,600,100,300,500,700,50,150,750,25,125,175','300','25,50,100,125,150,175,200,400,500,600,700,750',3,'LRR'" })
	public void testDeleteKeyInRedBlackTreeToMakeBRBRCase(
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted,
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeDeleted, String sortedOrderAfterDeletion,
			Integer expectedBlackHeight, String expectedRotation) {
		insertValues(valuesToBeInserted);
		for (Integer keyToBeDeleted : keysToBeDeleted)
			redBlackTree.delete(keyToBeDeleted);
		assertEquals(sortedOrderAfterDeletion, redBlackTree.readKeysInOrderTraversal());
		redBlackTree.validateBlackHeightOfTree(expectedBlackHeight);
	}

	@DisplayName("test delete key in red black tree to make BRBB case")
	@ParameterizedTest(name = "test delete keys {1} in red black tree created using {0} to make BRBB case")
	@CsvSource({ "'400,200,600,100,300,500,700,50,650,750,775','500','50,100,200,300,400,600,650,700,750,775',3",
			"'400,200,600,100,300,500,700,50,150,750,25','300','25,50,100,150,200,400,500,600,700,750',3" })
	public void testDeleteKeyInRedBlackTreeToMakeBRBBCase(
			@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted,
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeDeleted, String sortedOrderAfterDeletion,
			Integer expectedBlackHeight) {
		insertValues(valuesToBeInserted);
		for (Integer keyToBeDeleted : keysToBeDeleted)
			redBlackTree.delete(keyToBeDeleted);
		assertEquals(sortedOrderAfterDeletion, redBlackTree.readKeysInOrderTraversal());
		redBlackTree.validateBlackHeightOfTree(expectedBlackHeight);
	}

	@Nested
	class RBTRandomTest {
		private final QuickSorting quickSorting = new QuickSorting();
		/*
		 * private final AVLTreeSpy<Integer, String> avlTreeSpy = new
		 * AVLTreeTest.AVLTreeSpy<>((parentKey, keyToBEComapred) ->
		 * parentKey.equals(keyToBEComapred) ? 0 : parentKey > keyToBEComapred ? -1 : 1,
		 * new AVLTreeInorderTraversalForValuesInAscendingOrder());
		 */

		@DisplayName("test RBT insert and delete using random numbers")
		@RepeatedTest(2)
		public void testRBTInsertAndDeleteUsingRandomNumbers(RepetitionInfo repetitionInfo) {
			Integer[] keysInserted = generateRandomShuffledNumbers(repetitionInfo, 2000);
			for (int i = 0; i < keysInserted.length; i++) {
				// avlTreeSpy.insert(j, valueOf(j));
				redBlackTree.insert(keysInserted[i], valueOf(keysInserted[i]));
				Integer[] insertedListArray = Arrays.copyOfRange(keysInserted, 0, i + 1);
				quickSorting.sort(insertedListArray);
				assertEquals(createCsvString(insertedListArray), redBlackTree.readKeysInOrderTraversal());
				// assertEquals(avlTreeSpy.getTreeInorderTraversalData(),
				// redBlackTree.readKeysInOrderTraversal());
				redBlackTree.validateBlackHeightOfTree();
			}
			for (int i = 0; i < keysInserted.length; i++) {
				redBlackTree.delete(keysInserted[i]);
				// avlTreeSpy.remove(integer);
				Integer[] remainingValues = Arrays.copyOfRange(keysInserted, i + 1, keysInserted.length);
				quickSorting.sort(remainingValues);
				assertEquals(createCsvString(remainingValues), redBlackTree.readKeysInOrderTraversal());
				// assertEquals(avlTreeSpy.getTreeInorderTraversalData(),
				// redBlackTree.readKeysInOrderTraversal());
				redBlackTree.validateBlackHeightOfTree();
			}
		}

		private String createCsvString(Integer[] sortedValues) {
			String csvString = "";
			for (Integer sortedValue : sortedValues)
				csvString += sortedValue + ",";
			return !csvString.isEmpty() ? csvString.substring(0, csvString.length() - 1) : null;
		}
	}

	private void insertAndAssert(Integer... valuesToBeInserted) {
		insertValues(valuesToBeInserted);
		assertAlreadyInsertedKeys(valuesToBeInserted);
	}

	private void insertValues(Integer... valuesToBeInserted) {
		for (Integer valueToBeInserted : valuesToBeInserted)
			redBlackTree.insert(valueToBeInserted, valueOf(valueToBeInserted));
	}

	private void assertAlreadyInsertedKeys(Integer... valuesToBeSearched) {
		for (Integer valueToBeSearched : valuesToBeSearched)
			assertEquals(Integer.toString(valueToBeSearched), redBlackTree.search(valueToBeSearched));
	}

	class RedBlackTreeSpy<K, V> extends RedBlackTree<K, V> {

		private final EularTourTreeTraversal<String> redBlackTreeInOrderTraversalToReadKeysInAscendingOrder;
		private final RBT_BlackHeightPropertyValidationUsingPreOrderTraversal rebBlackTree_BlackHeightValidator;

		public RedBlackTreeSpy(Comparator<K> comparator) {
			super(comparator);
			this.redBlackTreeInOrderTraversalToReadKeysInAscendingOrder = new RedBlackTreeInOrderTraversalToReadKeysInAscendingOrder();
			this.rebBlackTree_BlackHeightValidator = new RBT_BlackHeightPropertyValidationUsingPreOrderTraversal();
		}

		public String readKeysInOrderTraversal() {
			return redBlackTreeInOrderTraversalToReadKeysInAscendingOrder.treeTraversal(root);
		}

		public void validateBlackHeightOfTree(int expectedBlackHeight) {
			rebBlackTree_BlackHeightValidator.validateBlackHeight(root, expectedBlackHeight);
		}

		public void validateBlackHeightOfTree() {
			rebBlackTree_BlackHeightValidator.validateBlackHeight(root);
		}

	}

}
