package storm;

import storm.mysqlDB.DBIntial;
import storm.mysqlDB.SensorInsertAndGet;
import storm.spout.SpoutParams;

/**
 * �������sensor�� SQL����
 * Created by Yuan on 2016/5/3.
 */
public class InsertOneSensorMain {
    public static void main(String[] args){

        //��ȡˮPHֵ���
        SensorConfigReader.reader();//��ȡSensorConfig.properties�������ļ���SensorConfigInfo��
        DBIntial.initial();
        String SensorID="urn:liesmars:insitusensor:BaoxieWeatherSoilStation1-FY-H2";
        SpoutParams spoutParams= SensorInsertAndGet.fromXMLToParams(SensorID);
        String ip="202.114.118.60";
        String port="9007";
        int StartAddress=0;
        int slave_address=1;
        int socket_out_time=200000;
        int sleep_time=1000;
        String log_file="C:\\log";
        String protocol="modbus";

        spoutParams.ipAddress=ip;
        spoutParams.port=port;
        spoutParams.startingAddress=StartAddress;
        spoutParams.slaveAddress=slave_address;
        spoutParams.socketTimeOut=socket_out_time;
        spoutParams.sleepTime=sleep_time;
        spoutParams.logFile=log_file;
        spoutParams.protocol=protocol;
        spoutParams.property_Name_Unit_StartPos_Len.get(0).setStartpos(27);
        spoutParams.property_Name_Unit_StartPos_Len.get(0).setLen(2);

        SensorInsertAndGet.InsertParams(SensorID,spoutParams);
    }
}
