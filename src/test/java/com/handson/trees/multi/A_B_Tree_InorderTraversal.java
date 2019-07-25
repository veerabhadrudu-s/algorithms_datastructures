/**
 * 
 */
package com.handson.trees.multi;

import com.handson.trees.multi.A_B_TreeNode.Entry;

/**
 * @author sveera
 *
 */
public class A_B_Tree_InorderTraversal {

	public String travel(A_B_TreeNode a_b_TreeNode) {
		String result = travelRecursively(a_b_TreeNode);
		return result != null && !result.isEmpty() ? result.substring(0, result.length() - 1) : null;
	}

	private String travelRecursively(A_B_TreeNode a_b_TreeNode) {
		if (a_b_TreeNode == null)
			return null;
		Entry[] keys = a_b_TreeNode.keys;
		A_B_TreeNode[] children = a_b_TreeNode.children;
		StringBuffer stringBuffer = new StringBuffer();
		int index = 0;
		for (; index < keys.length && keys[index] != null; index++) {
			String childValue = travelRecursively(children[index]);
			if (childValue != null)
				stringBuffer.append(childValue);
			stringBuffer.append(String.valueOf(keys[index].key)).append(",");
		}
		String childValue = travelRecursively(children[index]);
		if (childValue != null)
			stringBuffer.append(childValue);
		return stringBuffer.toString();
	}
}
