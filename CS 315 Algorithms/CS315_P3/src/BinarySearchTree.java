import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class BinarySearchTree<K extends Comparable<K>, T> {

	TreeNode<K,T> root;
		
	public BinarySearchTree()
	{
		root = null;
	}
	
	/////////////////////////////////////////////////////
	//  Retrieval Methods
	
	public T search(K key)
	{
		return search(key,root);
	}
	
	private T search(K key, TreeNode<K,T> cur)
	{
		if (cur == null) return null;
		else {
			if (key.equals(cur.key)) return cur.info;
			if (key.compareTo(cur.key)< 0) return search(key, cur.left);
			else return search(key, cur.right);
		}
	}
	

	
	
	/////////////////////////////////////////////////////
	//  Insert Methods
	
	public void insert(K key, T info)	
	{
	
		if (root == null) {
			root = new TreeNode<K,T>(key, info);
			return;
		}
		
		TreeNode<K,T> cur = root;
		TreeNode<K,T> prev = null;
		
		while (cur != null) {
			prev = cur;
			
			if (key.equals(cur.key))
				return; //Duplicate - quit
			else if (key.compareTo(cur.key) <= 0) 
				cur = cur.left;
			else
				cur = cur.right;			
		}

		if (key.compareTo(prev.key) < 0) {
			prev.left = new TreeNode<K,T>(key, info);
		}
		else {
			prev.right = new TreeNode<K,T>(key, info);
		}
	}

	/////////////////////////////////////////////////////
	//  Delete Methods

	
	public void delete(K key)
	{
		deleteByCopy(key);
	}
	
	private void deleteByCopy(K key)
	{		
		//////////////////////
		//Find Node
		TreeNode<K,T> prev=null, cur = root;
		while (cur != null && !cur.key.equals(key)) {
			prev = cur;
			if (key.compareTo(cur.key) <= 0) 
				cur = cur.left;
			else
				cur = cur.right;			
		}
		
		if (cur == null) return;
		
		//////////////////////
		//  Handle Copy from Immediate Predecessor
		
		TreeNode<K,T> node = cur;
		
		//No left child
		if (cur.left == null)
			 node = cur.right;
		
		//No right child
		else if (cur.right == null)
			node = cur.left;
		
		//If both children - then delete by copy
		else {
			//Find Right Most of Left Subtree
			
			TreeNode<K,T> tmp = cur.left, 
			              previous = cur;
			
			while(tmp.right != null) {
				previous = tmp;
				tmp = tmp.right;
			}
			
			//Copy value into node to be deleted.
			cur.key = tmp.key;
			cur.info = tmp.info;
			
			//Delete copied node
			if (previous == cur) 
				previous.left = tmp.left;			
			else previous.right = tmp.left;
		}	
		

		//Update the appropriate "parent" node of the deleted
		if (cur == root) 
			root = node;			
		else if (cur == prev.left)
			prev.left = node;
			
		else
			prev.right = node;			
	}
	
		
	///////////////////////////////////
	//  Traversal
		
	public void inorderTraversal()
	{
		inorderTraversal(root);
	}
	
	private void inorderTraversal(TreeNode<K,T> cur)
	{
		if (cur == null) return;
		
		inorderTraversal(cur.left);
		System.out.println(cur.info);
		inorderTraversal(cur.right);		
	}
	
	public void preorderTraversal()
	{
		preorderTraversal(root);
	}
	
	private void preorderTraversal(TreeNode<K,T> cur)
	{
		if (cur == null) return;
		
		System.out.println(cur.info);
		preorderTraversal(cur.left);		
		preorderTraversal(cur.right);
	}
	
	public void postorderTraversal()
	{
		postorderTraversal(root);
	}
	
	private void postorderTraversal(TreeNode<K,T> cur)
	{
		if (cur == null) return;
		
		postorderTraversal(cur.left);		
		postorderTraversal(cur.right);
		System.out.println(cur.info);		
	}

	@SuppressWarnings("unchecked")
	public void breadthFirstTraversal()
	{
		Queue<Object> queue = new LinkedBlockingQueue<Object>();
		queue.add(root);
		
		TreeNode<K,T> cur;
		
		while (!queue.isEmpty()) {			
			cur = (TreeNode<K,T>) queue.remove();			
			System.out.println(cur.info);
			
			if (cur.left != null)
				queue.add(cur.left);
			if (cur.right != null)
				queue.add(cur.right);
		}		
	}
	
	////////////////////////////////////////////////////
	//  Find Properties of Tree
	
	
	public int getHeight()
	{
		return getHeight(root);
	}
	
	public int getHeight(TreeNode<K,T> cur)
	{
		if (cur == null) return 0;
		else {
			return 1 + max(getHeight(cur.left),getHeight(cur.right));
		}			
	}

	private int max(int x, int y)
	{
		if (x > y) return x;
		else return y;
	}

	//----------------------------------------
	
	
	public boolean isFull()
	{
		return isFull(root);
	}
	
	private boolean isFull(TreeNode<K,T> cur)
	{
		if ((cur.left == null) && (cur.right == null))
			return true;
		else if ((cur.left == null) || (cur.right == null))
			return false;
		else
			return isFull(cur.left) && isFull(cur.right);
	}
	
	//----------------------------------------
	
	public boolean isBalanced()
	{
		return isBalanced(root);
	}
	
	private boolean isBalanced(TreeNode<K,T> cur)
	{
		if ((cur.left == null) && (cur.right == null))
			return true;
		else if (Math.abs(getBalanceFactor(cur)) <= 1)
			return isBalanced(cur.left) && isBalanced(cur.right);
		else
			return false;
	}

	private int getBalanceFactor(TreeNode<K,T> cur)
	{
		return getHeight(cur.right) - getHeight(cur.left);
	}
	
	//----------------------------------------
	
	
	public int countNodes()
	{
		return countNodes(root);
	}
	
	private int countNodes(TreeNode<K,T> cur)
	{
		if (cur == null) return 0;
		else
			return 1 + countNodes(cur.left) + countNodes(cur.right);			
	}
	
	//-----------------------------------------

	
		
	//------------------------------------------
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BinarySearchTree<Integer,Integer> t = new BinarySearchTree<Integer,Integer>();
		
		//		   0
		//		-4    4
		//		    1   6
		//            3 
		t.insert(0, 0);
		t.insert(4, 4);
		t.insert(1, 1);
		t.insert(3, 3);
		t.insert(-4, -4);
		t.insert(6,6);

		System.out.println("\n");
		
		//	Search Test
		
		System.out.println(t.search(0));
		System.out.println(t.search(1));
		System.out.println(t.search(2));
		System.out.println(t.search(3));
		System.out.println(t.search(4));
		System.out.println(t.search(-4));
	
		System.out.println("\n");
		
		//	Delete Test
		System.out.println("isBalanced=" + t.isBalanced());
		System.out.println("Height =" + t.getHeight());
		System.out.println("Nodes =" + t.countNodes());
		
		t.delete(4);

		System.out.println("Height =" + t.getHeight());
		System.out.println("Nodes =" + t.countNodes());

		//Traversal Test
		
		System.out.println("\n\nPreorder:");
		t.preorderTraversal();
		System.out.println("\n\nInorder:");
		t.inorderTraversal();
		System.out.println("\n\nPosotrder:");
		t.postorderTraversal();
		System.out.println("\n\nBreadth First:");
		t.breadthFirstTraversal();


	}

}
