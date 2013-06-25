package projets4.case2;


import projets4.case2.bolt.ArimaBolt;
import projets4.case2.spout.RandomSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
/**
 * Case 2.
 * This is a storm topology using random generated tuples as inputs
 *
 */
public class Topology {
public static void main(String[] args) throws Exception {
        
        TopologyBuilder builder = new TopologyBuilder();
        
        builder.setSpout("RandomSpout", new RandomSpout(), 1);

        builder.setBolt("ArimaBolt", new ArimaBolt(), 12)
                 .fieldsGrouping("RandomSpout", new Fields("obj"));

        Config conf = new Config();
        conf.setDebug(true);

        
        if(args!=null && args.length > 0) {
            conf.setNumWorkers(30);
            
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {        
            conf.setMaxTaskParallelism(3);

            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("Case2", conf, builder.createTopology());
        
            Thread.sleep(30000);

            cluster.shutdown();
        }
    }
}