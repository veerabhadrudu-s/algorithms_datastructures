/**
 * 
 */
package com.handson.trees.multi;

import com.handson.trees.multi.Two_Four_Tree_Node.Entry;

/**
 * @author sveera
 *
 */
public class Two_Four_Tree_InorderTraversal {

	public String travel(Two_Four_Tree_Node two_Four_Tree_Node) {
		String result = travelRecursively(two_Four_Tree_Node);
		return result != null && !result.isEmpty() ? result.substring(0, result.length() - 1) : null;
	}

	private String travelRecursively(Two_Four_Tree_Node two_Four_Tree_Node) {
		if (two_Four_Tree_Node == null)
			return null;
		Entry[] keys = two_Four_Tree_Node.keys;
		Two_Four_Tree_Node[] children = two_Four_Tree_Node.children;
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
