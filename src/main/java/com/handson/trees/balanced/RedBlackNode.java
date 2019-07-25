/**
 * 
 */
package com.handson.trees.balanced;

import com.handson.trees.BinaryTreeNode;

/**
 * @author sveera
 *
 */
public class RedBlackNode<K, V> extends BinaryTreeNode<K, V> {

	public Colour colour = Colour.E;

	enum Colour {
		R, B, E
	}

}
