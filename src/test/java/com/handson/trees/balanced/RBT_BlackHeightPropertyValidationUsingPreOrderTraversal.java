/**
 * 
 */
package com.handson.trees.balanced;

import static com.handson.trees.balanced.RedBlackNode.Colour.B;
import static com.handson.trees.balanced.RedBlackNode.Colour.R;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sveera
 *
 */
public class RBT_BlackHeightPropertyValidationUsingPreOrderTraversal {

	public void validateBlackHeight(RedBlackNode<?, ?> rootNode, int expectedBlackHeight) {
		validateBlackHeightTreeUsingPreOrderTraversal(rootNode, 0, expectedBlackHeight);
	}

	public void validateBlackHeight(RedBlackNode<?, ?> rootNode) {
		int expectedBlackHeight = 0;
		RedBlackNode<?, ?> node = rootNode;
		while (node != null) {
			if (node.colour == B)
				expectedBlackHeight++;
			node = (RedBlackNode<?, ?>) node.leftChild;
		}
		validateBlackHeightTreeUsingPreOrderTraversal(rootNode, 0, expectedBlackHeight);
	}

	private void validateBlackHeightTreeUsingPreOrderTraversal(RedBlackNode<?, ?> node, int parentNodeBlackHeight,
			int expectedBlackHeight) {
		if (node == null)
			return;
		if (node.leftChild == null && node.rightChild == null)
			assertEquals(expectedBlackHeight, parentNodeBlackHeight,
					"Black height property violated for external node having parent " + node.parentNode.key);
		else {
			if (node.colour == B)
				parentNodeBlackHeight++;
			else if (node.colour == R)
				if (node.parentNode != null && ((RedBlackNode<?, ?>) node.parentNode).colour == R)
					throw new DoubleRedProblemException(DoubleRedProblemException.class.getSimpleName()
							+ " indentifed between parent node " + node.parentNode.key + " child node " + node.key);
			validateBlackHeightTreeUsingPreOrderTraversal((RedBlackNode<?, ?>) node.leftChild, parentNodeBlackHeight,
					expectedBlackHeight);
			validateBlackHeightTreeUsingPreOrderTraversal((RedBlackNode<?, ?>) node.rightChild, parentNodeBlackHeight,
					expectedBlackHeight);
		}

	}

	class DoubleRedProblemException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public DoubleRedProblemException(String message) {
			super(message);
		}
	}

}
