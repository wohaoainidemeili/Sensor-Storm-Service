package storm.socketOperation;

import org.apache.storm.tuple.Values;
import storm.SOS.SOSWrapper;
import storm.socketOperation.protocol.CRC16;
import storm.socketOperation.protocol.Moudus;
import storm.spout.SpoutParams;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * this class is used in topology
 * to solve the data recieve mission
 * Created by Administrator on 2016/5/8.
 */
public class RecieveData {
    SpoutParams spoutParams;

    public RecieveData(SpoutParams spoutParams){
        this.spoutParams=spoutParams;
    }

    /**
     * this function is used to getSOSInformation from recieved socket data
     * @return
     */
    public SOSWrapper getSOSInfoFromRecieveData(){
        Socket socket=null;
        SOSWrapper sosWrapper=null;

        OutputStream outputStream= null;//���ڷ���socket��Ϣ
        try {
            outputStream = socket.getOutputStream();

        InputStream inputStream=socket.getInputStream();//���ڽ���socket��Ϣ

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
                //MODBUSЭ��������յ�����
            sosWrapper= Moudus.solveRecievedData(spoutParams,recieveBuffer);

        }else {
            //System.out.println("����Ĵ�����ͨ��Э�飡");
            Thread.interrupted();
        }
        sosWrapper.setSimpleTime(recieveDataTime);

        try {
            Thread.sleep(Integer.valueOf(spoutParams.sleepTime));
            //Ҳ�������½���һ���̣߳�ÿ10���鵱ǰ��sleepֵ�Ƿ񱻸��£��˴�û�����������
        } catch (InterruptedException e) {
            System.out.println("˯���̴߳���"+e.getMessage());
        }

    } catch (IOException e) {
        System.out.println("Socket��������!"+e.getMessage());
    }


    return sosWrapper;
    }
}
