package projets4.case1;

import projets4.case1.bolt.ArimaBolt;
import projets4.case1.bolt.FilterBolt;
import projets4.case1.bolt.WriteToFileBolt;
import projets4.case1.spout.ReadFromFileSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

/**
 * This topology uses input entries read from a text file. It is used for test
 * and demonstration of the validity of the predictions
 * 
 * @author sbt
 * 
 */
public class TopologyReadFromFile {
	public static void main(String[] args) throws Exception {

		TopologyBuilder builder = new TopologyBuilder();
		// Modify the text file for your own case
		
		builder.setSpout("ReadFromFileSpout", new ReadFromFileSpout(
				"/home/sbt/inputs"), 100);
		
		builder.setBolt("SliderBolt", new FilterBolt(), 5).fieldsGrouping(
				"ReadFromFileSpout", new Fields("obj"));
		
		builder.setBolt("ArimaBolt", new ArimaBolt(), 10).fieldsGrouping(
				"SliderBolt", new Fields("obj"));
		
		builder.setBolt("WriteToFileBolt",
				new WriteToFileBolt("/home/sbt/outputs"), 1).fieldsGrouping(
				"ArimaBolt", new Fields("obj"));

		Config conf = new Config();
		conf.setDebug(false);

		if (args != null && args.length > 0) {
			conf.setNumWorkers(100);

			StormSubmitter.submitTopology(args[0], conf,
					builder.createTopology());
		} else {
			conf.setMaxTaskParallelism(100);

			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("Case1", conf, builder.createTopology());

			Thread.sleep(30000);

			cluster.shutdown();
		}
	}
}