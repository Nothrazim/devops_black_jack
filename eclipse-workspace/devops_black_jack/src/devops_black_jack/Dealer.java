package devops_black_jack;
import java.util.ArrayList;

public class Dealer {
	int hand_value = 0;
	ArrayList<Card> hand = new ArrayList<Card>();
	Deck Deck;
	SQL sql;
	public Dealer() {
		Deck = new Deck();
		sql = new SQL();
	}
		
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
				if(this.hand_value > 21) {
					for(Card card: hand) {
						if(card.getValue() == 11) {
							card.setAceToValueOne();
							break;
						}
					}
					hand_value = 0;
					for(Card card: hand) {
						hand_value += card.getValue();
					}
				}
					
				System.out.println("House value is: " + hand_value);
				}
			}
		System.out.println("Dealers final hand:");
		for (Card object: hand) {
		    System.out.println(object.name);
		    }
		System.out.println("Dealers final value is " + hand_value);
	
	}
	
	void deduce_winner(ArrayList<Player> player_list) {
		for (Player player: player_list) {
			int pval = player.getHand_Value();
			int dval = hand_value;
			System.out.println(player.name + " pval: " + pval + ", dealer val: " + dval);
			if (hand_value == 21 && pval == 21) { //double blackjack
				System.out.println(player.name + " gets money back");
			}
			else if (dval == 21) { //dealer blackjacks
				System.out.println("dealer blackjacks, dealer wins");
				player.updateBalance(-(player.getBet()));
				}
			else if (pval == 21) { //player blackjacks
				System.out.println("player blackjacks, " + player.name + " wins");
				player.updateBalance(player.getBet()*1.5);
				}
			else { 
				if (dval > 21) { //dealer busts
					System.out.println("dealer busts, " + player.name + " wins");
					player.updateBalance(player.getBet());
					}
				else if (pval > dval && pval <= 21) {
					System.out.println("player is higher than dealer, " + player.name + " wins");
					player.updateBalance(player.getBet());
					}
				else {
					System.out.println("dealer is higher than player, dealer wins.");
					player.updateBalance(-(player.getBet()));
					}
				}
			player.bet = 0;
			player.resetHand();
			//for when we use proper logins, updates database balance after every game
			sql.setBalance(player.getName(), player.getBalance());
			}
		
	}
	//dealer draws remaining cards until threshold of 17

}
