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
		
		boolean game_running = true;
		while(game_running) {
			Deck.create_deck();
		
			player_list.add(player1);
			player_list.add(player2);
		
			System.out.println("How many decks do you want to use?");
			while(true) {
				String str_deck_selection=scanner.nextLine();
				try {
					int deck_selection = Integer.parseInt(str_deck_selection);
					Deck.add_decks(deck_selection);
					break;
				}
				catch(Exception e) {
					System.out.println("Please enter the number of decks you wish to play with");
				}
			}
			
			Deck.shuffle_deck();
			
			//place bets
			for (Player player: player_list) {
				System.out.println(player.getName() + ", what will you bet?");
				while(true) {
					String str_player_bet=scanner.nextLine();
					try {
						double player_bet = Double.parseDouble(str_player_bet);
						if(player.getBalance() < player_bet) {
							System.out.println("You dont have that many credits, you have "+player.getBalance());
							continue;
						}
						player.setBet(player_bet);
						break;
					}
					catch(Exception e) {
						System.out.println("Please enter a bet, you have "+player.getBalance()+" credits");
					}
				}
			}
		
			theDealer.draw_first_card();
			
			
			//Player(s): Initial card draw		
			for (Player player: player_list) {
				player.drawcard();
				player.drawcard();
				System.out.println(player.getName() + " has drawn;");
				player.printHand();
				}
			
			
			//Player(s): Choose hit/stand/double/split
			for (Player player: player_list) {
				boolean playing = true;
				while(playing) {
					System.out.println(player.getName() + ", your hand contains:");
					player.printHand();
					player.setHandValue();
					System.out.println("Total value of hand: "+player.getHand_Value());
					System.out.println("What do you want to do?\n" + player_choices[0] + "\n"+ player_choices[1]);
					if (player.balance >= player.bet * 2) {
						System.out.println(player_choices[2]);
					}
					if (player.hand.get(0).value == player.hand.get(1).value) {
						System.out.println(player_choices[3]);
					}
					
					String choice = scanner.nextLine().toLowerCase();
					if(choice.contains("stand") || choice.contains("hit") || choice.contains("double") || choice.contains("split"))
						playing = player.chooseAction(choice);
					else {
						System.out.println("\nPlease enter a valid action");
						continue;
					}
				}
			}
		
			
			//dealer draws remaining cards until threshold of 17
			theDealer.draw_more();
			
			//Loop through player list and compare vs dealer_hand
			theDealer.deduce_winner(player_list);
			
			System.out.println("\nNext game!");
		}
		SQL sql = new SQL();
		sql.NewUser("Daniel", "123", 500);
		System.out.println(sql.Login("Daniel", "123"));
		System.out.println(sql.getBalance("Daniel", "123"));
		sql.setBalance("Daniel", 8991.123212d);
		scanner.close();
	}
}
