/**
 * 
 */
package com.handson.trees;

/**
 * @author sveera
 *
 */
public class BinarySearchingTree<K, V> {

	private final Comparator<K> comparator;
	protected BinaryTreeNode<K, V> rootNode;

	public BinarySearchingTree(Comparator<K> comparator) {
		super();
		this.comparator = comparator;
	}

	public void insert(K key, V value) {
		if (rootNode == null) {
			rootNode = createNewNodeWithValue(key, value);
			return;
		}
		insertNode(key, value, rootNode);
	}

	public V search(K key) {
		BinaryTreeNode<K, V> node = searchNode(key, rootNode);
		return node != null ? node.value : null;
	}

	public V findMinValue() {
		return rootNode != null ? minValueNode(rootNode).value : null;
	}

	public V findMaxValue() {
		return rootNode != null ? maxValueNode(rootNode).value : null;
	}

	public BinaryTreeNode<K, V> findSuccessor(K keySuccessorToBeFind) {
		BinaryTreeNode<K, V> node = searchNode(keySuccessorToBeFind, rootNode);
		return node != null ? findSuccesorNode(node) : null;
	}

	public BinaryTreeNode<K, V> findPredecessor(K keySuccessorToBeFind) {
		BinaryTreeNode<K, V> node = searchNode(keySuccessorToBeFind, rootNode);
		return node != null ? findPredecessorNode(node) : null;
	}

	public BinaryTreeNode<K, V> delete(K key) {
		BinaryTreeNode<K, V> node = searchNode(key, rootNode);
		return node != null ? deleteNode(node) : null;
	}

	private BinaryTreeNode<K, V> deleteNode(BinaryTreeNode<K, V> node) {
		if (isTreeWithOnlyRootNode(node)) {
			rootNode = null;
			return node;
		} else if (isLeafNode(node))
			return removeLeafNode(node);
		else if (isNodeWithSingleChild(node))
			return removeNodeWithSingleChild(node);
		else
			return removeNodeWithTwoChild_With_Its_Successor(node);
	}

	private BinaryTreeNode<K, V> removeLeafNode(BinaryTreeNode<K, V> node) {
		clearParentOfLeaf(node);
		return node;
	}

	private boolean isNodeWithSingleChild(BinaryTreeNode<K, V> node) {
		return node.leftChild == null || node.rightChild == null;
	}

	private BinaryTreeNode<K, V> removeNodeWithSingleChild(BinaryTreeNode<K, V> node) {
		if (isRootNode(node))
			removeRootNodeWithSingleChild(node);
		else if (node.leftChild != null)
			removeInternalNodeHavingOnlyLeftSubTree(node);
		else
			removeInternalNodeHavingOnlyRightSubTree(node);
		return node;
	}

	private BinaryTreeNode<K, V> removeNodeWithTwoChild_With_Its_Successor(BinaryTreeNode<K, V> node) {
		/*
		 * A successor can be a leaf or internal node with only right sub tree.A
		 * successor will never contain left subtree.
		 */
		BinaryTreeNode<K, V> successorNode = findSuccesorNode(node);
		node.value = successorNode.value;
		if (isLeafNode(successorNode))
			clearParentOfLeaf(successorNode);
		else
			rearrangeSucessorRightSubTreeToSucessorParent(successorNode);
		return node;
	}

	private void clearParentOfLeaf(BinaryTreeNode<K, V> successorNode) {
		if (successorNode.parentNode.leftChild == successorNode)
			successorNode.parentNode.leftChild = null;
		else
			successorNode.parentNode.rightChild = null;
	}

	private void rearrangeSucessorRightSubTreeToSucessorParent(BinaryTreeNode<K, V> successorNode) {
		if (successorNode.parentNode.rightChild == successorNode)
			successorNode.parentNode.rightChild = successorNode.rightChild;
		else
			successorNode.parentNode.leftChild = successorNode.rightChild;
		successorNode.rightChild.parentNode = successorNode.parentNode;
	}

	private boolean isRootNode(BinaryTreeNode<K, V> node) {
		return node.parentNode == null;
	}

	private boolean isTreeWithOnlyRootNode(BinaryTreeNode<K, V> node) {
		return isRootNode(node) && isLeafNode(node);
	}

	private boolean isLeafNode(BinaryTreeNode<K, V> node) {
		return node.leftChild == null && node.rightChild == null;
	}

	private void removeRootNodeWithSingleChild(BinaryTreeNode<K, V> node) {
		if (node.leftChild != null)
			rootNode = rootNode.leftChild;
		else
			rootNode = rootNode.rightChild;
		rootNode.parentNode = null;
	}

	private void removeInternalNodeHavingOnlyLeftSubTree(BinaryTreeNode<K, V> node) {
		node.leftChild.parentNode = node.parentNode;
		if (node.parentNode.leftChild == node)
			node.parentNode.leftChild = node.leftChild;
		else
			node.parentNode.rightChild = node.leftChild;
	}

	private void removeInternalNodeHavingOnlyRightSubTree(BinaryTreeNode<K, V> node) {
		node.rightChild.parentNode = node.parentNode;
		if (node.parentNode.leftChild == node)
			node.parentNode.leftChild = node.rightChild;
		else
			node.parentNode.rightChild = node.rightChild;
	}

	private BinaryTreeNode<K, V> findPredecessorNode(BinaryTreeNode<K, V> node) {
		if (node.leftChild != null)
			return maxValueNode(node.leftChild);
		while (node.parentNode != null && node.parentNode.leftChild == node)
			node = node.parentNode;
		return node.parentNode;
	}

	private BinaryTreeNode<K, V> findSuccesorNode(BinaryTreeNode<K, V> node) {
		if (node.rightChild != null)
			return minValueNode(node.rightChild);
		while (node.parentNode != null && node.parentNode.rightChild == node)
			node = node.parentNode;
		return node.parentNode;
	}

	private BinaryTreeNode<K, V> maxValueNode(BinaryTreeNode<K, V> node) {
		return node.rightChild != null ? maxValueNode(node.rightChild) : node;
	}

	private BinaryTreeNode<K, V> minValueNode(BinaryTreeNode<K, V> node) {
		return node.leftChild != null ? minValueNode(node.leftChild) : node;
	}

	private void insertNode(K key, V value, BinaryTreeNode<K, V> currentNode) {
		int comparatorResult = comparator.compare(currentNode.key, key);
		if (comparatorResult == 0)
			currentNode.value = value;
		else if (comparatorResult < 0)
			if (currentNode.leftChild != null)
				insertNode(key, value, currentNode.leftChild);
			else
				createNewLeftLeaf(key, value, currentNode);
		else {
			if (currentNode.rightChild != null)
				insertNode(key, value, currentNode.rightChild);
			else
				createNewRightLeaf(key, value, currentNode);
		}
	}

	private void createNewRightLeaf(K key, V value, BinaryTreeNode<K, V> currentNode) {
		BinaryTreeNode<K, V> rightChild = createNewNodeWithValue(key, value);
		currentNode.rightChild = rightChild;
		rightChild.parentNode = currentNode;
	}

	private void createNewLeftLeaf(K key, V value, BinaryTreeNode<K, V> currentNode) {
		BinaryTreeNode<K, V> leftChild = createNewNodeWithValue(key, value);
		currentNode.leftChild = leftChild;
		leftChild.parentNode = currentNode;
	}

	private BinaryTreeNode<K, V> searchNode(K key, BinaryTreeNode<K, V> currentNode) {
		if (currentNode == null)
			return null;
		int comparatorResult = comparator.compare(currentNode.key, key);
		if (comparatorResult == 0)
			return currentNode;
		else if (comparatorResult < 0)
			return searchNode(key, currentNode.leftChild);
		else
			return searchNode(key, currentNode.rightChild);
	}

	private BinaryTreeNode<K, V> createNewNodeWithValue(K key, V value) {
		BinaryTreeNode<K, V> node = new BinaryTreeNode<>();
		node.key = key;
		node.value = value;
		return node;
	}

}
