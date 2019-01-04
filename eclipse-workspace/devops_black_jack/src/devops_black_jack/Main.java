package devops_black_jack;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	
	public static void main(String[] args) {
		System.out.println("Welcome to the scuffed casino");
		Scanner scanner=new Scanner(System.in);
		SQL sql = new SQL();
		
		Main game = new Main();
		String [] logininfo = game.LoginOrRegister(scanner, sql, false);
		
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
			System.out.println("[Add] player\n"
					+ "[Play] blackjack\n"
					+ "[Change] deck\n"
					+ "[Rules]\n"
					+ "[Balance]");
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
				game.gameloop(Deck, scanner, player_list, theDealer);
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
				try { sql.connect.close(); }
				catch (Exception e) { System.err.println("Exiting without closing database connection!");}
				System.exit(0);
			case "balance":
				game.balanceOptions(player_list, scanner, sql);
				break;
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
	private void gameloop(Deck Deck, Scanner scanner, ArrayList<Player> player_list, Dealer theDealer) {
		String[] player_choices = {"Hit", "Stand", "Double", "Split"};
		boolean game_running = true;
			while(game_running) {
			
			for (Player player: player_list) {
				System.out.println(player.getName() +" your balance is: " + player.getBalance());
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
				player.initialdraw();
				if (player_list.size() > 1)
					player.printHand();
				}
			
			//FOR NOW IT JUST PRINTS THE PLAYERS NAME TWICE FOR TWO HANDS
			//PROPER BALANCE CHECKING NEEDS TO BE DONE TOO, AS IT STANDS THE HANDS HAVE INDIVIDUAL BALANCES, VERY BAD
			boolean split = false;
			for (int i = 0; i < player_list.size(); i++) {
				if(split)
					i--;
				boolean playing = true;
				boolean doubledown = false;
				split = false;
				//player_list.get(i).extraHandCounter +
				while(playing) {
					split = false;
					player_list.get(i).printHand();
					player_list.get(i).setHandValue();
					System.out.println("Total value of hand: "+player_list.get(i).getHand_Value());
					if (player_list.get(i).getHand_Value() == 21) {
						break;
					}
					System.out.println("What do you want to do?\n" + player_choices[0] + "\n"+ player_choices[1]);
					if (player_list.get(i).getBalance() >= player_list.get(i).getBet()) {
						System.out.println(player_choices[2]);
						doubledown = true;
					}
					if (player_list.get(i).getHand().size() > 1 && player_list.get(i).getHand().get(0).getName().substring(0, 3).equals(player_list.get(i).getHand().get(1).getName().substring(0, 3)) && player_list.get(i).getBalance() >= player_list.get(i).getBet() && player_list.get(i).getHand().size() <= 2) {
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
					theDealer.draw_more(); //dealer draws remaining cards until threshold of 17
					break;
				}
			}
			
			//Loop through player list and compare vs dealer_hand
			theDealer.deduce_winner(player_list);
			
			//removes extra hands from playerlist
			for(int i = 0; i < player_list.size(); i++) {
				if(player_list.get(i).isExtraHand())
					player_list.remove(i);
			}
			
			boolean endmenu = true;
			while(endmenu) {
				
				System.out.println("[Play] again\n"
						+ "[Return] to menu");
				String continueplaying = scanner.nextLine().toLowerCase();
				Deck.clear_deck();
				Deck.create_deck();
				Deck.add_decks(Deck.getDeckCount());
				Deck.shuffle_deck();
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
	
	
	
	private String [] LoginOrRegister(Scanner scanner, SQL sql, boolean addplayer) {
		while(true) {
			System.out.println("Do you want to \n[Login]\n[Register]");
			if(addplayer)
				System.out.println("[Back]");
			String logreg = scanner.nextLine().toLowerCase();
			if(logreg.equals("login") || logreg.equals("register") || (logreg.equals("back") && addplayer)) {
				if(logreg.equals("back")) {
					String [] what = {"0"};
					return what;
				}
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
		String [] addedplayer = this.LoginOrRegister(scanner, sql, true);
		if(addedplayer.length != 1) {	
		Player player = new Player(addedplayer[0], addedplayer[1], sql.getBalance(addedplayer[0], addedplayer[1]));
		player_list.add(player);
		System.out.println("Player "+addedplayer[0]+ " has been added to the game");
		}
		
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
	
	private void balanceOptions(ArrayList<Player> player_list, Scanner scanner, SQL sql) {
		boolean menuing = true;
		String playername = null;
		if(player_list.size() == 1)
			playername = player_list.get(0).getName();
		while(menuing) {
			double money = 0;
			System.out.println("What would you like to do?\n"
					+ "[Check] balance\n"
					+ "[Add] funds\n"
					+ "[List] of players\n"
					+ "[Back]");
			String option = scanner.nextLine().toLowerCase();
			switch (option) {
			case "add":
				if(player_list.size() != 1) {
					System.out.println("Who would you like to add funds to?\n>>");
					playername = scanner.nextLine();
					}
				System.out.println("How much would you like to add?");
				boolean submenuing = true;
				while(submenuing) {
					String moneystring = scanner.nextLine();
					try {
						money = Double.parseDouble(moneystring);
						if(money < 0) {
							System.out.println("Please enter a number greater than zero");
							continue;
						}
						submenuing = false;
					}
					catch(Exception e) {
						System.out.println("Please enter a number");
						continue;
					}
				}
				for(Player player : player_list) {
					if(playername.equals(player.getName())) {
						sql.updateBalance("win", player.getName(), player.getPassword(), money);
						System.out.println(money+" has been added to your account");
						break;
					}
				}
				break;
			case "back":
				menuing = false;
				break;
			case "list":
				System.out.println("------- PLAYERS -------");
				for(Player player : player_list) {
					System.out.println(player.getName());
				}
				break;
			case "check":
				if(player_list.size() !=1) {
					System.out.println("Which players balance would you like to check?");
					playername = scanner.nextLine();
				}
				boolean success = false;
				for(Player player : player_list) {
					if(player.getName().equals(playername)) {
						System.out.println(player.getName()+" your balance is "+player.getBalance());
						success = true;
					}
						
					
				}
				if(!success)
					System.out.println("No player by that name is in this game");
				break;
			default:
				System.out.println("Please enter a valid option");
				break;
			}
		}
	}
}
