/**
 * 
 */
package com.handson.trees.balanced;

import com.handson.trees.BinaryTreeNode;
import com.handson.trees.Comparator;

/**
 * @author sveera
 *
 */
public class AVLTree<K, V> {

	private final Comparator<K> comparator;
	protected AVLTreeNode<K, V> rootNode;

	public AVLTree(Comparator<K> comparator) {
		super();
		this.comparator = comparator;
	}

	public V search(K key) {
		AVLTreeNode<K, V> searchedNode = searchNode(rootNode, key);
		return searchedNode != null ? searchedNode.value : null;
	}

	private AVLTreeNode<K, V> searchNode(AVLTreeNode<K, V> treeNode, K key) {
		if (treeNode == null)
			return null;
		if (comparator.compare(treeNode.key, key) == 0)
			return treeNode;
		else if (comparator.compare(treeNode.key, key) < 0)
			return searchNode((AVLTreeNode<K, V>) treeNode.leftChild, key);
		else
			return searchNode((AVLTreeNode<K, V>) treeNode.rightChild, key);
	}

	public void insert(K key, V value) {
		if (rootNode == null) {
			rootNode = createNewNode(key, value);
			return;
		}
		AVLTreeNode<K, V> newlyInsertedNode = insertNode(rootNode, key, value);
		if (newlyInsertedNode != null)
			balanceTreeAfterInsertion(null, newlyInsertedNode);
	}

	public void remove(K key) {
		AVLTreeNode<K, V> searchedNode = searchNode(rootNode, key);
		if (searchedNode == null)
			return;
		removeNode(searchedNode);
	}

	public AVLTreeNode<K, V> findPredecessorKeyNode(K key) {
		AVLTreeNode<K, V> nodePredecessorToBeFind = searchNode(rootNode, key);
		return nodePredecessorToBeFind != null ? findPredecessorKeyNode(nodePredecessorToBeFind) : null;
	}

	private AVLTreeNode<K, V> findPredecessorKeyNode(AVLTreeNode<K, V> node) {
		if (node.leftChild != null)
			return findMaximumKeyNode((AVLTreeNode<K, V>) node.leftChild);
		while (node.parentNode != null && node.parentNode.leftChild == node)
			node = (AVLTreeNode<K, V>) node.parentNode;
		return (AVLTreeNode<K, V>) node.parentNode;
	}

	public AVLTreeNode<K, V> findSuccessorKeyNode(K key) {
		AVLTreeNode<K, V> nodeSuccessorToBeFind = searchNode(rootNode, key);
		return nodeSuccessorToBeFind != null ? findSuccessorKeyNode(nodeSuccessorToBeFind) : null;
	}

	private AVLTreeNode<K, V> findSuccessorKeyNode(AVLTreeNode<K, V> node) {
		if (node.rightChild != null)
			return findMinimumKeyNode((AVLTreeNode<K, V>) node.rightChild);
		while (node.parentNode != null && node.parentNode.rightChild == node)
			node = (AVLTreeNode<K, V>) node.parentNode;
		return (AVLTreeNode<K, V>) node.parentNode;
	}

	public AVLTreeNode<K, V> findMaximumKeyNode() {
		return rootNode == null ? null : findMaximumKeyNode(rootNode);
	}

	private AVLTreeNode<K, V> findMaximumKeyNode(AVLTreeNode<K, V> node) {
		return node.rightChild != null ? findMaximumKeyNode((AVLTreeNode<K, V>) node.rightChild) : node;
	}

	public AVLTreeNode<K, V> findMinimumKeyNode() {
		return rootNode == null ? null : findMinimumKeyNode(rootNode);
	}

	private AVLTreeNode<K, V> findMinimumKeyNode(AVLTreeNode<K, V> node) {
		return node.leftChild != null ? findMinimumKeyNode((AVLTreeNode<K, V>) node.leftChild) : node;
	}

	private void removeNode(AVLTreeNode<K, V> nodeToBeRemoved) {
		if (isTreeWithOnlyRootNode(nodeToBeRemoved)) {
			rootNode = null;
			return;
		} else if (isLeafNode(nodeToBeRemoved)) {
			removeLeafNode(nodeToBeRemoved);
			balanceTreeAfterLeafNodeDeletion((AVLTreeNode<K, V>) nodeToBeRemoved.parentNode);
		} else if (isNodeWithOnlyOneChildLeafRemaining(nodeToBeRemoved)) {
			removeNodeWithOneLeafNode(nodeToBeRemoved);
			balanceTreeAfterRemovingNodeHavingOneLeaf(nodeToBeRemoved);
		} else {
			AVLTreeNode<K, V> successorNodeOfNodeToBeRemoved = findSuccessorKeyNode(nodeToBeRemoved);
			removeNodeWithTwoSubTrees(nodeToBeRemoved, successorNodeOfNodeToBeRemoved);
			balanceTreeAfterRemovingNodeHavingTwoChilds(successorNodeOfNodeToBeRemoved);
		}
	}

	private void balanceTreeAfterRemovingNodeHavingTwoChilds(AVLTreeNode<K, V> successorNodeOfNodeToBeRemoved) {
		if (isLeafNode(successorNodeOfNodeToBeRemoved))
			balanceTreeAfterLeafNodeDeletion((AVLTreeNode<K, V>) successorNodeOfNodeToBeRemoved.parentNode);
		else if (isNodeWithOnlyOneChildLeafRemaining(successorNodeOfNodeToBeRemoved))
			balanceTreeAfterRemovingNodeHavingOneLeaf(successorNodeOfNodeToBeRemoved);
	}

	private void removeNodeWithTwoSubTrees(AVLTreeNode<K, V> nodeToBeRemoved,
			AVLTreeNode<K, V> successorNodeOfNodeToBeRemoved) {
		nodeToBeRemoved.key = successorNodeOfNodeToBeRemoved.key;
		nodeToBeRemoved.value = successorNodeOfNodeToBeRemoved.value;
		AVLTreeNode<K, V> successorNodeChild = (AVLTreeNode<K, V>) successorNodeOfNodeToBeRemoved.rightChild;
		AVLTreeNode<K, V> successorNodeParent = (AVLTreeNode<K, V>) successorNodeOfNodeToBeRemoved.parentNode;
		if (successorNodeParent.leftChild == successorNodeOfNodeToBeRemoved)
			successorNodeParent.leftChild = successorNodeChild;
		else
			successorNodeParent.rightChild = successorNodeChild;
		if (successorNodeChild != null)
			successorNodeChild.parentNode = successorNodeParent;
	}

	private void balanceTreeAfterRemovingNodeHavingOneLeaf(AVLTreeNode<K, V> nodeRemoved) {
		if (isTreeWithOnlyRootNode(rootNode))
			return;
		AVLTreeNode<K, V> parentNode = (AVLTreeNode<K, V>) nodeRemoved.parentNode;
		AVLTreeNode<K, V> removedNodeChild = nodeRemoved.leftChild != null ? (AVLTreeNode<K, V>) nodeRemoved.leftChild
				: (AVLTreeNode<K, V>) nodeRemoved.rightChild;
		AVLTreeNode<K, V> removedNodeSibling = parentNode.leftChild == removedNodeChild
				? (AVLTreeNode<K, V>) parentNode.rightChild
				: (AVLTreeNode<K, V>) parentNode.leftChild;
		int heightDifference = calculateNodeHeightDifference(removedNodeChild, removedNodeSibling);
		if (heightDifference == -1)
			return;
		else if (heightDifference == 0)
			checkForHeightBalancePropertyViolationAndRotateTreeNodesAfterDeletion(removedNodeChild,
					(AVLTreeNode<K, V>) removedNodeChild.parentNode);
		else if (heightDifference == -2) {
			parentNode.height--;
			String grandParentToParentRelationType = findChildRelationType(removedNodeSibling, parentNode);
			AVLTreeNode<K, V> removedNodeSiblingChildWithLargestHeight = getLargestHeightChildNode(
					grandParentToParentRelationType, removedNodeSibling);
			String relationType = findNodeRelations(removedNodeSiblingChildWithLargestHeight, removedNodeSibling,
					parentNode);
			rotateTreeNodes(removedNodeSiblingChildWithLargestHeight, removedNodeSibling, parentNode);
			if (relationType.equals("LL") || relationType.equals("RR"))
				checkForHeightBalancePropertyViolationAndRotateTreeNodesAfterDeletion(removedNodeSibling,
						(AVLTreeNode<K, V>) removedNodeSibling.parentNode);
			else
				checkForHeightBalancePropertyViolationAndRotateTreeNodesAfterDeletion(
						removedNodeSiblingChildWithLargestHeight,
						(AVLTreeNode<K, V>) removedNodeSiblingChildWithLargestHeight.parentNode);
		}

	}

	private void removeLeafNode(AVLTreeNode<K, V> node) {
		if (node.parentNode.leftChild == node)
			node.parentNode.leftChild = null;
		else
			node.parentNode.rightChild = null;
	}

	private void removeNodeWithOneLeafNode(AVLTreeNode<K, V> nodeToBeRemoved) {
		if (isRootNode(nodeToBeRemoved)) {
			removeRootNodeWithSingleChild(nodeToBeRemoved);
			return;
		}
		AVLTreeNode<K, V> parentNode = (AVLTreeNode<K, V>) nodeToBeRemoved.parentNode;
		AVLTreeNode<K, V> childNode = nodeToBeRemoved.leftChild != null ? (AVLTreeNode<K, V>) nodeToBeRemoved.leftChild
				: (AVLTreeNode<K, V>) nodeToBeRemoved.rightChild;
		if (parentNode.leftChild == nodeToBeRemoved)
			parentNode.leftChild = childNode;
		else
			parentNode.rightChild = childNode;
		childNode.parentNode = parentNode;
	}

	private void removeRootNodeWithSingleChild(AVLTreeNode<K, V> node) {
		if (node.leftChild != null)
			rootNode = (AVLTreeNode<K, V>) rootNode.leftChild;
		else
			rootNode = (AVLTreeNode<K, V>) rootNode.rightChild;
		rootNode.parentNode = null;
	}

	private void balanceTreeAfterLeafNodeDeletion(AVLTreeNode<K, V> parentNode) {
		if (isNodeWithOnlyOneChildLeafRemaining(parentNode))
			return;
		if (isInternalNodeHasOnlyOneSubTree(parentNode) && parentNode.height == 3) {
			AVLTreeNode<K, V> childNode = getAvailableSingleChildNode(parentNode);
			AVLTreeNode<K, V> grandChildNode = getAvailableSingleChildNode(childNode);
			balanceTreeAfterLeafDeletionWhoseParentNodeHasSingleSubTreeRemaining(grandChildNode, childNode, parentNode);
		} else if (isLeafNode(parentNode)) {
			parentNode.height--;
			checkForHeightBalancePropertyViolationAndRotateTreeNodesAfterDeletion(parentNode,
					(AVLTreeNode<K, V>) parentNode.parentNode);
		}
	}

	private AVLTreeNode<K, V> getAvailableSingleChildNode(AVLTreeNode<K, V> node) {
		return node.rightChild == null ? (AVLTreeNode<K, V>) node.leftChild : (AVLTreeNode<K, V>) node.rightChild;
	}

	private void balanceTreeAfterLeafDeletionWhoseParentNodeHasSingleSubTreeRemaining(AVLTreeNode<K, V> grandChildNode,
			AVLTreeNode<K, V> childNode, AVLTreeNode<K, V> parentNode) {
		String relationType = findNodeRelations(grandChildNode, childNode, parentNode);
		parentNode.height--;
		rotateTreeNodes(grandChildNode, childNode, parentNode);
		if (relationType.equals("LL") || relationType.equals("RR"))
			checkForHeightBalancePropertyViolationAndRotateTreeNodesAfterDeletion(childNode,
					(AVLTreeNode<K, V>) childNode.parentNode);
		else
			checkForHeightBalancePropertyViolationAndRotateTreeNodesAfterDeletion(grandChildNode,
					(AVLTreeNode<K, V>) grandChildNode.parentNode);
	}

	private void checkForHeightBalancePropertyViolationAndRotateTreeNodesAfterDeletion(
			AVLTreeNode<K, V> travelledPathChildNode, AVLTreeNode<K, V> parentNode) {
		if (parentNode == null)
			return;
		AVLTreeNode<K, V> grandParentNode = (AVLTreeNode<K, V>) parentNode.parentNode;
		AVLTreeNode<K, V> leftChild = (AVLTreeNode<K, V>) parentNode.leftChild;
		AVLTreeNode<K, V> rightChild = (AVLTreeNode<K, V>) parentNode.rightChild;
		if (leftChild != null && rightChild != null) {
			AVLTreeNode<K, V> siblingNode = travelledPathChildNode == leftChild ? rightChild : leftChild;
			int heightDifference = calculateNodeHeightDifference(siblingNode,
					travelledPathChildNode == leftChild ? leftChild : rightChild);
			if (heightDifference == 1)
				return;
			else if (heightDifference == 0) {
				parentNode.height--;
				checkForHeightBalancePropertyViolationAndRotateTreeNodesAfterDeletion(parentNode, grandParentNode);
			} else if (heightDifference == 2) {
				parentNode.height--;
				String grandParentToParentRelationType = findChildRelationType(siblingNode, parentNode);
				AVLTreeNode<K, V> siblingChildWithLargestHeight = getLargestHeightChildNode(
						grandParentToParentRelationType, siblingNode);
				rotateTreeNodes(siblingChildWithLargestHeight, siblingNode, parentNode);
				checkForHeightBalancePropertyViolationAndRotateTreeNodesAfterDeletion(parentNode, grandParentNode);
			}
		}
	}

	private String findChildRelationType(AVLTreeNode<K, V> siblingNode, BinaryTreeNode<K, V> parentNode) {
		return parentNode.leftChild == siblingNode ? "L" : "R";
	}

	private AVLTreeNode<K, V> getLargestHeightChildNode(String grandParentToParentRelationType,
			AVLTreeNode<K, V> node) {
		AVLTreeNode<K, V> leftChild = (AVLTreeNode<K, V>) node.leftChild;
		AVLTreeNode<K, V> rightChild = (AVLTreeNode<K, V>) node.rightChild;
		return grandParentToParentRelationType.equals("L")
				? leftChild.height >= rightChild.height ? leftChild : rightChild
				: rightChild.height >= leftChild.height ? rightChild : leftChild;
	}

	private boolean isNodeWithOnlyOneChildLeafRemaining(AVLTreeNode<K, V> parentNode) {
		return isInternalNodeHasOnlyOneSubTree(parentNode) && parentNode.height == 2;
	}

	private boolean isInternalNodeHasOnlyOneSubTree(AVLTreeNode<K, V> parentNode) {
		return parentNode.leftChild == null ^ parentNode.rightChild == null;
	}

	private boolean isTreeWithOnlyRootNode(AVLTreeNode<K, V> node) {
		return isRootNode(node) && isLeafNode(node);
	}

	private boolean isLeafNode(AVLTreeNode<K, V> searchedNode) {
		return searchedNode.leftChild == null && searchedNode.rightChild == null;
	}

	private AVLTreeNode<K, V> insertNode(AVLTreeNode<K, V> treeNode, K key, V value) {
		int comparatorResult = comparator.compare(treeNode.key, key);
		if (comparatorResult == 0) {
			treeNode.value = value;
			return null;
		}
		if (comparatorResult < 0)
			if (treeNode.leftChild != null)
				return insertNode((AVLTreeNode<K, V>) treeNode.leftChild, key, value);
			else
				return createLeftChildUnderNode(treeNode, key, value);
		else {
			if (treeNode.rightChild != null)
				return insertNode((AVLTreeNode<K, V>) treeNode.rightChild, key, value);
			else
				return createRightChildUnderNode(treeNode, key, value);
		}
	}

	private AVLTreeNode<K, V> createRightChildUnderNode(AVLTreeNode<K, V> treeNode, K key, V value) {
		AVLTreeNode<K, V> newNode = createNewNode(key, value);
		treeNode.rightChild = newNode;
		newNode.parentNode = treeNode;
		return newNode;
	}

	private AVLTreeNode<K, V> createLeftChildUnderNode(AVLTreeNode<K, V> treeNode, K key, V value) {
		AVLTreeNode<K, V> newNode = createNewNode(key, value);
		treeNode.leftChild = newNode;
		newNode.parentNode = treeNode;
		return newNode;
	}

	private AVLTreeNode<K, V> createNewNode(K key, V value) {
		AVLTreeNode<K, V> newNode = new AVLTreeNode<>();
		newNode.key = key;
		newNode.value = value;
		return newNode;
	}

	private void balanceTreeAfterInsertion(AVLTreeNode<K, V> travelledPathChildNode, AVLTreeNode<K, V> node) {
		if (isRootNode(node))
			return;
		AVLTreeNode<K, V> parentNode = (AVLTreeNode<K, V>) node.parentNode;
		AVLTreeNode<K, V> rightNode = (AVLTreeNode<K, V>) parentNode.rightChild;
		AVLTreeNode<K, V> leftNode = (AVLTreeNode<K, V>) parentNode.leftChild;
		if (leftNode == node)
			checkForHeightBalancePropertyViolationAndRotateTreeNodesAfterInsertion(travelledPathChildNode, node,
					rightNode, parentNode);
		else
			checkForHeightBalancePropertyViolationAndRotateTreeNodesAfterInsertion(travelledPathChildNode, node,
					leftNode, parentNode);
	}

	private void checkForHeightBalancePropertyViolationAndRotateTreeNodesAfterInsertion(
			AVLTreeNode<K, V> travelledPathChildNode, AVLTreeNode<K, V> node, AVLTreeNode<K, V> siblingNode,
			AVLTreeNode<K, V> parentNode) {
		if (siblingNode != null) {
			int heightDiffernce = calculateNodeHeightDifference(node, siblingNode);
			if (heightDiffernce == 0)
				return;
			else if (heightDiffernce == 1) {
				parentNode.height++;
				balanceTreeAfterInsertion(node, parentNode);
			} else if (heightDiffernce == 2)
				rotateTreeNodes(travelledPathChildNode, node, parentNode);
		} else if (node.height > 1)
			rotateTreeNodes(travelledPathChildNode, node, parentNode);
		else
			incrementHeightOfNodeWhichWasLeafPreviously(parentNode, node);
	}

	private int calculateNodeHeightDifference(AVLTreeNode<K, V> node, AVLTreeNode<K, V> siblingtNode) {
		return node.height - siblingtNode.height;
	}

	private void incrementHeightOfNodeWhichWasLeafPreviously(AVLTreeNode<K, V> parentNode, AVLTreeNode<K, V> node) {
		parentNode.height++;
		balanceTreeAfterInsertion(node, parentNode);
	}

	private void rotateTreeNodes(AVLTreeNode<K, V> travelledPathChildNode, AVLTreeNode<K, V> node,
			AVLTreeNode<K, V> parentNode) {
		String relationType = findNodeRelations(travelledPathChildNode, node, parentNode);
		switch (relationType) {
		case "LL":
			rotateRightTreeNodes(node, parentNode);
			break;
		case "LR":
			rotateLeftRightTreeNodes(travelledPathChildNode, node, parentNode);
			break;
		case "RR":
			rotateLeftTreeNodes(node, parentNode);
			break;
		case "RL":
			rotateRightLeftTreeNodes(travelledPathChildNode, node, parentNode);
		}
	}

	private String findNodeRelations(AVLTreeNode<K, V> travelledPathChildNode, AVLTreeNode<K, V> node,
			AVLTreeNode<K, V> parentNode) {
		String relationType = parentNode.leftChild == node ? "L" : "R";
		relationType += node.leftChild == travelledPathChildNode ? "L" : "R";
		return relationType;
	}

	private void rotateLeftRightTreeNodes(AVLTreeNode<K, V> travelledPathChildNode, AVLTreeNode<K, V> node,
			AVLTreeNode<K, V> parentNode) {
		node.height--;
		travelledPathChildNode.height++;
		travelledPathChildNode.parentNode = parentNode;
		parentNode.leftChild = travelledPathChildNode;
		AVLTreeNode<K, V> travelledPathChildNodeLeftChild = (AVLTreeNode<K, V>) travelledPathChildNode.leftChild;
		node.rightChild = travelledPathChildNodeLeftChild;
		if (travelledPathChildNodeLeftChild != null)
			travelledPathChildNodeLeftChild.parentNode = node;
		node.parentNode = travelledPathChildNode;
		travelledPathChildNode.leftChild = node;
		rotateRightTreeNodes(travelledPathChildNode, parentNode);
	}

	private void rotateRightTreeNodes(AVLTreeNode<K, V> node, AVLTreeNode<K, V> parentNode) {
		parentNode.height--;
		AVLTreeNode<K, V> nodeRightChild = (AVLTreeNode<K, V>) node.rightChild;
		parentNode.leftChild = nodeRightChild;
		if (nodeRightChild != null)
			nodeRightChild.parentNode = parentNode;
		swapGrandParentPositionWithParentNode(node, parentNode);
		parentNode.parentNode = node;
		node.rightChild = parentNode;
	}

	private void rotateRightLeftTreeNodes(AVLTreeNode<K, V> travelledPathChildNode, AVLTreeNode<K, V> node,
			AVLTreeNode<K, V> parentNode) {
		node.height--;
		travelledPathChildNode.height++;
		parentNode.rightChild = travelledPathChildNode;
		travelledPathChildNode.parentNode = parentNode;
		AVLTreeNode<K, V> travelledPathChildNodeRightNode = (AVLTreeNode<K, V>) travelledPathChildNode.rightChild;
		node.leftChild = travelledPathChildNodeRightNode;
		if (travelledPathChildNodeRightNode != null)
			travelledPathChildNodeRightNode.parentNode = node;
		travelledPathChildNode.rightChild = node;
		node.parentNode = travelledPathChildNode;
		rotateLeftTreeNodes(travelledPathChildNode, parentNode);
	}

	private void rotateLeftTreeNodes(AVLTreeNode<K, V> node, AVLTreeNode<K, V> parentNode) {
		parentNode.height--;
		AVLTreeNode<K, V> nodeLeftChild = (AVLTreeNode<K, V>) node.leftChild;
		parentNode.rightChild = nodeLeftChild;
		if (nodeLeftChild != null)
			nodeLeftChild.parentNode = parentNode;
		swapGrandParentPositionWithParentNode(node, parentNode);
		parentNode.parentNode = node;
		node.leftChild = parentNode;
	}

	private void swapGrandParentPositionWithParentNode(AVLTreeNode<K, V> node, AVLTreeNode<K, V> parentNode) {
		if (parentNode.parentNode != null)
			if (parentNode.parentNode.leftChild == parentNode)
				parentNode.parentNode.leftChild = node;
			else
				parentNode.parentNode.rightChild = node;
		else
			rootNode = node;
		node.parentNode = parentNode.parentNode;
	}

	private boolean isRootNode(AVLTreeNode<K, V> node) {
		return node.parentNode == null;
	}
}