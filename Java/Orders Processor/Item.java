package processor;

public class Item implements Comparable<Item> {
	private String description;
	private double cost;
	private int quantity;

	public Item(String description, int quantity, double cost) {
		this.description = description;
		this.quantity = quantity;
		this.cost = cost;
	}

	public String getDescription() {
		return description;
	}

	public double getCost() {
		return cost;
	}

	public int getQuantity() {
		return quantity;
	}

	public void increaseQuantity() {
		quantity++;
	}

	public int compareTo(Item o) {
		return this.description.compareTo(o.description);
	}

}
