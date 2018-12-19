package devops_black_jack;

import devops_black_jack.Card;

public class Deck {
	Deck(){
		System.out.println();
	}
	
	Card[] deck = new Card[52];

	String[] suites = {"Hearts", "Spades", "Diamonds", "Clubs"};
	
	void loop_cards(){
		for(int suit = 0; suit < suites.length; suit++) {
			System.out.println("Suit: " + suites[suit]);
			for(int n = 1; n < 14; n++) {
				if (n == 11) {
					deck[n] = new Card("Jack of " + suites[suit], 10);
					}
				else if (n == 12) {
					deck[n] = new Card("Queen of " + suites[suit], 10);
					}
				else if (n == 13) {
					deck[n] = new Card("King of " + suites[suit], 10);
					}
				else {
					deck[n] = new Card(n + " of " + suites[suit], n);
					System.out.println(deck[n].name);
					}
				}
			}
		}
	
}
