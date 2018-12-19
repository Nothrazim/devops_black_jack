package devops_black_jack;

public class Card {
	String name;
	int value;
	
	Card(String name, int value){
		this.name = name;
		this.value = value;
	}
	
	void courtCard(int value){
		System.out.println("HI!");
	}
	
}
