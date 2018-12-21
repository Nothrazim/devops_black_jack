
package devops_black_jack;
import java.util.ArrayList;

public class Player {
	
	int hand_value;
	double balance;
	double bet;
	String name;
	Deck Deck;
	ArrayList<Card> hand = new ArrayList<Card>();
	
	public Player(String name, double balance){
		this.balance = balance;
		this.name = name;
		Deck = new Deck();
	}
	
	boolean chooseAction(String choice) {
		if (choice.equals("hit")) {
			boolean returnvalue = Hit();
			return returnvalue;
		}
		else if (choice.equals("stand")) {
			boolean returnvalue = Stand();
			return returnvalue;
		}
		else if (choice.equals("double")) {
			boolean returnvalue = Doubledown();
			return returnvalue;
		}
		else if (choice.equals("split")) {
			boolean returnvalue = Split();
			return returnvalue;
		}
		return true;
	}
	
	boolean Hit() {
		System.out.println("\nHit!");
		this.drawcard();
		this.setHandValue();
		if(this.getHand_Value()>21) {
			System.out.println(this.getName() + ", your hand contains:");
			this.printHand();
			System.out.println("Total value of hand: "+this.getHand_Value());
			System.out.println("Bust!");
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
		this.drawcard();
		this.setHandValue();
		System.out.println(this.getName() + ", your hand contains:");
		this.printHand();
		System.out.println("Total value of hand: "+this.getHand_Value());
		if(this.getHand_Value() > 21)
			System.out.println("Bust!");
		return false;
	}

	boolean Split() {
		System.out.println("\nSplit!");
		return false;
	}	
	
	void drawcard() {
		Card card = Deck.draw_card();
		hand.add(card);
	}
	
	public void updateBalance(double difference) {
		balance = balance+difference;
	}

	public double getBalance() {
		return balance;
	}
	
	public void setBet(double bet) {
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
			}
	}

	public void printHand() {
		for (Card card: this.hand) {
			System.out.println(card.name);
			}
		System.out.println("");
		}	
	
	public String getName() {
		return name;
	}
	
	public int getHand_Value() {
		return this.hand_value;
	}
	
}