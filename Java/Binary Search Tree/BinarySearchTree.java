package implementation;

import java.util.Comparator;

import java.util.TreeSet;

public class BinarySearchTree<K, V> {
	/*
	 * You may not modify the Node class and may not add any instance nor static
	 * variables to the BinarySearchTree class.
	 */
	private class Node {
		private K key;
		private V value;
		private Node left, right;

		private Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	private Node root;
	private int treeSize, maxEntries;
	private Comparator<K> comparator;

	public BinarySearchTree(Comparator<K> comparator, int maxEntries) {
		if (comparator == null || maxEntries < 1) {
			throw new IllegalArgumentException("Invalid parameters");
		} else {
			this.comparator = comparator;
			this.maxEntries = maxEntries;
			this.treeSize = 0;
		}
	}

	public BinarySearchTree<K, V> add(K key, V value) throws TreeIsFullException {
		if (root == null) {
			root = new Node(key, value);
			treeSize++;
			return this;
		} else {
			return addAux(key, value, root);
		}
	}

	private BinarySearchTree<K, V> addAux(K key, V value, Node rootAux) throws TreeIsFullException {
		int comparison = this.comparator.compare(key, rootAux.key);

		if (comparison == 0) {
			rootAux.value = value;
			return this;
		} else if (comparison < 0) {
			if (rootAux.left == null) {
				treeSize++;
				rootAux.left = new Node(key, value);
				return this;
			} else {
				return addAux(key, value, rootAux.left);
			}
		} else {
			if (rootAux.right == null) {
				treeSize++;
				rootAux.right = new Node(key, value);
				return this;
			} else {
				return addAux(key, value, rootAux.right);
			}
		}
	}

	public String toString() {
		if (this.treeSize == 0) {
			return "EMPTY TREE";
		} else {
			return toStringAux(root);
		}
	}// toString

	private String toStringAux(Node rootAux) {
		if (rootAux == null) {
			return "";
		} else {
			return toStringAux(rootAux.left) + "{" + rootAux.key + ":" + rootAux.value + "}"
					+ toStringAux(rootAux.right);
		}
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return treeSize;
	}

	public boolean isFull() {
		return treeSize == maxEntries;
	}

	public KeyValuePair<K, V> getMinimumKeyValue() throws TreeIsEmptyException {
		if (this.size() == 0) {
			throw new TreeIsEmptyException("Empty tree");
		} else {
			return new KeyValuePair(getMinimumKeyValueAux(root).key, getMinimumKeyValueAux(root).value);
		}

	}

	private Node getMinimumKeyValueAux(Node rootAux) {
		if (rootAux.left == null) {
			return rootAux;
		} else {
			return getMinimumKeyValueAux(rootAux.left);
		}
	}

	public KeyValuePair<K, V> getMaximumKeyValue() throws TreeIsEmptyException {
		if (this.size() == 0) {
			throw new TreeIsEmptyException("Empty tree");
		} else {
			return new KeyValuePair(getMaximumKeyValueAux(root).key, getMaximumKeyValueAux(root).value);
		}
	}

	private Node getMaximumKeyValueAux(Node rootAux) {
		if (rootAux.right == null) {
			return rootAux;
		} else {
			return getMaximumKeyValueAux(rootAux.right);
		}
	}

	public KeyValuePair<K, V> find(K key) {
		return findAux(key, root);
	}

	private KeyValuePair<K, V> findAux(K key, Node rootAux) {
		if (rootAux == null) {
			return null;
		} else {
			int comparison = comparator.compare(key, rootAux.key);
			if (comparison == 0) {
				return new KeyValuePair(rootAux.key, rootAux.value);
			} else if (comparison < 0) {
				return findAux(key, rootAux.left);
			} else {
				return findAux(key, rootAux.right);
			}
		}
	}

	public BinarySearchTree<K, V> delete(K key) throws TreeIsEmptyException {
		if (key == null) {
			throw new IllegalArgumentException("Invalid parameter");
		} else if (this.size() == 0) {
			throw new TreeIsEmptyException("Empty tree");
		} else {
			treeSize--;
			return deleteAux(key, root, root);
		}
	}

	private BinarySearchTree<K, V> deleteAux(K key, Node curNode, Node root) throws TreeIsEmptyException {

		if (curNode == null) {
			return this;
		} else if (comparator.compare(key, curNode.key) == 0) {
			if (curNode.left == null && curNode.right == null) {// node is a leaf
				if (comparator.compare(root.key, curNode.key) < 0) {
					root.right = null;
				} else {
					root.left = null;
				}
				return this;
			} else if ((curNode.left == null && curNode.right != null)
					|| (curNode.left != null && curNode.right == null)) {// 1 child
				if (curNode.left == null) {
					curNode.key = curNode.right.key;
					curNode.value = curNode.right.value;
					deleteAux(curNode.right.key, curNode.right, curNode);
				} else if (curNode.right == null) {
					curNode.key = curNode.left.key;
					curNode.value = curNode.left.value;
					deleteAux(curNode.left.key, curNode.left, curNode);
				}
				return this;

			} else {// 2 children
				K maxKey = getMinimumKeyValueAux(this.root.right).key;
				V maxValue = getMinimumKeyValueAux(this.root.right).value;

				curNode.key = maxKey;
				curNode.value = maxValue;

				if (curNode.left == null) {
					deleteAux(maxKey, curNode.right, curNode);
				} else {
					deleteAux(maxKey, curNode.left, curNode);
				}
				return this;
			}

		} else if (comparator.compare(key, curNode.key) > 0) {
			return deleteAux(key, curNode.right, curNode);
		} else if (comparator.compare(key, curNode.key) < 0) {
			return deleteAux(key, curNode.left, curNode);
		}
		return null;
	}

	public void processInorder(Callback<K, V> callback) {
		if (callback == null) {
			throw new IllegalArgumentException("Invalid parameter");
		} else {
			processInorderAux(root, callback);
		}
	}

	private void processInorderAux(Node rootAux, Callback<K, V> callback) {
		if (rootAux == null) {
			return;
		} else {
			processInorderAux(rootAux.left, callback);
			callback.process(rootAux.key, rootAux.value);
			processInorderAux(rootAux.right, callback);
		}
	}

	public BinarySearchTree<K, V> subTree(K lowerLimit, K upperLimit) {
		if (lowerLimit == null || upperLimit == null || comparator.compare(lowerLimit, upperLimit) > 0) {
			throw new IllegalArgumentException("Invalid parameters");
		} else {
			BinarySearchTree<K, V> inRange = new BinarySearchTree<K, V>(comparator, maxEntries);
			return subTreeAux(root, lowerLimit, upperLimit, inRange, inRange.root, inRange.root);

		}
	}

	private BinarySearchTree<K, V> subTreeAux(Node curNode, K lowerLimit, K upperLimit, BinarySearchTree<K, V> inRange,
			Node inRangeCur, Node inRangeRoot) {

		if (curNode == null) {
			return inRange;
		} else if (comparator.compare(lowerLimit, curNode.key) <= 0
				&& comparator.compare(upperLimit, curNode.key) >= 0) {
			if (inRange.root == null) {
				inRange.root = new Node(curNode.key, curNode.value);
				inRange.treeSize++;

				subTreeAux(curNode.left, lowerLimit, upperLimit, inRange, curNode.left, inRange.root);
				subTreeAux(curNode.right, lowerLimit, upperLimit, inRange, curNode.right, inRange.root);

			} else {
				if (comparator.compare(curNode.key, inRangeRoot.key) > 0) {
					inRangeRoot.right = curNode;
					inRange.treeSize++;
				} else {
					inRangeRoot.left = curNode;
					inRange.treeSize++;
				}
				subTreeAux(curNode.left, lowerLimit, upperLimit, inRange, curNode.left, inRange.root);
				subTreeAux(curNode.right, lowerLimit, upperLimit, inRange, curNode.right, inRange.root);
			}

		} else if (comparator.compare(upperLimit, curNode.key) < 0) {// curNode greater than upperLimit
			return subTreeAux(curNode.left, lowerLimit, upperLimit, inRange, curNode.left, inRangeCur);
		} else if (comparator.compare(lowerLimit, curNode.key) > 0) {// curNode less than lowerLimit
			return subTreeAux(curNode.right, lowerLimit, upperLimit, inRange, curNode.right, inRangeCur);
		}
		return inRange;
	}

	public TreeSet<V> getLeavesValues() {

		if (this.treeSize == 0) {
			return new TreeSet<V>();
		}
		TreeSet<V> newTree = new TreeSet<V>();
		return getLeavesValuesAux(root, newTree);
	}

	private TreeSet<V> getLeavesValuesAux(Node curRoot, TreeSet<V> leafValues) {

		if (curRoot == null) {
			return leafValues;
		} else if (curRoot.left == null && curRoot.right == null) {
			leafValues.add(curRoot.value);
			leafValues.addAll(leafValues);
		} else {
			getLeavesValuesAux(curRoot.left, leafValues);
			getLeavesValuesAux(curRoot.right, leafValues);
		}
		return leafValues;
	}
}
