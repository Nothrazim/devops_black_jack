package devops_black_jack;



import java.util.ArrayList;

public class Player {
	
	int hand_value;
	double balance;
	String name;
	double bet;
	Deck Deck;
	ArrayList<Card> hand = new ArrayList<Card>();
	
	public Player(String name, double balance){
		this.balance = balance;
		this.name = name;
		Deck = new Deck();
	}
	
	String Doubledown() {
		if(balance>=bet*2) {
			bet = bet*2;
			return "You have doubled down";
		}
			
		else
			return "You dont have enough credits";
	}
	
	void drawcard() {
		Card card = Deck.draw_card();
		hand.add(card);
	}
	
	public void setBet(double bet) {
		this.bet = bet;
		
		
	}
	public int setHandValue() {
		for (Card card: this.hand) {
			this.hand_value += card.value;
			}
		return hand_value;
	}
	
	public void updateBalance(int difference) {
		balance = balance+difference;
	}
	
}