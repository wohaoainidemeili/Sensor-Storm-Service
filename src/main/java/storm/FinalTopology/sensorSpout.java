package storm.FinalTopology;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import storm.SOS.SOSWrapper;
import storm.mysqlDB.SensorInsertAndGet;
import storm.socketOperation.RecievedSensorDataSolve;
import storm.spout.SpoutParams;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/8.
 */
public class sensorSpout extends BaseRichSpout {
    SpoutOutputCollector collector;
    ServerSocket serverSocket;
    SpoutParams spoutParams;
    String SensorID;
    Socket socket;

    public void getParams(SpoutParams params){
        this.spoutParams=params;
    }
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("SOS"));
    }

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {

        System.out.print("test!");
        this.collector=spoutOutputCollector;
        serverSocket= StaticServerSocket.serverSocket;
    }

    public void nextTuple() {
        try {
            socket=serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (socket!=null) {
            System.out.println("Start Spout!");
            RecievedSensorDataSolve sensorDataSolve = new RecievedSensorDataSolve(spoutParams);
            SOSWrapper sosWrapper = sensorDataSolve.RecieveMessage(socket);
            this.collector.emit(new Values(sosWrapper));
            System.out.print("SendData!");
            Utils.sleep(10000);
        }
    }
}
