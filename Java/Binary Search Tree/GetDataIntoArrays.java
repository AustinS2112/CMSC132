package implementation;

import java.util.*;

/**
 * 
 * Example of a callback class. This callback stores the key and values of a
 * node in ArrayLists. Those values can later on be accessed using the provided
 * get methods. Do not modify this class.
 * 
 * @author Department of Computer Science, UMCP
 * 
 * @param <K>
 * @param <V>
 */
public class GetDataIntoArrays<K, V> implements Callback<K, V> {
	private ArrayList<K> keys;
	private ArrayList<V> values;

	public GetDataIntoArrays() {
		keys = new ArrayList<K>();
		values = new ArrayList<V>();
	}

	public void process(K key, V value) {
		keys.add(key);
		values.add(value);
	}

	public ArrayList<K> getKeys() {
		return new ArrayList<K>(keys);
	}

	public ArrayList<V> getValues() {
		return new ArrayList<V>(values);
	}
}
