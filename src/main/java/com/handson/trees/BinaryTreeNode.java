/**
 * 
 */
package com.handson.trees;

/**
 * @author sveera
 *
 */
public class BinaryTreeNode<K, V> {

	public BinaryTreeNode<K, V> parentNode;
	public K key;
	public V value;
	public BinaryTreeNode<K, V> leftChild;
	public BinaryTreeNode<K, V> rightChild;

	@Override
	public String toString() {
		return "BinaryTreeNode [key=" + key + ", value=" + value + ", leftChild=" + leftChild + ", rightChild="
				+ rightChild + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((leftChild == null) ? 0 : leftChild.hashCode());
		result = prime * result + ((parentNode == null) ? 0 : parentNode.hashCode());
		result = prime * result + ((rightChild == null) ? 0 : rightChild.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BinaryTreeNode<K, V> other = (BinaryTreeNode<K, V>) obj;
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
		} else if (!(parentNode == other.parentNode))
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
