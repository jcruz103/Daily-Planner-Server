package serverPlanner;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.*;
import com.mysql.*;
import com.mysql.jdbc.*;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import java.io.*;

public class DatabaseConnection {
	
	public DatabaseConnection()
	{
		
	}
	
	public static java.sql.Connection getConnection() throws SQLException, IOException
	{
		String url = "jdbc:mysql://127.0.0.1:3306/TestDB";
		String un = "****";
		String pw = "*****";
		
		return DriverManager.getConnection(url, un, pw);
	}
	
	public void selectContacts(PrintWriter writer)
	{
		try
		{
			Statement stat = conn.createStatement();
			
			ResultSet result = stat.executeQuery("SELECT * FROM test");
			while(result.next())
			{
				for(int i = 1; i < 6; i++)
				{
					writer.println(result.getString(i));
					System.out.println(result.getString(i));
				}
			}
		}
		finally
		{
			conn.close();
		}
	}

}
