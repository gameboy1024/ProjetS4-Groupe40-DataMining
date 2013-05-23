/**
 * 
 */
package projets4.case1.bolt;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import projets4.case1.basic.VideoInfoVariant;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

/**
 * @author sbt
 * 
 */
public class WriteToFile extends BaseRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1728837723055319821L;
	private PrintWriter pw;
	private FileWriter fw;
	/*
	 * (non-Javadoc)
	 * 
	 * @see backtype.storm.task.IBolt#prepare(java.util.Map,
	 * backtype.storm.task.TopologyContext, backtype.storm.task.OutputCollector)
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		String path = "~/dataOut";
		try {
			FileWriter fw = new FileWriter(path, true);
			 pw = new PrintWriter(fw);

			// pw.close();

			// fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see backtype.storm.task.IBolt#execute(backtype.storm.tuple.Tuple)
	 */
	@Override
	public void execute(Tuple input) {
		Object obj = input.getValue(0);
		VideoInfoVariant videoInfoVariant = (VideoInfoVariant) input.getValue(1);
		long count = videoInfoVariant.getStreamCount();
		pw.println(obj + "\t" + count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * backtype.storm.topology.IComponent#declareOutputFields(backtype.storm
	 * .topology.OutputFieldsDeclarer)
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub

	}

}
