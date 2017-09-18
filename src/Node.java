
public class Node<T> {
	private T value;
	private Node<T> next;
	
	public Node(T val)
	{
		value = val;
	}
	
	public T get()
	{
		return value;
	}
	
	public void set(T item)
	{
		value = item;
	}
	
	public Node<T> getNext()
	{
		return next;
	}
	
	public void setNext(Node<T> node)
	{
		next = node;
	}
}
