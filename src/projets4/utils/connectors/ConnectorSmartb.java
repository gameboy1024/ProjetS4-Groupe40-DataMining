package projets4.utils.connectors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;

import projets4.case2.database.ConnexionSmartb;
import projets4.case2.database.Plug;
import projets4.exceptions.EndDataBaseException;

public class ConnectorSmartb {
	private ConnexionSmartb connexion;
	private ResultSet resultSet;

	public ConnectorSmartb(String path,String user,String pass) throws SQLException {
		connexion = new ConnexionSmartb(path,user,pass);
		connexion.connect();
		resultSet = connexion.query("SELECT * FROM feed_1");

	}

	public ArrayList<Plug> readNextSream() throws JSONException, SQLException,
			EndDataBaseException {

		if (resultSet.next()) {
			
			double power = resultSet.getDouble("data");
			ArrayList<Plug> stream = new ArrayList<Plug>();
			Plug plug =new Plug(1, power);
			stream.add(plug);
			return stream;
		}

		else
			throw new EndDataBaseException();
	}

}
