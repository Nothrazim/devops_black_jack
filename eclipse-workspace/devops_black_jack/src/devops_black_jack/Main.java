package devops_black_jack;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);

		ArrayList<Player> player_list = new ArrayList<Player>();
		Player player1 = new Player("Alice", 4);
		Player player2 = new Player("Bob", 4);
		Deck Deck = new Deck();
		Dealer theDealer = new Dealer();
		String[] player_choices = {"Hit", "Stand", "Double", "Split"};
		
		Deck.create_deck();
	
		player_list.add(player1);
		player_list.add(player2);

		System.out.println("How many decks do you want to use?");	
		int deck_selection=scanner.nextInt();
		Deck.add_decks(deck_selection);
		
		Deck.shuffle_deck();
		
		//place bets
		for (Player player: player_list) {
			System.out.println(player.getName() + ", what will you bet?");
			int player_bet=scanner.nextInt();
			player.setBet(player_bet);
			}

		//dealer draws first card.
		theDealer.draw_first_card();
		
		//
		//Player(s): Initial card draw
				
		for (Player player: player_list) {
			player.drawcard();
			player.drawcard();
			System.out.println(player.getName() + " has drawn;");
			player.getHand();
			}
		
		System.out.println("\n");
		
		//
		//Player(s): Choose hit/stand/double/split
		//
		for (Player player: player_list) {
			System.out.println(player.getName() + ", your hand contains:");
			player.getHand();
			System.out.println("What do you want to do?\n" + player_choices[0] + "\n"+ player_choices[1]);
			if (player.balance >= player.bet * 2) {
				System.out.println(player_choices[2]);
			}
			if (player.hand.get(0).value == player.hand.get(1).value) {
				System.out.println(player_choices[3]);
			}
			String choice = scanner.next();
			player.chooseAction(choice); //results will be handled in Player methods
			}

		
		//dealer draws remaining cards until threshold of 17
		theDealer.draw_more();
		//
		//Player(s): Loop through player list and compare vs dealer_hand
		//
		
		theDealer.deduce_winner(player_list);
		
		SQL sql = new SQL();
		sql.NewUser("Daniel", "123", 500);
		System.out.println(sql.Login("Daniel", "123"));
		System.out.println(sql.getBalance("Daniel", "123"));
		sql.setBalance("Daniel", 8991.123212d);
		scanner.close();
	}
}
