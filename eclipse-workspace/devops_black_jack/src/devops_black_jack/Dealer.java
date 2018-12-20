package devops_black_jack;
import java.util.ArrayList;

public class Dealer {
	int hand_value = 0;
	ArrayList<Card> hand = new ArrayList<Card>();
		
	//dealer draws first card.
	void draw_first_card() {
		System.out.println("\nDealer begins to draw.");
		Card dealer_card = Deck.draw_card();
		hand.add(dealer_card);
		System.out.println("House first card: " + dealer_card.getName());
		hand_value += dealer_card.value;
		System.out.println("House first value: " + hand_value + "\n");	
	}
	
	void draw_more() {
		boolean dealer_draw_check = true;
		while(dealer_draw_check) {
			if (hand_value >= 17) {
				dealer_draw_check = false;
			}
			else {
				Card dealer_card = Deck.draw_card();
				hand.add(dealer_card);
				System.out.println("House drew: " + dealer_card.getName());
				hand_value += dealer_card.value;
				System.out.println("House value is: " + hand_value);
				}
			}

		System.out.println("\ndealer final value: " + hand_value);
		for (Card object: hand) {
			    System.out.println("The house's final draw: "+object.name);
			    }	
	}
	
	void deduce_winner(ArrayList<Player> player_list) {
		for (Player player: player_list) {
			int pval = player.setHandValue();
			int dval = hand_value;
			System.out.println(player.name + " pval: " + pval + ", dealer val: " + dval);
			if (hand_value == 21 && pval == 21) { //double blackjack
				System.out.println(player.name + " gets money back");
				//update player.balance (with 0)
			}
			else if (dval == 21) { //dealer blackjacks
				System.out.println("dealer blackjacks, dealer wins");
				//update player.balance
				}
			else if (pval == 21) { //player blackjacks
				System.out.println("player blackjacks, " + player.name + " wins");
				//update player.balance
				}
			else { 
				if (dval > 21) { //dealer busts
					System.out.println("dealer busts, " + player.name + " wins");
					//update player.balance
					}
				else if (pval > dval && pval <= 21) {
					System.out.println("player is higher than dealer, " + player.name + " wins");
					//update player.balance
					}
				else {
					System.out.println("dealer is higher than player, dealer wins.");
					//update player.balance
					}
				}
			player.bet = 0;
			}
		
	}
	//dealer draws remaining cards until threshold of 17

}
