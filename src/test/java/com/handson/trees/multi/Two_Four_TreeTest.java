/**
 * 
 */
package com.handson.trees.multi;

import static com.handson.junit.RandomUtil.generateRandomShuffledNumbers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
import com.handson.sorting.QuickSorting;
import com.handson.trees.Comparator;

/**
 * @author sveera
 *
 */
public class Two_Four_TreeTest {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private Two_Four_TreeSpy<Integer, String> two_Four_Tree;
	private QuickSorting quickSorting = new QuickSorting();

	@BeforeEach
	public void setUp() throws Exception {
		two_Four_Tree = new Two_Four_TreeSpy<>((key1, key2) -> key1.equals(key2) ? 0 : key1 > key2 ? -1 : 1);
	}

	@Test
	@DisplayName("test In empty tree do search operation")
	public void testInEmptyTreeDoSearchOperation() {
		assertNull(two_Four_Tree.search(1));
	}

	@ParameterizedTest
	@DisplayName("test In empty tree do insert and search operations")
	@CsvSource({ "'1'", "'1,2'", "'1,2,3'", "'1,3,2'", "'2,3,1'", "'2,1,3'", "'3,2,1'", "'2,3,1'" })
	public void testInEmptyTreeDoInsertAndSearchOperations(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted) {
		insertAndAssert(keysToBeInserted);
	}

	@ParameterizedTest
	@DisplayName("test In full root node tree by performing insert and search operations and expecting split-promote operations")
	@CsvSource({ "'4,3,2,1'", "'1,2,3,4,5,-1,-2'" })
	public void testInFullRootNodeTreeByPerformingInsertAndSearchOperationsAndExpectSplit_PromoteOperations(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted) {
		insertAndAssert(keysToBeInserted);
	}

	@ParameterizedTest
	@DisplayName("test In tree of height 2 by performing insert and search operations and expect filling of remaining empty locations")
	@CsvSource({ "'1,2,3,4,5,-1,-2'" })
	public void testInTreeOfHeight2ByPerformingInsertAndSearchOperationsAndExpectFillingOfRemainingEmptyLocations(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted) {
		insertAndAssert(keysToBeInserted);
	}

	@ParameterizedTest
	@DisplayName("test In tree height of 2 by performing insert and search operations and expect split-promote operations")
	@CsvSource({ "'1,2,3,4,5,-1,-2,-3'", "'1,2,3,4,5,-1,-2,-3'", "'1,2,3,4,5,-1,-2,-3,6'",
			"'1,2,3,4,5,-1,-7,-9,-6,-5'" })
	public void testInTreeOfHeight2ByPerformingInsertAndSearchOperationsAndexpectSplit_PromoteOperations(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted) {
		insertAndAssert(keysToBeInserted);
	}

	@ParameterizedTest
	@DisplayName("test In tree height of 2 by performing insert and search operations and expect split-promote operations in 2 level's")
	@CsvSource({ "'1,2,3,4,5,-1,-7,-9,-6,-5,6'", "'1,2,3,4,5,-1,-7,-9,-6,-5,-11,-19,-27'" })
	public void testInTreeOfHeight2ByPerformingInsertAndSearchOperationsAndexpectSplit_PromoteOperationsIn2Levels(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted) {
		insertAndAssert(keysToBeInserted);
	}

	@ParameterizedTest
	@DisplayName("test In tree height of 3 by performing insert and search operations and "
			+ "expect split-promote operations in 2 level and only promote at root level")
	@CsvSource({ "'1,2,3,4,5,-1,-7,-9,-6,-5,6,7,8,9,10'",
			"'1,2,3,4,5,-1,-7,-9,-6,-5,-11,-19,-27,-37,-47,-57,-67,-77,-87,-97,-107,-117'" })
	public void testInTreeOfHeight3ByPerformingInsertAndSearchOperationsAndexpectSplit_PromoteOperationsIn2LevelsandOnlyPromoteAtRootLevel(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted) {
		insertAndAssert(keysToBeInserted);
	}

	@ParameterizedTest
	@DisplayName("test In tree height of 3 by performing insert and search operations and "
			+ "expect split-promote operations at multi-level")
	@CsvSource({ "'1392604920, -1863354565, 144387161, 591725709, 1081615135, -1785169498, -2140124934,"
			+ " -179594718, -1808944442, -434724537, -1950636752, 1764169746, 26467482, -985792663,"
			+ " -154435490, 1412503836, 248511264, -945291611, 1593483484, -456535355, -215454523,"
			+ " -1224260761, -892500943, 671372156, -1033936590, -1111846742, 230022786, 1062600446,"
			+ " 1448912869, 1008907525, -934666940'" })
	public void testInTreeHeightOf3ByPerformingInsertAndSearchOperationsAndExpectSplit_PromoteOperationsAtMulti_Level(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted) {
		for (int i = 0; i < keysToBeInserted.length; i++) {
			two_Four_Tree.insert(keysToBeInserted[i], String.valueOf(keysToBeInserted[i]));
			searchKeys(Arrays.copyOfRange(keysToBeInserted, 0, i));
		}
		quickSorting.sort(keysToBeInserted);
		assertEquals(createCsvString(keysToBeInserted), two_Four_Tree.getInOrderTraversalValues());
	}

	@Test
	@DisplayName("test In empty tree do delete operation")
	public void testInEmptyTreeDoDeleteOperation() {
		assertNull(two_Four_Tree.delete(1));
		assertNull(two_Four_Tree.getInOrderTraversalValues());
	}

	@ParameterizedTest
	@DisplayName("test In tree of height 1 delete invalid key")
	@CsvSource({ "'3,2,1','4'", "'3,2,1','-1'" })
	public void testInTreeOfHeight1DoDeleteInvalidKey(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted,
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeDeleted) {
		insertAndAssert(keysToBeInserted);
		quickSorting.sort(keysToBeInserted);
		for (int i = 0; i < keysToBeDeleted.length; i++) {
			assertNull(two_Four_Tree.delete(keysToBeDeleted[i]));
			assertEquals(createCsvString(keysToBeInserted), two_Four_Tree.getInOrderTraversalValues());
		}
	}

	@ParameterizedTest
	@DisplayName("test In tree of height 1 do delete operation")
	@CsvSource({ "'1','1'", "'1,2','1,2'", "'1,2,3','1,2,3'", "'1,3,2','1,3,2'", "'2,3,1','2,3,1'", "'2,1,3','2,1,3'",
			"'3,2,1','3,2,1'", "'2,3,1','2,3,1'" })
	public void testInTreeOfHeight1DoDeleteOperation(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted,
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeDeleted) {
		insertAndAssert(keysToBeInserted);
		deleteAndAssert(keysToBeDeleted);
	}

	@ParameterizedTest
	@DisplayName("test In tree of height 2 do delete operation of leaf node keys")
	@CsvSource({ "'100,200,300,400,-100,-200,500','-200,-100,400,500'" })
	public void testInTreeOfHeight1DoDeleteOperationOfLeafKeys(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted,
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeDeleted) {
		insertAndAssert(keysToBeInserted);
		deleteAndAssert(keysToBeInserted, keysToBeDeleted);
	}

	@ParameterizedTest
	@DisplayName("test In tree of height 2 do delete operation of root node keys")
	@CsvSource({ "'100,200,300,400,-100,-200,500','200,300'" })
	public void testInTreeOfHeight1DoDeleteOperationOfRootNodeKeys(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted,
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeDeleted) {
		insertAndAssert(keysToBeInserted);
		deleteAndAssert(keysToBeInserted, keysToBeDeleted);
	}

	@ParameterizedTest
	@DisplayName("test In tree of height 2 do delete operation of leaf node keys and expect borrow of key from sibling")
	@CsvSource({ "'100,200,300,400,-100,500,600,250','-100,100'", "'100,200,300,400,-100,500,600,250','250,300'",
			"'100,200,300,400,-100,500,600,250','500,600'",
			"'100,200,300,400,-100,500,600,250,-200,350,700','-100,100,-200'",
			"'100,200,300,400,-100,500,600,250,-200,350,700','250,300,350'",
			"'100,200,300,400,-100,500,600,250,-200,350,700','700,600,500'",
			"'100,200,300,400,-100,500,600,250,350','-100,100,200'",
			"'100,200,300,400,-100,500,600,250,700','250,300,400'",
			"'100,200,300,400,-100,500,600,250,350','600,500,400'" })
	public void testInTreeOfHeight1DoDeleteOperationOfLeafNodeKeysAndExpectBorrowOfKeyFromSibling(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted,
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeDeleted) {
		insertAndAssert(keysToBeInserted);
		deleteAndAssert(keysToBeInserted, keysToBeDeleted);
	}

	@ParameterizedTest
	@DisplayName("test In tree of height 2 do delete operation of root node keys and expect borrow of key between sibling leaves")
	@CsvSource({ "'100,200,300,400,-100,500,600,250,350','600,500,350'",
			"'100,200,300,400,-100,500,600,-200','200,100,-100'" })
	public void testInTreeOfHeight1DoDeleteOperationOfRootNodeKeysAndExpectBorrowOfKeyBetweenSiblingLeaves(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted,
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeDeleted) {
		insertAndAssert(keysToBeInserted);
		deleteAndAssert(keysToBeInserted, keysToBeDeleted);
	}

	@ParameterizedTest
	@DisplayName("test In tree of height 2 do delete operation of root node key and expect merge of leaf nodes")
	@CsvSource({ "'100,200,300,400','400,200'", "'100,200,300,400','400,100'",
			"'100,200,300,400,500,600,700,800','400,200'" })
	public void testInTreeOfHeight2DoDeleteOperationOfRootNodeKeyAndExpectMergeOfLeafnodes(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted,
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeDeleted) {
		insertAndAssert(keysToBeInserted);
		deleteAndAssert(keysToBeInserted, keysToBeDeleted);
	}

	@ParameterizedTest
	@DisplayName("test In tree of height 2 do delete operation of leaf keys and expect merge of leaf nodes")
	@CsvSource({ "'100,200,300,400,500,600,700,800','100'", "'100,200,300,400,500,600,700,800','300'",
			"'100,200,300,400,500,600,700,800','800,500'", "'100,200,300,400,500,600,700,800','800,700'" })
	public void testInTreeOfHeight2DoDeleteOperationOfLeafKeysAndExpectMergeOfLeafnodes(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted,
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeDeleted) {
		insertAndAssert(keysToBeInserted);
		deleteAndAssert(keysToBeInserted, keysToBeDeleted);
	}

	@ParameterizedTest
	@DisplayName("test In tree of height 3 do delete operation of keys and "
			+ "expect merge of leaf nodes and borrow of sibling key at leaf parent level")
	@CsvSource({ "'100,200,300,400,500,600,700,800,900,1000,1100','100'",
			"'100,200,300,400,500,600,700,800,900,1000,1100','200'",
			"'100,200,300,400,500,600,700,800,900,1000,1100','300'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,-100,-200,-300','1100,1000,400,600,700'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,-100,-200,-300','1100,1000,400,600,800'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,-100,-200,-300','1100,1000,400,600,900'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,-100,-200,-300','1100,1000,400,600,500'" })
	public void testInTreeOfHeight3DoDeleteOperationOfKeysAndExpectMergeOfLeafnodesAndBorrowOfSiblingKeyAtLeafParentLevel(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted,
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeDeleted) {
		insertAndAssert(keysToBeInserted);
		deleteAndAssert(keysToBeInserted, keysToBeDeleted);
	}

	@ParameterizedTest
	@DisplayName("test In tree of height 4 do delete operation of keys and expect merge at multiple levels")
	@CsvSource({
			"'100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200','800'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200','900'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200','1000'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200','1100'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200','2200,2100,2000,1900'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200','2200,2100,2000,1300'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200','2200,2100,2000,1400'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200','2200,2100,2000,1500'" })
	public void testInTreeOfHeight4DoDeleteOperationOfKeysAndExpectMergeAtMultipleLevels(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted,
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeDeleted) {
		insertAndAssert(keysToBeInserted);
		deleteAndAssert(keysToBeInserted, keysToBeDeleted);
	}

	@ParameterizedTest
	@DisplayName("test In Tree do delete operation of keys and expect merge upto root level")
	@CsvSource({
			"'100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200','100'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200','200'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200','300'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200','400'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200','500'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200','600'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200','700'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200',"
					+ "'2200,2100,2000,1900,1800,1700,1600,1500'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,-100,-200,-300','1100,1000,400,600,700,900,800'",
			"'100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200',"
					+ "'1100,1000,400,600,700,900,800'" })
	public void testInTreeDoDeleteOperationOfKeysAndExpectMergeUptoRootLevel(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted,
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeDeleted) {
		insertAndAssert(keysToBeInserted);
		deleteAndAssert(keysToBeInserted, keysToBeDeleted);
	}

	@ParameterizedTest
	@DisplayName("test In Tree Do Delete Operation For Failied Case 1")
	@CsvSource({ "'100,200,300,400,500,600,700,800,900,1000,1100,1200',"
			+ "'100,200,300,400,500,600,700,800,900,1000,1100,1200'", })
	public void testInTreeDoDeleteOperationForFailiedCase1(
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeInserted,
			@ConvertWith(IntegerArrayConverter.class) Integer[] keysToBeDeleted) {
		insertAndAssert(keysToBeInserted);
		deleteAndAssert(keysToBeInserted, keysToBeDeleted);
	}

	@RepeatedTest(10)
	@DisplayName("test insert and search operations using random numbers")
	public void testInsertAndSearchOperationsUsingRandomNumbers(RepetitionInfo repetitionInfo) {
		insertAndAssert(generateRandomShuffledNumbers(repetitionInfo, 4000));
	}

	@RepeatedTest(3)
	@DisplayName("test insert and delete operations using random numbers")
	public void testInsertAndDeleteOperationsUsingRandomNumbers(RepetitionInfo repetitionInfo) {
		Integer[] keysToBeInserted = generateRandomShuffledNumbers(repetitionInfo, 1500);
		insertAndAssert(keysToBeInserted);
		// logger.debug("Keys Inserted " + Arrays.deepToString(keysToBeInserted));
		deleteAndAssert(keysToBeInserted, Arrays.copyOf(keysToBeInserted, keysToBeInserted.length));
	}

	@RepeatedTest(3)
	@DisplayName("test insert and delete operations using random numbers arranged in assending order")
	public void testInsertAndDeleteOperationsUsingRandomNumbersArrangedInAssendingOrder(RepetitionInfo repetitionInfo) {
		Integer[] keysToBeInserted = generateRandomShuffledNumbers(repetitionInfo, 1500);
		insertAndAssert(keysToBeInserted);
		Integer[] keysToBeDeleted = Arrays.copyOf(keysToBeInserted, keysToBeInserted.length);
		quickSorting.sort(keysToBeDeleted);
		deleteAndAssert(keysToBeInserted, keysToBeDeleted);
	}

	private void insertAndAssert(Integer[] keysToBeInserted) {
		insertKeys(keysToBeInserted);
		searchKeys(keysToBeInserted);
		quickSorting.sort(keysToBeInserted);
		assertEquals(createCsvString(keysToBeInserted), two_Four_Tree.getInOrderTraversalValues());
	}

	private void deleteAndAssert(Integer[] keysToBeDeleted) {
		for (int i = 0; i < keysToBeDeleted.length; i++) {
			assertEquals(String.valueOf(keysToBeDeleted[i]), two_Four_Tree.delete(keysToBeDeleted[i]));
			Integer[] remaingKeys = Arrays.copyOfRange(keysToBeDeleted, i + 1, keysToBeDeleted.length);
			quickSorting.sort(remaingKeys);
			assertEquals(createCsvString(remaingKeys), two_Four_Tree.getInOrderTraversalValues());
		}
	}

	private void deleteAndAssert(Integer[] keysAlreadyInserted, Integer[] keysToBeDeleted) {
		quickSorting.sort(keysAlreadyInserted);
		SortedSet<Integer> keysInserted = new TreeSet<>();
		for (Integer keyAlreadyInserted : keysAlreadyInserted)
			keysInserted.add(keyAlreadyInserted);
		for (int i = 0; i < keysToBeDeleted.length; i++) {
			// logger.debug("Key to be deleted is " + keysToBeDeleted[i]);
			assertEquals(String.valueOf(keysToBeDeleted[i]), two_Four_Tree.delete(keysToBeDeleted[i]));
			keysInserted.remove(keysToBeDeleted[i]);
			assertEquals(createCsvString(keysInserted), two_Four_Tree.getInOrderTraversalValues());
		}
	}

	private String createCsvString(Integer[] sortedValues) {
		Set<Integer> distinctValues = new LinkedHashSet<>();
		for (Integer keyToBeInserted : sortedValues)
			distinctValues.add(keyToBeInserted);
		String csvString = "";
		for (Integer distinctValue : distinctValues)
			csvString += distinctValue + ",";
		return !csvString.isEmpty() ? csvString.substring(0, csvString.length() - 1) : null;
	}

	private String createCsvString(SortedSet<Integer> sortedSet) {
		Set<Integer> distinctValues = new LinkedHashSet<>();
		for (Integer keyToBeInserted : sortedSet)
			distinctValues.add(keyToBeInserted);
		String csvString = "";
		for (Integer distinctValue : distinctValues)
			csvString += distinctValue + ",";
		return !csvString.isEmpty() ? csvString.substring(0, csvString.length() - 1) : null;
	}

	private void searchKeys(Integer[] keysToBeInserted) {
		for (Integer keyToBeInserted : keysToBeInserted)
			assertEquals(String.valueOf(keyToBeInserted), two_Four_Tree.search(keyToBeInserted));
	}

	private void insertKeys(Integer[] keysToBeInserted) {
		for (Integer keyToBeInserted : keysToBeInserted)
			two_Four_Tree.insert(keyToBeInserted, String.valueOf(keyToBeInserted));
	}

	class Two_Four_TreeSpy<K, V> extends Two_Four_Tree<K, V> {

		private final Two_Four_Tree_InorderTraversal two_Four_Tree_InorderTraversal;
		private final Two_Four_Tree_LeavesValidator two_Four_Tree_LeavesValidator;

		public Two_Four_TreeSpy(Comparator<K> comparator) {
			super(comparator);
			two_Four_Tree_InorderTraversal = new Two_Four_Tree_InorderTraversal();
			two_Four_Tree_LeavesValidator = new Two_Four_Tree_LeavesValidator();

		}

		public String getInOrderTraversalValues() {
			two_Four_Tree_LeavesValidator.validate(rootNode);
			return two_Four_Tree_InorderTraversal.travel(rootNode);
		}

	}

}
