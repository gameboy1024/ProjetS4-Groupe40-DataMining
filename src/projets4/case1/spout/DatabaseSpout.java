package projets4.case1.spout;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import projets4.case1.basic.VideoInfoVariant;
import projets4.case1.connectors.ConnectorDataBase;
import projets4.case1.database.Connexion;
import projets4.case1.database.Stream;
import projets4.exceptions.EndDataBaseException;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

/**
 * Spout for test. 1. Read the data from the data file and generate a list. 2.
 * Generator randomly an output from the list.
 * 
 * @author Ahmed
 * 
 */
public class DatabaseSpout extends BaseRichSpout {

	private ArrayList<Stream> streams;
	private ConnectorDataBase connector;
	SpoutOutputCollector _collector;
	private int countForStreams;

	public DatabaseSpout(String dataFileName) {
		this.streams = new ArrayList<Stream>();
		connector = new ConnectorDataBase(dataFileName);
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

		long viewsCount = streams.get(countForStreams++).getChannel()
				.getViews_count();
		_collector.emit(new Values(streams.get(countForStreams).getChannel()
				.getId(), new VideoInfoVariant(-1, viewsCount)));

	}

	public void nextTupleForTest() throws JSONException, SQLException,
			EndDataBaseException {
		readNextStream();
		long viewsCount = streams.get(countForStreams).getChannel()
				.getViews_count();
		System.out.print("ID: "
				+ streams.get(countForStreams++).getChannel().getId());

		System.out.println("   Count: " + viewsCount);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("obj", "count"));

	}
}