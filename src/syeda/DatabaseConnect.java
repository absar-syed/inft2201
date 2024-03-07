/**
 * DatabaseConnect - Used to manage database connectivity (opening and closing connections)
 * @author Absar Syed
 */

package syeda;
import java.sql.*;

public class DatabaseConnect
{
	/**
	 * Database location
	 */
	static String url = "jdbc:postgresql://127.0.0.1:5432/inft2201_db";
	/**
	 * Connection object to open port to db
	 */
	static Connection aConnection;
	/**
	 * database user
	 */
	static String user = "inft2201_admin";
	/**
	 * database user password
	 */
	static String password = "inft2201_password";
	
	/**
	 * establishes the database connection
	 * @return Connection to the inft2201_db database
	 */
	public static Connection initialize()
	{
		try
 		{ 	
			Class.forName("org.postgresql.Driver"); // loads the JDBC Driver for PostGreSQL
			aConnection = DriverManager.getConnection(url, user, password); // creates the database connection instance
	    	
	 	}
		catch (ClassNotFoundException e)  //will occur if you did not import the PostGreSQL *.jar file with drivers
		{
			System.out.println(e);
		}
		catch (SQLException e)	//any other database exception (misnamed db, misnamed user, wrong password, etc)
			{ System.out.println(e); }
		return aConnection;
	}

	/**
	 * closes the database connection
	 */
	public static void terminate()
	{

		try
 		{
    		aConnection.close();
		}
		catch (SQLException e)
			{ System.out.println(e);	}
	}

	public static void main(String[] args)
	{

		try{

			initialize();
			System.out.println("Success");

		} catch (Exception e) {
			System.out.println("Failed! \n" + e.getMessage() );
		}

	}

}

