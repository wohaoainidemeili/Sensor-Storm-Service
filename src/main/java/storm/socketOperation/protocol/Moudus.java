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
    //构建发送指令
    public static byte[] getSendData(SpoutParams spoutParams){
        byte[] sendData=new byte[8];
        sendData[0]= (byte)(spoutParams.slaveAddress>>>24);//请求应答设备的485地址,因为Int32个字节，无符号右移24位得到最高位的byte

        sendData[1]=4;//读取数据的Modus功能码
        sendData[2]=(byte)(spoutParams.startingAddress>>>24);//第一个寄存器的Modbus地址，为两个字节
        sendData[3]=(byte)(spoutParams.startingAddress>>>16);

        sendData[4] = 0x00;//寄存器的数量的高位
        sendData[5]=0x10>>>8;//最后一个寄存器的低位

        sendData[6] = 0x00;//CRC低，预留该字节
        sendData[7] = 0x00;//CRC高，预留该字节
        //使用CRC16运算规则，计算 sendData6和sendData7
        return CRC16.CRCCaculate(sendData);
    }

    /**
     * 解析property的值，并生产SOSWrapper
     * @param spoutParams
     * @param recievedData
     * @return
     */
    public static SOSWrapper solveRecievedData(SpoutParams spoutParams,byte[] recievedData){
        SOSWrapper sosWrapper=new SOSWrapper();
        List<ObsProperties> properties=spoutParams.property_Name_Unit_StartPos_Len;
        //遍历所有属性
        for (ObsProperties property:properties){
            property.setValue(GetValueFromRecieveData(recievedData,property.getStartpos()));
        }
        sosWrapper.setSensorID(spoutParams.sensorID);
        sosWrapper.setLat(spoutParams.lat);
        sosWrapper.setLon(spoutParams.lon);
        sosWrapper.setSosAddress(SensorConfigInfo.getUrl());//获取SOS地址
        sosWrapper.setProperties(properties);
        return sosWrapper;
    }

    /**
     * 传感器接收的数据高字节在前，低字节在后，Modbus数据长度为两个字节
     * @param recieveData 接收到的byte数据
     * @param startPos 开始位置
     * @return 返回short结果
     */
    public static double GetValueFromRecieveData(byte[] recieveData,int startPos){
        //16位的数据以short获取,再除以10得到结果
       short shortRes= (short)((recieveData[startPos]<<8)|(recieveData[startPos+1]&0xff));
        return shortRes*1.0/10.0;
    }
}
