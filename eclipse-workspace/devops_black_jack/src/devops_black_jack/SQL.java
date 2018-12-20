package devops_black_jack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {
	
	Connection connect;
	public SQL(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}   
		catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost/Casino?" + "user=root");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	//RETURNS TRUE IS SUCCESSFUL, FALSE IF NOT	
	public boolean NewUser(String username, String password, int balance) {
		try {
			PreparedStatement s = connect.prepareStatement("insert into account values(default, ?, ?, ?)");
			s.setString(1, username);
			s.setString(2, password);
			s.setInt(3, balance);
			s.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//RETURNS TRUE IF SUCCESSFUL, FALSE IF NOT
	public boolean Login(String username, String password){
		try {
			Statement s = connect.createStatement();
			ResultSet rs = s.executeQuery("select * from account where username ='"+username+"' and password='"+password+"'");
		if(rs.next())
			return true;
		else
			return false;
		}
		 catch (SQLException e) {
			e.printStackTrace();
			return false;
	}
	}
		
	public int getBalance(String username, String password) {
		ResultSet balance = null;
		int intbalance = -1;
		try {
			Statement s = connect.createStatement();
			balance = s.executeQuery("select balance from account where username ='"+username+"' and password='"+password+"'");
			if (balance.next())
				intbalance = balance.getInt("Balance");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return intbalance;
	}
	
	public boolean setBalance(String username, int balance) {
		try {
			PreparedStatement s = connect.prepareStatement("update account set balance='"+balance+"' where username = '"+username+"'");
			s.executeUpdate();
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
}