package serverPlanner;

import java.io.*;
import java.net.*;
import java.sql.*;
import com.mysql.*;
import com.mysql.jdbc.Driver;


public class ServerPlanner {

	PrintWriter writer;
	public static void main(String[] args)
	{
		ServerPlanner plan = new ServerPlanner();
		plan.go();
	}
	public ServerPlanner()
	{
	}
	
	public void go()
	{
		try {
			ServerSocket serverSock = new ServerSocket(1234);

			while (true) {
				Socket serverSocket = serverSock.accept();
				writer = new PrintWriter(serverSocket.getOutputStream(), true);
				Thread t = new Thread(new ClientHandler(serverSocket));
				t.start();
				System.out.println("got a connection");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/************************************************/
	public class ClientHandler implements Runnable {
		BufferedReader reader;
		Socket sock;
		
		public ClientHandler(Socket clientSocket) {
			try {
				sock = clientSocket;
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		public void run() {
			String message;
		
			try 
			{
				while((message = reader.readLine()) != null)
				{
					if(message.toString() != "Get")
					{
						System.out.println(reader.readLine().toString());
						Connection conn = DatabaseConnection.getConnection();
						
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
			}
			catch (Exception ex) 
			{
				ex.printStackTrace();
			}
			

		}
	}
}
