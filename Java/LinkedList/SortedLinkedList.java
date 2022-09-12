package listClasses;

import java.util.*;

import listClasses.BasicLinkedList.Node;

/**
 * Implements a generic sorted list using a provided Comparator. It extends
 * BasicLinkedList class.
 * 
 * @author Dept of Computer Science, UMCP
 * 
 */

public class SortedLinkedList<T> extends BasicLinkedList<T> {
	private Comparator<T> comparator;

	public SortedLinkedList(Comparator<T> comparator) {
		super();
		this.comparator = comparator;
	}// Constructor

	public SortedLinkedList<T> add(T element) {
		Node newNode = new Node(element);
		Node curHead = head;

		if (head == null) {
			head = newNode;
		} // adding initial element

		while (curHead != null) {

			if (curHead.next == null) {// element is either first or last

				if (comparator.compare(head.data, element) > 0) {// element is first
					newNode.next = head;
					head = newNode;
				} else if (comparator.compare(head.data, element) < 0) {// element is last
					newNode.next = curHead.next;
					curHead.next = newNode;
				} else if (comparator.compare(head.data, element) == 0) {// duplicate
					newNode.next = curHead.next;
					curHead.next = newNode;
				}
				return this;
			} else if (comparator.compare(curHead.data, element) < 0
					&& comparator.compare(curHead.next.data, element) > 0) {// element inserts mid-list
				newNode.next = curHead.next;
				curHead.next = newNode;
				return this;
			} else if (comparator.compare(curHead.data, element) == 0) {// element is duplicate
				newNode.next = curHead.next;
				curHead.next = newNode;
				return this;
			} else {
				curHead = curHead.next;// cycle through list
			}
		} // while

		return this;
	}// add

	public SortedLinkedList<T> remove(T target) {
		super.remove(target, comparator);

		return this;
	}// remove

	public BasicLinkedList<T> addToEnd(T data) {
		throw new UnsupportedOperationException("Invalid operation for sorted list.");
	}// addToEnd

	public BasicLinkedList<T> addToFront(T data) {
		throw new UnsupportedOperationException("Invalid operation for sorted list.");
	}// addToFront

}