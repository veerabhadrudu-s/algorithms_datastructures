/**
 * 
 */
package com.handson.trees;

import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import com.handson.junit.IntegerArrayConverter;

/**
 * @author sveera
 *
 */
public class BinarySearchingTreeTest {

	/*
	 * In Binary Sorted Tree , total traversal value depends up on height of the
	 * binary sorted tree. As we already know that height (h) of the binary tree is
	 * always n-1 >= h >= log(n+1/2). Which gives us total traversal complexity as
	 * O(n) >= h >= O(log(n)). So, all operations such as insert , delete , search
	 * will take worst complexity of O(n) and average complexity of O(log(n))
	 */

	private BinarySearchingTreeSpy<Integer, String> binarySearchingTree;

	@BeforeEach
	public void setUp() throws Exception {
		binarySearchingTree = new BinarySearchingTreeSpy<>((key1, key2) -> key1 == key2 ? 0 : key1 > key2 ? -1 : 1,
				new BinaryTreeInorderTraversalForValuesInAscendingOrder());
	}

	// @formatter:off
	//
	//	 		3  	1			     50
	//	       /	 \				/  \
	//	      2  	  2			   /    \  	     
	//		 /     	   \		  /    	 \              
	//		1           3		 /        \   		  	
	//	   	   	   		 	    46	       94            
	//                     	   /  \       /  \
	//    	              	  28   47    77   96
	//                   	 /  \  	     /
	//                  	15	 16     54    
	//    	           		/     \
	//    	          	   1      27	
	//    	                     /
	//						    24
	// @formatter:on
	@DisplayName("test Insertion And Search Operations using input array")
	@CsvSource({ "'3,2,1','1,2,3'", "'1,2,3','1,2,3'", "'1,2,3,3','1,2,3'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47','1,15,16,24,27,28,46,47,50,54,77,94,96'" })
	@ParameterizedTest(name = "{0} and expect sorted Inorder Traversal Data {1}")
	public void testInsertionAndSearchOperations(@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted,
			String expectedInorderTraversalData) {
		for (Integer valueToBeInserted : valuesToBeInserted)
			binarySearchingTree.insert(valueToBeInserted, valueOf(valueToBeInserted));
		for (Integer valueToBeInserted : valuesToBeInserted)
			assertEquals(valueOf(valueToBeInserted), binarySearchingTree.search(valueToBeInserted));
		assertEquals(expectedInorderTraversalData, binarySearchingTree.getTreeInorderTraversalData());
	}

	@DisplayName("test find minimum Value in binary sorted tree constructed using input array")
	@CsvSource({ ",,", "'3,2,1','1,2,3','1'", "'1,2,3','1,2,3','1'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47','1,15,16,24,27,28,46,47,50,54,77,94,96','1'" })
	@ParameterizedTest(name = "{0} and expect Minimum Value {2}")
	public void testFindMinimumValueInTheTree(@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted,
			String expectedInorderTraversalData, String expectedMinValue) {
		for (Integer valueToBeInserted : valuesToBeInserted)
			binarySearchingTree.insert(valueToBeInserted, valueOf(valueToBeInserted));
		assertEquals(expectedInorderTraversalData, binarySearchingTree.getTreeInorderTraversalData());
		assertEquals(expectedMinValue, binarySearchingTree.findMinValue());
	}

	@DisplayName("test find maximum Value in binary sorted tree constructed using input array")
	@CsvSource({ ",,", "'3,2,1','1,2,3','3'", "'1,2,3','1,2,3','3'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47','1,15,16,24,27,28,46,47,50,54,77,94,96','96'" })
	@ParameterizedTest(name = " {0} and expect Maximum value {2}")
	public void testFindMaximumValueInTree(@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted,
			String expectedInorderTraversalData, String expectedMinValue) {
		for (Integer valueToBeInserted : valuesToBeInserted)
			binarySearchingTree.insert(valueToBeInserted, valueOf(valueToBeInserted));
		assertEquals(expectedInorderTraversalData, binarySearchingTree.getTreeInorderTraversalData());
		assertEquals(expectedMinValue, binarySearchingTree.findMaxValue());
	}

	@DisplayName("test find successor node in binary sorted tree constructed using input array")
	@CsvSource({ ",,", "'10',10,", "'2,1',2,", "'3,2,1',1,'2'", "'1,2,3',2,'3'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47',96,", "'50,46,94,28,15,16,27,24,1,96,77,54,47',50,'54'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47',94,'96'", "'50,46,94,28,15,16,27,24,1,96,77,54,47',24,'27'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47',27,'28'", "'50,46,94,28,15,16,27,24,1,96,77,54,47',47,'50'" })
	@ParameterizedTest(name = "{0} and expect successor of {1} as {2}")
	public void testfindSuccessor(@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted,
			Integer successorForKeyToBeSearched, String expectedSuccessor) {
		for (Integer valueToBeInserted : valuesToBeInserted)
			binarySearchingTree.insert(valueToBeInserted, valueOf(valueToBeInserted));
		BinaryTreeNode<Integer, String> actualSuccessorNode = binarySearchingTree
				.findSuccessor(successorForKeyToBeSearched);
		if (actualSuccessorNode != null)
			assertEquals(expectedSuccessor, actualSuccessorNode.value);
		else
			assertNull(expectedSuccessor, "Expected successor is not null but actualSuccessorNode found is null");
	}

	@DisplayName("test find predecessor node in binary sorted tree constructed using input array")
	@CsvSource({ ",,", "'10',10,", "'2,1',2,'1'", "'3,2,1',1,", "'1,2,3',2,'1'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47',96,'94'", "'50,46,94,28,15,16,27,24,1,96,77,54,47',100,",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47',50,47", "'50,46,94,28,15,16,27,24,1,96,77,54,47',94,'77'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47',24,'16'", "'50,46,94,28,15,16,27,24,1,96,77,54,47',27,'24'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47',28,'27'", "'50,46,94,28,15,16,27,24,1,96,77,54,47',54,'50'" })
	@ParameterizedTest(name = "{0} and expect predecessor of {1} as {2}")
	public void testfindPredecessor(@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted,
			Integer predecessorForKeyToBeSearched, String expectedPredecessor) {
		for (Integer valueToBeInserted : valuesToBeInserted)
			binarySearchingTree.insert(valueToBeInserted, valueOf(valueToBeInserted));
		BinaryTreeNode<Integer, String> actualPredecessorNode = binarySearchingTree
				.findPredecessor(predecessorForKeyToBeSearched);
		if (actualPredecessorNode != null)
			assertEquals(expectedPredecessor, actualPredecessorNode.value);
		else
			assertNull(expectedPredecessor, "Expected predecessor is not null but actualSuccessorNode found is null");
	}

	@DisplayName("test delete key in binary sorted tree constructed using input array")
	@CsvSource({ "'10',10,", "'1,2',1,'2'", "'2,1',1,'2'", "'2,1',2,'1'", "'3,2,1',2,'1,3'", "'1,2,3',1,'2,3'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47',96,'1,15,16,24,27,28,46,47,50,54,77,94'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47',24,'1,15,16,27,28,46,47,50,54,77,94,96'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47',27,'1,15,16,24,28,46,47,50,54,77,94,96'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47',28,'1,15,16,24,27,46,47,50,54,77,94,96'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47',54,'1,15,16,24,27,28,46,47,50,77,94,96'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47,62,72',54,'1,15,16,24,27,28,46,47,50,62,72,77,94,96'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47,100,101,102',96,'1,15,16,24,27,28,46,47,50,54,77,94,100,101,102'",
			"'2,3,1',2,'1,3'", "'50,46,94,28,15,16,27,24,1,96,77,54,47',94,'1,15,16,24,27,28,46,47,50,54,77,96'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47',50,'1,15,16,24,27,28,46,47,54,77,94,96'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47,55',50,'1,15,16,24,27,28,46,47,54,55,77,94,96'",
			"'50,46,94,28,15,16,27,24,1,96,77,54,47,55,100',94,'1,15,16,24,27,28,46,47,50,54,55,77,96,100'" })
	@ParameterizedTest(name = "{0} and expect in-order traversal {2} after delete of key {1}")
	public void testDeleteKey(@ConvertWith(IntegerArrayConverter.class) Integer[] valuesToBeInserted,
			Integer keyToBeDeleted, String expectedInorderTraversalData, ArgumentsAccessor arguments) {
		for (Integer valueToBeInserted : valuesToBeInserted)
			binarySearchingTree.insert(valueToBeInserted, valueOf(valueToBeInserted));
		binarySearchingTree.delete(keyToBeDeleted);
		assertEquals(expectedInorderTraversalData, binarySearchingTree.getTreeInorderTraversalData());
		if (binarySearchingTree.getTreeInorderTraversalData() != null)
			assertEquals(arguments.getString(0).split(",").length - 1,
					binarySearchingTree.getTreeInorderTraversalData().split(",").length);
	}

	class BinarySearchingTreeSpy<K, V> extends BinarySearchingTree<K, V> {

		private EularTourTreeTraversal<String> eularTourTreeTraversal;

		public BinarySearchingTreeSpy(Comparator<K> comparator, EularTourTreeTraversal<String> eularTourTreeTraversal) {
			super(comparator);
			this.eularTourTreeTraversal = eularTourTreeTraversal;
		}

		public String getTreeInorderTraversalData() {
			return eularTourTreeTraversal.treeTraversal(rootNode);
		}

	}

}
