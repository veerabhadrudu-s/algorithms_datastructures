/**
 * 
 */
package com.handson.trees;

import static com.handson.trees.BinaryTreeUtil.createTreeNode;
import static com.handson.trees.BinaryTreeUtil.linkLeftChildren;
import static com.handson.trees.BinaryTreeUtil.linkRightChildren;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.copyOfRange;

/**
 * @author sveera
 *
 */
public class BinaryTreeCreator {

	public BinaryTreeNode<Integer, Integer> constructTree(String preOrderTraversal, String inOrderTraversal) {
		String[] preOrderTraversalValues = preOrderTraversal.split(",");
		String[] inOrderTraversalValues = inOrderTraversal.split(",");
		return constructTree(preOrderTraversalValues, inOrderTraversalValues);
	}

	private BinaryTreeNode<Integer, Integer> constructTree(String[] preOrderTraversalValues,
			String[] inOrderTraversalValues) {
		if (preOrderTraversalValues.length == 1 && inOrderTraversalValues.length == 1
				&& preOrderTraversalValues[0].equals(inOrderTraversalValues[0]))
			return createTreeNode(parseInt(inOrderTraversalValues[0]));
		int rootNodeIndexInInOrderValues = calculateRootNodeIndexValue(inOrderTraversalValues,
				preOrderTraversalValues[0]);
		BinaryTreeNode<Integer, Integer> parentNode = constructParentNode(inOrderTraversalValues,
				rootNodeIndexInInOrderValues);
		constructLeftSubTree(preOrderTraversalValues, inOrderTraversalValues, rootNodeIndexInInOrderValues, parentNode);
		constructRightSubTree(preOrderTraversalValues, inOrderTraversalValues, rootNodeIndexInInOrderValues,
				parentNode);
		return parentNode;

	}

	private BinaryTreeNode<Integer, Integer> constructParentNode(String[] inOrderTraversalValues,
			int rootNodeIndexInInOrderValues) {
		return createTreeNode(parseInt(inOrderTraversalValues[rootNodeIndexInInOrderValues]));
	}

	private void constructRightSubTree(String[] preOrderTraversalValues, String[] inOrderTraversalValues,
			int rootNodeIndexInInOrderValues, BinaryTreeNode<Integer, Integer> parentNode) {
		if (inOrderTraversalValues.length - 1 > rootNodeIndexInInOrderValues) {
			BinaryTreeNode<Integer, Integer> rightChild = constructTree(
					copyOfRange(preOrderTraversalValues, rootNodeIndexInInOrderValues + 1,
							preOrderTraversalValues.length),
					copyOfRange(inOrderTraversalValues, rootNodeIndexInInOrderValues + 1,
							inOrderTraversalValues.length));
			linkRightChildren(parentNode, rightChild);
		}
	}

	private void constructLeftSubTree(String[] preOrderTraversalValues, String[] inOrderTraversalValues,
			int rootNodeIndexInInOrderValues, BinaryTreeNode<Integer, Integer> parentNode) {
		if (rootNodeIndexInInOrderValues > 0) {
			BinaryTreeNode<Integer, Integer> leftChild = constructTree(
					copyOfRange(preOrderTraversalValues, 1, rootNodeIndexInInOrderValues + 1),
					copyOfRange(inOrderTraversalValues, 0, rootNodeIndexInInOrderValues));
			linkLeftChildren(parentNode, leftChild);
		}
	}

	private int calculateRootNodeIndexValue(String[] inOrderTraversalValues, String rootValueOfTree) {
		int rootValueIndexOfTree = -1;
		for (int i = 0; i < inOrderTraversalValues.length; i++)
			if (inOrderTraversalValues[i].equals(rootValueOfTree)) {
				rootValueIndexOfTree = i;
				break;
			}
		if (rootValueIndexOfTree == -1)
			throw new RuntimeException();
		return rootValueIndexOfTree;
	}

}
