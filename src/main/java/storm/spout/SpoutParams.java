package storm.spout;

import storm.SOS.ObsProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * CreateInsertXMLSpout���������������õ�����Ϣ����
 * Created by Yuan on 2016/4/20.
 */
public class SpoutParams implements Serializable {
    public String sensorID;
    public List<ObsProperties> property_Name_Unit_StartPos_Len=new ArrayList<ObsProperties>();//����ID�������뵥λ��Ϣ,��ȡ��Ϣ����㣬��Ϣ����

    public  int sleepTime;//ÿ�������뷢��һ�����ݻ�ȡ��Ϣ����λ��
    public  double lat;//γ��
    public  double lon;//����

    public  String ipAddress;//������IP��ַ
    public  String port;//�������ӿڵ�ַ
    public  int startingAddress;//�Ĵ�����ʼ��ȡλ��
    public  int slaveAddress;//�ӻ���ַ
    public  int socketTimeOut;//���ݽ��ճ�ʱ����λ��ms

    public  String logFile;//��־�ļ�
    public  String protocol;//Э������



}
