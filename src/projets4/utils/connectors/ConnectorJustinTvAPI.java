package projets4.utils.connectors;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import projets4.case1.database.Stream;

public class ConnectorJustinTvAPI {

	private HttpGet httpget;

	public ConnectorJustinTvAPI(String dataFileName) {
		httpget = new HttpGet(dataFileName);
	}

	public ArrayList<Stream> readNextSream() throws ClientProtocolException, IOException, JSONException {
		HttpClient httpclient = new DefaultHttpClient();
		ResponseHandler<String> gestionnaire_reponse = new BasicResponseHandler();
        String reponse = httpclient.execute(httpget, gestionnaire_reponse);
        JSONArray ja = new JSONArray(reponse);
		return (getStramfromJsonArray(ja));
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