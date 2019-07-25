/**
 * 
 */
package com.handson.trees;

/**
 * @author sveera
 *
 */
public class BinaryTreeReverserUsingPostOrderTraversal extends EularTourTreeTraversal<Void> {

	public void reverseTree(BinaryTreeNode<Integer, Integer> tree) {
		treeTraversal(tree);
	}

	@Override
	protected <K, V> Void afterRight(BinaryTreeNode<K, V> tree) {
		BinaryTreeNode<K, V> tmpNode = tree.leftChild;
		tree.leftChild = tree.rightChild;
		tree.rightChild = tmpNode;
		return null;
	}

	@Override
	protected <K, V> Void handleForLeafNode(BinaryTreeNode<K, V> tree) {
		return null;
	}

	@Override
	protected <K, V> Void beforeLeftChild(BinaryTreeNode<K, V> tree) {
		return null;
	}

	@Override
	protected <K, V> Void fromBottom(BinaryTreeNode<K, V> tree) {
		return null;
	}

	@Override
	protected Void combineResult(Void beforeLeftChildVisitResult, Void leftNodeResult, Void fromBottomVisitResult,
			Void rightNodeResult, Void afterRightVisitResult) {
		return null;
	}

}
