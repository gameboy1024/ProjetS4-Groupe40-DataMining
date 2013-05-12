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
import projets4.case1.database.Connexion;
import projets4.case1.database.Stream;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

/**
 * Spout for test.
 * 1. Read the data from the data file and generate a list.
 * 2. Generator randomly an output from the list.
 * 
 * @author Ahmed
 *
 */
public class DatabaseSpout extends BaseRichSpout{

	private ArrayList<Stream> streams;
	private final int maxSize = 500; 
	private Connexion connexion;
    SpoutOutputCollector _collector;
    private int countForStreams;
    
	public DatabaseSpout(String dataFileName) {
		this.streams = new ArrayList<Stream>();
		countForStreams = 0;
		connexion = new Connexion(dataFileName);
	}
	
	public void establishAconnection() throws JSONException{
		
		connexion.connect();
		ResultSet resultSet = connexion
				.query("SELECT * FROM crawler_access where cron_id='9'");
		try {
			int count = 0;
			while (resultSet.next() && count++ < maxSize) {
				String s = resultSet.getString("response");
				JSONArray ja = new JSONArray(s);
				add(ja);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public void add(JSONArray listeStreams) throws JSONException {
		for (int i = 0; i < listeStreams.length(); i++) {
			JSONObject streamJson = listeStreams.optJSONObject(i);
			Stream stream = new Stream(streamJson);
			streams.add(stream);
		}
	}
	
	
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		_collector = collector;
		
		try {
			this.establishAconnection();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void nextTuple() {
        Utils.sleep(100);
        if (countForStreams >= streams.size()) return;
        long viewsCount = Long.parseLong(streams.get(countForStreams).getChannel().getViews_count());
        _collector.emit(new Values(streams.get(countForStreams).getChannel().getId(),new VideoInfoVariant(-1,viewsCount)));
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("obj", "count"));
		
	}
}