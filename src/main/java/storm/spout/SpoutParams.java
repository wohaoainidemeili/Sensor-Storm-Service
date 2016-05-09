package storm.spout;

import storm.SOS.ObsProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * CreateInsertXMLSpout发射数据所必须用到的信息集合
 * Created by Yuan on 2016/4/20.
 */
public class SpoutParams implements Serializable {
    public String sensorID;
    public List<ObsProperties> property_Name_Unit_StartPos_Len=new ArrayList<ObsProperties>();//属性ID，名称与单位信息,读取信息的起点，信息长度

    public  int sleepTime;//每隔多少秒发送一次数据获取消息。单位秒
    public  double lat;//纬度
    public  double lon;//经度

    public  String ipAddress;//传感器IP地址
    public  String port;//传感器接口地址
    public  int startingAddress;//寄存器开始读取位置
    public  int slaveAddress;//从机地址
    public  int socketTimeOut;//数据接收超时，单位：ms

    public  String logFile;//日志文件
    public  String protocol;//协议类型



}
