package devops_black_jack;


import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		
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

		int dealer_value = 0;
		ArrayList<Card> dealer_hand = new ArrayList<Card>();
			
		//dealer draws first card.
		System.out.println("\nDealer begins to draw.");
		Card dealer_card = Deck.draw_card();
		dealer_hand.add(dealer_card);
		System.out.println("House first card: " + dealer_card.getName());
		dealer_value += dealer_card.value;
		System.out.println("House first value: " + dealer_value + "\n");

		//
		//Player(s): Initial card draw
		//
		for (Player player: player_list) {
			player.drawcard();  //drawcard not functioning right now
			System.out.println(player.name + "has drawn a card.");
			}
		
		//
		//Player(s): Choose hit/stand/double/split
		//

		//
		//Player(s): Resolve results
		//
		for (Player player: player_list) {
			System.out.println(player.name + ", your hand contains");
			for (Card card: player.hand) {
				System.out.println(card.name);
				}
			}
		
		//dealer draws remaining cards until threshold of 17
		boolean dealer_draw_check = true;
		while(dealer_draw_check) {
			if (dealer_value >= 17) {
				dealer_draw_check = false;
			}
			else {
				dealer_card = Deck.draw_card();
				dealer_hand.add(dealer_card);
				System.out.println("House drew: " + dealer_card.getName());
				dealer_value += dealer_card.value;
				System.out.println("House value is: " + dealer_value);
				}
			}

		System.out.println("\ndealer final value: " + dealer_value);
		for (Card object: dealer_hand) {
			    System.out.println("The house's final draw: "+object.name);
			    }
		
		//
		//Player: Loop through and compare vs dealer_hand
		//
		
		
		SQL sql = new SQL();
		sql.NewUser("Daniel", "123", 500);
		System.out.println(sql.Login("Daniel", "123"));
		System.out.println(sql.getBalance("Daniel", "123"));
		sql.setBalance("Daniel", 8991.123212d);
		scanner.close();
	}
}
