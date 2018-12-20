package devops_black_jack;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		
		Deck Deck = new Deck();
		
		Deck.create_deck();

		
		
		
		
		
		ArrayList<Player> player_list = new ArrayList<Player>();
		Player player1 = new Player("Alice", 4);
		Player player2 = new Player("Bob", 4);
		
		
		
		player_list.add(player1);
		player_list.add(player2);

		//This goes into Game
		System.out.println("How many decks do you want to use?");	
		int deck_selection=scanner.nextInt();
		Deck.add_decks(deck_selection);
		
		Deck.shuffle_deck();
		
		//place bets
		for (Player player: player_list) {
			System.out.println(player.name + ", what will you bet?");
			int player_bet=scanner.nextInt();
			player.setBet(player_bet);
			}

		Dealer theDealer = new Dealer();
		
		//dealer draws first card.
		theDealer.draw_first_card();
		
		//
		//Player(s): Initial card draw
		
			
		
		for (Player player: player_list) {
			player.drawcard();
			player.drawcard();
			System.out.println(player.name + " has drawn 2 cards.");
			}
		
		//
		//Player(s): Choose hit/stand/double/split
		//

		//
		//Player(s): Resolve results
		//
		
		for (Player player: player_list) {
			System.out.println(player.name + ", your hand contains:");
			for (Card card: player.hand) {
				System.out.println(card.name);
				}
			}
		
		
		//dealer draws remaining cards until threshold of 17
		theDealer.draw_more();
		//
		//Player(s): Loop through player list and compare vs dealer_hand
		//
		for (Player player: player_list) {
			int pval = player.setHandValue();
			int dval = theDealer.hand_value;
			System.out.println(player.name + " pval: " + pval + ", dealer val: " + dval);
			if (theDealer.hand_value == 21 && pval == 21) { //double blackjack
				System.out.println(player.name + " gets money back");
			}
			else if (dval == 21) { //dealer blackjacks
				System.out.println("dealer blackjacks, dealer wins");
				player.balance -= player.bet;
				}
			else if (pval == 21) { //player blackjacks
				System.out.println("player blackjacks, " + player.name + " wins");
				player.balance += player.bet;
				}
			else { 
				if (dval > 21) { //dealer busts
					System.out.println("dealer busts, " + player.name + " wins");
					player.balance += player.bet;
					}
				else if (pval > dval && pval <= 21) {
					System.out.println("player is higher than dealer, " + player.name + " wins");
					player.balance += player.bet;
					}
				else {
					System.out.println("dealer is higher than player, dealer wins.");
					player.balance -= player.bet;
					}
				}
			player.bet = 0;
			}
		
		SQL sql = new SQL();
		sql.NewUser("Daniel", "123", 500);
		System.out.println(sql.Login("Daniel", "123"));
		System.out.println(sql.getBalance("Daniel", "123"));
		sql.setBalance("Daniel", 8991.123212d);
		scanner.close();
	}
}
