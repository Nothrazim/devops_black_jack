package devops_black_jack;

import java.util.*;

public class Deck {
	
	
	final static String[] suites = {"Hearts", "Spades", "Diamonds", "Clubs"};
	
	static ArrayList<Card> deck = new ArrayList<Card>();
	
	private int deckCount = 1;
	
	void create_deck(){
		for(int suit = 0; suit < suites.length; suit++) {
			for(int n = 1; n < 14; n++) {
				if (n == 1) {
					deck.add(new Card("Ace of " + suites[suit], 11));
					}
				else if (n == 11) {
					deck.add(new Card("Jack of " + suites[suit], 10));
					}
				else if (n == 12) {
					deck.add(new Card("Queen of " + suites[suit], 10));
					}
				else if (n == 13) {
					deck.add(new Card("King of " + suites[suit], 10));
					}
				else {
					deck.add(new Card(n + " of " + suites[suit], n));
					}
				}
			}
		}

    
	void add_decks(int value){
		ArrayList<Card> temp_deck = new ArrayList<Card>();
		for(int x = 1; x <= value-1; x++) {
			temp_deck.addAll(deck);
			}
		deck.addAll(temp_deck);
		System.out.println(value + " decks will be used");
		System.out.println("DECKSIZE " +deck.size());
		setDeckCount(value);
		}
	
	void shuffle_deck() {
		Collections.shuffle(deck);
	}
	
	void print_deck(ArrayList<Card> deck) {
		for (Card object: deck) {
		    System.out.println(object.getName());
		}
	}
	

	public Card draw_card() {
		Card card = deck.get(0);
		deck.remove(0);
		return card;
	}
	
	public void clear_deck() {
		deck.clear();
	}


	public int getDeckCount() {
		return deckCount;
	}


	public void setDeckCount(int deckCount) {
		this.deckCount = deckCount;
	}
	
}