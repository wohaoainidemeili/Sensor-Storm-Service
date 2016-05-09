package storm.Topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
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
public class ObservationInsertTopology {
    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {

        String sensorID;//���ⲿ����SensorID
        if (args.length!=0){
            sensorID=args[0];//�ǲ��Դ�����ID��Ϣ�����ⲿ����
        }else
            sensorID="urn:liesmars:insitusensor:BaoxieWeatherSoilStation1-FY-H2";//���ԵĴ�����ID��Ϣ
        SensorConfigReader.reader();//��ȡSensorConfig.properties�������ļ���SensorConfigInfo��
        DBIntial.initial();//��ʼ����ݿ⣬û�д�����Ļ�������ݿ��
        SpoutParams params= SensorInsertAndGet.getSpoutParams(sensorID);
        ServerSocket serverSocket=null;
        //socketserver start
        try {
            serverSocket=new ServerSocket();
            //bind ip and port
            serverSocket.bind(new InetSocketAddress(params.ipAddress,Integer.valueOf(params.port)));

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (serverSocket!=null) {

            //����Topology
            String Spout_ID = "Socket_Spout_ID";
            String RecieveData_ID="RecieveData_ID";
            String Blot_ID = "Insert_Blot_ID";

            SensorIDSpout sensorIDSpout = new SensorIDSpout();
            sensorIDSpout.getSpoutParams(params);

            RecieveDataBlot recieveDataBlot=new RecieveDataBlot();
            InsertSOSBlot insertSOSBlot = new InsertSOSBlot();


            TopologyBuilder topologyBuilder = new TopologyBuilder();
            topologyBuilder.setSpout(Spout_ID, sensorIDSpout);
            topologyBuilder.setBolt(RecieveData_ID,recieveDataBlot).fieldsGrouping(Spout_ID, new Fields("SensorID"));
            topologyBuilder.setBolt(Blot_ID, insertSOSBlot).shuffleGrouping(RecieveData_ID);//�������ķ�ʽ��spout�������ݴ���insertBlot���д���

            //���ص���ģʽ�������ļ����ã�ѡ��Ĭ������
            Config config = new Config();

            if (args.length == 0) {
                //����ģʽ����
                LocalCluster localCluster = new LocalCluster();
                localCluster.submitTopology("test1", config, topologyBuilder.createTopology());
                Utils.sleep(100000);
                localCluster.killTopology("test1");
                localCluster.shutdown();
            } else {
                StormSubmitter.submitTopology(args[0], config, topologyBuilder.createTopology());//�ύ���˵���Ⱥ
            }
        }
    }
}
