/**
 * 
 */
package com.handson.trees.balanced;

import static com.handson.trees.balanced.RedBlackNode.Colour.B;
import static com.handson.trees.balanced.RedBlackNode.Colour.E;
import static com.handson.trees.balanced.RedBlackNode.Colour.R;

import com.handson.trees.Comparator;

/**
 * @author sveera
 *
 */
public class RedBlackTree<K, V> {

	private final Comparator<K> comparator;
	protected RedBlackNode<K, V> root;

	public RedBlackTree(Comparator<K> comparator) {
		super();
		this.comparator = comparator;
	}

	public V search(K key) {
		final RedBlackNode<K, V> searchedNode = searchKeyNode(root, key);
		return searchedNode != null ? searchedNode.value : null;
	}

	private RedBlackNode<K, V> searchKeyNode(RedBlackNode<K, V> node, K key) {
		if (node == null || node.colour.equals(E))
			return null;
		int result = comparator.compare(node.key, key);
		return result == 0 ? node
				: result > 0 ? searchKeyNode((RedBlackNode<K, V>) node.rightChild, key)
						: searchKeyNode((RedBlackNode<K, V>) node.leftChild, key);
	}

	public void insert(K key, V value) {
		if (root == null) {
			createRootNode(key, value);
			return;
		}
		RedBlackNode<K, V> newlyInsertedNode = insertNode(root, key, value);
		if (newlyInsertedNode != null)
			balanceTreeAfterInsertion(newlyInsertedNode);
	}

	public K findMinimumKey() {
		return root != null ? findMinimumKeyNode(root).key : null;
	}

	public K findMaximumKey() {
		return root != null ? findMaximumKeyNode(root).key : null;
	}

	public RedBlackNode<K, V> findSuccessor(K successorForKeyToBeFound) {
		RedBlackNode<K, V> successorForNodeToBeFound = searchKeyNode(root, successorForKeyToBeFound);
		return successorForNodeToBeFound != null ? findSuccessor(successorForNodeToBeFound) : null;
	}

	public void delete(K key) {
		RedBlackNode<K, V> nodeToBeRemoved = searchKeyNode(root, key);
		if (nodeToBeRemoved == null)
			return;
		deleteExistingKeyNode(nodeToBeRemoved);
	}

	private void createRootNode(K key, V value) {
		root = createNodeWithExternalNodes(key, value);
		root.colour = B;
	}

	private RedBlackNode<K, V> insertNode(RedBlackNode<K, V> node, K key, V value) {
		int result = comparator.compare(node.key, key);
		if (result == 0) {
			node.value = value;
			return null;
		} else if (result < 0)
			if (((RedBlackNode<K, V>) node.leftChild).colour == E)
				return createNewLeftNode(node, key, value);
			else
				return insertNode((RedBlackNode<K, V>) node.leftChild, key, value);
		else {
			if (((RedBlackNode<K, V>) node.rightChild).colour == E)
				return createNewRightNode(node, key, value);
			else
				return insertNode((RedBlackNode<K, V>) node.rightChild, key, value);
		}
	}

	private RedBlackNode<K, V> createNewRightNode(RedBlackNode<K, V> node, K key, V value) {
		RedBlackNode<K, V> newRightChildNode = createNodeWithExternalNodes(key, value);
		newRightChildNode.parentNode = node;
		node.rightChild = newRightChildNode;
		return newRightChildNode;
	}

	private RedBlackNode<K, V> createNewLeftNode(RedBlackNode<K, V> node, K key, V value) {
		RedBlackNode<K, V> newLeftChildNode = createNodeWithExternalNodes(key, value);
		newLeftChildNode.parentNode = node;
		node.leftChild = newLeftChildNode;
		return newLeftChildNode;
	}

	private RedBlackNode<K, V> createNodeWithExternalNodes(K key, V value) {
		RedBlackNode<K, V> newNode = new RedBlackNode<K, V>();
		RedBlackNode<K, V> leftExternalNode = new RedBlackNode<K, V>();
		RedBlackNode<K, V> rightExternalNode = new RedBlackNode<K, V>();
		newNode.key = key;
		newNode.value = value;
		newNode.colour = R;
		leftExternalNode.parentNode = newNode;
		rightExternalNode.parentNode = newNode;
		newNode.leftChild = leftExternalNode;
		newNode.rightChild = rightExternalNode;
		return newNode;
	}

	private void balanceTreeAfterInsertion(RedBlackNode<K, V> newlyInsertedNode) {
		balanceTreeAfterInsertionToResolveDoubleRedProblem(newlyInsertedNode);
		if (root.colour == R)
			root.colour = B;
	}

	private void balanceTreeAfterInsertionToResolveDoubleRedProblem(RedBlackNode<K, V> node) {
		if (node.parentNode == null || ((RedBlackNode<K, V>) node.parentNode).colour == B)
			return;
		RedBlackNode<K, V> parentNode = (RedBlackNode<K, V>) node.parentNode;
		RedBlackNode<K, V> grandParentNode = (RedBlackNode<K, V>) parentNode.parentNode;
		RedBlackNode<K, V> parentNodeSibling = grandParentNode.leftChild == parentNode
				? (RedBlackNode<K, V>) grandParentNode.rightChild
				: (RedBlackNode<K, V>) grandParentNode.leftChild;
		if (parentNodeSibling.colour == R) {
			grandParentNode.colour = R;
			parentNode.colour = B;
			parentNodeSibling.colour = B;
			balanceTreeAfterInsertionToResolveDoubleRedProblem(grandParentNode);
		} else {
			String relationShipType = findRelationBetweenNodes(grandParentNode, parentNode, node);
			switch (relationShipType) {
			case "RR":
				balanceBlackHeightByLeftRotationAfterInsertion(grandParentNode, parentNode, node);
				break;
			case "RL":
				balanceBlackHeightByRightLeftRotationAfterInsertion(grandParentNode, parentNode, node);
				break;
			case "LL":
				balanceBlackHeightByRightRotationAfterInsertion(grandParentNode, parentNode, node);
				break;
			case "LR":
				balanceBlackHeightByLeftRightRotationAfterInsertion(grandParentNode, parentNode, node);
			}
		}
	}

	private void balanceBlackHeightByRightLeftRotationAfterInsertion(RedBlackNode<K, V> grandParentNode,
			RedBlackNode<K, V> parentNode, RedBlackNode<K, V> node) {
		rotateRightFirstInRightLeftRotation(grandParentNode, parentNode, node);
		balanceBlackHeightByLeftRotationAfterInsertion(grandParentNode, node, parentNode);
	}

	private void balanceBlackHeightByLeftRotationAfterInsertion(RedBlackNode<K, V> grandParentNode,
			RedBlackNode<K, V> parentNode, RedBlackNode<K, V> node) {
		balanceBlackHeightByLeftRotation(grandParentNode, parentNode, node);
		parentNode.colour = B;
		grandParentNode.colour = R;
	}

	private void balanceBlackHeightByLeftRightRotationAfterInsertion(RedBlackNode<K, V> grandParentNode,
			RedBlackNode<K, V> parentNode, RedBlackNode<K, V> node) {
		rotateLeftFirstInLeftRightRotation(grandParentNode, parentNode, node);
		balanceBlackHeightByRightRotationAfterInsertion(grandParentNode, node, parentNode);
	}

	private void balanceBlackHeightByRightRotationAfterInsertion(RedBlackNode<K, V> grandParentNode,
			RedBlackNode<K, V> parentNode, RedBlackNode<K, V> node) {
		balanceBlackHeightByRightRotation(grandParentNode, parentNode, node);
		parentNode.colour = B;
		grandParentNode.colour = R;
	}

	private void deleteExistingKeyNode(RedBlackNode<K, V> nodeToBeRemoved) {
		if (nodeToBeRemoved == root && isLeafNode(root)) {
			root = null;
			return;
		}
		if (isNodeWithTwoChildren(nodeToBeRemoved)) {
			RedBlackNode<K, V> successorNode = findSuccessor(nodeToBeRemoved);
			swapNodeToBeRemovedWithSuccessor(nodeToBeRemoved, successorNode);
			nodeToBeRemoved = successorNode;
		}
		if (isRedNodeLeaf(nodeToBeRemoved))
			removeRedLeafNode(nodeToBeRemoved);
		else if (isBlackNodeWithSingleRedChild(nodeToBeRemoved))
			removeBlackNodeHavingSingleRedChild(nodeToBeRemoved);
		else
			removeBlackLeafNode(nodeToBeRemoved);
	}

	private void removeBlackLeafNode(RedBlackNode<K, V> blackNodeToBeRemoved) {
		RedBlackNode<K, V> blackNodeParent = (RedBlackNode<K, V>) blackNodeToBeRemoved.parentNode;
		RedBlackNode<K, V> blackNodeToBeRemovedSibling;
		if (blackNodeParent.leftChild == blackNodeToBeRemoved) {
			blackNodeParent.leftChild = blackNodeToBeRemoved.rightChild;
			blackNodeToBeRemovedSibling = (RedBlackNode<K, V>) blackNodeParent.rightChild;
		} else {
			blackNodeParent.rightChild = blackNodeToBeRemoved.rightChild;
			blackNodeToBeRemovedSibling = (RedBlackNode<K, V>) blackNodeParent.leftChild;
		}
		blackNodeToBeRemoved.rightChild.parentNode = blackNodeParent;
		balanceBlackHeightOfLeaves(blackNodeParent, blackNodeToBeRemovedSibling);
	}

	private void balanceBlackHeightOfLeaves(RedBlackNode<K, V> parentNode, RedBlackNode<K, V> childNode) {
		String colorRelationship = identifyColorRelationShips(parentNode, childNode);
		switch (colorRelationship) {
		case "RBR":
			rotateTreeForRBRCase(parentNode, childNode);
			break;
		case "RBB":
			changeColorForRBBCase(parentNode, childNode);
			break;
		case "BRBR":
			rotateTreeForBRBRCase(parentNode, childNode);
			break;
		case "BRBB":
			rotateTreeForBRBBCase(parentNode, childNode);
			break;
		case "BBR":
			rotateTreeForBBRCase(parentNode, childNode);
			break;
		case "BBB":
			changeColorForBBBCase(parentNode, childNode);
			if (parentNode.parentNode != null)
				balanceBlackHeightOfLeaves((RedBlackNode<K, V>) parentNode.parentNode,
						parentNode.parentNode.leftChild == parentNode
								? (RedBlackNode<K, V>) parentNode.parentNode.rightChild
								: (RedBlackNode<K, V>) parentNode.parentNode.leftChild);
			break;
		}
	}

	private void rotateTreeForBRBBCase(RedBlackNode<K, V> parentNode, RedBlackNode<K, V> childNode) {
		RedBlackNode<K, V> grandParentNode = (RedBlackNode<K, V>) parentNode.parentNode;
		String relationShipType = parentNode.leftChild == childNode ? "L" : "R";
		RedBlackNode<K, V> grandChild = relationShipType.equals("L") ? (RedBlackNode<K, V>) childNode.rightChild
				: (RedBlackNode<K, V>) childNode.leftChild;
		relationShipType += relationShipType.equals("L") ? "R" : "L";
		if (relationShipType.equals("LR")) {
			parentNode.leftChild = grandChild;
			childNode.rightChild = parentNode;
		} else {
			parentNode.rightChild = grandChild;
			childNode.leftChild = parentNode;
		}
		grandChild.parentNode = parentNode;
		parentNode.parentNode = childNode;
		changeParentRelationOfNewRootInSubTree(parentNode, childNode, grandParentNode);
		grandChild.colour = R;
		childNode.colour = B;

	}

	private void rotateTreeForBRBRCase(RedBlackNode<K, V> parentNode, RedBlackNode<K, V> childNode) {
		RedBlackNode<K, V> grandParentNode = (RedBlackNode<K, V>) parentNode.parentNode;
		String relationShipType = parentNode.leftChild == childNode ? "L" : "R";
		RedBlackNode<K, V> grandChild = relationShipType.equals("L") ? (RedBlackNode<K, V>) childNode.rightChild
				: (RedBlackNode<K, V>) childNode.leftChild;
		relationShipType += relationShipType.equals("L") ? "R" : "L";
		RedBlackNode<K, V> grateGrandChild = relationShipType.equals("RL")
				? ((RedBlackNode<K, V>) grandChild.leftChild).colour == R ? (RedBlackNode<K, V>) grandChild.leftChild
						: (RedBlackNode<K, V>) grandChild.rightChild
				: ((RedBlackNode<K, V>) grandChild.rightChild).colour == R ? (RedBlackNode<K, V>) grandChild.rightChild
						: (RedBlackNode<K, V>) grandChild.leftChild;
		relationShipType += grandChild.leftChild == grateGrandChild ? "L" : "R";
		if (relationShipType.equals("RLL") || relationShipType.equals("LRR"))
			rotateForRLL_or_LRR(relationShipType, grandParentNode, parentNode, childNode, grandChild, grateGrandChild);
		else
			rotateForLRL_or_RLR(relationShipType, grandParentNode, parentNode, childNode, grandChild, grateGrandChild);
	}

	private void rotateForRLL_or_LRR(String relationShipType, RedBlackNode<K, V> grandParentNode,
			RedBlackNode<K, V> parentNode, RedBlackNode<K, V> childNode, RedBlackNode<K, V> grandChild,
			RedBlackNode<K, V> grateGrandChild) {
		if (relationShipType.equals("RLL")) {
			grandChild.leftChild = grateGrandChild.rightChild;
			grateGrandChild.rightChild.parentNode = grandChild;
			grateGrandChild.rightChild = parentNode.rightChild;
			parentNode.rightChild.parentNode = grateGrandChild;
			parentNode.rightChild = grateGrandChild.leftChild;
			grateGrandChild.leftChild.parentNode = parentNode;
			grateGrandChild.leftChild = parentNode;
		} else {
			grandChild.rightChild = grateGrandChild.leftChild;
			grateGrandChild.leftChild.parentNode = grandChild;
			grateGrandChild.leftChild = parentNode.leftChild;
			parentNode.leftChild.parentNode = grateGrandChild;
			parentNode.leftChild = grateGrandChild.rightChild;
			grateGrandChild.rightChild.parentNode = parentNode;
			grateGrandChild.rightChild = parentNode;
		}
		parentNode.parentNode = grateGrandChild;
		changeParentRelationOfNewRootInSubTree(parentNode, grateGrandChild, grandParentNode);
		grateGrandChild.colour = B;
	}

	private void rotateForLRL_or_RLR(String relationShipType, RedBlackNode<K, V> grandParentNode,
			RedBlackNode<K, V> parentNode, RedBlackNode<K, V> childNode, RedBlackNode<K, V> grandChild,
			RedBlackNode<K, V> grateGrandChild) {
		if (relationShipType.equals("LRL")) {
			parentNode.leftChild = grandChild.rightChild;
			grandChild.rightChild.parentNode = parentNode;
			grandChild.leftChild = childNode;
			grandChild.rightChild = parentNode;
			childNode.rightChild = grateGrandChild;
		} else {
			parentNode.rightChild = grandChild.leftChild;
			grandChild.leftChild.parentNode = parentNode;
			grandChild.rightChild = childNode;
			grandChild.leftChild = parentNode;
			childNode.leftChild = grateGrandChild;
		}
		childNode.parentNode = grandChild;
		parentNode.parentNode = grandChild;
		grateGrandChild.parentNode = childNode;
		changeParentRelationOfNewRootInSubTree(parentNode, grandChild, grandParentNode);
		grateGrandChild.colour = B;
	}

	private void changeColorForRBBCase(RedBlackNode<K, V> parentNode, RedBlackNode<K, V> childNode) {
		parentNode.colour = B;
		childNode.colour = R;
	}

	private void rotateTreeForRBRCase(RedBlackNode<K, V> parentNode, RedBlackNode<K, V> childNode) {
		RedBlackNode<K, V> rightGrandChildNode = (RedBlackNode<K, V>) childNode.rightChild;
		RedBlackNode<K, V> leftGrandChildNode = (RedBlackNode<K, V>) childNode.leftChild;
		RedBlackNode<K, V> grandChildNode = parentNode.leftChild == childNode
				? rightGrandChildNode.colour == R ? rightGrandChildNode : leftGrandChildNode
				: leftGrandChildNode.colour == R ? leftGrandChildNode : rightGrandChildNode;
		String relationShipType = findRelationBetweenNodes(parentNode, childNode, grandChildNode);
		rotateInSubTree(parentNode, childNode, grandChildNode, relationShipType);
		parentNode.colour = B;
		if (relationShipType.equals("LL") || relationShipType.equals("RR")) {
			grandChildNode.colour = B;
			childNode.colour = R;
		}
	}

	private void rotateTreeForBBRCase(RedBlackNode<K, V> parentNode, RedBlackNode<K, V> childNode) {
		RedBlackNode<K, V> rightGrandChildNode = (RedBlackNode<K, V>) childNode.rightChild;
		RedBlackNode<K, V> leftGrandChildNode = (RedBlackNode<K, V>) childNode.leftChild;
		RedBlackNode<K, V> grandChildNode = parentNode.leftChild == childNode
				? rightGrandChildNode.colour == R ? rightGrandChildNode : leftGrandChildNode
				: leftGrandChildNode.colour == R ? leftGrandChildNode : rightGrandChildNode;
		rotateInSubTree(parentNode, childNode, grandChildNode,
				findRelationBetweenNodes(parentNode, childNode, grandChildNode));
		grandChildNode.colour = B;
	}

	private void rotateInSubTree(RedBlackNode<K, V> parentNode, RedBlackNode<K, V> childNode,
			RedBlackNode<K, V> grandChildNode, String relationShipType) {
		switch (relationShipType) {
		case "RR":
			balanceBlackHeightByLeftRotation(parentNode, childNode, grandChildNode);
			break;
		case "RL":
			balanceBlackHeightByRightLeftRotation(parentNode, childNode, grandChildNode);
			break;
		case "LL":
			balanceBlackHeightByRightRotation(parentNode, childNode, grandChildNode);
			break;
		case "LR":
			balanceBlackHeightByLeftRightRotation(parentNode, childNode, grandChildNode);
		}
	}

	private void changeColorForBBBCase(RedBlackNode<K, V> parentNode, RedBlackNode<K, V> childNode) {
		childNode.colour = R;
	}

	private String identifyColorRelationShips(RedBlackNode<K, V> parentNode, RedBlackNode<K, V> childNode) {
		RedBlackNode<K, V> leftGrandChildNode = (RedBlackNode<K, V>) childNode.leftChild;
		RedBlackNode<K, V> rightGrandChildNode = (RedBlackNode<K, V>) childNode.rightChild;
		String colorRealtionShip = "";
		colorRealtionShip += parentNode.colour == R ? "R" : "B";
		colorRealtionShip += childNode.colour == R ? "R" : "B";
		colorRealtionShip += parentNode.leftChild == childNode
				? (rightGrandChildNode.colour == R ? "R" : leftGrandChildNode.colour == R ? "R" : "B")
				: (leftGrandChildNode.colour == R ? "R" : rightGrandChildNode.colour == R ? "R" : "B");
		if (!colorRealtionShip.equals("BRB"))
			return colorRealtionShip;
		colorRealtionShip += parentNode.rightChild == childNode
				? ((RedBlackNode<K, V>) leftGrandChildNode.leftChild).colour == R
						|| ((RedBlackNode<K, V>) leftGrandChildNode.rightChild).colour == R ? "R" : "B"
				: ((RedBlackNode<K, V>) rightGrandChildNode.rightChild).colour == R
						|| ((RedBlackNode<K, V>) rightGrandChildNode.leftChild).colour == R ? "R" : "B";
		return colorRealtionShip;
	}

	private void swapNodeToBeRemovedWithSuccessor(RedBlackNode<K, V> nodeToBeRemoved,
			RedBlackNode<K, V> successorNode) {
		nodeToBeRemoved.key = successorNode.key;
		nodeToBeRemoved.value = successorNode.value;
	}

	private boolean isNodeWithTwoChildren(RedBlackNode<K, V> node) {
		return ((RedBlackNode<K, V>) node.leftChild).colour != E && ((RedBlackNode<K, V>) node.rightChild).colour != E;
	}

	private void removeBlackNodeHavingSingleRedChild(RedBlackNode<K, V> node) {
		RedBlackNode<K, V> childNode = ((RedBlackNode<K, V>) node.leftChild).colour == E
				? (RedBlackNode<K, V>) node.rightChild
				: (RedBlackNode<K, V>) node.leftChild;
		node.key = childNode.key;
		node.value = childNode.value;
		if (node.leftChild == childNode)
			node.leftChild = childNode.rightChild;
		else
			node.rightChild = childNode.rightChild;
		childNode.rightChild.parentNode = node;
	}

	private boolean isBlackNodeWithSingleRedChild(RedBlackNode<K, V> node) {
		return node.colour == B && isNodeWithSingleChild(node);
	}

	private boolean isNodeWithSingleChild(RedBlackNode<K, V> node) {
		return ((RedBlackNode<K, V>) node.rightChild).colour == E ^ ((RedBlackNode<K, V>) node.leftChild).colour == E;
	}

	private void removeRedLeafNode(RedBlackNode<K, V> redLeafNode) {
		if (redLeafNode.parentNode.leftChild == redLeafNode)
			redLeafNode.parentNode.leftChild = redLeafNode.rightChild;
		else
			redLeafNode.parentNode.rightChild = redLeafNode.rightChild;
		redLeafNode.rightChild.parentNode = redLeafNode.parentNode;
	}

	private boolean isRedNodeLeaf(RedBlackNode<K, V> node) {
		return isLeafNode(node) && node.colour == R;
	}

	private boolean isLeafNode(RedBlackNode<K, V> node) {
		return ((RedBlackNode<K, V>) node.leftChild).colour == E && ((RedBlackNode<K, V>) node.rightChild).colour == E;
	}

	private String findRelationBetweenNodes(RedBlackNode<K, V> grandParentNode, RedBlackNode<K, V> parentNode,
			RedBlackNode<K, V> node) {
		String relationShipType = grandParentNode.leftChild == parentNode ? "L" : "R";
		return relationShipType += parentNode.leftChild == node ? "L" : "R";
	}

	private RedBlackNode<K, V> findSuccessor(RedBlackNode<K, V> node) {
		if (((RedBlackNode<K, V>) node.rightChild).colour != E)
			return findMinimumKeyNode((RedBlackNode<K, V>) node.rightChild);
		while (node.parentNode != null && node.parentNode.rightChild == node)
			node = (RedBlackNode<K, V>) node.parentNode;
		return (RedBlackNode<K, V>) node.parentNode;
	}

	private RedBlackNode<K, V> findMinimumKeyNode(RedBlackNode<K, V> node) {
		return ((RedBlackNode<K, V>) node.leftChild).colour == E ? node
				: findMinimumKeyNode((RedBlackNode<K, V>) node.leftChild);
	}

	private RedBlackNode<K, V> findMaximumKeyNode(RedBlackNode<K, V> node) {
		return ((RedBlackNode<K, V>) node.rightChild).colour == E ? node
				: findMaximumKeyNode((RedBlackNode<K, V>) node.rightChild);
	}

	private void balanceBlackHeightByRightLeftRotation(RedBlackNode<K, V> grandParentNode,
			RedBlackNode<K, V> parentNode, RedBlackNode<K, V> node) {
		rotateRightFirstInRightLeftRotation(grandParentNode, parentNode, node);
		balanceBlackHeightByLeftRotation(grandParentNode, node, parentNode);
	}

	private void rotateRightFirstInRightLeftRotation(RedBlackNode<K, V> grandParentNode, RedBlackNode<K, V> parentNode,
			RedBlackNode<K, V> node) {
		parentNode.leftChild = node.rightChild;
		node.rightChild.parentNode = parentNode;
		parentNode.parentNode = node;
		node.rightChild = parentNode;
		node.parentNode = grandParentNode;
		grandParentNode.rightChild = node;
	}

	private void balanceBlackHeightByLeftRotation(RedBlackNode<K, V> grandParentNode, RedBlackNode<K, V> parentNode,
			RedBlackNode<K, V> node) {
		RedBlackNode<K, V> grateGrandParentNode = (RedBlackNode<K, V>) grandParentNode.parentNode;
		changeParentRelationOfNewRootInSubTree(grandParentNode, parentNode, grateGrandParentNode);
		grandParentNode.parentNode = parentNode;
		parentNode.leftChild.parentNode = grandParentNode;
		grandParentNode.rightChild = parentNode.leftChild;
		parentNode.leftChild = grandParentNode;
	}

	private void balanceBlackHeightByLeftRightRotation(RedBlackNode<K, V> grandParentNode,
			RedBlackNode<K, V> parentNode, RedBlackNode<K, V> node) {
		rotateLeftFirstInLeftRightRotation(grandParentNode, parentNode, node);
		balanceBlackHeightByRightRotation(grandParentNode, node, parentNode);
	}

	private void rotateLeftFirstInLeftRightRotation(RedBlackNode<K, V> grandParentNode, RedBlackNode<K, V> parentNode,
			RedBlackNode<K, V> node) {
		parentNode.rightChild = node.leftChild;
		node.leftChild.parentNode = parentNode;
		parentNode.parentNode = node;
		node.leftChild = parentNode;
		node.parentNode = grandParentNode;
		grandParentNode.leftChild = node;
	}

	private void balanceBlackHeightByRightRotation(RedBlackNode<K, V> grandParentNode, RedBlackNode<K, V> parentNode,
			RedBlackNode<K, V> node) {
		RedBlackNode<K, V> grateGrandParentNode = (RedBlackNode<K, V>) grandParentNode.parentNode;
		changeParentRelationOfNewRootInSubTree(grandParentNode, parentNode, grateGrandParentNode);
		grandParentNode.parentNode = parentNode;
		parentNode.rightChild.parentNode = grandParentNode;
		grandParentNode.leftChild = parentNode.rightChild;
		parentNode.rightChild = grandParentNode;
	}

	private void changeParentRelationOfNewRootInSubTree(RedBlackNode<K, V> previousRoot, RedBlackNode<K, V> newRoot,
			RedBlackNode<K, V> rootParent) {
		if (rootParent != null)
			if (rootParent.leftChild == previousRoot)
				rootParent.leftChild = newRoot;
			else
				rootParent.rightChild = newRoot;
		else
			root = newRoot;
		newRoot.parentNode = rootParent;
	}

}
