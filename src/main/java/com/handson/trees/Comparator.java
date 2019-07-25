/**
 * 
 */
package com.handson.trees;

/**
 * @author sveera
 *
 */
public interface Comparator<K> {
	int compare(K parentNodeKey, K childNodeKey);
}
