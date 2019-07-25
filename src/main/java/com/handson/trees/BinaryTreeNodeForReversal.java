/**
 * 
 */
package com.handson.trees;

/**
 * @author sveera
 *
 */
public class BinaryTreeNodeForReversal<K, V> extends BinaryTreeNode<K, V> {

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BinaryTreeNodeForReversal<K, V> other = (BinaryTreeNodeForReversal<K, V>) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (leftChild == null) {
			if (other.leftChild != null)
				return false;
		} else if (!leftChild.equals(other.leftChild))
			return false;
		if (parentNode == null) {
			if (other.parentNode != null)
				return false;
		} else if (!(parentNode.key == other.parentNode.key))
			return false;
		if (rightChild == null) {
			if (other.rightChild != null)
				return false;
		} else if (!rightChild.equals(other.rightChild))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
