package storm.socketOperation.protocol;

import storm.SOS.ObsProperties;
import storm.SOS.SOSWrapper;
import storm.SensorConfigInfo;
import storm.spout.SpoutParams;

import java.util.List;

/**
 * Created by Yuan on 2016/4/19.
 */
public class Moudus {
    //��������ָ��
    public static byte[] getSendData(SpoutParams spoutParams){
        byte[] sendData=new byte[8];
        sendData[0]= (byte)(spoutParams.slaveAddress>>>24);//����Ӧ���豸��485��ַ,��ΪInt32���ֽڣ��޷�������24λ�õ����λ��byte

        sendData[1]=4;//��ȡ���ݵ�Modus������
        sendData[2]=(byte)(spoutParams.startingAddress>>>24);//��һ���Ĵ�����Modbus��ַ��Ϊ�����ֽ�
        sendData[3]=(byte)(spoutParams.startingAddress>>>16);

        sendData[4] = 0x00;//�Ĵ����������ĸ�λ
        sendData[5]=0x10>>>8;//���һ���Ĵ����ĵ�λ

        sendData[6] = 0x00;//CRC�ͣ�Ԥ�����ֽ�
        sendData[7] = 0x00;//CRC�ߣ�Ԥ�����ֽ�
        //ʹ��CRC16������򣬼��� sendData6��sendData7
        return CRC16.CRCCaculate(sendData);
    }

    /**
     * ����property��ֵ��������SOSWrapper
     * @param spoutParams
     * @param recievedData
     * @return
     */
    public static SOSWrapper solveRecievedData(SpoutParams spoutParams,byte[] recievedData){
        SOSWrapper sosWrapper=new SOSWrapper();
        List<ObsProperties> properties=spoutParams.property_Name_Unit_StartPos_Len;
        //������������
        for (ObsProperties property:properties){
            property.setValue(GetValueFromRecieveData(recievedData,property.getStartpos()));
        }
        sosWrapper.setSensorID(spoutParams.sensorID);
        sosWrapper.setLat(spoutParams.lat);
        sosWrapper.setLon(spoutParams.lon);
        sosWrapper.setSosAddress(SensorConfigInfo.getUrl());//��ȡSOS��ַ
        sosWrapper.setProperties(properties);
        return sosWrapper;
    }

    /**
     * ���������յ����ݸ��ֽ���ǰ�����ֽ��ں�Modbus���ݳ���Ϊ�����ֽ�
     * @param recieveData ���յ���byte����
     * @param startPos ��ʼλ��
     * @return ����short���
     */
    public static double GetValueFromRecieveData(byte[] recieveData,int startPos){
        //16λ��������short��ȡ,�ٳ���10�õ����
       short shortRes= (short)((recieveData[startPos]<<8)|(recieveData[startPos+1]&0xff));
        return shortRes*1.0/10.0;
    }
}
