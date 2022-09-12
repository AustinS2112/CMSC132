package listClasses;

import java.util.*;

public class BasicLinkedList<T> implements Iterable<T> {

	protected class Node {
		protected T data;
		protected Node next;

		protected Node(T data) {
			this.data = data;
			next = null;
		}
	}// Node class

	protected Node head, tail;

	protected int listSize;

	public BasicLinkedList() {
		head = null;
		tail = null;
		listSize = 0;
	}// defines empty linked list

	public int getSize() {
		return listSize;
	}// getSize

	public BasicLinkedList<T> addToEnd(T data) {
		Node newNode = new Node(data);

		if (head == null) {
			tail = newNode;
			head = tail;
		} else {
			tail.next = newNode;
			tail = newNode;
			newNode.next = null;
		}

		listSize++;

		return this;
	}// adds element to end of list

	public BasicLinkedList<T> addToFront(T data) {
		Node newNode = new Node(data);

		newNode.next = head;
		head = newNode;

		if (tail == null) {
			tail = newNode;
		}

		listSize++;

		return this;
	}// adds element to front of list

	public T getFirst() {
		if (listSize == 0) {
			return null;
		} else {
			return head.data;
		}
	}// gets first element

	public T getLast() {
		if (listSize == 0) {
			return null;
		} else {
			return tail.data;
		}
	}// gets last element

	public BasicLinkedList<T> remove(T targetData, Comparator<T> comparator) {
		Node curNode = head;
		Node prev = null;

		while (curNode != null) {
			if (comparator.compare(curNode.data, targetData) == 0) {
				listSize--;
				if (curNode == head) {
					head = head.next;
					prev = curNode;
					curNode = curNode.next;
				} else {
					prev.next = curNode.next;
					prev = curNode;
					curNode = curNode.next;
				}
			} else {
				prev = curNode;
				curNode = curNode.next;
			} // else
		} // while
		return this;
	}// remove

	public Iterator<T> iterator() {
		class MyIterator implements Iterator<T> {
			Node current;

			public MyIterator() {
				current = head;
			}

			public boolean hasNext() {
				return current != null;
			}

			public T next() {
				T returnVal = current.data;
				current = current.next;

				return returnVal;
			}
		}
		return new MyIterator();
	}// Iterator

	private ArrayList<T> getReverseAux(Node curHead, ArrayList<T> answer) {

		if (curHead != null) {
			getReverseAux(curHead.next, answer);
			answer.add(curHead.data);
			return answer;
		} else {
			return null;
		}
	}// Auxiliary method for recursion

	public ArrayList<T> getReverseArrayList() {
		ArrayList<T> answer = new ArrayList<T>();

		return getReverseAux(head, answer);
	}// getReverseArrayList

	private BasicLinkedList<T> getReverseListAux(Node curHead, BasicLinkedList<T> answer) {

		if (curHead != null) {
			getReverseListAux(curHead.next, answer);
			answer.addToEnd(curHead.data);
			return answer;
		} else {
			return null;
		}
	}// Aux method for getReverseList

	public BasicLinkedList<T> getReverseList() {
		BasicLinkedList<T> answer = new BasicLinkedList<T>();

		return getReverseListAux(head, answer);
	}// getReverseList

	public T retrieveFirstElement() {

		if (listSize == 0) {
			return null;
		} else {
			T data = head.data;
			head = head.next;
			listSize--;
			return data;
		}
	}// retrieveFirstElement

	public T retrieveLastElement() {

		if (listSize == 0) {
			return null;
		} else {
			T data = tail.data;
			delete(tail.data);
			listSize--;

			return data;
		}
	}// RetrieveLastElement

	private void delete(T target) {
		Node prev = null, curr = head;

		while (curr != null) {
			if (curr.data == target) {
				if (curr == head) {
					head = head.next;
				} else {
					tail = prev;
					prev.next = curr.next;
				}
				return;
			} else {
				prev = curr;
				curr = curr.next;
			}
		} // while
	}// Auxiliary delete method for retriveLastElement

}