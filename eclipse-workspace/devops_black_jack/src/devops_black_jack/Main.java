package devops_black_jack;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		// TODO Auto-generated method stub
		
		System.out.println("Hello weldt!");	

		Deck Decktest = new Deck();
		Decktest.create_deck();


		//This goes into Game
		System.out.println("How many decks do you want to use?");	
		int deck_selection=scanner.nextInt();
		Decktest.add_decks(deck_selection);
		
		Decktest.shuffle_deck(Decktest.deck);
		Decktest.print_deck(Decktest.deck);
		Card drawncard = Decktest.draw_card();
		System.out.println("I DREW THIS " + drawncard.getName());
		
		
		
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
