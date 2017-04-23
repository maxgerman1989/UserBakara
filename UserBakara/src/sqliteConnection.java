import java.sql.*;
import javax.swing.*;

public class sqliteConnection {
	Connection con = null;
	
	public static Connection dbConnector()
	{
		try{
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Max\\workspace\\UserBakara\\employeeDB.sqlite");
			JOptionPane.showMessageDialog(null, "Connection Succefull");
			return con;
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.toString());
			return null;
		}
	}
	

}
