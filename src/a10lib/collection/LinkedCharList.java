package a10lib.collection;

import java.util.AbstractSequentialList;
import java.util.Deque;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A linked list class supporting primitve type char. Unfinished.Deprecated. I
 * Should not reinvent the wheel
 * 
 * @author Athensclub
 *
 */
@Deprecated
public class LinkedCharList extends AbstractSequentialList<Character> implements Deque<Character> {

    private static class Node {

	public Node(char value, Node next, Node prev) {
	    this.value = value;
	    this.next = next;
	    previous = prev;
	}

	char value;

	Node next;
	Node previous;

    }

    private class CharListIterator implements ListIterator<Character> {

	private Node current = head;

	private int index;

	@Override
	public void add(Character c) {
	    addBetween(current, current.next, c);
	}

	@Override
	public boolean hasNext() {
	    return tail == null ? true : current == tail;
	}

	@Override
	public boolean hasPrevious() {
	    return head == null ? true : current == head;
	}

	@Override
	public Character next() {
	    index++;
	    return (current = current.next).value;
	}

	@Override
	public int nextIndex() {
	    return index + 1;
	}

	@Override
	public Character previous() {
	    index--;
	    return (current = current.previous).value;
	}

	@Override
	public int previousIndex() {
	    return index - 1;
	}

	@Override
	public void remove() {
	    index--;
	    Node fallBack = current.previous;
	    removeNode(current);
	    current = fallBack;
	}

	@Override
	public void set(Character c) {
	    current.value = c;
	}

    }

    private Node head;

    private Node tail;

    private int size;

    @Override
    public void clear() {
	// make use of gc
	head = null;
	tail = null;
    }

    /**
     * Add a character at the given index.Preferred over add(int,Character)
     * 
     * @param index
     * @param c
     */
    public void addChar(int index, char c) {
	if (index == size) {
	    addChar(c);
	} else {
	    Node node = nodeAt(index);
	    addBetween(node, node.previous, c);
	}
    }

    /**
     * Link a given value between two nodes
     * 
     * @param before
     * @param after
     * @param value
     */
    private void addBetween(Node before, Node after, char value) {
	if (head == null) {

	}
	if (after == head) {
	    // add before head
	    Node node = new Node(value, head, null);
	    head.previous = node;
	    head = node;
	} else if (before == null) {
	    // add to head
	    Node node = new Node(value, head, null);
	    head = node;
	} else if (before == tail) {
	    // add after tail
	    Node next = new Node(value, null, tail);
	    tail.next = next;
	    tail = next;
	} else if (after == null) {
	    // no tail
	    Node node = new Node(value, null, before);
	    before.next = node;
	    tail = node;
	} else {
	    // add between head and tail
	    Node node = new Node(value, after, before);
	    before.next = node;
	    after.previous = node;
	}
	size++;
    }

    /**
     * Remove a node from a list
     * 
     * @param node
     * @return
     */
    private char removeNode(Node toRemove) {
	char value = toRemove.value;
	if (toRemove == head) {
	    head = toRemove.next;
	} else if (toRemove == tail) {
	    tail = toRemove.previous;
	} else {
	    Node before = toRemove.previous;
	    Node after = toRemove.next;
	    before.next = after;
	    after.previous = before;
	}
	size--;
	return value;
    }

    /**
     * Remove a character from this list and return it.Preferred over remove(int)
     * 
     * @param index
     * @return
     */
    public char removeChar(int index) {
	return removeNode(nodeAt(index));
    }

    @Override
    public Character remove(int index) {
	return removeChar(index);
    }

    /**
     * Add a character to this linked list. Preferred over add(Character).
     * 
     * @param value
     */
    public void addChar(char value) {
	if (head == null) {
	    // first element
	    head = new Node(value, null, null);
	} else if (tail == null) {
	    // second element
	    tail = new Node(value, null, head);
	} else {
	    Node next = new Node(value, null, tail);
	    tail.next = next;
	    tail = next;
	}
	size++;
    }

    /**
     * Set the character at given index with the given value and return the old
     * value.Preferred over set(int,Character)
     * 
     * @param index
     * @param value
     * @return
     */
    public char setChar(int index, char value) {
	Node node = nodeAt(index);
	char old = node.value;
	node.value = value;
	return old;
    }

    @Override
    public Character set(int index, Character value) {
	return setChar(index, value);
    }

    /**
     * Get a linked node at given index
     * 
     * @param index
     * @return
     */
    private Node nodeAt(int index) {
	if (index < 0 || index >= size) {
	    throw new IndexOutOfBoundsException("size:" + size + ", index:" + index);
	}
	Node result;
	if (index < size / 2) {
	    result = head;
	    for (int i = 0; i < index; i++, result = result.next)
		;
	} else {
	    result = tail;
	    for (int i = size - 1; i > index; i--, result = result.previous)
		;
	}
	return result;
    }

    /**
     * Get the char value at the given index.Preffered over get(int)
     * 
     * @param index
     * @return
     */
    public char getChar(int index) {
	return nodeAt(index).value;
    }

    @Override
    public Character get(int index) {
	return getChar(index);
    }

    @Override
    public int size() {
	return size;
    }

    @Override
    public ListIterator<Character> listIterator(int index) {
	CharListIterator it = new CharListIterator();
	it.current = nodeAt(index);
	return it;
    }

    /**
     * Add the character to the head of this list
     * 
     * @param c
     */
    public void addFirstChar(char c) {

    }

    @Override
    public void addFirst(Character c) {
	add(0, c);
    }

    @Override
    public void addLast(Character arg0) {
	//TODO
	//add();
    }

    @Override
    public Iterator<Character> descendingIterator() {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * Get the first element of this list and throw exception if it doesn't
     * exist.Preferred over getFirst()
     * 
     * @return
     */
    public char getFirstChar() {
	if (head == null) {
	    throw new NoSuchElementException();
	}
	return head.value;
    }

    /**
     * Get the last element of this list and throw exception if it doesn't
     * exist.Preferred over getLast()
     * 
     * @return
     */
    public char getLastChar() {
	if (tail == null) {
	    throw new NoSuchElementException();
	}
	return tail.value;
    }

    @Override
    public Character element() {
	return getFirst();
    }

    @Override
    public Character getFirst() {
	return getFirstChar();
    }

    @Override
    public Character getLast() {
	return getLastChar();
    }

    @Override
    public boolean offer(Character c) {
	return offerLast(c);
    }

    @Override
    public boolean offerFirst(Character c) {
	try {
	    addFirst(c);
	} catch (Exception e) {
	    return false;
	}
	return true;
    }

    @Override
    public boolean offerLast(Character c) {
	try {
	    addLast(c);
	} catch (Exception e) {
	    return false;
	}
	return true;
    }

    @Override
    public Character peek() {
	return peekFirst();
    }

    @Override
    public Character peekFirst() {
	if (head == null) {
	    return null;
	}
	return head.value;
    }

    @Override
    public Character peekLast() {
	if (tail == null) {
	    return null;
	}
	return tail.value;
    }

    @Override
    public Character poll() {
	return pollFirst();
    }

    @Override
    public Character pollFirst() {
	if (head == null) {
	    return null;
	}
	return remove(0);
    }

    @Override
    public Character pollLast() {
	//TODO
	return null;
    }

    @Override
    public Character pop() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void push(Character arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public Character remove() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Character removeFirst() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object c) {
	//TODO
	return false;
    }

    @Override
    public Character removeLast() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean removeLastOccurrence(Object arg0) {
	// TODO Auto-generated method stub
	return false;
    }

}
