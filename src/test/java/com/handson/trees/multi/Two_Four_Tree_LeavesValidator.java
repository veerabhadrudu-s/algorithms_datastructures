/**
 * 
 */
package com.handson.trees.multi;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sveera
 *
 */
public class Two_Four_Tree_LeavesValidator {

	public void validate(Two_Four_Tree_Node two_Four_Tree_Node) {
		if (two_Four_Tree_Node == null)
			return;
		int expectedHeight = 1;
		Two_Four_Tree_Node tempNode = two_Four_Tree_Node;
		for (; tempNode.children[0] != null; expectedHeight++)
			tempNode = tempNode.children[0];
		travelRecursively(two_Four_Tree_Node, expectedHeight, 1);
	}

	private void travelRecursively(Two_Four_Tree_Node two_Four_Tree_Node, int expectedHeight, int actualHeight) {
		if (isLeafNode(two_Four_Tree_Node)) {
			validateLeafNode(two_Four_Tree_Node, expectedHeight, actualHeight);
			return;
		}
		actualHeight++;
		Two_Four_Tree_Node[] children = two_Four_Tree_Node.children;
		for (int i = 0; i < children.length && children[i] != null; i++)
			travelRecursively(children[i], expectedHeight, actualHeight);
	}

	private void validateLeafNode(Two_Four_Tree_Node two_Four_Tree_Node, int expectedHeight, int actualHeight) {
		assertEquals(expectedHeight, actualHeight);
		if (isEmptyLeafNode(two_Four_Tree_Node)) {
			throw new EmptyLeafNodeException();
		}
	}

	private boolean isEmptyLeafNode(Two_Four_Tree_Node two_Four_Tree_Node) {
		return two_Four_Tree_Node.keys[0] == null;
	}

	private boolean isLeafNode(Two_Four_Tree_Node two_Four_Tree_Node) {
		return two_Four_Tree_Node.children[0] == null;
	}

	class EmptyLeafNodeException extends RuntimeException {

		private static final long serialVersionUID = 1L;

	}
}
