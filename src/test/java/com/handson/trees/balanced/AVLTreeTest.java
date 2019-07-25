/**
 * 
 */
package com.handson.trees.balanced;

import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.handson.trees.Comparator;
import com.handson.trees.EularTourTreeTraversal;

/**
 * @author sveera
 */
public class AVLTreeTest {

	private AVLTreeSpy<Integer, String> avlTreeSpy;

	@BeforeEach
	public void setUp() throws Exception {
		avlTreeSpy = new AVLTreeSpy<>((parentKey, keyToBEComapred) -> parentKey.equals(keyToBEComapred) ? 0
				: parentKey > keyToBEComapred ? -1 : 1, new AVLTreeInorderTraversalForValuesInAscendingOrderWithHeight());
	}

	@Test
	@DisplayName("test Search Tree With Empty Tree")
	public void testSearchTreeWithEmptyTree() {
		assertEquals(null, avlTreeSpy.search(1));
		assertEquals(null, avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test Insert With Only Root Node")
	public void testInsertWithOnlyRootNode() {
		Integer[] valuesToBeInserted = new Integer[] { 1 };
		insertValues(valuesToBeInserted);
		assertAlreadyInsertedKeys(valuesToBeInserted);
		assertEquals("1-1", avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test Insert Node in Tree of Height 1")
	public void testInsertNodeInTreeofHeight1() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 150, 50 };
		insertValues(valuesToBeInserted);
		assertAlreadyInsertedKeys(valuesToBeInserted);
		assertEquals("50-1,100-2,150-1", avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test Insert Node in Tree of Height 2")
	public void testInsertNodeInTreeofHeight2() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 150, 50, 25, 175 };
		insertValues(valuesToBeInserted);
		assertAlreadyInsertedKeys(valuesToBeInserted);
		assertEquals("25-1,50-2,100-3,150-2,175-1", avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test Insert Node to create complete binary Tree of Height 3")
	public void testInsertNodeToCreateCompleteBinaryTreeOfHeight3() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 150, 50, 75, 125, 25, 175 };
		insertValues(valuesToBeInserted);
		assertAlreadyInsertedKeys(valuesToBeInserted);
		assertEquals("25-1,50-2,75-1,100-3,125-1,150-2,175-1", avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test Insert Node to create complete binary Tree of Height 4")
	public void testInsertNodeToCreateCompleteBinaryTreeOfHeight4() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 150, 50, 75, 125, 25, 175, 13, 37, 62, 87, 113, 137, 162,
				187 };
		insertValues(valuesToBeInserted);
		assertAlreadyInsertedKeys(valuesToBeInserted);
		assertEquals("13-1,25-2,37-1,50-3,62-1,75-2,87-1,100-4,113-1,125-2,137-1,150-3,162-1,175-2,187-1",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test Insert Node to make tree height property violation and expect right rotation in tree of height 3 ")
	public void testInsertNodeToMakeTreeHeightPropertyViolationAndExpectRightRotationInTreeOfHeight3() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 50, 150, 25, 175, 13 };
		insertValues(valuesToBeInserted);
		assertAlreadyInsertedKeys(valuesToBeInserted);
		assertEquals("13-1,25-2,50-1,100-3,150-2,175-1", avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test Insert Node to make tree height property violation and expect left right rotation in tree of height 3 ")
	public void testInsertNodeToMakeTreeHeightPropertyViolationAndExpectLeftRightRotationInTreeOfHeight3() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 50, 150, 25, 175, 37 };
		insertValues(valuesToBeInserted);
		assertAlreadyInsertedKeys(valuesToBeInserted);
		assertEquals("25-1,37-2,50-1,100-3,150-2,175-1", avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test Insert Node to make tree height property violation and expect left rotation in tree of height 3 ")
	public void testInsertNodeToMakeTreeHeightPropertyViolationAndExpectLeftRotationInTreeOfHeight3() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 50, 150, 25, 175, 187 };
		insertValues(valuesToBeInserted);
		assertAlreadyInsertedKeys(valuesToBeInserted);
		assertEquals("25-1,50-2,100-3,150-1,175-2,187-1", avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test Insert Node to make tree height property violation and expect right left rotation in tree of height 3 ")
	public void testInsertNodeToMakeTreeHeightPropertyViolationAndExpectRightLeftRotationInTreeOfHeight3() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 50, 150, 25, 175, 162 };
		insertValues(valuesToBeInserted);
		assertAlreadyInsertedKeys(valuesToBeInserted);
		assertEquals("25-1,50-2,100-3,150-1,162-2,175-1", avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test Insert Node to make tree height property violation and expect right rotation at height level 3 in tree of height 4")
	public void testInsertNodeToMakeTreeHeightPropertyViolationAndExpectRightRotationAtHeightLevel3InTreeOfHeight4() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 50, 150, 25, 75, 125, 175, 13, 37, 6 };
		insertValues(valuesToBeInserted);
		assertAlreadyInsertedKeys(valuesToBeInserted);
		assertEquals("6-1,13-2,25-3,37-1,50-2,75-1,100-4,125-1,150-2,175-1", avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test Insert Node to make tree height property violation and expect left right rotation at height level 3 in tree of height 4")
	public void testInsertNodeToMakeTreeHeightPropertyViolationAndExpectLeftRightRotationAtHeightLevel3InTreeOfHeight4() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 50, 150, 25, 75, 125, 175, 13, 37, 41 };
		insertValues(valuesToBeInserted);
		assertAlreadyInsertedKeys(valuesToBeInserted);
		assertEquals("13-1,25-2,37-3,41-1,50-2,75-1,100-4,125-1,150-2,175-1", avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test Insert Node to make tree height property violation and expect left rotation at height level 3 in tree of height 4")
	public void testInsertNodeToMakeTreeHeightPropertyViolationAndExpectLeftRotationAtHeightLevel3InTreeOfHeight4() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 50, 150, 25, 75, 125, 175, 162, 187, 194 };
		insertValues(valuesToBeInserted);
		assertAlreadyInsertedKeys(valuesToBeInserted);
		assertEquals("25-1,50-2,75-1,100-4,125-1,150-2,162-1,175-3,187-2,194-1",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test Insert Node to make tree height property violation and expect right left rotation at height level 3 in tree of height 4")
	public void testInsertNodeToMakeTreeHeightPropertyViolationAndExpectRightLeftRotationAtHeightLevel3InTreeOfHeight4() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 50, 150, 25, 75, 125, 175, 162, 187, 156 };
		insertValues(valuesToBeInserted);
		assertAlreadyInsertedKeys(valuesToBeInserted);
		assertEquals("25-1,50-2,75-1,100-4,125-1,150-2,156-1,162-3,175-2,187-1",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test Insert Node to make tree height property violation and expect left rotation at root node level")
	public void testInsertNodeToMakeTreeHeightPropertyViolationAndExpectLeftRotationAtRootNodeLevel() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 50, 150, 25, 75, 125, 175, 113, 137, 162, 187, 194 };
		insertValues(valuesToBeInserted);
		assertAlreadyInsertedKeys(valuesToBeInserted);
		assertEquals("25-1,50-2,75-1,100-3,113-1,125-2,137-1,150-4,162-1,175-3,187-2,194-1",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test Insert Node to make tree height property violation and expect right left rotation at root node level")
	public void testInsertNodeToMakeTreeHeightPropertyViolationAndExpectRightLeftRotationAtRootNodeLevel() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 50, 150, 25, 75, 125, 175, 113, 137, 162, 187, 106 };
		insertValues(valuesToBeInserted);
		assertAlreadyInsertedKeys(valuesToBeInserted);
		assertEquals("25-1,50-2,75-1,100-3,106-1,113-2,125-4,137-1,150-3,162-1,175-2,187-1",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test Insert Node to make tree height property violation and expect right rotation at root node level")
	public void testInsertNodeToMakeTreeHeightPropertyViolationAndExpectRightRotationAtRootNodeLevel() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 50, 150, 25, 75, 125, 175, 13, 37, 62, 87, 6 };
		insertValues(valuesToBeInserted);
		assertAlreadyInsertedKeys(valuesToBeInserted);
		assertEquals("6-1,13-2,25-3,37-1,50-4,62-1,75-2,87-1,100-3,125-1,150-2,175-1",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test Insert Node to make tree height property violation and expect left right rotation at root node level")
	public void testInsertNodeToMakeTreeHeightPropertyViolationAndExpectLeftRightRotationAtRootNodeLevel() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 50, 150, 25, 75, 125, 175, 13, 37, 62, 87, 94 };
		insertValues(valuesToBeInserted);
		assertAlreadyInsertedKeys(valuesToBeInserted);
		assertEquals("13-1,25-2,37-1,50-3,62-1,75-4,87-2,94-1,100-3,125-1,150-2,175-1",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test find Maximum Value")
	public void test_find_Maximum_Value() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 100, 300, 500, 700, 50, 150, 250, 350, 450, 550,
				650, 750, 25, 75, 125, 225, 425, 475, 525, 575, 625, 675, 675, 725, 12, 412, 438, 462, 512, 612, 406 };
		insertValues(valuesToBeInserted);
		assertEquals(
				"12-1,25-2,50-3,75-1,100-4,125-1,150-2,200-5,225-1,250-2,300-3,350-1,400-7,406-1,412-2,425-3,438-1,450-4,462-1,475-2,"
						+ "500-5,512-1,525-2,550-3,575-1,600-6,612-1,625-2,650-3,675-1,700-4,725-1,750-2",
				avlTreeSpy.getTreeInorderTraversalData());
		assertEquals(new Integer(750), avlTreeSpy.findMaximumKeyNode().key);

	}

	@Test
	@DisplayName("test find Minimum Value")
	public void test_find_Minimum_Value() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 100, 300, 500, 700, 50, 150, 250, 350, 450, 550,
				650, 750, 25, 75, 125, 225, 425, 475, 525, 575, 625, 675, 675, 725, 12, 412, 438, 462, 512, 612, 406 };
		insertValues(valuesToBeInserted);
		assertEquals(
				"12-1,25-2,50-3,75-1,100-4,125-1,150-2,200-5,225-1,250-2,300-3,350-1,400-7,406-1,412-2,425-3,438-1,450-4,462-1,475-2,"
						+ "500-5,512-1,525-2,550-3,575-1,600-6,612-1,625-2,650-3,675-1,700-4,725-1,750-2",
				avlTreeSpy.getTreeInorderTraversalData());
		assertEquals(new Integer(12), avlTreeSpy.findMinimumKeyNode().key);

	}

	@Test
	@DisplayName("test find Successor Value")
	public void test_find_Successor_Value() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 100, 300, 500, 700, 50, 150, 250, 350, 450, 550,
				650, 750, 25, 75, 125, 225, 275, 325, 375, 775, 725, 625, 425, 12, 212, 238, 263, 312, 790, 206 };
		insertValues(valuesToBeInserted);
		assertEquals(
				"12-1,25-2,50-3,75-1,100-4,125-1,150-2,200-6,206-1,212-2,225-3,238-1,250-4,263-1,275-2,300-5,312-1,325-2,350-3,375-1,"
						+ "400-7,425-1,450-2,500-3,550-1,600-5,625-1,650-2,700-4,725-1,750-3,775-2,790-1",
				avlTreeSpy.getTreeInorderTraversalData());
		assertEquals(new Integer(425), avlTreeSpy.findSuccessorKeyNode(400).key);
		assertEquals(new Integer(400), avlTreeSpy.findSuccessorKeyNode(375).key);
		assertEquals(new Integer(25), avlTreeSpy.findSuccessorKeyNode(12).key);
		assertEquals(new Integer(790), avlTreeSpy.findSuccessorKeyNode(775).key);
		assertNull(avlTreeSpy.findSuccessorKeyNode(790));
	}

	@Test
	@DisplayName("test find Predecessor Value")
	public void test_find_Predecessor_Value() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 100, 300, 500, 700, 50, 150, 250, 350, 450, 550,
				650, 750, 25, 75, 125, 225, 425, 475, 525, 575, 625, 675, 675, 725, 12, 412, 438, 462, 512, 612, 406 };
		insertValues(valuesToBeInserted);
		assertEquals(
				"12-1,25-2,50-3,75-1,100-4,125-1,150-2,200-5,225-1,250-2,300-3,350-1,400-7,406-1,412-2,425-3,438-1,450-4,462-1,475-2,"
						+ "500-5,512-1,525-2,550-3,575-1,600-6,612-1,625-2,650-3,675-1,700-4,725-1,750-2",
				avlTreeSpy.getTreeInorderTraversalData());
		assertEquals(new Integer(350), avlTreeSpy.findPredecessorKeyNode(400).key);
		assertEquals(new Integer(575), avlTreeSpy.findPredecessorKeyNode(600).key);
		assertEquals(new Integer(400), avlTreeSpy.findPredecessorKeyNode(406).key);
		assertEquals(new Integer(425), avlTreeSpy.findPredecessorKeyNode(438).key);
		assertEquals(new Integer(700), avlTreeSpy.findPredecessorKeyNode(725).key);
		assertNull(avlTreeSpy.findPredecessorKeyNode(12));
	}

	@Test
	@DisplayName("test remove key in Empty Tree")
	public void testRemoveKeyInEmptyTree() {
		avlTreeSpy.remove(1);
		assertEquals(null, avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove key in Tree with only root element")
	public void testRemoveKeyInTreeWithOnlyRootElement() {
		avlTreeSpy.insert(100, valueOf(100));
		avlTreeSpy.remove(100);
		assertEquals(null, avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove leaf key in Tree with height 2")
	public void testRemoveLeafKeyInTreeWithHeight2() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 50, 150 };
		insertValues(valuesToBeInserted);
		avlTreeSpy.remove(50);
		assertEquals("100-2,150-1", avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove leaf key in Tree with height 3")
	public void testRemoveLeafKeyInTreeWithHeight3() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 50, 150, 25, 75, 125, 175 };
		insertValues(valuesToBeInserted);
		avlTreeSpy.remove(125);
		assertEquals("25-1,50-2,75-1,100-3,150-2,175-1", avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove leaf key in Tree with height 3 to make height balancing property violation and rotate tree")
	public void testRemoveLeafKeyInTreeWithHeight3ToMakeHeightBalancingPropertyViolationAndRotateTree() {
		Integer[] valuesToBeInserted = new Integer[] { 100, 50, 150, 25, 75, 125, 175, 13, 37, 62, 87, 187 };
		insertValues(valuesToBeInserted);
		avlTreeSpy.remove(125);
		assertEquals("13-1,25-2,37-1,50-3,62-1,75-2,87-1,100-4,150-1,175-2,187-1",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove leaf key in Tree with height 7 to make height balancing property violation and rotate tree right scenario-1")
	public void testRemoveLeafKeyInTreeWithHeight7ToMakeHeightBalancingPropertyViolationAndRotateTreeRightScenario_1() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 100, 300, 500, 700, 50, 150, 250, 350, 450, 550,
				650, 750, 25, 75, 125, 175, 225, 275, 325, 775, 725, 625, 425, 12, 38, 63, 112, 212, 790, 6 };
		insertValues(valuesToBeInserted);
		assertEquals(
				"6-1,12-2,25-3,38-1,50-4,63-1,75-2,100-5,112-1,125-2,150-3,175-1,200-6,212-1,225-2,250-3,275-1,300-4,325-1,"
						+ "350-2,400-7,425-1,450-2,500-3,550-1,600-5,625-1,650-2,700-4,725-1,750-3,775-2,790-1",
				avlTreeSpy.getTreeInorderTraversalData());
		avlTreeSpy.remove(790);
		assertEquals(
				"6-1,12-2,25-3,38-1,50-4,63-1,75-2,100-5,112-1,125-2,150-3,175-1,200-6,212-1,225-2,250-3,275-1,300-4,325-1,"
						+ "350-2,400-5,425-1,450-2,500-3,550-1,600-4,625-1,650-2,700-3,725-1,750-2,775-1",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove leaf key in Tree with height 7 to make height balancing property violation and rotate tree right scenario-2")
	public void testRemoveLeafKeyInTreeWithHeight7ToMakeHeightBalancingPropertyViolationAndRotateTreeRightScenario_2() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 100, 300, 500, 700, 50, 150, 250, 350, 450, 550,
				650, 750, 25, 75, 125, 175, 225, 275, 325, 775, 725, 625, 425, 12, 38, 63, 112, 212, 790, 6 };
		insertValues(valuesToBeInserted);
		assertEquals(
				"6-1,12-2,25-3,38-1,50-4,63-1,75-2,100-5,112-1,125-2,150-3,175-1,200-6,212-1,225-2,250-3,275-1,300-4,325-1,"
						+ "350-2,400-7,425-1,450-2,500-3,550-1,600-5,625-1,650-2,700-4,725-1,750-3,775-2,790-1",
				avlTreeSpy.getTreeInorderTraversalData());
		avlTreeSpy.remove(725);
		assertEquals(
				"6-1,12-2,25-3,38-1,50-4,63-1,75-2,100-5,112-1,125-2,150-3,175-1,200-6,212-1,225-2,250-3,275-1,300-4,325-1,"
						+ "350-2,400-5,425-1,450-2,500-3,550-1,600-4,625-1,650-2,700-3,750-1,775-2,790-1",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove leaf key in Tree with height 7 to make height balancing property violation and rotate tree left right scenario-1")
	public void testRemoveLeafKeyInTreeWithHeight7ToMakeHeightBalancingPropertyViolationAndRotateTreeLeftRightScenario_1() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 100, 300, 500, 700, 50, 150, 250, 350, 450, 550,
				650, 750, 25, 75, 125, 225, 275, 325, 375, 775, 725, 625, 425, 12, 212, 238, 263, 312, 790, 206 };
		insertValues(valuesToBeInserted);
		assertEquals(
				"12-1,25-2,50-3,75-1,100-4,125-1,150-2,200-6,206-1,212-2,225-3,238-1,250-4,263-1,275-2,300-5,312-1,325-2,350-3,375-1,"
						+ "400-7,425-1,450-2,500-3,550-1,600-5,625-1,650-2,700-4,725-1,750-3,775-2,790-1",
				avlTreeSpy.getTreeInorderTraversalData());
		avlTreeSpy.remove(790);
		assertEquals("12-1,25-2,50-3,75-1,100-4,125-1,150-2,200-5,206-1,212-2,225-3,238-1,250-4,263-1,275-2,"
				+ "300-6,312-1,325-2,350-3,375-1,400-5,425-1,450-2,500-3,550-1,600-4,625-1,650-2,700-3,725-1,750-2,775-1",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove leaf key in Tree with height 7 to make height balancing property violation and rotate tree left right scenario-2")
	public void testRemoveLeafKeyInTreeWithHeight7ToMakeHeightBalancingPropertyViolationAndRotateTreeLeftRightScenario_2() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 100, 300, 500, 700, 50, 150, 250, 350, 450, 550,
				650, 750, 25, 75, 125, 225, 275, 325, 375, 775, 725, 625, 425, 12, 212, 238, 263, 312, 790, 206 };
		insertValues(valuesToBeInserted);
		assertEquals(
				"12-1,25-2,50-3,75-1,100-4,125-1,150-2,200-6,206-1,212-2,225-3,238-1,250-4,263-1,275-2,300-5,312-1,325-2,350-3,375-1,"
						+ "400-7,425-1,450-2,500-3,550-1,600-5,625-1,650-2,700-4,725-1,750-3,775-2,790-1",
				avlTreeSpy.getTreeInorderTraversalData());
		avlTreeSpy.remove(725);
		assertEquals("12-1,25-2,50-3,75-1,100-4,125-1,150-2,200-5,206-1,212-2,225-3,238-1,250-4,263-1,275-2,"
				+ "300-6,312-1,325-2,350-3,375-1,400-5,425-1,450-2,500-3,550-1,600-4,625-1,650-2,700-3,750-1,775-2,790-1",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove leaf key in Tree with height 7 to make height balancing property violation and rotate tree right left scenario-1")
	public void testRemoveLeafKeyInTreeWithHeight7ToMakeHeightBalancingPropertyViolationAndRotateTreeRightLeftScenario_1() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 100, 300, 500, 700, 50, 150, 250, 350, 450, 550,
				650, 750, 25, 75, 125, 225, 425, 475, 525, 575, 625, 675, 675, 725, 12, 412, 438, 462, 512, 612, 406 };
		insertValues(valuesToBeInserted);
		assertEquals(
				"12-1,25-2,50-3,75-1,100-4,125-1,150-2,200-5,225-1,250-2,300-3,350-1,400-7,406-1,412-2,425-3,438-1,450-4,462-1,475-2,"
						+ "500-5,512-1,525-2,550-3,575-1,600-6,612-1,625-2,650-3,675-1,700-4,725-1,750-2",
				avlTreeSpy.getTreeInorderTraversalData());
		avlTreeSpy.remove(12);
		assertEquals(
				"25-1,50-2,75-1,100-3,125-1,150-2,200-4,225-1,250-2,300-3,350-1,400-5,406-1,412-2,425-3,438-1,450-4,462-1,475-2,"
						+ "500-6,512-1,525-2,550-3,575-1,600-5,612-1,625-2,650-3,675-1,700-4,725-1,750-2",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove leaf key in Tree with height 7 to make height balancing property violation and rotate tree right left scenario-2")
	public void testRemoveLeafKeyInTreeWithHeight7ToMakeHeightBalancingPropertyViolationAndRotateTreeRightLeftScenario_2() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 100, 300, 500, 700, 50, 150, 250, 350, 450, 550,
				650, 750, 25, 75, 125, 225, 425, 475, 525, 575, 625, 675, 675, 725, 12, 412, 438, 462, 512, 612, 406 };
		insertValues(valuesToBeInserted);
		assertEquals(
				"12-1,25-2,50-3,75-1,100-4,125-1,150-2,200-5,225-1,250-2,300-3,350-1,400-7,406-1,412-2,425-3,438-1,450-4,462-1,475-2,"
						+ "500-5,512-1,525-2,550-3,575-1,600-6,612-1,625-2,650-3,675-1,700-4,725-1,750-2",
				avlTreeSpy.getTreeInorderTraversalData());
		avlTreeSpy.remove(75);
		assertEquals(
				"12-1,25-2,50-1,100-3,125-1,150-2,200-4,225-1,250-2,300-3,350-1,400-5,406-1,412-2,425-3,438-1,450-4,462-1,475-2,"
						+ "500-6,512-1,525-2,550-3,575-1,600-5,612-1,625-2,650-3,675-1,700-4,725-1,750-2",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove root having single leaf")
	public void testRemoveRootHavingSingleLeaf() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200 };
		insertValues(valuesToBeInserted);
		avlTreeSpy.remove(400);
		assertEquals("200-1", avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove node having Leaf in tree of height 3 scenario-1")
	public void testRemoveNodeHavingLeafInTreeOfHeight_3_Scenario_1() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 700 };
		insertValues(valuesToBeInserted);
		avlTreeSpy.remove(600);
		assertEquals("200-1,400-2,700-1", avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove node having Leaf in tree of height 3 scenario-2")
	public void testRemoveNodeHavingLeafInTreeOfHeight_3_Scenario_2() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 700, 100 };
		insertValues(valuesToBeInserted);
		avlTreeSpy.remove(600);
		assertEquals("100-1,200-2,400-3,700-1", avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove node having leaf key in Tree with height 7 to make height balancing property violation and rotate tree right scenario-1")
	public void testRemoveNodeHavingLeafKeyInTreeWithHeight7ToMakeHeightBalancingPropertyViolationAndRotateTreeLeftScenario_1() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 100, 300, 500, 700, 50, 150, 250, 350, 450, 550,
				650, 750, 25, 75, 125, 175, 225, 275, 325, 775, 725, 625, 425, 12, 38, 63, 112, 212, 790, 6 };
		insertValues(valuesToBeInserted);
		assertEquals(
				"6-1,12-2,25-3,38-1,50-4,63-1,75-2,100-5,112-1,125-2,150-3,175-1,200-6,212-1,225-2,250-3,275-1,300-4,325-1,"
						+ "350-2,400-7,425-1,450-2,500-3,550-1,600-5,625-1,650-2,700-4,725-1,750-3,775-2,790-1",
				avlTreeSpy.getTreeInorderTraversalData());
		avlTreeSpy.remove(775);
		assertEquals(
				"6-1,12-2,25-3,38-1,50-4,63-1,75-2,100-5,112-1,125-2,150-3,175-1,200-6,212-1,225-2,250-3,275-1,300-4,325-1,"
						+ "350-2,400-5,425-1,450-2,500-3,550-1,600-4,625-1,650-2,700-3,725-1,750-2,790-1",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove node having leaf in Tree with height 7 to make height balancing property violation and rotate tree right scenario-2")
	public void testRemoveNodeHavingLeafInTreeWithHeight7ToMakeHeightBalancingPropertyViolationAndRotateTreeLeftScenario_2() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 100, 300, 500, 700, 50, 150, 250, 350, 450, 550,
				650, 750, 25, 75, 125, 225, 275, 325, 375, 775, 725, 625, 425, 12, 212, 238, 263, 312, 790, 206 };
		insertValues(valuesToBeInserted);
		assertEquals(
				"12-1,25-2,50-3,75-1,100-4,125-1,150-2,200-6,206-1,212-2,225-3,238-1,250-4,263-1,275-2,300-5,312-1,325-2,350-3,375-1,"
						+ "400-7,425-1,450-2,500-3,550-1,600-5,625-1,650-2,700-4,725-1,750-3,775-2,790-1",
				avlTreeSpy.getTreeInorderTraversalData());
		avlTreeSpy.remove(775);
		assertEquals("12-1,25-2,50-3,75-1,100-4,125-1,150-2,200-5,206-1,212-2,225-3,238-1,250-4,263-1,275-2,"
				+ "300-6,312-1,325-2,350-3,375-1,400-5,425-1,450-2,500-3,550-1,600-4,625-1,650-2,700-3,725-1,750-2,790-1",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove node having leaf in Tree with height 7 to make height balancing property violation and rotate tree right scenario-3")
	public void testRemoveNodeHavingLeafInTreeWithHeight7ToMakeHeightBalancingPropertyViolationAndRotateTreeLeftScenario_3() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 100, 300, 500, 700, 50, 150, 250, 350, 450, 550,
				650, 750, 25, 75, 125, 175, 225, 275, 325, 425, 475, 525, 625, 412, 212, 112, 62, 38, 12, 6 };
		insertValues(valuesToBeInserted);
		assertEquals(
				"6-1,12-2,25-3,38-1,50-4,62-1,75-2,100-5,112-1,125-2,150-3,175-1,200-6,212-1,225-2,250-3,275-1,300-4,325-1,350-2,"
						+ "400-7,412-1,425-2,450-3,475-1,500-4,525-1,550-2,600-5,625-1,650-2,700-3,750-1",
				avlTreeSpy.getTreeInorderTraversalData());
		avlTreeSpy.remove(550);
		assertEquals(
				"6-1,12-2,25-3,38-1,50-4,62-1,75-2,100-5,112-1,125-2,150-3,175-1,200-6,212-1,225-2,250-3,275-1,300-4,325-1,"
						+ "350-2,400-5,412-1,425-2,450-3,475-1,500-2,525-1,600-4,625-1,650-2,700-3,750-1",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove node having leaf in Tree with height 7 to make height balancing property violation and rotate tree right scenario-3")
	public void testRemoveNodeHavingLeafInTreeWithHeight7ToMakeHeightBalancingPropertyViolationAndRotateTreeLeftScenario_4() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 100, 300, 500, 700, 50, 150, 250, 350, 450, 550,
				650, 750, 25, 75, 125, 175, 225, 275, 325, 425, 475, 525, 625, 462, 212, 112, 62, 38, 12, 6 };
		insertValues(valuesToBeInserted);
		assertEquals(
				"6-1,12-2,25-3,38-1,50-4,62-1,75-2,100-5,112-1,125-2,150-3,175-1,200-6,212-1,225-2,250-3,275-1,300-4,325-1,350-2,"
						+ "400-7,425-1,450-3,462-1,475-2,500-4,525-1,550-2,600-5,625-1,650-2,700-3,750-1",
				avlTreeSpy.getTreeInorderTraversalData());
		avlTreeSpy.remove(550);
		assertEquals(
				"6-1,12-2,25-3,38-1,50-4,62-1,75-2,100-5,112-1,125-2,150-3,175-1,200-6,212-1,225-2,250-3,275-1,300-4,325-1,"
						+ "350-2,400-5,425-1,450-2,462-1,475-3,500-2,525-1,600-4,625-1,650-2,700-3,750-1",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove root node having 2 child leaf")
	public void testRemoveRootNodeHaving2ChildLeaf() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600 };
		insertValues(valuesToBeInserted);
		avlTreeSpy.remove(400);
		assertEquals("200-1,600-2", avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove root node having one leaf and one subtree")
	public void testRemoveRootNodeHavingOneLeafAndOneSubtree() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 700 };
		insertValues(valuesToBeInserted);
		avlTreeSpy.remove(400);
		assertEquals("200-1,600-2,700-1", avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove root node of tree having height 4")
	public void testRemoveRootNodeOfTreeHavingHeight_4() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 100, 500, 700, 550 };
		insertValues(valuesToBeInserted);
		avlTreeSpy.remove(400);
		assertEquals("100-1,200-2,500-3,550-1,600-2,700-1", avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove root node with tree of height 7 to make height balancing property violation and rotate tree right scenario-1")
	public void testRemoveRootNodeWithTreeOfHeight_7_ToMakeHeightBalancingPropertyViolationAndRotateTreeRightScenario_1() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 100, 300, 500, 700, 50, 150, 250, 350, 450, 550,
				650, 750, 25, 75, 125, 175, 225, 275, 325, 425, 475, 525, 625, 412, 212, 112, 62, 38, 12, 6 };
		insertValues(valuesToBeInserted);
		assertEquals(
				"6-1,12-2,25-3,38-1,50-4,62-1,75-2,100-5,112-1,125-2,150-3,175-1,200-6,212-1,225-2,250-3,275-1,300-4,325-1,350-2,"
						+ "400-7,412-1,425-2,450-3,475-1,500-4,525-1,550-2,600-5,625-1,650-2,700-3,750-1",
				avlTreeSpy.getTreeInorderTraversalData());
		avlTreeSpy.remove(400);
		assertEquals(
				"6-1,12-2,25-3,38-1,50-4,62-1,75-2,100-5,112-1,125-2,150-3,175-1,200-6,212-1,225-2,250-3,275-1,300-4,325-1,350-2,"
						+ "412-5,425-1,450-2,475-1,500-3,525-1,550-2,600-4,625-1,650-2,700-3,750-1",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	@Test
	@DisplayName("test remove internal node having 2 child subtrees with tree of height 7 to make height balancing property violation and rotate tree right scenario-2")
	public void testRemoveRootNodeWithTreeOfHeight_7_ToMakeHeightBalancingPropertyViolationAndRotateTreeRightScenario_2() {
		Integer[] valuesToBeInserted = new Integer[] { 400, 200, 600, 100, 300, 500, 700, 50, 150, 250, 350, 450, 550,
				650, 750, 25, 75, 125, 175, 225, 275, 325, 425, 475, 525, 625, 412, 212, 112, 62, 38, 12, 6 };
		insertValues(valuesToBeInserted);
		assertEquals(
				"6-1,12-2,25-3,38-1,50-4,62-1,75-2,100-5,112-1,125-2,150-3,175-1,200-6,212-1,225-2,250-3,275-1,300-4,325-1,350-2,"
						+ "400-7,412-1,425-2,450-3,475-1,500-4,525-1,550-2,600-5,625-1,650-2,700-3,750-1",
				avlTreeSpy.getTreeInorderTraversalData());
		avlTreeSpy.remove(450);
		assertEquals(
				"6-1,12-2,25-3,38-1,50-4,62-1,75-2,100-5,112-1,125-2,150-3,175-1,200-6,212-1,225-2,250-3,275-1,300-4,325-1,350-2,"
						+ "400-5,412-1,425-2,475-1,500-3,525-1,550-2,600-4,625-1,650-2,700-3,750-1",
				avlTreeSpy.getTreeInorderTraversalData());
	}

	private void insertValues(Integer[] valuesToBeInserted) {
		for (Integer valueToBeInserted : valuesToBeInserted)
			avlTreeSpy.insert(valueToBeInserted, valueOf(valueToBeInserted));
	}

	private void assertAlreadyInsertedKeys(Integer[] valuesToBeInserted) {
		for (Integer valueToBeSearched : valuesToBeInserted)
			assertEquals(Integer.toString(valueToBeSearched), avlTreeSpy.search(valueToBeSearched));
	}

	static class AVLTreeSpy<K, V> extends AVLTree<K, V> {

		private final EularTourTreeTraversal<String> eularTourTreeTraversal;

		public AVLTreeSpy(Comparator<K> comparator, EularTourTreeTraversal<String> eularTourTreeTraversal) {
			super(comparator);
			this.eularTourTreeTraversal = eularTourTreeTraversal;
		}

		public String getTreeInorderTraversalData() {
			return eularTourTreeTraversal.treeTraversal(rootNode);
		}
	}

}
