package storm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Yuan on 2016/4/20.
 */
public class SensorConfigReader {
    //��ȡSensorConfig.properties�ļ�����ȡ�������
    public static   void  reader(){
        Properties prop=new Properties();//��ȡproperties
        try {
            InputStream in=ClassLoader.getSystemResourceAsStream("SensorConfig.properties");//��ȡ�ļ�
            prop.load(in);//���ص�properties�����б���
            SensorConfigInfo sensorConfigInfo=new SensorConfigInfo(prop);
        }catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

    }
}
