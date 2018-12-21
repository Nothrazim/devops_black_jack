
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
	
	void chooseAction(String choice) {
		if (choice.equals("Hit")) {
			Hit();
		}
		else if (choice.equals("Stand")) {
			Stand();
		}
		else if (choice.equals("Double")) {
			Doubledown();
		}
		if (choice.equals("Split")) {
			Split();
		}
	}
	
	void Hit() {
		System.out.println("Hit!");
	}

	void Stand() {
		System.out.println("Stand!");
	}
	
	String Doubledown() {
		if(balance>=bet*2) {
			bet = bet*2;
			return "You have doubled down";
		}
			
		else
			return "You dont have enough credits";
	}

	void Split() {
		System.out.println("Split!");
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
		}	
	
	public String getName() {
		return name;
	}
	
	public int getHand_Value() {
		return this.hand_value;
	}
	
}