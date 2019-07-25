/**
 * 
 */
package com.handson.trees;

/**
 * @author sveera
 *
 */
public abstract class EularTourTreeTraversal<R> {

	public final <K, V> R treeTraversal(BinaryTreeNode<K, V> tree) {
		if (tree == null)
			return null;
		if (tree.leftChild == null && tree.rightChild == null)
			return handleForLeafNode(tree);
		else {
			R beforeLeftChildVisitResult = beforeLeftChild(tree);
			R leftNodeResult = treeTraversal(tree.leftChild);
			R fromBottomVisitResult = fromBottom(tree);
			R rightNodeResult = treeTraversal(tree.rightChild);
			R afterRightVisitResult = afterRight(tree);
			return combineResult(beforeLeftChildVisitResult, leftNodeResult, fromBottomVisitResult, rightNodeResult,
					afterRightVisitResult);
		}

	}

	protected abstract <K, V> R handleForLeafNode(BinaryTreeNode<K, V> tree);

	protected abstract <K, V> R beforeLeftChild(BinaryTreeNode<K, V> tree);

	protected abstract <K, V> R fromBottom(BinaryTreeNode<K, V> tree);

	protected abstract <K, V> R afterRight(BinaryTreeNode<K, V> tree);

	protected abstract R combineResult(R beforeLeftChildVisitResult, R leftNodeResult, R fromBottomVisitResult,
			R rightNodeResult, R afterRightVisitResult);

}
