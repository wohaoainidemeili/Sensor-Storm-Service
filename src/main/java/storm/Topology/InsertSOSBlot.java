package storm.Topology;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import storm.SOS.SOSWrapper;

import java.util.Map;

/**
 * insert data by sos
 * Created by Administrator on 2016/5/8.
 */
public class InsertSOSBlot extends BaseRichBolt {
    private OutputCollector collector;
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector=outputCollector;
    }

    public void execute(Tuple tuple) {
        SOSWrapper sosWrapper=(SOSWrapper)tuple.getValueByField("SOSWrapper");
        sosWrapper.Insert();
        System.out.println("Insert Data Successfully!");
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
