package devops_black_jack;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		// TODO Auto-generated method stub
		

		Deck Decktest = new Deck();
		Decktest.create_deck();


		//This goes into Game
		System.out.println("How many decks do you want to use?");	
		int deck_selection=scanner.nextInt();
		Decktest.add_decks(deck_selection);
		
		Decktest.shuffle_deck(Decktest.deck);
		
		//place bets

		int dealer_value = 0;
		ArrayList<Card> dealer_hand = new ArrayList<Card>();
		
		
		//dealer draws first card.
		System.out.println("\nDealer begins to draw.");
		Card dealer_card = Decktest.draw_card();
		dealer_hand.add(dealer_card);
		System.out.println("House first card: " + dealer_card.getName());
		dealer_value += dealer_card.value;
		System.out.println("House first value: " + dealer_value + "\n");

		//
		//Player(s): Initial card draw
		ArrayList<Card> phand;
		Player player = new Player("Player", 5000);
		player.drawcard();
		phand = player.getHand();
		for(Card c: phand) {
			System.out.println(c.getName());
		}
		int choice = scanner.nextInt();
		System.out.println("What would you like to do? [1] Hit");
		if(choice == 1) {
			player.drawcard();
			phand = player.getHand();
			for(Card c: phand) {
				System.out.println(c.getName());
			}
		}
			
		
		
		//
		//Player(s): Choose hit/stand/double/split
		//

		//
		//Player(s): Resolve results
		//
		
		//dealer draws remaining cards until threshold of 17
		boolean dealer_draw_check = true;
		while(dealer_draw_check) {
			if (dealer_value >= 17) {
				dealer_draw_check = false;
			}
			else {
				dealer_card = Decktest.draw_card();
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
		//for(int i = 0; i < 100; i++) {
			//System.out.println("hehe nr hehe: " + i);
			//}
		//scanner.close();
	}
}
