/**
 * 
 */
package com.handson.trees;

import static com.handson.trees.BinaryTreeUtil.createTreeNode;
import static com.handson.trees.BinaryTreeUtil.createTwoChildrenNode;
import static com.handson.trees.BinaryTreeUtil.linkLeftChildren;
import static com.handson.trees.BinaryTreeUtil.linkParentWithChildren;
import static com.handson.trees.BinaryTreeUtil.linkRightChildren;
import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author sveera
 *
 */
public class BinaryTreeCreatorTest {

	private BinaryTreeCreator binaryTreeCreator;

	@BeforeEach
	public void setUp() throws Exception {
		binaryTreeCreator = new BinaryTreeCreator();
	}

	// @formatter:off
	// 1
	// @formatter:on
	@Test
	@DisplayName("test Construct Tree For Root Node")
	public void testConstructTreeForRootNode() {
		String preOrderTraversal = "1", inOrderTraversal = "1";
		BinaryTreeNode<Integer, Integer> actualBinaryTree = binaryTreeCreator.constructTree(preOrderTraversal,
				inOrderTraversal);
		assertEquals(createTreeNode(1), actualBinaryTree);
	}

	// @formatter:off
	//			 1
	//			/	
	//		   /
	//        2
	// @formatter:on
	@Test
	@DisplayName("test Construct Tree For Root Node With Left Leaf")
	public void testConstructTreeForRootNodeWithLeftLeaf() {
		String preOrderTraversal = "1,2", inOrderTraversal = "2,1";
		BinaryTreeNode<Integer, Integer> actualBinaryTree = binaryTreeCreator.constructTree(preOrderTraversal,
				inOrderTraversal);
		BinaryTreeNode<Integer, Integer> rootNode = createTreeNode(1);
		BinaryTreeNode<Integer, Integer> leftChild = createTreeNode(2);
		linkLeftChildren(rootNode, leftChild);
		assertEquals(rootNode, actualBinaryTree);
	}

	// @formatter:off
	//			 1
	//			  \	
	//		       \
	//              2
	// @formatter:on
	@Test
	@DisplayName("test Construct Tree For Root Node With Right Leaf")
	public void testConstructTreeForRootNodeWithRightLeaf() {
		String preOrderTraversal = "1,2", inOrderTraversal = "1,2";
		BinaryTreeNode<Integer, Integer> actualBinaryTree = binaryTreeCreator.constructTree(preOrderTraversal,
				inOrderTraversal);
		BinaryTreeNode<Integer, Integer> rootNode = createTreeNode(1);
		BinaryTreeNode<Integer, Integer> rightChild = createTreeNode(2);
		linkRightChildren(rootNode, rightChild);
		assertEquals(rootNode, actualBinaryTree);
	}

	// @formatter:off
	// 		 1           1
	//		/ \   		/ \  	
	//	   /   \ 	   /   \
	//    2     3	  3     2
	// @formatter:on
	@DisplayName("test Construct Tree For Root Node With Two Leaves")
	@ParameterizedTest(name = "with Pre-order string {0} and In-order string {1}")
	@CsvSource({ "'1,2,3','2,1,3'", "'1,3,2', '3,1,2'" })
	public void testConstructTreeForRootNodeWithWithTwoLeaves(String preOrderTraversal, String inOrderTraversal) {
		String[] inOrderTraversalValues = inOrderTraversal.split(",");
		BinaryTreeNode<Integer, Integer> rootNode = createTreeNode(parseInt(inOrderTraversalValues[1]));
		BinaryTreeNode<Integer, Integer> rightChild = createTreeNode(parseInt(inOrderTraversalValues[2]));
		BinaryTreeNode<Integer, Integer> leftChild = createTreeNode(parseInt(inOrderTraversalValues[0]));
		BinaryTreeNode<Integer, Integer> actualBinaryTree = binaryTreeCreator.constructTree(preOrderTraversal,
				inOrderTraversal);
		linkParentWithChildren(rootNode, leftChild, rightChild);
		assertEquals(rootNode, actualBinaryTree);
	}

	// @formatter:off
	//	 			  1           
	//				/  \   		  	
	//	   	   	   /    \ 	   
	//    	  	  2      3	  
	//			 / \    / \
	//		    4   5	6  7	
	//		    
	// @formatter:on
	@Test
	@DisplayName("test Construct Complete Binary Tree of Height 2 ")
	public void testConstructCompleteBinaryTreeOfHeight2() {
		String preOrderTraversal = "1,2,4,5,3,6,7", inOrderTraversal = "4,2,5,1,6,3,7";
		BinaryTreeNode<Integer, Integer> actualBinaryTree = binaryTreeCreator.constructTree(preOrderTraversal,
				inOrderTraversal);
		BinaryTreeNode<Integer, Integer> rootNode = createCompleteBinaryTreeOfOrder2();
		assertEquals(rootNode, actualBinaryTree);
	}

	@Test
	@DisplayName("test Construct Binary Tree With Only Right Nodes")
	public void testConstructBinaryTreeWithOnlyRightNodes() {
		String preOrderTraversal = "1,2,3,4,5,6,7,8", inOrderTraversal = "1,2,3,4,5,6,7,8";
		BinaryTreeNode<Integer, Integer> actualBinaryTree = binaryTreeCreator.constructTree(preOrderTraversal,
				inOrderTraversal);
		BinaryTreeNode<Integer, Integer> rootNode = createTreeNode(1);
		BinaryTreeNode<Integer, Integer> parentNode = rootNode;
		for (int i = 2; i <= 8; i++) {
			BinaryTreeNode<Integer, Integer> currentNode = createTreeNode(i);
			linkRightChildren(parentNode, currentNode);
			parentNode = currentNode;
		}
		assertEquals(rootNode, actualBinaryTree);
	}

	@Test
	@DisplayName("test Construct Binary Tree With Only Left Nodes")
	public void testConstructBinaryTreeWithOnlyLeftNodes() {
		String preOrderTraversal = "1,2,3,4,5,6,7,8", inOrderTraversal = "8,7,6,5,4,3,2,1";
		BinaryTreeNode<Integer, Integer> actualBinaryTree = binaryTreeCreator.constructTree(preOrderTraversal,
				inOrderTraversal);
		BinaryTreeNode<Integer, Integer> rootNode = createTreeNode(1);
		BinaryTreeNode<Integer, Integer> parentNode = rootNode;
		for (int i = 2; i <= 8; i++) {
			BinaryTreeNode<Integer, Integer> currentNode = createTreeNode(i);
			linkLeftChildren(parentNode, currentNode);
			parentNode = currentNode;
		}
		assertEquals(rootNode, actualBinaryTree);
	}

	// @formatter:off
	//	 			 1           
	//				/ \   		  	
	//	   	   	   /   \ 	   
	//    	  	  2     3	  
	//			 / \   / \
	// 		    4   5 6   7	
	//		       /       \
	//     		  8         9	   
	// @formatter:on
	@Test
	@DisplayName("test Construct Binary Tree of Height 3 ")
	public void testConstructBinaryTreeOfHeight3() {
		String preOrderTraversal = "1,2,4,5,8,3,6,7,9", inOrderTraversal = "4,2,8,5,1,6,3,7,9";
		BinaryTreeNode<Integer, Integer> actualBinaryTree = binaryTreeCreator.constructTree(preOrderTraversal,
				inOrderTraversal);
		BinaryTreeNode<Integer, Integer> rootNode = createCompleteBinaryTreeOfOrder2();
		BinaryTreeNode<Integer, Integer> _3rdOrderLeftTreeNode = createTreeNode(8);
		rootNode.leftChild.rightChild.leftChild = _3rdOrderLeftTreeNode;
		_3rdOrderLeftTreeNode.parentNode = rootNode.leftChild.rightChild;
		BinaryTreeNode<Integer, Integer> _3rdOrderRightTreeNode = createTreeNode(9);
		rootNode.rightChild.rightChild.rightChild = _3rdOrderRightTreeNode;
		_3rdOrderRightTreeNode.parentNode = rootNode.rightChild.rightChild;
		assertEquals(rootNode, actualBinaryTree);
	}

	private BinaryTreeNode<Integer, Integer> createCompleteBinaryTreeOfOrder2() {
		BinaryTreeNode<Integer, Integer> rootNode = createTreeNode(1);
		BinaryTreeNode<Integer, Integer> leftSubTree = createTwoChildrenNode(2, 4, 5);
		BinaryTreeNode<Integer, Integer> rightSubTree = createTwoChildrenNode(3, 6, 7);
		linkParentWithChildren(rootNode, leftSubTree, rightSubTree);
		return rootNode;
	}

}
