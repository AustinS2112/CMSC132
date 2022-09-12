package enumeratedTypes;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> deckOfCards;

	public Deck() {
		deckOfCards = new ArrayList<Card>();
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				deckOfCards.add(new Card(rank, suit));
			}
		}
	}

	public String toString() {
		String answer = "";

		for (Card card : deckOfCards) {
			answer = (answer.equals("") ? "" : (answer + "\n"));
			answer += card;
		}
		return answer;
	}

	public void shuffle() {
		Collections.shuffle(deckOfCards);
	}

	public static void main(String[] args) {
		Deck deck = new Deck();

		System.out.println("\n***** Initial Deck *****\n");
		System.out.println(deck);

		System.out.println("\n***** After shuffling deck *****\n");
		deck.shuffle();
		System.out.println(deck);
	}
}