package devops_black_jack;

import java.util.ArrayList;

public class Player {
	double balance;
	String name;
	int hand_value;
	int bet;
	ArrayList<Card> hand = new ArrayList<Card>();
	
	
	public Player(String name, int balance){
		System.out.println("Welcome " + name + ", you have " + balance +" to play for");
	}
	
	
	
	public double checkBalance() {
		//Is to be continued
		
		return balance;
	}


	public String getName() {
		
		return name;
	}



	public void chooseAction(String choice) {
		//hit stand double split
		
		
	}
	
	public int setHandValue() {
        for (Card card: this.hand) {
            this.hand_value += card.value;
            }
        return hand_value;
    }



	public int getBet() {
		return bet;
	}



	public ArrayList<Card> getHand() {
		for (Card player_card: this.hand) {
			System.out.println(player_card);
		}
		return this.hand;
	}



	public void setBet(int player_bet) {
		this.bet= player_bet;
		
	}



	public double updateBalance(double d) {
		//Is to be continued
		
		return balance;
	}
	
	void draw_card() {
		System.out.println("Player begins to draw.");
		Card player_card = Deck.deck.get(0);
		hand.add(player_card);
		Deck.deck.remove(0);
		System.out.println("Player card: " + player_card.getName());
		hand_value += player_card.value;
		System.out.println( getName() + " first value: " + hand_value + "\n");
		
	}
	
	//end
}
