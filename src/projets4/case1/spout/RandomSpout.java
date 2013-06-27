package projets4.case1.spout;

import java.util.Map;
import java.util.Random;

import projets4.case1.data.VideoInfoVariant;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

/**
 * 
 * Class to simulate the servers who generates data.
 * 
 * Not used any more
 * 
 * @author Botu
 *
 */
public class RandomSpout extends BaseRichSpout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4692686503571492341L;
    SpoutOutputCollector _collector;
    Random _rand;   
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
        _collector = collector;
        _rand = new Random();
		
	}

	@Override
	public void nextTuple() {
		Utils.sleep(3000);
		String object = "Test_Video";
		 _collector.emit(new Values(object, new VideoInfoVariant(-1, (long) _rand.nextInt(100))));
		
	}
    @Override
    public void ack(Object id) {
    }

    @Override
    public void fail(Object id) {
    }

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		  declarer.declare(new Fields("obj", "count"));
		
	}

}
