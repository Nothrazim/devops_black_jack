package devops_black_jack;
import java.util.ArrayList;

public class Dealer {
	private int hand_value = 0;
	private ArrayList<Card> hand = new ArrayList<Card>();
	private Deck Deck;
	public Dealer() {
		Deck = new Deck();
	}
		
	void resetHand() {
		hand.clear();
		this.hand_value = 0;
	}
	
	
	//dealer draws first card.
	void draw_first_card() {
		System.out.println("\nDealer begins to draw.");
		Card dealer_card = Deck.draw_card();
		hand.add(dealer_card);
		System.out.println("House first card: " + dealer_card.getName());
		hand_value += dealer_card.getValue();
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
				hand_value += dealer_card.getValue();
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
		    System.out.println(object.getName());
		    }
		System.out.println("Dealers final value is " + hand_value);
	
	}
	
	void deduce_winner(ArrayList<Player> player_list) {
		for (Player player: player_list) {
			int pval = player.getHand_Value();
			int dval = hand_value;
			System.out.println(player.getName()+"" + Player.numberIntToString[player.getExtraHandCounter()] + " pval: " + pval + ", dealer val: " + dval);
			if (hand_value == pval) { //tie, money back
				System.out.println(player.getName()+" "+ Player.numberIntToString[player.getExtraHandCounter()] + " gets money back");
				player.updateBalance(player.getBet());
			}
			else if (dval == 21) { //dealer blackjacks
				System.out.println("dealer blackjacks, dealer wins");
				}
			else if (pval == 21) { //player blackjacks
				System.out.println("player blackjacks, "
			+ player.getName()+""+ Player.numberIntToString[player.getExtraHandCounter()] + " wins");
				player.updateBalance(player.getBet()*2.5);
				}
			else { 
				if (dval > 21 && pval <= 21) { //dealer busts
					System.out.println("dealer busts, " + player.getName()+""
				+ Player.numberIntToString[player.getExtraHandCounter()] + " wins");
					player.updateBalance(player.getBet()*2);
					}
				else if (pval > dval && pval <= 21) {
					System.out.println("player is higher than dealer, " + player.getName()+" "
				+ Player.numberIntToString[player.getExtraHandCounter()] + " wins");
					player.updateBalance(player.getBet()*2);
					}
				else if(dval > pval && dval <= 21){
					System.out.println("dealer is higher than player, dealer wins.");
					}
				else {
					System.out.println("Dealer Wins!");
				}
				}
			player.setBet(0);
			player.resetHand();
			//for when we use proper logins, updates database balance after every game
			//sql.setBalance(player.getName(), player.getBalance());
			}
		this.resetHand();
	}
	//dealer draws remaining cards until threshold of 17

}
