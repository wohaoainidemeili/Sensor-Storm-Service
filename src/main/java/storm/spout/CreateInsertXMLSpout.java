package storm.spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import storm.SOS.SOSWrapper;
import storm.SensorConfigInfo;
import storm.mysqlDB.SensorInsertAndGet;
import storm.socketOperation.RecievedSensorDataSolve;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * ����SOSWrap����ݣ�δ���XML
 * Created by Yuan on 2016/4/20.
 */
public class CreateInsertXMLSpout extends BaseRichSpout{

    String SensorID;//��������ID��Ϣ
    SpoutParams params;

    SpoutOutputCollector collector;
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    //�ⲿ��ȡSensorID��Ϣ
    public void getSensorID(String SensorID){
        this.SensorID=SensorID;
    }
    public void getParams(SpoutParams params){
        this.params=params;
    }
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("InsertObsXML"));
    }

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector=spoutOutputCollector;
    }


    public void nextTuple() {
        //��ȡproperties�ļ�����ȡSocket����
        String url= SensorConfigInfo.getUrl();

        //for test
//        SOSWrapper sosWrapper=null;
//        if (params.protocol.equals("modbus")){
//            sosWrapper=new SOSWrapper();
//            sosWrapper.setLat(params.lat);
//            sosWrapper.setLon(params.lon);
//            sosWrapper.setProperties(params.property_Name_Unit_StartPos_Len);
//            sosWrapper.setSensorID(params.sensorID);
//            sosWrapper.setSimpleTime(simpleDateFormat.format(new Date()));
//            sosWrapper.properties.get(0).setValue(1.2);
//        }
//        System.out.println("send Data!");
//        collector.emit(new Values(sosWrapper));
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //�޸�DescribeSensor
        //SpoutParams params= SensorInsertAndGet.getSpoutParams(SensorID);

        //��ݻ�ȡ�Ĳ���ʹ��Socket��ȡ���������
        RecievedSensorDataSolve recievedSensorDataSolve=new RecievedSensorDataSolve(params,collector);
        recievedSensorDataSolve.StartServer();
    }

}
