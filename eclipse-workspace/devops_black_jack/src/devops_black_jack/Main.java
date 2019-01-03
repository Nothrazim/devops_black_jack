package devops_black_jack;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Welcome to the scuffed casino");
		Scanner scanner=new Scanner(System.in);
		SQL sql = new SQL();
		
		Main game = new Main();
		String [] logininfo = game.LoginOrRegister(scanner, sql);
		
		ArrayList<Player> player_list = new ArrayList<Player>();
		Player player = new Player(logininfo[0],logininfo[1], sql.getBalance(logininfo[0], logininfo[1]));
		Deck Deck = new Deck();
		Dealer theDealer = new Dealer();

		
		player_list.add(player);
		System.out.println("Welcome to the game "+logininfo[0]);
		Deck.create_deck();
		Deck.shuffle_deck();
		while(true) {
		game.bigMenu(scanner, game, Deck, player_list, theDealer, sql);
		}
		//game.deckChoices(Deck, player_list, scanner);
		//game.gameloop(scanner, player_list, theDealer);
	}
	
	private void bigMenu(Scanner scanner, Main game, Deck Deck, ArrayList<Player> player_list, Dealer theDealer, SQL sql) {
		boolean menuing = true;
		while(menuing) {
			boolean canremove = (player_list.size()>1);
			System.out.println("What would you like to do?");
			System.out.println("[Add] player\n[Play] blackjack\n[Change] deck\n[Rules]");
			if(canremove) {
				System.out.println("[Remove] player");
			}
			System.out.println("[Quit]");
			String menuchoice = scanner.nextLine().toLowerCase();
			switch (menuchoice) {
			case "add":
				game.addPlayer(player_list, scanner, sql);
				menuing = false;
				break;
			case "play":
				game.gameloop(scanner, player_list, theDealer);
				menuing = false;
				break;
			case "change":
				game.deckChoices(Deck, player_list, scanner);
				menuing = false;
				break;
			case "remove":
				if(canremove)
					game.removePlayer(player_list, scanner);
				else
					System.out.println("Please enter a valid option");
				break;
			case "rules":
				//print rules
				break;
			case "quit":
				System.exit(0);
			default:
				System.out.println("Please enter a valid option");
				continue;
			}
		}
	}
	
	private void deckChoices(Deck Deck, ArrayList<Player> player_list, Scanner scanner) {
		
		Deck.clear_deck();
		Deck.create_deck();
	
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
			
	}
	private void gameloop(Scanner scanner, ArrayList<Player> player_list, Dealer theDealer) {
		//place bets
		String[] player_choices = {"Hit", "Stand", "Double", "Split"};
		boolean game_running = true;
			while(game_running) {
			
			for (Player player: player_list) {
				System.out.println(player.name +" your balance is: " + player.getBalance());
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
			//needs to be changed to index instead of foreach, split needs to decrease iterator by 1
			//so the player can play both the split hand and the original hand
			//throws ConcurrentModificationException on second hand after split, thats bad
			//doesnt throw ConcurrentModificationException on non foreach loop
			//PROPER PRINTS WILL NEED TO BE HANDLED
			//FOR NOW IT JUST PRINTS THE PLAYERS NAME TWICE FOR TWO HANDS
			//PROPER BALANCE CHECKING NEEDS TO BE DONE TOO, AS IT STANDS THE HANDS HAVE INDIVIDUAL BALANCES, VERY BAD
			boolean split = false;
			for (int i = 0; i < player_list.size(); i++) {
				if(split)
					i--;
				boolean playing = true;
				boolean doubledown = false;
				split = false;
				boolean splitskip = false;
				while(playing) {
					split = false;
					System.out.println(player_list.get(i).getName() + ", your hand contains:");
					player_list.get(i).printHand();
					player_list.get(i).setHandValue();
					System.out.println("Total value of hand: "+player_list.get(i).getHand_Value());
					System.out.println("What do you want to do?\n" + player_choices[0] + "\n"+ player_choices[1]);
					if (player_list.get(i).getBalance() >= player_list.get(i).bet * 2) {
						System.out.println(player_choices[2]);
						doubledown = true;
					}
					if (player_list.get(i).hand.size() > 1 && !splitskip && player_list.get(i).hand.get(0).name.substring(0, 3).equals(player_list.get(i).hand.get(1).name.substring(0, 3)) && player_list.get(i).getBalance() >= player_list.get(i).getBet()) {
						System.out.println(player_choices[3]);
						//the idea is to create a new player instance with the same name, balance and bet on split
						//insert the new player object at the index of the first player objects name +1 to get the "hands" in correct order
						//remove the split card from first player object hand and add to "second" player objects hand
						//IT ACTUALLY WORKS NOW, LEAVING THIS COMMENT IN CASE SOMEONE WANTS A QUICK OVERVIEW
						split = true;
					}
					
					String choice = scanner.nextLine().toLowerCase();
					if(choice.equals("stand") || choice.equals("hit") || (choice.equals("double") && doubledown) || (choice.equals("split") && split)) {
						playing = player_list.get(i).chooseAction(choice, player_list);
					}
					else {
						System.out.println("\nPlease enter a valid action");
						continue;
					}
				}
			}
		
			for(Player player : player_list) {
				player.setHandValue();
				if(player.getHand_Value() <= 21) {
					theDealer.draw_more();
					break;
				}
			}
			//dealer draws remaining cards until threshold of 17
			
			//Loop through player list and compare vs dealer_hand
			theDealer.deduce_winner(player_list);
			
			//removes extra hands from playerlist
			for(int i = 0; i < player_list.size(); i++) {
				if(player_list.get(i).isExtraHand())
					player_list.remove(i);
			}
			
			boolean endmenu = true;
			while(endmenu) {
				System.out.println("[Play] again\n[Return] to menu");
				String continueplaying = scanner.nextLine().toLowerCase();
				switch (continueplaying) {
				case "play":
					endmenu = false;
					System.out.println("\nNext game!");
					break;
				
				case "return":
					game_running = false;
					endmenu = false;
					break;
					
				default:
					System.out.println("Please enter a valid option");
				}
			}
		

		
		}
	}
	
	
	
	private String [] LoginOrRegister(Scanner scanner, SQL sql) {
		while(true) {
			System.out.println("Do you want to login or register?");
			String logreg = scanner.nextLine().toLowerCase();
			if(logreg.equals("login") || logreg.equals("register")) {
				System.out.print("Enter Username\n>> ");
				String username = scanner.nextLine();
				System.out.print("Enter Password\n>> ");
				String password = scanner.nextLine();
				switch (logreg) {
				case "login":
					if(sql.Login(username, password)) {
						String [] logininfo = {username, password};
						return logininfo;
					}
					else {
						System.out.println("Login failed");
						continue;
					}
				case "register":
					if(sql.NewUser(username, password, 500)) {
						System.out.println("Registering successful");
						continue;
					}
					else {
						System.out.println("Registering failed");
						continue;
					}
				default:
					System.out.println("Please enter login or register");
					continue;
				}
			}
				else
					continue;
		}
	}
	
	
	private void addPlayer(ArrayList<Player> player_list, Scanner scanner, SQL sql) {
		String [] addedplayer = this.LoginOrRegister(scanner, sql);
		Player player = new Player(addedplayer[0], addedplayer[1], sql.getBalance(addedplayer[0], addedplayer[1]));
		player_list.add(player);
		System.out.println("Player "+addedplayer[0]+ " has been added to the game");
	}
	
	private void removePlayer(ArrayList<Player> player_list, Scanner scanner) {
		boolean removed = false;
		System.out.println("Which player would you like to remove?\n---------------------\nPlayer List\n---------------------");
		for(Player player : player_list) {
			System.out.println(player.getName());
		}
		
		System.out.println("---------------------");
		String removeplayer = scanner.nextLine();
		
		for(Player player : player_list) {
			if(player.getName().equals(removeplayer)) {
				player_list.remove(player);
				removed = true;
				break;
			}
		}
		
		if(removed)
			System.out.println(removeplayer+" has been removed from the game");
		else
			System.out.println("No player by the name of "+removeplayer+" is playing in this game");
	}
}