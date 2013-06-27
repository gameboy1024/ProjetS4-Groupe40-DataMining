package projets4.case2.database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnexionSmartb implements Serializable{
	/**
	 * 
	 */
	private String dBPath;
	private String user;
	private String password;
	private Connection connection;
	private Statement statement = null;
	
	
	public ConnexionSmartb(String dBPath, String user, String password) {

		this.dBPath = dBPath;
		this.user = user;
		this.password = password;
		System.out.println("jdbc:mysql:" + dBPath + "?" + "user=" + this.user
				+ "&password=" + this.password);

	}

	public void connect() throws SQLException {

		//DriverManager.getConnection("jdbc:mysql://localhost/test?"
				//+ "user=root&password=");
		connection = DriverManager.getConnection("jdbc:mysql:" + dBPath,this.user,this.password);
		//connection = DriverManager.getConnection("jdbc:mysql:" + dBPath
				// + "user=" + this.user + "&password=" + this.password);
		// + "//localhost/test?" + "user=monty&password=greatsqldb");
		
		statement = connection.createStatement();

		System.out.println("Connexion a " + dBPath + " avec succees");	
		
	}
	public ResultSet query(String requet) throws SQLException {
		ResultSet resultat = null;
			resultat = statement.executeQuery(requet);
		return resultat;
	}
	public void close() throws SQLException {
		
			connection.close();
			statement.close();
	}
}