package devops_black_jack;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello weldt!");	

		System.out.println("FISK");
		System.out.println("THIS IS A TEST");

		System.out.println("The master FISK 2");

		System.out.println("THIS IS A LOT OF TESTS");
		System.out.println("THIS IS A TEST");
		System.out.println("THIS IS A TEST");
		System.out.println("THIS IS A TEST");


		for(int i = 0; i < 100; i++) {
			System.out.println("hehe nr hehe: " + i);
			
		}
		
		SQL sql = new SQL();
		//sql.NewUser("Daniel", "123", 500);
		System.out.println(sql.Login("Daniel", "123"));
		System.out.println(sql.getBalance("Daniel", "123"));
		sql.setBalance("Daniel", 1000);
	}
}
