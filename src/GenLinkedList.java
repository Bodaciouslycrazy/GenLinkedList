import java.util.*;
import java.lang.StringBuilder;

/* PROJECT 1
 * GENLINKEDLIST
 * 
 * Bodie Malik
 * Sun September 17
 * 
 * CS 3345.002
 * 
 * Notes:
 * Some of these function return the linked list. This is to allow for chaining methods. ex:
 * mylist.addFront(5).addFront(6).addEnd(4);
 * etc...
 * 
 */

public class GenLinkedList<T>
{
	
	private int length = 0;
	Node front;
	Node back;
	private final boolean extraPrint = true;
	
	//runs in constant time
	public GenLinkedList<T> addFront(T item) //Done
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
		
		return this;
	}
	
	//runs in constant time
	public GenLinkedList<T> addEnd(T item) //Done
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
		
		return this;
	}
	
	//runs in constant time
	public GenLinkedList<T> removeFront() throws Exception //Done
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
		
		return this;
	}
	
	//runs in O(N)
	public GenLinkedList<T> removeEnd() throws Exception //Done
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
		
		return this;
	}
	
	public void set(int pos, T item) throws Exception //Done
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
	
	public T get(int pos) throws Exception //Done
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
	public void swap(int pos1, int pos2) throws Exception //Done
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
	
	public void shift(int amt) throws Exception //Done
	{
		if(Math.abs(amt) >= length)
		{
			throw new Exception("Can't shift larger than the list! (Modulus maybe?)");
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
	
	public void removeMatching(T item) //Done
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
	
	public void erase(int pos, int num) throws Exception //Done
	{
		if(num <= 0 || pos < 0)
		{
			throw new Exception("Invalid input.");
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
	
	public void insertList( List<T> list, int pos) throws Exception //Done
	{
		if(pos > length)
		{
			throw new Exception("Index out of bounds.");
		}
		else if(list.size() <= 0)
		{
			return;
		}

		
		Node inserta = new Node(list.get(0));
		Node insertb = inserta;
		int listPos = 1;
		while(listPos < list.size())
		{
			insertb.setNext( new Node(list.get(listPos)));
			insertb = insertb.getNext();
			listPos++;
		}
		
		//inserta is pointing at the first of the new nodes, insertb is pointing at the last of the new nodes
		
		//lots of special cases
		if(length == 0)
		{
			front = inserta;
			back = insertb;
		}
		else if(pos == 0)
		{
			insertb.setNext(front);
			front = inserta;
		}
		else if(pos == length)
		{
			back.setNext(inserta);
			back = insertb;
		}
		else
		{
			//find the node at pos and pos+1
			Node n0 = front;
			for(int i = 0; i < pos; i++)
			{
				n0 = n0.getNext();
			}
			Node n1 = n0.getNext();
			
			n0.setNext(inserta);
			insertb.setNext(n1);
		}
		
		length += list.size();
	}
	
	
	
	
	//************Other Methods***************
	
	
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		
		if(length == 0)
		{
			sb.append("[ ]");
		}
		else
		{
			sb.append("[ ");
			
			Node n = front;
			
			for(int i = 0; i < length; i++)
			{
				sb.append(n.get());
				sb.append(" ");
				n = n.getNext();
			}
			
			sb.append("]");
		}
		
		if(extraPrint) //prints extra information about where the front and end are pointing to, and the length.
		{
			String f = "null";
			String b = "null";
			
			if(front != null)
				f = front.get().toString();
			if(back != null)
				b = back.get().toString();
			
			sb.append(" F: ").append(f).append(" B: ").append(b).append(" L: ").append(length);
		}
		
		return sb.toString();
	}
	
	public void clear()
	{
		length = 0;
		front = null;
		back = null;
	}
	
	public static void main(String[] args) throws Exception
	{
		System.out.println("Welcome to Bodie's GenLinkedList!");
		System.out.println("There are some things to note about this implementation:");
		System.out.println("1 - some of the basic adding/removing commands return a reference of the list. This is to allow for chaining methods.");
		System.out.println("2 - the toString() method will add some extra things to the string. In addition to printing the list, it also prints the front (F) back (B) and length (L) of the list to help understand what is happening.");
		System.out.println("\tYou can change this by turning the value \"extraPrint\" to false.\n");
		System.out.println("*****Testing GenLinkedList*****");
		GenLinkedList<Integer> il = new GenLinkedList<Integer>();
		
		//Add Front
		System.out.println("Testing addFront");
		for(int i = 0; i < 10; i++)
		{
			il.addFront((int)(Math.random() * 10));
		}
		System.out.println(il);
		
		//Add end
		System.out.println("Testing addEnd");
		for(int i = 0; i < 10; i++)
		{
			il.addEnd((int)(Math.random() * 10 + 10));
		}
		System.out.println(il);
		
		//remove front
		System.out.println("Testing removeFront");
		for(int i = 0; i < 20; i++)
		{
			il.removeFront();
		}
		System.out.println(il);
		
		//remove back
		System.out.println("Adding some elements for more testing");
		il.addEnd(3);
		il.addEnd(4);
		il.addEnd(5);
		System.out.println(il);
		System.out.println("Testing removeEnd");
		il.removeEnd();
		il.removeEnd();
		il.removeEnd();
		System.out.println(il);
		
		//error testing
		System.out.println("Error Testing");
		try
		{
			il.removeFront();
		}
		catch(Exception e)
		{
			System.out.println("Correct Exception caught: " + e.toString());
		}
		try
		{
			il.removeEnd();
		}
		catch(Exception e)
		{
			System.out.println("Correct Exception caught: " + e.toString());
		}
		
		//test insert list
		System.out.println("Testing insertList");
		ArrayList<Integer> al = new ArrayList<Integer>();
		for(int i = 0; i < 10; i++)
		{
			al.add(i);
		}
		
		il.insertList(al, 0);
		System.out.println(il);
		
		al = new ArrayList<Integer>();
		for(int i = 0; i < 3; i++)
		{
			al.add(i+10);
		}
		
		il.insertList(al, 0);
		System.out.println(il);
		il.insertList(al, 5);
		System.out.println(il);
		il.insertList(al, 16);
		System.out.println(il);
		
		//test get and set
		System.out.println("Testing get and set");
		System.out.println("The third element is: " + il.get(2));
		System.out.println("Setting third element to 30.");
		il.set(2, 30);
		System.out.println(il);
		
		//test swap
		System.out.println("Testing swap");
		System.out.println(il);
		il.swap(0, 1);
		System.out.println(il);
		il.swap(3,2);
		System.out.println(il);
		il.swap(4,4);
		System.out.println(il);
		
		il.clear();
		System.out.println("Testing shift");
		for(int i = 0; i < 10; i++)
		{
			il.addEnd(i);
		}
		System.out.println(il);
		il.shift(3);
		System.out.println(il);
		il.shift(-2);
		System.out.println(il);
		
		try
		{
			il.shift(11);
		}
		catch(Exception e)
		{
			System.out.println("Correct Exception caught: " + e.toString());
		}
		
		System.out.println("Testing removeMatching");
		il.addEnd(7).addEnd(1).addEnd(11).addEnd(4);
		il.addFront(11);
		System.out.println(il);
		il.removeMatching(7);
		System.out.println(il);
		il.removeMatching(11);
		System.out.println(il);
		il.removeMatching(4);
		System.out.println(il);
		il.removeMatching(1);
		System.out.println(il);
		il.removeMatching(50);
		System.out.println(il);
		
		System.out.println("Testing erase");
		il.clear();
		for(int i = 0; i < 20; i++)
		{
			il.addEnd(i);
		}
		System.out.println(il);
		
		il.erase(0, 3);
		System.out.println(il);
		il.erase(14, 3);
		System.out.println(il);
		il.erase(5, 3);
		System.out.println(il);
		il.erase(0, 10);
		System.out.println(il);
		
		
		try
		{
			il.erase(5, 1);
		}
		catch(Exception e)
		{
			System.out.println("Correct Exception caught: " + e.toString());
		}
		try
		{
			il.erase(0, 2);
		}
		catch(Exception e)
		{
			System.out.println("Correct Exception caught: " + e.toString());
		}
		try
		{
			il.erase(-1, 1);
		}
		catch(Exception e)
		{
			System.out.println("Correct Exception caught: " + e.toString());
		}
		try 
		{
			il.erase(0, -1);
		}
		catch(Exception e)
		{
			System.out.println("Correct Exception caught: " + e.toString());
		}
		
		il.erase(0, 1);
		System.out.println(il);
		
		System.out.println("*****DONE TESTING*****");
		System.out.println("Feel free to add your own code to test this linked list.");
		
		
		
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



