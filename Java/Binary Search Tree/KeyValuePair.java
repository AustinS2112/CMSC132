package implementation;

/**
 * 
 * Use to keep track of key/value pairs retrieved from the tree. Do not modify
 * this class.
 * 
 * @author Department of Computer Science, UMCP
 * 
 * @param <K>
 * @param <V>
 */
public class KeyValuePair<K, V> {
	private K key;
	private V value;

	public KeyValuePair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}
}
