package devops_black_jack;

import java.util.*;
import devops_black_jack.Card;

public class Deck {
	String[] suites = {"Hearts", "Spades", "Diamonds", "Clubs"};
	
	Deck(){
		System.out.println();
	}
	
	ArrayList<Card> deck = new ArrayList<Card>();
	
	
	void create_deck(){
		int y = 0; //counter to run through all cards created for print statements
		for(int suit = 0; suit < suites.length; suit++) {
			for(int n = 1; n < 14; n++) {
				if (n == 1) {
					deck.add(new Card("Ace of " + suites[suit], 1));
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
				y += 1;
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
		}
	
	void shuffle_deck(ArrayList<Card> deck) {
		Collections.shuffle(deck);
		System.out.println("Deck has been shuffled.");
	}
	
	void print_deck(ArrayList<Card> deck) {
		for (Card object: deck) {
		    System.out.println(object.name);
		}
	}
	
	Card draw_card() {
		Card card = deck.get(0);
		deck.remove(0);
		return card;
	}
	
}