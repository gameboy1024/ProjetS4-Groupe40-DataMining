package projets4.case1;

import projets4.case1.bolt.ArimaBolt;
import projets4.case1.bolt.FilterBolt;
import projets4.case1.spout.DatabaseSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class Topology2 {
	public static void main(String[] args) throws Exception {

		TopologyBuilder builder = new TopologyBuilder();
		// Modify the database target for your own case
		builder.setSpout("DatabaseSpout", new DatabaseSpout(
				"/home/sbt/2012-08-01.db"), 1);

		builder.setBolt("SliderBolt", new FilterBolt(), 8).fieldsGrouping(
				"DatabaseSpout", new Fields("obj"));
		builder.setBolt("ArimaBolt", new ArimaBolt(), 12).fieldsGrouping(
				"SliderBolt", new Fields("obj"));

		Config conf = new Config();
		conf.setDebug(false);

		if (args != null && args.length > 0) {
			conf.setNumWorkers(3);

			StormSubmitter.submitTopology(args[0], conf,
					builder.createTopology());
		} else {
			conf.setMaxTaskParallelism(3);

			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("Case1", conf, builder.createTopology());

			Thread.sleep(30000);

			cluster.shutdown();
		}
	}
}