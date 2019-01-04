package devops_black_jack;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {
	
	final static String DB_URL = "jdbc:mysql://java.bx.nimell.se:33306/casino";
	final static String mySQLUser = "coffee";
	final static String mySQLPass = "hunter12";
	
	Connection connect;
	public SQL(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}   
		catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			connect = DriverManager.getConnection(DB_URL, mySQLUser, mySQLPass);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	//RETURNS TRUE IS SUCCESSFUL, FALSE IF NOT	
	public boolean NewUser(String username, String password, double balance) {
		if (this.Login(username, password))  // Try logging in before creating new user
			return true;
		else {
			try {
				PreparedStatement s = connect.prepareStatement("insert into account values(default, ?, ?, ?)");
				s.setString(1, username);
				s.setString(2, password);
				s.setDouble(3, balance);
				s.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}}
		return true;
	}
	//RETURNS TRUE IF SUCCESSFUL, FALSE IF NOT
	public boolean Login(String username, String password){
		try (Statement s = connect.createStatement()) {
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
		
	public double getBalance(String username, String password) {
		ResultSet balance = null;
		double intbalance = -1;
		try (Statement s = connect.createStatement())
			{ balance = s.executeQuery("select balance from account where username ='"+username+"' and password='"+password+"'");
			if (balance.next())
				intbalance = balance.getDouble("Balance");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return intbalance;
	}
	
	public boolean setBalance(String username, double balance) {
		BigDecimal decimalbalance = BigDecimal.valueOf(balance);
		try (PreparedStatement s = connect.prepareStatement(
				"update account set balance='"+decimalbalance+
				"' where username = '"+username+"'")) {
			s.executeUpdate();
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean updateBalance(String BetOrWin ,String username, String password, double bet) {
			bet = Math.abs(bet);
			if(BetOrWin.toLowerCase() == "bet")
				bet = -bet;
			BigDecimal bdbet = BigDecimal.valueOf(bet);
		try (PreparedStatement s = connect.prepareStatement(
				"update account set balance=balance + '"+bdbet+
				"' where username='"+username+"' and password='"+password+"'")) {
			s.executeUpdate();
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}