/**
 * 
 */
package com.handson.trees.multi;

import com.handson.trees.Comparator;

/**
 * @author sveera
 *
 */
public class A_B_TreeSpy<K, V> extends A_B_Tree<K, V> {

	private final A_B_Tree_InorderTraversal a_b_Tree_InorderTraversal;
	private final A_B_Tree_Validator a_b_Tree_Validator;

	public A_B_TreeSpy(Comparator<K> comparator, int minimumChildren, int maxChildren) {
		super(comparator, minimumChildren, maxChildren);
		a_b_Tree_InorderTraversal = new A_B_Tree_InorderTraversal();
		a_b_Tree_Validator = new A_B_Tree_Validator(minimumChildren - 1);
	}

	public String getInOrderTraversalValues() {
		a_b_Tree_Validator.validate(rootNode);
		return a_b_Tree_InorderTraversal.travel(rootNode);
	}
}
