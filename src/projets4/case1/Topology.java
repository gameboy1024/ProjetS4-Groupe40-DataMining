package projets4.case1;


import projets4.case1.bolt.ArimaBolt;
import projets4.case1.bolt.FilterBolt;
import projets4.case1.spout.RandomSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
/**
 * This is a storm topology using random generated tuples as inputs
 *
 */
public class Topology {
public static void main(String[] args) throws Exception {
        
        TopologyBuilder builder = new TopologyBuilder();
        
        builder.setSpout("RandomSpout", new RandomSpout(), 2);

        builder.setBolt("SliderBolt", new FilterBolt(), 8)
                 .fieldsGrouping("RandomSpout", new Fields("obj"));
        builder.setBolt("ArimaBolt", new ArimaBolt(), 12)
                 .fieldsGrouping("SliderBolt", new Fields("obj"));

        Config conf = new Config();
        conf.setDebug(true);

        
        if(args!=null && args.length > 0) {
            conf.setNumWorkers(3);
            
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {        
            conf.setMaxTaskParallelism(3);

            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("Case1", conf, builder.createTopology());
        
            Thread.sleep(3000000);

            cluster.shutdown();
        }
    }
}