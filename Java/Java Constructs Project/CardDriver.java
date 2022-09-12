package enumeratedTypes;

public class CardDriver {

	public static void main(String[] args) {
		System.out.println("Printing deck of cards");
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				System.out.println(new Card(rank, suit));
			}
		}
	}
}
