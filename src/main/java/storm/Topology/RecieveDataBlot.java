package storm.Topology;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import storm.SOS.SOSWrapper;
import storm.socketOperation.RecieveData;
import storm.spout.SpoutParams;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/8.
 */
public class RecieveDataBlot extends BaseRichBolt {
    OutputCollector collector;

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector=outputCollector;
    }

    public void execute(Tuple tuple) {
        SpoutParams params=(SpoutParams)tuple.getValueByField("SensorID");
        //single thread to solve data recieve
        RecieveData recieveData=new RecieveData(params);
        SOSWrapper sosWrapper= recieveData.getSOSInfoFromRecieveData();
        this.collector.emit(new Values(sosWrapper));
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

        outputFieldsDeclarer.declare(new Fields("SOSWrapper"));
    }
}
