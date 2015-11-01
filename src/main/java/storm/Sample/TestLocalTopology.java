package storm.Sample;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import storm.Sample.component.TestBolt;
import storm.Sample.component.TestSpout;

public class TestLocalTopology {

  public static void main(String[] args) throws Exception {
    try {
      TopologyBuilder topologyBuilder = new TopologyBuilder();
      topologyBuilder.setSpout("SimpleSpout", new TestSpout(), 1);
      topologyBuilder.setBolt("SimpleBolt", new TestBolt(), 3).shuffleGrouping("SimpleSpout");
      Config config = new Config();
      config.setDebug(true);

      config.setMaxTaskParallelism(1);
      LocalCluster cluster = new LocalCluster();
      cluster.submitTopology("simple", config, topologyBuilder.createTopology());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}