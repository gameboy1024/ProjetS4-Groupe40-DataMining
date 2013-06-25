/**
 * 
 */
package projets4.case1.bolt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import projets4.basic.VideoInfoVariant;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

/**
 * @author sbt
 * 
 */
public class WriteToFileBolt extends BaseRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1728837723055319821L;
	private PrintWriter printWriter;
	private FileWriter fileWriter;
	private String targetFileName;
	
	public WriteToFileBolt(String targetFileName) {
		this.targetFileName = targetFileName;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see backtype.storm.task.IBolt#prepare(java.util.Map,
	 * backtype.storm.task.TopologyContext, backtype.storm.task.OutputCollector)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		try {
			fileWriter = new FileWriter(targetFileName, true);
			printWriter  = new PrintWriter(fileWriter);

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
		String videoName = input.getString(0);
		double[] prediction = (double [])input.getValue(1);
		String predictionString = "";
		for (double d : prediction) {
			predictionString += '\t' + d;
		}
		printWriter.println(videoName + "\t" + predictionString);
		printWriter.flush();
		try {
			fileWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
