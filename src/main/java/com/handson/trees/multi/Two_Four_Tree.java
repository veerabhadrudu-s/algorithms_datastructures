/**
 * 
 */
package com.handson.trees.multi;

import java.util.Arrays;

import com.handson.trees.Comparator;
import com.handson.trees.multi.Two_Four_Tree_Node.Entry;

/**
 * @author sveera
 *
 */
public class Two_Four_Tree<K, V> {

	private final Comparator<K> comparator;
	protected Two_Four_Tree_Node rootNode;
	// private final int minimumNoOfKeysRequired = a-1;
	private final int minimumNoOfKeysRequired = 1;
	private final int minimumKeyIndexRequired = minimumNoOfKeysRequired - 1;

	public Two_Four_Tree(Comparator<K> comparator) {
		this.comparator = comparator;
	}

	public V search(K key) {
		if (rootNode == null)
			return null;
		Entry keyEntry = searchKeyEntry(rootNode, key);
		return keyEntry != null ? (V) keyEntry.value : null;
	}

	public void insert(K key, V value) {
		if (rootNode == null) {
			rootNode = constructNewNode(key, value);
			return;
		}
		insertNewKey(rootNode, key, value);
	}

	public V delete(K key) {
		if (rootNode == null)
			return null;
		return deleteKey(rootNode, key);
	}

	private V deleteKey(Two_Four_Tree_Node node, K key) {
		int indxAtWhichKeyToBeDel = -1;
		while (indxAtWhichKeyToBeDel == -1 && node != null) {
			Entry[] keys = node.keys;
			for (int i = 0; i < keys.length && keys[i] != null; i++) {
				int result = comparator.compare((K) keys[i].key, key);
				if (result == 0) {
					indxAtWhichKeyToBeDel = i;
					break;
				} else if (result < 0) {
					node = node.children[i];
					break;
				} else if (keys.length - 1 == i || keys[i + 1] == null) {
					node = node.children[i + 1];
					break;
				}
			}
		}
		V valueOfKey = indxAtWhichKeyToBeDel == -1 ? null : (V) node.keys[indxAtWhichKeyToBeDel].value;
		if (indxAtWhichKeyToBeDel != -1)
			deleteKeyAtIndex(node, indxAtWhichKeyToBeDel);
		return valueOfKey;
	}

	private void deleteKeyAtIndex(Two_Four_Tree_Node node, int indxAtWhichKeyToBeDel) {
		if (isLeafNode(node))
			deleteKeyInLeafNode(node, indxAtWhichKeyToBeDel);
		else {
			Two_Four_Tree_Node successorKeyNode = node.children[indxAtWhichKeyToBeDel + 1];
			while (successorKeyNode.children[0] != null)
				successorKeyNode = successorKeyNode.children[0];
			node.keys[indxAtWhichKeyToBeDel] = successorKeyNode.keys[0];
			deleteKeyInLeafNode(successorKeyNode, 0);
			node = successorKeyNode;
		}
		if (isRootNodeEmpty(node))
			rootNode = null;
		else if (!isRootNode(node) && isNodeHasLessThanMinNoOfKeys(node))
			balanceKeysInNode(node);
	}

	private void balanceKeysInNode(Two_Four_Tree_Node node) {
		Two_Four_Tree_Node parentNode = node.parent;
		Two_Four_Tree_Node[] children = parentNode.children;
		int childNodeIndex = findIndexPositionOfChildInParent(parentNode, node);
		int siblingNodeIndex = childNodeIndex == parentNode.noOfExistingKeysInNode ? childNodeIndex - 1
				: childNodeIndex + 1;
		int parentKeyIndex = childNodeIndex == 0 ? 0
				: childNodeIndex == parentNode.noOfExistingKeysInNode ? childNodeIndex - 1 : childNodeIndex;
		if (isNodeHasMoreThanMinNoOfKeys(children[siblingNodeIndex]))
			fillNodeByBorrowingFromSiblingNode(parentNode, children[childNodeIndex], children[siblingNodeIndex],
					childNodeIndex, siblingNodeIndex, parentKeyIndex);
		else {
			if (childNodeIndex > siblingNodeIndex)
				mergeChildAtEndOfSibling(parentNode, parentKeyIndex, childNodeIndex, children[childNodeIndex],
						children[siblingNodeIndex]);
			else
				mergeChildInFrontOfSibling(parentNode, parentKeyIndex, children[childNodeIndex],
						children[siblingNodeIndex]);
			if (isRootNodeEmpty(parentNode)) {
				rootNode = children[0];
				rootNode.parent = null;
			} else if (!isRootNode(parentNode) && isNodeHasLessThanMinNoOfKeys(parentNode))
				balanceKeysInNode(parentNode);
		}
	}

	private void fillNodeByBorrowingFromSiblingNode(Two_Four_Tree_Node parentNode, Two_Four_Tree_Node childNode,
			Two_Four_Tree_Node siblingNode, int childNodeIndex, int siblingNodeIndex, int parentKeyIndex) {
		if (childNodeIndex > siblingNodeIndex)
			addParentKeyInFrontOfChild(childNode, parentNode, parentKeyIndex, siblingNode);
		else
			addParentKeyAtEndOfChild(childNode, parentNode, parentKeyIndex, siblingNode);
	}

	private void addParentKeyInFrontOfChild(Two_Four_Tree_Node childNode, Two_Four_Tree_Node parentNode,
			int parentKeyIndex, Two_Four_Tree_Node siblingNode) {
		int siblingNodeKeyIndex = siblingNode.noOfExistingKeysInNode - 1;
		Two_Four_Tree_Node siblingChild = siblingNode.children[siblingNode.noOfExistingKeysInNode];
		shiftKeysToRightFromindex(childNode, 0);
		shiftChildrenToRightFromindex(childNode, 0);
		childNode.keys[0] = parentNode.keys[parentKeyIndex];
		childNode.children[0] = siblingChild;
		if (siblingChild != null)
			siblingChild.parent = childNode;
		childNode.noOfExistingKeysInNode++;
		parentNode.keys[parentKeyIndex] = siblingNode.keys[siblingNodeKeyIndex];
		siblingNode.keys[siblingNodeKeyIndex] = null;
		siblingNode.children[siblingNode.noOfExistingKeysInNode] = null;
		siblingNode.noOfExistingKeysInNode--;
	}

	private void addParentKeyAtEndOfChild(Two_Four_Tree_Node childNode, Two_Four_Tree_Node parentNode,
			int parentKeyIndex, Two_Four_Tree_Node siblingNode) {
		int siblingNodeKeyIndex = 0;
		Two_Four_Tree_Node siblingChild = siblingNode.children[0];
		childNode.keys[minimumKeyIndexRequired] = parentNode.keys[parentKeyIndex];
		childNode.children[minimumKeyIndexRequired + 1] = siblingChild;
		if (siblingChild != null)
			siblingChild.parent = childNode;
		childNode.noOfExistingKeysInNode++;
		parentNode.keys[parentKeyIndex] = siblingNode.keys[siblingNodeKeyIndex];
		shiftKeysToLeftFromindex(siblingNode, siblingNodeKeyIndex);
		shiftChildrenToLeftFromindex(siblingNode, siblingNodeKeyIndex);
		siblingNode.noOfExistingKeysInNode--;
	}

	private void mergeChildAtEndOfSibling(Two_Four_Tree_Node parentNode, int parentKeyIndex, int childNodeIndex,
			Two_Four_Tree_Node childNodeWithLessThanMinKeys, Two_Four_Tree_Node siblingNode) {
		for (int i = 0; i <= childNodeWithLessThanMinKeys.noOfExistingKeysInNode; i++) {
			siblingNode.children[siblingNode.noOfExistingKeysInNode + 1 + i] = childNodeWithLessThanMinKeys.children[i];
			if (childNodeWithLessThanMinKeys.children[i] != null)
				childNodeWithLessThanMinKeys.children[i].parent = siblingNode;
		}
		siblingNode.keys[siblingNode.noOfExistingKeysInNode++] = parentNode.keys[parentKeyIndex];
		for (int i = 0; i < childNodeWithLessThanMinKeys.noOfExistingKeysInNode; i++)
			siblingNode.keys[siblingNode.noOfExistingKeysInNode++] = childNodeWithLessThanMinKeys.keys[i];
		parentNode.keys[parentKeyIndex] = null;
		parentNode.children[childNodeIndex] = null;
		parentNode.noOfExistingKeysInNode--;
	}

	/*
	 * If (a,b) tree doesn't work properly for split and promote case this method
	 * need's to debugged.
	 */
	private void mergeChildInFrontOfSibling(Two_Four_Tree_Node parentNode, int parentKeyIndex,
			Two_Four_Tree_Node childNodeWithLessThanMinKeys, Two_Four_Tree_Node siblingNode) {
		shiftKeysToRight(siblingNode, childNodeWithLessThanMinKeys.noOfExistingKeysInNode + 1);
		shiftChildrenToRight(siblingNode, childNodeWithLessThanMinKeys.noOfExistingKeysInNode + 1);
		for (int i = 0; i <= childNodeWithLessThanMinKeys.noOfExistingKeysInNode; i++) {
			siblingNode.children[i] = childNodeWithLessThanMinKeys.children[i];
			if (childNodeWithLessThanMinKeys.children[i] != null)
				childNodeWithLessThanMinKeys.children[i].parent = siblingNode;
		}
		for (int i = 0; i < childNodeWithLessThanMinKeys.noOfExistingKeysInNode; i++, siblingNode.noOfExistingKeysInNode++)
			siblingNode.keys[i] = childNodeWithLessThanMinKeys.keys[i];
		siblingNode.keys[childNodeWithLessThanMinKeys.noOfExistingKeysInNode] = parentNode.keys[parentKeyIndex];
		siblingNode.noOfExistingKeysInNode++;
		shiftKeysToLeftFromindex(parentNode, parentKeyIndex);
		shiftChildrenToLeftFromindex(parentNode, parentKeyIndex);
		parentNode.noOfExistingKeysInNode--;
	}

	private void deleteKeyInLeafNode(Two_Four_Tree_Node node, int indxAtWhichKeyToBeDel) {
		node.keys[indxAtWhichKeyToBeDel] = null;
		node.noOfExistingKeysInNode--;
		shiftKeysToLeftFromindex(node, indxAtWhichKeyToBeDel);
	}

	private Entry searchKeyEntry(Two_Four_Tree_Node node, K key) {
		if (node == null)
			return null;
		Entry[] keys = node.keys;
		for (int i = 0; i < keys.length && keys[i] != null; i++) {
			int result = comparator.compare((K) keys[i].key, key);
			if (result == 0)
				return keys[i];
			else if (result < 0)
				return searchKeyEntry(node.children[i], key);
			else if (keys.length - 1 == i || keys[i + 1] == null)
				return searchKeyEntry(node.children[i + 1], key);
		}
		return null;
	}

	private void insertNewKey(Two_Four_Tree_Node node, K key, V value) {
		if (isLeafNode(node))
			handleInsertionInLeafNode(node, key, value);
		else {
			Entry[] keys = node.keys;
			for (int i = 0; i < keys.length && keys[i] != null; i++) {
				int result = comparator.compare((K) keys[i].key, key);
				if (result == 0) {
					keys[i].value = value;
					return;
				} else if (result < 0) {
					insertNewKey(node.children[i], key, value);
					return;
				} else if (result > 0 && (keys.length - 1 == i || keys[i + 1] == null))
					insertNewKey(node.children[i + 1], key, value);
			}
		}

	}

	private void handleInsertionInLeafNode(Two_Four_Tree_Node node, K key, V value) {
		Entry[] keys = node.keys;
		int indexPositionToBeInserted = 0;
		for (; indexPositionToBeInserted < keys.length
				&& keys[indexPositionToBeInserted] != null; indexPositionToBeInserted++) {
			int result = comparator.compare((K) keys[indexPositionToBeInserted].key, key);
			if (result == 0) {
				keys[indexPositionToBeInserted].value = value;
				return;
			} else if (result < 0)
				break;
		}
		if (isNodeFull(node))
			insertKeyInFullNode(node, new Two_Four_Tree_Node[node.children.length + 1],
					createNewEntryNode(key, value, node), indexPositionToBeInserted);
		else
			insertKeyInNonFullNode(node, key, value, indexPositionToBeInserted);
	}

	private void insertKeyInFullNode(Two_Four_Tree_Node node, Two_Four_Tree_Node[] newChildren, Entry newKey,
			int indexPositionToBeInserted) {
		Entry[] tempKeys = new Entry[node.keys.length + 1];
		for (int i = 0; i < indexPositionToBeInserted; i++)
			tempKeys[i] = node.keys[i];
		tempKeys[indexPositionToBeInserted] = newKey;
		for (int i = indexPositionToBeInserted + 1; i < tempKeys.length; i++)
			tempKeys[i] = node.keys[i - 1];
		int medianIndex = (tempKeys.length - 1) / 2;
		if (!isRootNode(node) && !isNodeFull(node.parent))
			insertInParentHavingFreeSpace(node.parent, newChildren, tempKeys);
		else if (isRootNode(node))
			rootNode = splitNodeByCreatingNewNode(node, newChildren, tempKeys);
		else {
			int indexPositionInParentNode = findIndexPositionInWhichNewKeyToInserted(node.parent,
					(K) tempKeys[medianIndex].key);
			Two_Four_Tree_Node leftNode = constructNewNode(node.parent, tempKeys, 0, medianIndex - 1);
			Two_Four_Tree_Node rightNode = constructNewNode(node.parent, tempKeys, medianIndex + 1,
					tempKeys.length - 1);
			copyChildren(leftNode, newChildren, 0, medianIndex);
			copyChildren(rightNode, newChildren, medianIndex + 1, newChildren.length - 1);
			Two_Four_Tree_Node[] existingChildrenParent = node.parent.children;
			Two_Four_Tree_Node[] newChildrenOfParent = new Two_Four_Tree_Node[node.parent.children.length + 1];
			for (int i = 0, newChildIndex = 0; i < existingChildrenParent.length; i++, newChildIndex++) {
				if (existingChildrenParent[i] == node) {
					newChildrenOfParent[newChildIndex] = leftNode;
					newChildrenOfParent[++newChildIndex] = rightNode;
				} else
					newChildrenOfParent[newChildIndex] = existingChildrenParent[i];
			}
			insertKeyInFullNode(node.parent, newChildrenOfParent, tempKeys[medianIndex], indexPositionInParentNode);
		}
	}

	private void insertInParentHavingFreeSpace(Two_Four_Tree_Node grandParent, Two_Four_Tree_Node[] grandChildren,
			Entry[] tempKeys) {
		int medianIndex = (tempKeys.length - 1) / 2;
		int indexPositionInParentNode = findIndexPositionInWhichNewKeyToInserted(grandParent,
				(K) tempKeys[medianIndex].key);
		shiftKeysToRightFromindex(grandParent, indexPositionInParentNode);
		shiftChildrenToRightFromindex(grandParent, indexPositionInParentNode + 1);
		grandParent.keys[indexPositionInParentNode] = tempKeys[medianIndex];
		Two_Four_Tree_Node leftParentNode = constructNewNode(grandParent, tempKeys, 0, medianIndex - 1);
		Two_Four_Tree_Node rightParentNode = constructNewNode(grandParent, tempKeys, medianIndex + 1,
				tempKeys.length - 1);
		grandParent.children[indexPositionInParentNode] = leftParentNode;
		grandParent.children[indexPositionInParentNode + 1] = rightParentNode;
		copyChildren(leftParentNode, grandChildren, 0, medianIndex);
		copyChildren(rightParentNode, grandChildren, medianIndex + 1, grandChildren.length - 1);
		grandParent.noOfExistingKeysInNode++;
	}

	private int findIndexPositionInWhichNewKeyToInserted(Two_Four_Tree_Node parentNode, K key) {
		Entry[] parentKeys = parentNode.keys;
		int i = 0;
		for (; i < parentKeys.length && parentKeys[i] != null; i++)
			if (comparator.compare((K) parentKeys[i].key, key) < 0)
				return i;
		return i;
	}

	private Two_Four_Tree_Node splitNodeByCreatingNewNode(Two_Four_Tree_Node existingNode,
			Two_Four_Tree_Node[] newChildren, Entry[] tempKeys) {
		int medianIndex = (tempKeys.length - 1) / 2;
		Two_Four_Tree_Node newParentNode = constructNewNode((K) tempKeys[medianIndex].key,
				(V) tempKeys[medianIndex].value);
		Two_Four_Tree_Node leftNode = constructNewNode(newParentNode, tempKeys, 0, medianIndex - 1);
		Two_Four_Tree_Node rightNode = constructNewNode(newParentNode, tempKeys, medianIndex + 1, tempKeys.length - 1);
		copyChildren(leftNode, newChildren, 0, medianIndex);
		copyChildren(rightNode, newChildren, medianIndex + 1, newChildren.length - 1);
		newParentNode.children[0] = leftNode;
		newParentNode.children[1] = rightNode;
		return newParentNode;
	}

	private void insertKeyInNonFullNode(Two_Four_Tree_Node node, K key, V value, int indexPositionToBeInserted) {
		if (node.noOfExistingKeysInNode != indexPositionToBeInserted)
			shiftKeysToRightFromindex(node, indexPositionToBeInserted);
		Entry entry = createNewEntryNode(key, value, node);
		node.keys[indexPositionToBeInserted] = entry;
		node.noOfExistingKeysInNode++;
	}

	private Two_Four_Tree_Node constructNewNode(Two_Four_Tree_Node parentNode, Entry[] tempKeys, int fromKeys,
			int toKeys) {
		Two_Four_Tree_Node newNode = new Two_Four_Tree_Node();
		for (int newNodeIndex = 0, i = fromKeys; i <= toKeys; i++, newNodeIndex++, newNode.noOfExistingKeysInNode++)
			newNode.keys[newNodeIndex] = tempKeys[i];
		newNode.parent = parentNode;
		return newNode;
	}

	private void copyChildren(Two_Four_Tree_Node parentNode, Two_Four_Tree_Node[] newChildren, int fromChildren,
			int toChildren) {
		for (int newNodeIndex = 0, i = fromChildren; i <= toChildren; parentNode.children[newNodeIndex++] = newChildren[i++])
			if (newChildren[i] != null)
				newChildren[i].parent = parentNode;
	}

	private void shiftKeysToRightFromindex(Two_Four_Tree_Node node, int indexPositionToBeShifted) {
		Entry[] keys = node.keys;
		for (int i = keys.length - 1; i > indexPositionToBeShifted; i--)
			keys[i] = keys[i - 1];
	}

	private void shiftChildrenToRightFromindex(Two_Four_Tree_Node node, int indexPositionToBeShifted) {
		Two_Four_Tree_Node[] children = node.children;
		for (int i = children.length - 1; i > indexPositionToBeShifted; i--)
			children[i] = children[i - 1];
	}

	private void shiftKeysToLeftFromindex(Two_Four_Tree_Node node, int indexPositionToBeShifted) {
		Entry[] keys = node.keys;
		for (int i = indexPositionToBeShifted; i < keys.length - 1; i++)
			keys[i] = keys[i + 1];
		keys[keys.length - 1] = null;
	}

	private void shiftKeysToRight(Two_Four_Tree_Node node, int noOfEmptySpacesRequired) {
		Entry[] keysCopy = Arrays.copyOf(node.keys, node.keys.length);
		Entry[] keys = node.keys;
		for (int i = 0; i < node.noOfExistingKeysInNode; i++)
			keys[i] = null;
		for (int i = 0; i < node.noOfExistingKeysInNode; i++)
			keys[noOfEmptySpacesRequired++] = keysCopy[i];
	}

	private void shiftChildrenToRight(Two_Four_Tree_Node node, int noOfEmptySpacesRequired) {
		Two_Four_Tree_Node[] childrenCopy = Arrays.copyOf(node.children, node.children.length);
		Two_Four_Tree_Node[] children = node.children;
		for (int i = 0; i < node.noOfExistingKeysInNode + 1; i++)
			children[i] = null;
		for (int i = 0; i < node.noOfExistingKeysInNode + 1; i++)
			children[noOfEmptySpacesRequired++] = childrenCopy[i];
	}

	private void shiftChildrenToLeftFromindex(Two_Four_Tree_Node node, int indexPositionToBeShifted) {
		Two_Four_Tree_Node[] children = node.children;
		for (int i = indexPositionToBeShifted; i < children.length - 1; i++)
			children[i] = children[i + 1];
		children[children.length - 1] = null;
	}

	private Two_Four_Tree_Node constructNewNode(K key, V value) {
		Two_Four_Tree_Node two_Four_Tree_Node = new Two_Four_Tree_Node();
		Two_Four_Tree_Node.Entry entry = createNewEntryNode(key, value, two_Four_Tree_Node);
		two_Four_Tree_Node.keys[0] = entry;
		two_Four_Tree_Node.noOfExistingKeysInNode++;
		return two_Four_Tree_Node;
	}

	private Two_Four_Tree_Node.Entry createNewEntryNode(K key, V value, Two_Four_Tree_Node two_Four_Tree_Node) {
		Two_Four_Tree_Node.Entry entry = two_Four_Tree_Node.new Entry();
		entry.key = key;
		entry.value = value;
		return entry;
	}

	private int findIndexPositionOfChildInParent(Two_Four_Tree_Node parentNode, Two_Four_Tree_Node childNode) {
		Two_Four_Tree_Node[] children = parentNode.children;
		int i = 0;
		for (; i < children.length; i++)
			if (children[i] == childNode)
				return i;
		return i;
	}

	private boolean isRootNodeEmpty(Two_Four_Tree_Node node) {
		return node.noOfExistingKeysInNode == 0 && node == rootNode;
	}

	private boolean isNodeHasLessThanMinNoOfKeys(Two_Four_Tree_Node node) {
		return node.noOfExistingKeysInNode < minimumNoOfKeysRequired;
	}

	private boolean isNodeHasMoreThanMinNoOfKeys(Two_Four_Tree_Node node) {
		return node.noOfExistingKeysInNode > minimumNoOfKeysRequired;
	}

	private boolean isRootNode(Two_Four_Tree_Node node) {
		return node.parent == null;
	}

	private boolean isLeafNode(Two_Four_Tree_Node node) {
		return node.children[0] == null;
	}

	private boolean isNodeFull(Two_Four_Tree_Node node) {
		return node.noOfExistingKeysInNode == node.keys.length;
	}

}
