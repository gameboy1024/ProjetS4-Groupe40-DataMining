package projets4.case1.spout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import projets4.basic.VideoInfoVariant;
import projets4.case1.database.Stream;
import projets4.utils.connectors.ConnectorJustinTvAPI;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class RealStreamsSpout extends BaseRichSpout{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3687581634797278512L;
	private ArrayList<Stream> streams;
	private ConnectorJustinTvAPI connector;
	SpoutOutputCollector _collector;
	private int countForStreams;

	public RealStreamsSpout(String dataFileName) {
		this.streams = new ArrayList<Stream>();
		connector = new ConnectorJustinTvAPI(dataFileName);
		countForStreams = 0;
	}

	public void readNextStream() throws ClientProtocolException, IOException,
			JSONException {
		while (countForStreams >= streams.size()) {
			System.out
					.println("-------------------------------------------------------------");
			streams = connector.readNextSream();
			countForStreams = 0;
		}
	}

	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		_collector = collector;
	}

	public void nextTuple() {
		Utils.sleep(100);
			try {
				readNextStream();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		long viewsCount = streams.get(countForStreams++).getChannel()
				.getViews_count();
		_collector.emit(new Values(streams.get(countForStreams).getChannel()
				.getId(), new VideoInfoVariant(-1, viewsCount)));

	}

	public void nextTupleForTest() throws ClientProtocolException, IOException,
			JSONException {
		readNextStream();
		long viewsCount = streams.get(countForStreams).getChannel()
				.getViews_count();
		System.out.print("ID: "
				+ streams.get(countForStreams++).getChannel().getId());

		System.out.println("   Count: " + viewsCount);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("obj", "count"));

	}

}