package projets4.case1.spout;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import projets4.case1.basic.VideoInfoVariant;
import projets4.case1.connectors.ConnectorJustinTvAPI;
import projets4.case1.database.Stream;
import projetsS4.case1.Exeptions.EndDataBaseException;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class RealStreamsSpot {
	private ArrayList<Stream> streams;
	private ConnectorJustinTvAPI connector;
	SpoutOutputCollector _collector;
	private int countForStreams;

	public RealStreamsSpot(String dataFileName) {
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

	public void nextTuple() throws ClientProtocolException, IOException,
			JSONException {
		Utils.sleep(100);
		readNextStream();

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
