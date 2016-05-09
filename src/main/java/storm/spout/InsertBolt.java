package storm.spout;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import storm.SOS.SOSWrapper;

import java.util.Map;

/**
 * ���SOSWrapper���XML�ĵ�
 * Created by Yuan on 2016/4/28.
 */
public class InsertBolt extends BaseRichBolt {
    private OutputCollector collector;
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector=collector;
    }

    public void execute(Tuple tuple) {
        SOSWrapper sosWrapper=(SOSWrapper)(tuple.getValueByField("InsertObsXML"));
        //��sosWrapperת��Ϊxml�ĵ�
        sosWrapper.Insert();
        //��xml����sos��
    }

    public void cleanup() {

    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

}
