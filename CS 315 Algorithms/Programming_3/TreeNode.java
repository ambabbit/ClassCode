public class TreeNode<K extends Comparable<K>,T>  {
	
	public K key;
	public T info;
	
	public TreeNode<K,T> left;
	public TreeNode<K,T> right;
	

	public TreeNode(K key, T info)
	{
		this.key = key;
		this.info = info;
		left = right = null;
	}
	
	public TreeNode(K key, T info, 
			          TreeNode<K,T> left, 
			          TreeNode<K,T> right)
	{
		this(key, info);
		this.left = left;
		this.right = right;
	}
	
	public String toString()
	{
		return key.toString() + "=>" + info.toString();
	}
	
	public static void main(String[] args) 
	{
		TreeNode<String, String> t = new TreeNode<String, String>("blah", "blahblah");
		System.out.println(t);
	}

}