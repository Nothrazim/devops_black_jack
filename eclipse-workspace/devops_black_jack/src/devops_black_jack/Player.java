
package devops_black_jack;
import java.util.ArrayList;

public class Player {
	
	int hand_value;
	double balance;
	double bet;
	String name;
	String password;
	Deck Deck;
	SQL sql;
	boolean extraHand = false;
	
	ArrayList<Card> hand = new ArrayList<Card>();
	
	public Player(String name, String password, double balance){
		this.balance = balance;
		this.name = name;
		this.password = password;
		Deck = new Deck();
		sql = new SQL();
	}
	
	boolean chooseAction(String choice, ArrayList<Player> player_list) {
		boolean returnvalue;
		switch (choice) {
		case "hit":
			returnvalue = Hit();
			return returnvalue;
		case "stand":
			returnvalue = Stand();
			return returnvalue;
		case "double":
			returnvalue = Doubledown();
			return returnvalue;
		case "split":
			returnvalue = Split(player_list);
			return returnvalue;
		default:
			return true;
		}
	}
	
	boolean Hit() {
		System.out.println("\nHit!");
		this.drawcard();
		this.setHandValue();
		if(this.getHand_Value()>21) {
			System.out.println(this.getName() + ", your hand contains:");
			this.printHand();
			System.out.println("Total value of hand: "+this.getHand_Value());
			System.out.println("Bust!\n");
			return false;
	}
		return true;
	}

	boolean Stand() {
		System.out.println("\nStand!");
		return false;
	}
	
	boolean Doubledown() {
		System.out.println("\nDoubling down!");
		this.setBet(this.getBet()*2);
		sql.updateBalance("bet", this.name, this.password, this.getBet());
		this.drawcard();
		this.setHandValue();
		System.out.println(this.getName() + ", your hand contains:");
		this.printHand();
		System.out.println("Total value of hand: "+this.getHand_Value());
		if(this.getHand_Value() > 21)
			System.out.println("Bust!\n");
		return false;
	}

	boolean Split(ArrayList<Player> player_list) {
		System.out.println("\nSplit!");
		//test
		int indexcount = 0;
		for(Player player:player_list) {
			if(player.getName().equals(this.getName()))
					indexcount++;
			
		}
		for(int i = 0; i < player_list.size(); i++) {
			if(player_list.get(i).getName().equals(this.getName())) {
				player_list.add(i+indexcount, new Player(this.getName(), this.getPassword() ,this.getBalance()));
				break;
			}
		}
		
				
		for(int i = 0; i < player_list.size(); i++) {
			if(player_list.get(i).getName().equals(this.getName())) {
				player_list.get(i+indexcount).setBet(this.getBet());
				player_list.get(i+indexcount).hand.add(this.hand.get(this.hand.size()-1));
				this.hand.remove(this.hand.size()-1);
				player_list.get(i+indexcount).setExtraHand(true);
				return true;
			}
		}
		//test
		return false;
	}	
	
	void drawcard() {
		Card card = Deck.draw_card();
		hand.add(card);
	}
	
	public void updateBalance(double difference) {
		sql.updateBalance("win", this.name, this.password, difference);
		balance = balance+difference;
	}

	public double getBalance() {
		balance = sql.getBalance(this.name, this.password);
		return balance;
	}
	
	public void setBet(double bet) {
		sql.updateBalance("bet", this.name, this.password, bet);
		this.bet = bet;	
	}
	
	public double getBet() {
		return bet;
	}
	
	public void setHandValue() {
		this.hand_value = 0;
		for (Card card: this.hand) {
			this.hand_value += card.value;
			}
		if(this.hand_value > 21) {
			for(Card card: this.hand) {
				if(card.getValue() == 11) {
					card.setAceToValueOne();
					break;
				}
			}
		}
		this.hand_value = 0;
		for (Card card: this.hand) {
			this.hand_value += card.value;
			if(this.hand.size() == 1) 
				if(card.value == 1)
				card.setAceToValueEleven();
		}
	}

	public void printHand() {
		for (Card card: this.hand) {
			System.out.println("	"+card.name);
			}
		System.out.println("");
		}	
	
	public String getName() {
		return name;
	}
	
	private String getPassword() {
		return this.password;
	}
	
	public int getHand_Value() {
		return this.hand_value;
	}
	
	public void resetHand() {
		this.hand.clear();
	}
	
	public boolean isExtraHand() {
		return extraHand;
	}

	public void setExtraHand(boolean extraHand) {
		this.extraHand = extraHand;
	}
	
}