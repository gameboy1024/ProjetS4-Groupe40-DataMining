package projets4.case2.spout;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.json.JSONException;

import projets4.basic.VideoInfoVariant;
import projets4.case1.database.Stream;
import projets4.exceptions.EndDataBaseException;
import projets4.utils.connectors.ConnectorDataBase;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

/**
 * Spout for test. 1. Read the data from the data file and generate a list. 2.
 * Generator randomly an output from the list.
 * 
 * @author Ahmed
 * 
 */
public class DatabaseSpout extends BaseRichSpout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7574387276513792730L;
	private LinkedList<Stream> streams;
	private ConnectorDataBase connector;
	SpoutOutputCollector _collector;
	private static final Logger LOG = Logger.getLogger(DatabaseSpout.class);
	private static final int STREAM_COUNT_TREDHOLD = 30;
	private int countForStreams;
	private String dataFileName;
	
	public DatabaseSpout(String dataFileName) {
		this.dataFileName = dataFileName;
	}
	/**
	 * Get a list of new entries and append to the current entry list
	 * @throws JSONException
	 * @throws SQLException
	 * @throws EndDataBaseException
	 */
	public void readNextStream() throws JSONException, SQLException,
			EndDataBaseException {
			streams.addAll(connector.readNextSream());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		_collector = collector;
		this.streams = new LinkedList<Stream>();
		connector = new ConnectorDataBase(dataFileName);
		countForStreams = 0;
	}

	@Override
	public void nextTuple() {
		// If the current entry list size is smaller than the thredhold, we read new entries
		if (streams.size()<STREAM_COUNT_TREDHOLD) {
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
		}
		//Should be unabled during the real test
		//Utils.sleep(100);
		try {
			long viewsCount = streams.getFirst().getChannel()
					.getViews_count();
			_collector.emit(new Values(streams.getFirst().getChannel()
					.getId(), new VideoInfoVariant(-1, viewsCount)));
			streams.removeFirst();
		} catch (NoSuchElementException e) {
			//If no element left in the list, could be a problem
			LOG.warn("Entry stream list is empty, check the spout!");
		}
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