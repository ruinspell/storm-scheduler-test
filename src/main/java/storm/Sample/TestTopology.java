package storm.Sample;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import storm.Sample.component.TestBolt;
import storm.Sample.component.TestSpout;

public class TestTopology {

  public static void main(String[] args) throws Exception {
    try {
      // 实例化TopologyBuilder类。
      TopologyBuilder topologyBuilder = new TopologyBuilder();
      // 设置喷发节点并分配并发数，该并发数将会控制该对象在集群中的线程数。
      topologyBuilder.setSpout("SimpleSpout", new TestSpout(), 1);
      // 设置数据处理节点并分配并发数。指定该节点接收喷发节点的策略为随机方式。
      topologyBuilder.setBolt("SimpleBolt", new TestBolt(), 3).shuffleGrouping("SimpleSpout");
      Config config = new Config();
      config.setDebug(true);
      config.setNumWorkers(1);
      StormSubmitter.submitTopology(args[0], config, topologyBuilder.createTopology());


    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}