package projets4.case1.spout;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

import projets4.case1.data.VideoInfoVariant;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Time;
import backtype.storm.utils.Utils;

/**
 * 
 * This is a spout who reads input entries from a text file. Used for test and
 * demonstrate purpose.
 * 
 * @author sbt
 * 
 */
public class ReadFromFileSpout extends BaseRichSpout {

	private static final long serialVersionUID = 6386043982761692136L;
	private SpoutOutputCollector _collector;
	// File operators
	private FileReader fileReader = null;
	private BufferedReader bufferedReader = null;
	private String sourceFileName;
	private ArrayList<Long> inputs;
	// To distinguish different videos, we generate a random int as video ID
	private int videoId;
	private static final Logger LOG = Logger.getLogger(ReadFromFileSpout.class);
	
	public ReadFromFileSpout(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		_collector = collector;
		Random random = new Random(Time.currentTimeMillis());
		this.videoId = random.nextInt(10000);
		this.inputs = new ArrayList<Long>();
		
		try {
			fileReader = new FileReader(sourceFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while (bufferedReader.ready()) {
				inputs.add(Long.parseLong(bufferedReader.readLine()));
			}
			System.out.println("Data loading finished!");
			bufferedReader.close();
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("File not found : " + sourceFileName);
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@Override
	public void nextTuple() {
		Utils.sleep(1000);
		
		String object = "Test_Video" + this.videoId;
		if (!inputs.isEmpty()) {
			_collector.emit(new Values(object, new VideoInfoVariant(-1, inputs.remove(0))));	
		}else {
			LOG.warn("Input data used up!");
		}

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("obj", "count"));
	}

}
