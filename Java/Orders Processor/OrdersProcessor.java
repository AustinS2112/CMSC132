package processor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class OrdersProcessor {

	public static Client readClients(String clientFileName, String dataFileName) throws FileNotFoundException {
		Client client = null;
		TreeMap<String, Double> itemCosts = new TreeMap<String, Double>();
		Scanner itemScanner = new Scanner(new File(dataFileName));
		while (itemScanner.hasNext()) {
			String itemDescription = itemScanner.next();
			double itemCost = itemScanner.nextDouble();
			itemCosts.put(itemDescription, itemCost);
		} // retrieving the costs of individual items
		itemScanner.close();

		Scanner scanner = new Scanner(new File(clientFileName));
		int numItems = getNumItemsTotal(clientFileName);
		while (scanner.hasNext()) {
			scanner.next();
			int clientId = scanner.nextInt();

			client = new Client(clientId);// create client

			for (int i = 1; i <= numItems; i++) {
				String itemName = scanner.next();
				scanner.next();
				if (i < numItems) {
					scanner.nextLine();
				}
				client.addItem(itemName, getNumItems(clientFileName, itemName), itemCosts.get(itemName));
			} // adding items to client
		}
		scanner.close();

		return client;
	}// creates client from datafile

	private static int getNumItems(String dataFileName, String itemName) throws FileNotFoundException {
		int itemCount = 0;

		Scanner scanner = new Scanner(new File(dataFileName));

		while (scanner.hasNext()) {
			if (scanner.nextLine().contains(itemName)) {
				itemCount++;
			}
		}
		return itemCount++;
	}// Auxiliary to get the total number of items of a specific description

	private static int getNumItemsTotal(String dataFileName) throws FileNotFoundException {
		int itemCount = 0;
		Scanner scanner = new Scanner(new File(dataFileName));

		while (scanner.hasNext()) {
			itemCount++;
			scanner.nextLine();
		}
		scanner.close();
		return itemCount - 1;
	}// Auxiliary to get number of items in a file

	public static String processOneClient(Client client, TreeMap<Item, Integer> itemsCount, Income totalIncome) {
		String clientReport = "";

		clientReport += "----- Order details for client with Id: " + client.getClientId() + " -----";
		double totalOrderPerCustomer = 0;
		for (Item item : client) {
			String description = item.getDescription();
			double costPerItem = item.getCost();
			int quantity = item.getQuantity();
			double totalCost = quantity * costPerItem;

			totalOrderPerCustomer += totalCost;

			clientReport += "\nItem's name: " + description + ", Cost per item: "
					+ NumberFormat.getCurrencyInstance().format(costPerItem) + ", Quantity: " + quantity + ", Cost: "
					+ NumberFormat.getCurrencyInstance().format(totalCost);

			Integer numItems = itemsCount.get(item);
			if (numItems == null) {
				itemsCount.put(item, item.getQuantity());
			} else {
				itemsCount.put(item, numItems + item.getQuantity());
			}
		} // for
		clientReport += "\nOrder Total: " + NumberFormat.getCurrencyInstance().format(totalOrderPerCustomer) + "\n";
		client.addReport(clientReport);

		totalIncome.add(totalOrderPerCustomer);

		return clientReport;
	}

	public static void main(String args[]) throws InterruptedException, IOException {
		ArrayList<Client> allClients = new ArrayList<Client>();
		TreeMap<Item, Integer> itemsCount = new TreeMap<>();
		Income totalIncome = new Income(0);
		String orderSummary = "";

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter item's data file name: ");
		String dataFileName = scanner.next();

		System.out.print("\nEnter 'y' for multiple threads, any other charater otherwise: ");
		String threadCondition = scanner.next();

		System.out.print("\nEnter number of orders to process: ");
		int numOrders = scanner.nextInt();

		System.out.print("\nEnter order's base filename: ");
		String baseFileName = scanner.next();

		System.out.print("\nEnter result's filename: ");
		String resultFileName = scanner.next();

		long startTime = System.currentTimeMillis();

		scanner.close();

		for (int i = 1; i <= numOrders; i++) {
			allClients.add(readClients(baseFileName + i + ".txt", dataFileName));
		}

		if (threadCondition.equals("y")) {
			ArrayList<Thread> allThreads = new ArrayList<Thread>();
			for (Client client : allClients) {
				System.out.println("Reading order for client with id: " + client.getClientId());
				Processor processor = new Processor(client, itemsCount, totalIncome);
				allThreads.add(new Thread(processor));
			}

			for (Thread thread : allThreads) {
				thread.start();
			}

			for (Thread thread : allThreads) {
				thread.join();
			}

			Collections.sort(allClients);
			for (Client client : allClients) {
				orderSummary += client.getReport();
			} // getting each client's report

			orderSummary += "***** Summary of all orders *****\n";

			Set<Map.Entry<Item, Integer>> itemsCountSet = itemsCount.entrySet();
			for (Map.Entry<Item, Integer> currentEntry : itemsCountSet) {
				orderSummary += "Summary - Item's name: " + currentEntry.getKey().getDescription() + ", Cost per item: "
						+ NumberFormat.getCurrencyInstance().format(currentEntry.getKey().getCost()) + ", Number sold: "
						+ currentEntry.getValue() + ", Item's Total: " + NumberFormat.getCurrencyInstance()
								.format(currentEntry.getKey().getCost() * currentEntry.getValue())
						+ "\n";
			}
			orderSummary += "Summary Grand Total: " + NumberFormat.getCurrencyInstance().format(totalIncome.getValue())
					+ "\n";

			BufferedWriter writeToFile = new BufferedWriter(new FileWriter(resultFileName));

			writeToFile.write(orderSummary);
			writeToFile.flush();
			writeToFile.close();

			long endTime = System.currentTimeMillis();
			System.out.println("Processing time (msec): " + (endTime - startTime));
			System.out.println("Results can be found in the file: " + resultFileName);
		} else {
			String orderSummaryNonThreaded = "";
			for (Client client : allClients) {
				System.out.println("Reading order for client with id: " + client.getClientId());
				orderSummaryNonThreaded += processOneClient(client, itemsCount, totalIncome);
			} // generating a report for each client

			orderSummaryNonThreaded += "***** Summary of all orders *****\n";

			Set<Map.Entry<Item, Integer>> itemsCountSet = itemsCount.entrySet();
			for (Map.Entry<Item, Integer> currentEntry : itemsCountSet) {
				orderSummaryNonThreaded += "Summary - Item's name: " + currentEntry.getKey().getDescription()
						+ ", Cost per item: "
						+ NumberFormat.getCurrencyInstance().format(currentEntry.getKey().getCost()) + ", Number sold: "
						+ currentEntry.getValue() + ", Item's Total: " + NumberFormat.getCurrencyInstance()
								.format(currentEntry.getKey().getCost() * currentEntry.getValue())
						+ "\n";
			}
			orderSummaryNonThreaded += "Summary Grand Total: "
					+ NumberFormat.getCurrencyInstance().format(totalIncome.getValue()) + "\n";

			BufferedWriter writeToFile = new BufferedWriter(new FileWriter(resultFileName));

			writeToFile.write(orderSummaryNonThreaded);
			writeToFile.flush();
			writeToFile.close();

			long endTimeNonThreaded = System.currentTimeMillis();
			System.out.println("Processing time (msec): " + (endTimeNonThreaded - startTime));
			System.out.println("Results can be found in the file: " + resultFileName);
			return;
		}

	}
}