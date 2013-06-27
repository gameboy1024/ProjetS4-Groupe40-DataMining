package projets4.case2.test;

import java.sql.SQLException;

import org.json.JSONException;

import projets4.case2.spout.SmartbSpout;
import projets4.exceptions.EndDataBaseException;

public class DatabaseTest {

	/**
	 * @param args
	 * @throws JSONException
	 * 
	 * @throws EndDataBaseException jfrijrf[rjfnhriwiru
	 * @throws SQLException
	 */
	public static void main(String[] args) throws JSONException, SQLException, EndDataBaseException {
		// TODO Auto-generated method stub

		/*DatabaseSpout spout = new DatabaseSpout(
				"/home/hmed/workspace/sqliteTest/BD/2012-08-01.db");*/
		SmartbSpout spout = new  SmartbSpout("//localhost/smartb", "root",	"");

		//RealStreamsSpot spout = new  RealStreamsSpot("http://api.justin.tv/api/stream/list.json");
		while (true) {
			
				spout.nextTupleForTest();
			

		}
	}

}
