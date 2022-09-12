package processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Client implements Iterable<Item>, Comparable<Client> {
	private int clientId;
	private ArrayList<Item> items;
	private String report;

	public Client(int clientId) {
		this.clientId = clientId;
		this.items = new ArrayList<Item>();
	}

	public int getClientId() {
		return clientId;
	}

	public String getReport() {
		return report;
	}

	public void addItem(String description, int quantity, double cost) {
		if (items.size() == 0) {
			items.add(new Item(description, quantity, cost));
		} else {
			for (Item item : items) {
				if (item.getDescription().equals(description)) {
					return;
				}
			}
			items.add(new Item(description, quantity, cost));
			Collections.sort(items);
		}
	}

	public String itemsToString() {
		String result = "";
		for (Item item : items) {
			result = result + item.getDescription() + "\n";
		}
		return result;
	}

	public void addReport(String report) {
		this.report = report;
	}

	public int compareTo(Client o) {
		return this.clientId - o.clientId;
	}

	public Iterator<Item> iterator() {
		return items.iterator();
	}

}
