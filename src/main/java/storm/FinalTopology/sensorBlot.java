package storm.FinalTopology;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import storm.SOS.SOSWrapper;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/8.
 */
public class sensorBlot extends BaseRichBolt {
    OutputCollector collector;
    SOSWrapper sosWrapper;
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector=outputCollector;
    }

    public void execute(Tuple tuple) {
       sosWrapper=(SOSWrapper)tuple.getValueByField("SOS");
        sosWrapper.Insert();
        System.out.print("Success!");
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
