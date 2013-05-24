package projets4.case1.connectors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import projets4.case1.database.Connexion;
import projets4.case1.database.Stream;
import projets4.exceptions.EndDataBaseException;

public class ConnectorDataBase {

	private Connexion connexion;
	private ResultSet resultSet;

	public ConnectorDataBase(String dataFileName) {
		connexion = new Connexion(dataFileName);
		connexion.connect();
		resultSet = connexion
				.query("SELECT * FROM crawler_access where cron_id='9'");
		connexion = new Connexion(dataFileName);

	}

	public ArrayList<Stream> readNextSream() throws JSONException,
			SQLException, EndDataBaseException {

		if (resultSet.next()) {
			String s = resultSet.getString("response");
			JSONArray ja = new JSONArray(s);
			return (getStramfromJsonArray(ja));
		}

		else
			throw new EndDataBaseException();
	}

	public ArrayList<Stream> getStramfromJsonArray(JSONArray listeStreams)
			throws JSONException {
		ArrayList<Stream> streams = new ArrayList<Stream>();
		for (int i = 0; i < listeStreams.length(); i++) {
			JSONObject streamJson = listeStreams.optJSONObject(i);
			Stream stream = new Stream(streamJson);
			streams.add(stream);
		}
		return streams;
	}
}