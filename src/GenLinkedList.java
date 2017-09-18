import java.util.*;
import java.lang.StringBuilder;

/* Bodie Malik
 * Sun September 17
 * 
 * 
 * 
 */

public class GenLinkedList<T> {
	
	private int length = 0;
	Node front;
	Node back;
	
	//runs in constant time
	public void addFront(T item)
	{
		if(length == 0)
		{
			front = new Node(item);
			back = front;
		}
		else
		{
			Node temp = new Node(item, front);
			front = temp;
		}
		
		length++;
	}
	
	//runs in constant time
	public void addEnd(T item)
	{
		if(length == 0)
		{
			front = new Node(item);
			back = front;
		}
		else
		{
			Node temp = new Node(item);
			back.setNext(temp);
			back = temp;
		}
		
		length++;
	}
	
	//runs in constant time
	public void removeFront() throws Exception
	{
		if(length <= 0)
		{
			throw new Exception("Can't remove from an empty list!");
		}
		else if(length == 1)
		{
			front = back = null;
			length--;
		}
		else
		{
			front = front.getNext();
			length--;
		}
	}
	
	//runs in O(N)
	public void removeEnd() throws Exception
	{
		if(length == 0)
		{
			throw new Exception("Can't remove from an empty list!");
		}
		else if(length == 1)
		{
			front = back = null;
			length--;
		}
		else
		{
			
			//Get the node before the back
			Node n = front;
			
			for(int i = 0; i < length - 2; i++)
			{
				n = n.getNext();
			}
			
			back = n;
			n.setNext(null);
			length--;
		}
	}
	
	public void set(int pos, T item) throws Exception
	{
		if(pos >= length)
		{
			throw new Exception("Index out of bounds.");
		}
		else
		{
			Node n = front;
			
			for(int i = 0; i < pos; i++)
			{
				n = n.getNext();
			}
			
			n.set(item);
		}
	}
	
	public T get(int pos) throws Exception
	{
		if(pos >= length )
		{
			throw new Exception("Index out of bounds.");
		}
		else
		{
			Node n = front;
			
			for(int i = 0; i < pos; i++) {
				n = n.getNext();
			}
			
			return n.get();
		}
	}
	
	
	//Swap does not move any of the nodes, but only moves the values stored in them.
	public void swap(int pos1, int pos2) throws Exception
	{
		if(pos1 >= length || pos2 >= length)
		{
			throw new Exception("Index out of bounds.");
		}
		else if(pos1 > pos2) //make sure the earlier node is pos1
		{
			//swap the values
			int temp = pos1;
			pos1 = pos2;
			pos2 = temp;
		}
		
		//find the two nodes
		Node n = front;
		Node swap1;
		Node swap2;
		
		for(int i = 0; i < pos1; i++)
		{
			n = n.getNext();
		}
		
		swap1 = n;
		
		for(int i = 0; i < pos2 - pos1; i++)
		{
			n = n.getNext();
		}
		
		swap2 = n;
		
		//Swap the values
		T temp = swap1.get();
		swap1.set(swap2.get());
		swap2.set(temp);
		
	}
	
	public void shift(int amt) throws Exception//cyclic!
	{
		if(Math.abs(amt) >= length)
		{
			throw new Exception("Can't shift larger than the list!");
		}
		
		back.setNext(front); //this makes the linked list cycle
		//linked list can easily shift forward, but have trouble going backward.
		//so, change backwards cycles to their forward counterpart
		if(amt < 0)
		{
			amt = length + amt;
		}
		
		
		//move front and back
		for(int i = 0; i < amt; i++)
		{
			front = front.getNext();
			back = back.getNext();
		}
		
		//unlink the back so that it is no longer cyclic
		back.setNext(null);
		
		
	}
	
	public void removeMatching(T item)
	{
		if(length == 0)
			return;
		
		//find the first element that does not match
		while( length > 0 && front.get() == item)
		{
			front = front.getNext();
			length--;
		}
		
		if(length > 0)
		{
			Node n = front;
			
			while(n.getNext() != null)
			{
				if(n.getNext().get() == item)
				{
					n.setNext(n.getNext().getNext());
					length--;
				}
				else
				{
					n = n.getNext();
				}
			}
			
			back = n;
		}
		else
		{
			front = back = null;
		}
	}
	
	public void erase(int pos, int num) throws Exception
	{
		if(num <= 0)
		{
			throw new Exception("Invalid number size.");
		}
		else if( pos + num - 1 >= length)
		{
			throw new Exception("Index out of bounds.");
		}
		else if(pos == 0) //if pos == 0, the front must change
		{
			Node n = front;
			
			for(int i = 0; i < num; i++)
			{
				n = n.getNext();
			}
			
			front = n;
			length -= num;
			if(length == 0)
			{
				back = front;
			}
		}
		else //the front is not changing
		{
			//we need to move the link from node pos - 1 to the node at pos + num
			Node before = front;
			Node after;
			
			for(int i = 0; i < pos - 1; i++)
			{
				before = before.getNext();
			}
			after = before.getNext();
			for(int i = 0; i < num; i++)
			{
				after = after.getNext();
			}
			
			before.setNext(after);
			if(after == null)
			{
				back = before;
			}
			length -= num;
		}
	}
	
	public void insertList( List<T> list, int pos) throws Exception
	{
		if(pos > length)
		{
			throw new Exception("Index out of bounds.");
		}

		Node begin = front;
		for(int i = 0; i < pos; i++)
		{
			begin = begin.getNext();
		}
		Node after = begin.getNext();
		
		for(int i = 0; i < list.size(); i++)
		{
			Node temp = new Node(list.get(i));
			begin.setNext(temp);
			begin = begin.getNext();
		}
		
		if(after != null )
		{
			begin.setNext(after);
		}
		else
		{
			back = begin;
		}
		
		length += list.size();
	}
	
	//Methods not part of assignment requirements
	public String toString()
	{
		
		if(length == 0)
		{
			return "[ ]";
		}
		else
		{
			StringBuilder sb = new StringBuilder();
			sb.append("[ ");
			
			Node n = front;
			
			for(int i = 0; i < length; i++)
			{
				sb.append(n.get());
				sb.append(" ");
				n = n.getNext();
			}
			
			sb.append("]");
			return sb.toString();
		}
	}
	
	public void clear()
	{
		length = 0;
		front = null;
		back = null;
	}
	
	public static void main(String[] args) throws Exception
	{
		System.out.println("*****Testing GenLinkedList*****");
		GenLinkedList<String> sl = new GenLinkedList<String>();
		System.out.println("Testing addEnd()");
		sl.addEnd("one");
		sl.addEnd("two");
		sl.addEnd("three");
		sl.addEnd("four");
		sl.addEnd("five");
		System.out.println(sl.toString());
		System.out.println("Testing addFront()");
		sl.addFront("bob");
		sl.addFront("joe");
		sl.addFront("bill");
		System.out.println(sl.toString());
		System.out.println("Testing removeFront and removeEnd");
		sl.removeFront();
		sl.removeEnd();
		System.out.println(sl.toString());
		System.out.println("Testing set");
		sl.set(5, "FOUR");
		System.out.println(sl.toString());
		System.out.println("testing get");
		System.out.println(sl.get(2));
		System.out.println("Testing swap");
		sl.swap(0, 1);
		sl.swap(3, 2);
		sl.swap(4, 4);
		System.out.println(sl.toString());
		
		System.out.println("*****INTEGER LINKED LIST*****");
		GenLinkedList<Integer> il = new GenLinkedList<Integer>();
		il.addEnd(0);
		il.addEnd(1);
		il.addEnd(2);
		il.addEnd(3);
		il.addEnd(4);
		System.out.println(il.toString());
		System.out.println("Testing erase");
		il.erase(1, 2);
		System.out.println(il);
		il.addEnd(5);
		il.addEnd(6);
		System.out.println(il);
		il.erase(2, 3);
		System.out.println(il);
		il.addEnd(7);
		System.out.println(il);
		il.erase(0, 1);
		System.out.println(il);
		il.erase(0, 2);
		System.out.println(il);
		
		System.out.println("Done testing");
	}
	

	private class Node {
		private T value;
		private Node next;
		
		public Node(T val)
		{
			value = val;
		}
		
		public Node(T val, Node p)
		{
			value = val;
			next = p;
		}
		
		public T get()
		{
			return value;
		}
		
		public void set(T item)
		{
			value = item;
		}
		
		public Node getNext()
		{
			return next;
		}
		
		public void setNext(Node node)
		{
			next = node;
		}
	}

}



