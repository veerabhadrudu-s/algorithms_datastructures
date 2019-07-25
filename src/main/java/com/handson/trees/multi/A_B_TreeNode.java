/**
 * 
 */
package com.handson.trees.multi;

/**
 * @author sveera
 *
 */
public class A_B_TreeNode {

	public A_B_TreeNode parent;
	public int noOfExistingKeysInNode = 0;
	public final Entry[] keys;
	public final A_B_TreeNode[] children;

	public A_B_TreeNode(int childrenSize) {
		super();
		this.keys = new Entry[childrenSize - 1];
		this.children = new A_B_TreeNode[childrenSize];
	}

	public class Entry {
		public Object key;
		public Object value;

		@Override
		public String toString() {
			return "" + key;
		}
	}
}
