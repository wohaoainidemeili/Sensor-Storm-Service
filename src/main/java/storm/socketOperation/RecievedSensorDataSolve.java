package storm.socketOperation;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.tuple.Values;
import storm.SOS.SOSWrapper;
import storm.socketOperation.protocol.CRC16;
import storm.socketOperation.protocol.Moudus;
import storm.spout.SpoutParams;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ���ʹ�������Ϣ�������񲢽������յĴ���������
 * ��Ҫ�Ļ����ǣ�������Ϊ�ͻ��˸�����202.114.118.60�����ϸ����˿ڷ������ݣ�
 * ������ֻ��Ҫ����SocketServer����������˿ڣ�������scoketclient��ȡ���ݺͷ�������
 * socket test not used in topology
 * Created by Yuan on 2016/4/20.
 */
public class RecievedSensorDataSolve{
    ServerSocket serverSocket;//socket����
    List<Socket> listenSockets=new ArrayList<Socket>();//���ڴ洢��ǰ�ö˿��µ�����socket
    SpoutParams spoutParams;
    SpoutOutputCollector collector;
    public RecievedSensorDataSolve(SpoutParams spoutParams){
        this.spoutParams=spoutParams;
    }
    public RecievedSensorDataSolve(SpoutParams spoutParams,SpoutOutputCollector collector){
        this.spoutParams=spoutParams;
        this.collector=collector;
    }
   //����Socketģʽ������ͨ��
    public void StartServer(){
//        try {
            //serverSocket=new ServerSocket();
            //serverSocket.bind(new InetSocketAddress(spoutParams.ipAddress, Integer.valueOf(spoutParams.port)));//ָ�������ķ�������ַ�Ͷ˿ڣ��������ĵ�ַ�˿�

            //����һ���˿���Ϣ����ʱ������ListenClientConnectִ��socket�ͻ�������������ӵĲ���
            Thread listenThread=new Thread(new Runnable() {
                public void run() {
                    ListenClientConnect();
                }
            });
            listenThread.start();

//        } catch (IOException e) {
//           System.out.println("���ݽ����������Storm.socketOperation.RecieveSensorDataSolveException");
//        }

    }

    public void ListenClientConnect(){
        try {
            //����socket����ʹ��accept����������ȡ
            //�յ��ͻ������󣬴���һ���µ�Socket Client����ִ��
            final Socket socketClient=serverSocket.accept();
            listenSockets.add(socketClient);
            //����һ���߳̽�����ǰclient�Ľ��
            Thread recieveThread=new Thread(new Runnable() {
                public void run() {
                    RecieveMessage(socketClient);
                }
            });
            recieveThread.start();

        } catch (IOException e) {
            System.out.println("socket�ͻ��˻�ȡ����");
        }


    }
    public SOSWrapper RecieveMessage(Socket socketClient)
    {
        SOSWrapper sosWrapper=null;
        try {

            System.out.print("get start!");
            OutputStream outputStream=socketClient.getOutputStream();//���ڷ���socket��Ϣ
            InputStream inputStream=socketClient.getInputStream();//���ڽ���socket��Ϣ

            byte[] sendData=null;
            //����Э��Ҫ��ͬ�����Ʋ�ͬ�ķ������ݺͽ�������
            //��ǰ��ʵ��ModbusЭ��
            if(spoutParams.protocol.equals("modbus")){
                sendData= Moudus.getSendData(spoutParams);
            }else {
               // System.out.println("����Ĵ�����ͨ��Э�飡");
                Thread.interrupted();//�����ǰ�Ǵ����ͨ��Э�飬���жϵ�ǰ��������
            }
            outputStream.write(sendData);//д��socket
            outputStream.flush();
            //��ȡ���صĽ���������1024λ��������
            byte[] recievedData=new byte[1024];

            int recieveDataLen= inputStream.read(recievedData);//�������ݲ����ؽ��յ��ֽ���
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");//����ʱ���ʽ��Ҫת��UTCʱ���ʽ
            String recieveDataTime=df.format(new Date());

            //��ͬЭ�����ݽ���
            if(spoutParams.protocol.equals("modbus")) {
                //CRC��֤,ͨ����ִ�����²���
                byte[] recieveBuffer=new byte[recieveDataLen];//���յ����ݱ���
                for (int i=0;i<recieveDataLen;i++){
                    recieveBuffer[i]=recievedData[i];
                }
                //if (CRC16.CRCcheck(recieveBuffer)) {
                    //MODBUSЭ��������յ�����
                    sosWrapper= Moudus.solveRecievedData(spoutParams,recieveBuffer);
                //}
            }else {
                //System.out.println("����Ĵ�����ͨ��Э�飡");
                Thread.interrupted();
            }
            sosWrapper.setSimpleTime(recieveDataTime);

            //��sosWrapper���ͳ�ȥ
            //this.collector.emit(new Values(sosWrapper));

            //����˯�ߣ����ݵ�ǰ���趨ʱ�䣬˯��

//            try {
//                Thread.sleep(Integer.valueOf(spoutParams.sleepTime));
//                //Ҳ�������½���һ���̣߳�ÿ10���鵱ǰ��sleepֵ�Ƿ񱻸��£��˴�û�����������
//            } catch (InterruptedException e) {
//                System.out.println("˯���̴߳���"+e.getMessage());
//            }

        } catch (IOException e) {
            System.out.println("Socket��������!"+e.getMessage());
        }
        return sosWrapper;

    }

}
