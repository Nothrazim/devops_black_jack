package devops_black_jack;

public class Card {
	String name;
	int value;
	
	Card(String name, int value){
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}
	
	public void setAceToValueOne() {
		value = 1;
	}
	
}
