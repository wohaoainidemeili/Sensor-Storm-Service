package storm.Topology;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import storm.spout.SpoutParams;

import java.util.Map;

/**
 * this Spout is designed to send SensorID by a period
 * the time is decided by the sleep_time of SpoutParams
 * Created by Administrator on 2016/5/8.
 */
public class SensorIDSpout extends BaseRichSpout{
    SpoutOutputCollector collector;
    SpoutParams params;
    // to get the params used in this part
    public void getSpoutParams(SpoutParams params){
        this.params=params;
    }
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("SensorID"));
    }

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector=collector;
    }

    public void nextTuple() {
        //send all the params to the recievedataBlot
        this.collector.emit(new Values(params));

        //sleep the thread
        org.apache.storm.utils.Utils.sleep(params.sleepTime);

        //also if the sleeptime changed we should do something
    }
}
