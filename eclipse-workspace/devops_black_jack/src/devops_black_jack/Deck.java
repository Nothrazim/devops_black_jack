package devops_black_jack;

import devops_black_jack.Card;

public class Deck {
	Deck(){
		System.out.println();
	}
	
	Card[] deck = new Card[52];

	String[] suites = {"Hearts", "Spades", "Diamonds", "Clubs"};
	
	public void loop_cards(){
		for(int suit = 0; suit < suites.length; suit++) {
			System.out.println("Suit: " + suites[suit]);
			for(int a = 1; a < 13; a++) {
				deck[a] = new Card(a + " of " + suites[suit], a);
				System.out.println(deck[a].name);
				}
			}
		}
	
}
