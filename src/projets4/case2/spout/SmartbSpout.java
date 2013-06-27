package projets4.case2.spout;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONException;

import projets4.case2.database.Plug;
import projets4.exceptions.EndDataBaseException;
import projets4.utils.connectors.ConnectorSmartb;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;

/**
 * Spout for test. 1. Read the data from the data file and generate a list. 2.
 * Generator randomly an output from the list.
 * 
 * @author Ahmed
 * 
 */
public class SmartbSpout extends BaseRichSpout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8157712309140940517L;
	private ArrayList<Plug> streams;
	private ConnectorSmartb connector;
	SpoutOutputCollector _collector;
	private int countForStreams;

	public SmartbSpout(String path,String user,String pass) throws SQLException {
		this.streams = new ArrayList<Plug>();
		connector = new ConnectorSmartb(path,user,pass);
		countForStreams = 0;
	}

	public void readNextStream() throws JSONException, SQLException,
			EndDataBaseException {
		while (countForStreams >= streams.size()) {
			System.out
					.println("-------------------------------------------------------------");
			streams = connector.readNextSream();
			countForStreams = 0;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		_collector = collector;
	}

	@Override
	public void nextTuple() {
		Utils.sleep(100);
		try {
			readNextStream();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EndDataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//double power = streams.get(countForStreams++).getPower();
		//_collector.emit(new Values(streams.get(countForStreams).getId(), ));

	}

	public void nextTupleForTest() throws JSONException, SQLException,
			EndDataBaseException {
		readNextStream();
		double power = streams.get(countForStreams).getPower();
		System.out.print("ID: "
				+ streams.get(countForStreams++).getId());

		System.out.println("   Power: " + power);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("obj", "count"));

	}
}
