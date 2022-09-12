package processor;

import java.text.NumberFormat;
import java.util.TreeMap;

public class Processor implements Runnable {
	private Income totalIncome;
	private Client client;
	private TreeMap<Item, Integer> itemsCount;
	// private String report;

	public Processor(Client client, TreeMap<Item, Integer> itemsCount, Income totalIncome) {
		this.client = client;
		this.totalIncome = totalIncome;
		this.itemsCount = itemsCount;
	}

	public void run() {
		String report = "----- Order details for client with Id: " + client.getClientId() + " -----";
		double totalOrderPerCustomer = 0;
		for (Item item : client) {
			String description = item.getDescription();
			double costPerItem = item.getCost();
			int quantity = item.getQuantity();
			double totalCost = quantity * costPerItem;

			totalOrderPerCustomer += totalCost;

			report += "\nItem's name: " + description + ", Cost per item: "
					+ NumberFormat.getCurrencyInstance().format(costPerItem) + ", Quantity: " + quantity + ", Cost: "
					+ NumberFormat.getCurrencyInstance().format(totalCost);

			synchronized (itemsCount) {
				Integer numItems = itemsCount.get(item);
				if (numItems == null) {
					itemsCount.put(item, item.getQuantity());
				} else {
					itemsCount.put(item, numItems + item.getQuantity());
				}
			}
		} // for
		report += "\nOrder Total: " + NumberFormat.getCurrencyInstance().format(totalOrderPerCustomer) + "\n";
		client.addReport(report);

		synchronized (totalIncome) {
			totalIncome.add(totalOrderPerCustomer);
		}

	}
}
