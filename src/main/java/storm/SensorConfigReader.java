package storm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Yuan on 2016/4/20.
 */
public class SensorConfigReader {
    //读取SensorConfig.properties文件，获取相关配置
    public static   void  reader(){
        Properties prop=new Properties();//读取properties
        try {
            InputStream in=ClassLoader.getSystemResourceAsStream("SensorConfig.properties");//读取文件
            prop.load(in);//加载到properties属性列表中
            SensorConfigInfo sensorConfigInfo=new SensorConfigInfo(prop);
        }catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

    }
}
