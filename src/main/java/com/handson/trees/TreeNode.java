/**
 * 
 */
package com.handson.trees;

/**
 * @author sveera
 *
 */
public class TreeNode<K, V> {

	public TreeNode<K, V> parentNode;
	public K key;
	public V value;
	public TreeNode<K, V> leftChild;
	public TreeNode<K, V>[] rightChildren;

}
