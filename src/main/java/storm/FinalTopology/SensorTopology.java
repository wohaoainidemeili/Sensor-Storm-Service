package storm.FinalTopology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;
import storm.SensorConfigReader;
import storm.mysqlDB.DBIntial;
import storm.mysqlDB.SensorInsertAndGet;
import storm.spout.SpoutParams;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

/**
 * Created by Administrator on 2016/5/8.
 */
public class SensorTopology {
    public static void main(String[] args){

        SensorConfigReader.reader();//��ȡSensorConfig.properties�������ļ���SensorConfigInfo��
        DBIntial.initial();//��ʼ����ݿ⣬û�д�����Ļ�������ݿ��

        //serversocket
        ServerSocket serverSocket=null;
        String SensorID="urn:liesmars:insitusensor:BaoxieWeatherSoilStation1-FY-H2";
        SpoutParams spoutParams= SensorInsertAndGet.getSpoutParams(SensorID);
        System.out.println(spoutParams.sensorID);
        try {
            serverSocket=new ServerSocket();
            serverSocket.bind(new InetSocketAddress(spoutParams.ipAddress,Integer.valueOf(spoutParams.port)));
        } catch (IOException e) {
            e.printStackTrace();
            try {
                serverSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        StaticServerSocket servers=new StaticServerSocket(serverSocket);

        String spout_id="spout_id";
        String bolt_id="bolt_id";
        sensorSpout sensors=new sensorSpout();
        sensors.getParams(spoutParams);
        sensorBlot sensorb=new sensorBlot();

        TopologyBuilder builder=new TopologyBuilder();
        builder.setSpout(spout_id,sensors);
        builder.setBolt(bolt_id,sensorb).fieldsGrouping(spout_id,new Fields("SOS"));

        Config config=new Config();
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("test1", config, builder.createTopology());
        Utils.sleep(100000);
        localCluster.killTopology("test1");
        localCluster.shutdown();
    }
}
