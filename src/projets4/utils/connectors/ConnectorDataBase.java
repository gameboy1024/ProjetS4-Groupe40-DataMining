package projets4.utils.connectors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import projets4.case1.database.Connexion;
import projets4.case1.database.Stream;
import projets4.exceptions.EndDataBaseException;

public class ConnectorDataBase{

	private Connexion connexion;
	private ResultSet resultSet;
	private static final int NEW_STREAM_SIZE = 100;
	
	public ConnectorDataBase(String dataFileName) {
		connexion = new Connexion(dataFileName);
		connexion.connect();
		resultSet = connexion
				.query("SELECT * FROM crawler_access where cron_id='9'");
		connexion = new Connexion(dataFileName);

	}

	public LinkedList<Stream> readNextSream() throws JSONException,
			SQLException, EndDataBaseException {
		LinkedList<Stream> newStreams = new LinkedList<Stream>();
		for (int i = 0; i < NEW_STREAM_SIZE; i++) {
			String s = resultSet.getString("response");
			JSONArray ja = new JSONArray(s);
			newStreams.addAll(getStramfromJsonArray(ja));
		}
		return newStreams;
	}

	public LinkedList<Stream> getStramfromJsonArray(JSONArray listeStreams)
			throws JSONException {
		LinkedList<Stream> streams = new LinkedList<Stream>();
		for (int i = 0; i < listeStreams.length(); i++) {
			JSONObject streamJson = listeStreams.optJSONObject(i);
			Stream stream = new Stream(streamJson);
			streams.add(stream);
		}
		return streams;
	}
}