package devops_black_jack;


import java.util.ArrayList;

public class Player {
	
	double balance;
	String name;
	double bet;
	ArrayList<Card> hand = new ArrayList<Card>();
	
	public Player(String name, double balance){
		this.balance = balance;
		this.name = name;
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
	
}
