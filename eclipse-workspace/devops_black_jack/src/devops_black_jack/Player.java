package devops_black_jack;

public class Player {
	int pott;
	int balance;
	
	
	
	public Player(String name, int balance){
		System.out.println("Welcome " + name + ", you have " + balance +" to play for");
	}
	
	
	
	public int checkBalance() {
		
		return balance;
	}
	
	void playerHand() {
		
	}
	
	void choose() {
		//hit/stand/double/split
	}
	
	
}
