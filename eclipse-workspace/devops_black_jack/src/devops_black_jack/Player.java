package devops_black_jack;

public class Player {
	int pott;
	int balance;
	String name;
	int hand_value;
	int bet;
	
	
	public Player(String name, int balance){
		System.out.println("Welcome " + name + ", you have " + balance +" to play for");
	}
	
	
	
	public int checkBalance() {
		
		return balance;
	}
	
	void playerHand() {
		
		
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
		// TODO Auto-generated method stub
		return bet;
	}
	
	//end
}
