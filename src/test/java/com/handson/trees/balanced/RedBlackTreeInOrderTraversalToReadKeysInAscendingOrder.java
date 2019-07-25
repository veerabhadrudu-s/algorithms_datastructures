/**
 * 
 */
package com.handson.trees.balanced;

import com.handson.trees.BinaryTreeNode;
import com.handson.trees.EularTourTreeTraversal;

/**
 * @author sveera
 *
 */
public class RedBlackTreeInOrderTraversalToReadKeysInAscendingOrder extends EularTourTreeTraversal<String> {

	@Override
	protected <K, V> String handleForLeafNode(BinaryTreeNode<K, V> tree) {
		return null;
	}

	@Override
	protected <K, V> String beforeLeftChild(BinaryTreeNode<K, V> tree) {
		return null;
	}

	@Override
	protected <K, V> String fromBottom(BinaryTreeNode<K, V> tree) {
		if (tree instanceof RedBlackNode)
			return String.valueOf(tree.key);
		return null;
	}

	@Override
	protected <K, V> String afterRight(BinaryTreeNode<K, V> tree) {
		return null;
	}

	@Override
	protected String combineResult(String beforeLeftChildVisitResult, String leftNodeResult,
			String fromBottomVisitResult, String rightNodeResult, String afterRightVisitResult) {
		String combinedResult = returnEmptyForNull(leftNodeResult) + returnEmptyForNull(fromBottomVisitResult)
				+ returnEmptyForNull(rightNodeResult);
		return combinedResult.substring(0, combinedResult.length() - 1);
	}

	private String returnEmptyForNull(String result) {
		return result != null ? result + "," : "";
	}

}
