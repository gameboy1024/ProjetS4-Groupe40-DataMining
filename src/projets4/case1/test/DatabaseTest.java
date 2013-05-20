package projets4.case1.test;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import projets4.case1.spout.DatabaseSpout;
import projets4.case1.spout.RealStreamsSpot;
import projetsS4.case1.Exeptions.EndDataBaseException;

public class DatabaseTest {

	/**
	 * @param args
	 * @throws JSONException
	 * @throws EndDataBaseException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws JSONException {
		// TODO Auto-generated method stub

		/*DatabaseSpout spout = new DatabaseSpout(
				"/home/hmed/workspace/sqliteTest/BD/2012-08-01.db");*/
		RealStreamsSpot spout = new  RealStreamsSpot("http://api.justin.tv/api/stream/list.json");
		while (true) {
			try {
				spout.nextTupleForTest();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
