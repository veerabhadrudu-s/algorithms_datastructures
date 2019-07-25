/**
 * 
 */
package com.handson.trees.multi;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sveera
 *
 */
public class A_B_Tree_Validator {

	private final int minimumNoOfKeysRequired;
	private final int minimumKeyIndexRequired;

	public A_B_Tree_Validator(int minimumNoOfKeysRequired) {
		super();
		this.minimumNoOfKeysRequired = minimumNoOfKeysRequired;
		this.minimumKeyIndexRequired = minimumNoOfKeysRequired - 1;
	}

	public void validate(A_B_TreeNode a_b_TreeNode) {
		if (a_b_TreeNode == null)
			return;
		int expectedHeight = 1;
		A_B_TreeNode tempNode = a_b_TreeNode;
		for (; tempNode.children[0] != null; expectedHeight++)
			tempNode = tempNode.children[0];
		travelRecursively(a_b_TreeNode, expectedHeight, 1);
	}

	private void travelRecursively(A_B_TreeNode a_b_TreeNode, int expectedHeight, int actualHeight) {
		if (isNonRootNodeHasLessThanMinNoOfKeys(a_b_TreeNode)) {
			throw new NodeWithLessThanMinimumKeysException("Node " + a_b_TreeNode + " has - "
					+ a_b_TreeNode.noOfExistingKeysInNode + " keys which are less than minimum no of keys - "
					+ minimumNoOfKeysRequired + " required");
		} else if (isLeafNode(a_b_TreeNode)) {
			assertEquals(expectedHeight, actualHeight);
			return;
		}
		actualHeight++;
		A_B_TreeNode[] children = a_b_TreeNode.children;
		for (int i = 0; i < children.length && children[i] != null; i++)
			travelRecursively(children[i], expectedHeight, actualHeight);
	}

	private boolean isNonRootNodeHasLessThanMinNoOfKeys(A_B_TreeNode node) {
		return node.parent != null && (node.noOfExistingKeysInNode < minimumNoOfKeysRequired
				|| node.keys[minimumKeyIndexRequired] == null);
	}

	private boolean isLeafNode(A_B_TreeNode a_b_TreeNode) {
		return a_b_TreeNode.children[0] == null;
	}

	class NodeWithLessThanMinimumKeysException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public NodeWithLessThanMinimumKeysException(String message) {
			super(message);
		}

	}
}
