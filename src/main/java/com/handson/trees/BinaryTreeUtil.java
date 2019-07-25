/**
 * 
 */
package com.handson.trees;

/**
 * @author sveera
 *
 */
public class BinaryTreeUtil {

	public static BinaryTreeNode<Integer, Integer> createTwoChildrenNode(int nodeKeyAndValue, int leftChildKeyAndValue,
			int rightChildKeyAndValue) {
		BinaryTreeNode<Integer, Integer> node = createTreeNode(nodeKeyAndValue);
		BinaryTreeNode<Integer, Integer> leftChild = createTreeNode(leftChildKeyAndValue);
		BinaryTreeNode<Integer, Integer> rightChild = createTreeNode(rightChildKeyAndValue);
		linkParentWithChildren(node, leftChild, rightChild);
		return node;
	}

	public static BinaryTreeNode<Integer, Integer> createTreeNode(int keyAndValue) {
		BinaryTreeNode<Integer, Integer> treeNode = new BinaryTreeNodeForReversal<>();
		treeNode.key = keyAndValue;
		treeNode.value = keyAndValue;
		return treeNode;
	}

	public static void linkParentWithChildren(BinaryTreeNode<Integer, Integer> node,
			BinaryTreeNode<Integer, Integer> leftChild, BinaryTreeNode<Integer, Integer> rightChild) {
		linkLeftChildren(node, leftChild);
		linkRightChildren(node, rightChild);
	}

	public static void linkLeftChildren(BinaryTreeNode<Integer, Integer> node,
			BinaryTreeNode<Integer, Integer> leftChild) {
		node.leftChild = leftChild;
		leftChild.parentNode = node;
	}

	public static void linkRightChildren(BinaryTreeNode<Integer, Integer> node,
			BinaryTreeNode<Integer, Integer> rightChild) {
		node.rightChild = rightChild;
		rightChild.parentNode = node;
	}
}
