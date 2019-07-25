/**
 * 
 */
package com.handson.trees.multi;

/**
 * @author sveera
 *
 */
public class Two_Four_Tree_Node {

	public Two_Four_Tree_Node parent;
	public int noOfExistingKeysInNode;
	public Entry[] keys = new Entry[3];
	public Two_Four_Tree_Node[] children = new Two_Four_Tree_Node[4];

	public class Entry {
		public Object key;
		public Object value;

		@Override
		public String toString() {
			return "" + key;
		}
	}
}
