package projets4.case1.test;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import projets4.case1.spout.RealStreamsSpout;
import projets4.exceptions.EndDataBaseException;

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
		RealStreamsSpout spout = new  RealStreamsSpout("http://api.justin.tv/api/stream/list.json");
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