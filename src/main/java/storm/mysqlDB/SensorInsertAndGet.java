package storm.mysqlDB;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import storm.SOS.HttpRequestAndPost;
import storm.SOS.ObsProperties;
import storm.SensorConfigInfo;
import storm.spout.SpoutParams;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ��ȡSpout��Ҫ�Ĳ���
 * Created by Yuan on 2016/4/19.
 */
public class SensorInsertAndGet {

    //����ݿ��ж�ȡ��ǰSensorID��Ӧ����Ϣ
    public static SpoutParams getSpoutParams(String SensorID){
        SpoutParams spoutParams=new SpoutParams();
        spoutParams.sensorID=SensorID;
        //��ȡ��ݿ����ӵĲ�����Ϣ
        String host=SensorConfigInfo.getHost();
        String user=SensorConfigInfo.getUser();
        String passWord=SensorConfigInfo.getPassword();
        String dbname= SensorConfigInfo.getDbName();
        String driver=SensorConfigInfo.getDriver();

        //������ݿ�
        SqlConnectionPool connectionPool=new SqlConnectionPool(host,dbname,user,passWord,driver);
        Connection con= connectionPool.getConnection();

        //��ȡ��������Ϣ��������ȡ��Sensor
        String sensorInfoQuery="select * from "+SensorConfigInfo.getSensorTableName()+" where "+SensorConfigInfo.getSt_id()+"='"+SensorID+"'";
        String propertyInfoQuery="select * from "+SensorConfigInfo.getPropertyTableName()+" where "+SensorConfigInfo.getPt_PID()+"='"+SensorID+"'";

        Statement state=null;
        ResultSet resultSet=null;
        try {
           state =con.createStatement();
            resultSet=state.executeQuery(sensorInfoQuery);
            while (resultSet.next()) {
                String ip = resultSet.getString(SensorConfigInfo.getSt_ip());
                String port = resultSet.getString(SensorConfigInfo.getSt_port());
                int startingAddress = resultSet.getInt(SensorConfigInfo.getSt_startingAddress());
                int slaveAddress = resultSet.getInt(SensorConfigInfo.getSt_slaveAddress());
                int socketOutOfTime = resultSet.getInt(SensorConfigInfo.getSt_socketOutofTime());
                int sleepTime = resultSet.getInt(SensorConfigInfo.getSt_sleepTime());
                String logFile = resultSet.getString(SensorConfigInfo.getSt_logFile());
                double lat = resultSet.getDouble(SensorConfigInfo.getSt_lat());
                double lon = resultSet.getDouble(SensorConfigInfo.getSt_lon());
                String protocol = resultSet.getString(SensorConfigInfo.getSt_protocal());

                spoutParams.ipAddress = ip;
                spoutParams.port = port;
                spoutParams.startingAddress = startingAddress;
                spoutParams.slaveAddress = slaveAddress;
                spoutParams.socketTimeOut = socketOutOfTime;
                spoutParams.sleepTime = sleepTime;
                spoutParams.logFile = logFile;
                spoutParams.lat = lat;
                spoutParams.lon = lon;
                spoutParams.protocol = protocol;
                //��ȡproperty�еĴ���������
                resultSet = state.executeQuery(propertyInfoQuery);

                //ѭ����ȡ���е�Properties
                while (resultSet.next()) {
                    String propertyID = resultSet.getString(SensorConfigInfo.getPt_propertyID());
                    String propertyName = resultSet.getString(SensorConfigInfo.getPt_propertyName());
                    String propertyUnit = resultSet.getString(SensorConfigInfo.getPt_propertyUnit());
                    int propertyStartPos = resultSet.getInt(SensorConfigInfo.getPt_propertyStartPos());
                    int propertyLen = resultSet.getInt(SensorConfigInfo.getPt_propertyLen());

                    ObsProperties obsProperty = new ObsProperties(propertyID, propertyName, propertyUnit, propertyStartPos, propertyLen);
                    spoutParams.property_Name_Unit_StartPos_Len.add(obsProperty);

                }
            }

        } catch (SQLException e) {
            System.out.println("��ݿ��ѯ����:"+e.getMessage());
        }
        //�ͷ���ݿ�������Դ
        finally {
            if (state!=null){
                try {
                    state.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con!=null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return spoutParams;

    }

    /**
     * ����ҳ�˻�ȡ���������Ϣ���뵽����
     * @param SensorID ������ID
     * @param spoutParams ��������Ӧ�Ļ���Ϣ
     */
    public static void InsertParams(String SensorID,SpoutParams spoutParams){
        //��ȡ��ݿ����ӵĲ�����Ϣ
        String host=SensorConfigInfo.getHost();
        String user=SensorConfigInfo.getUser();
        String passWord=SensorConfigInfo.getPassword();
        String dbname= SensorConfigInfo.getDbName();
        String driver=SensorConfigInfo.getDriver();

        //������ݿ�
        SqlConnectionPool connectionPool=new SqlConnectionPool(host,dbname,user,passWord,driver);
        Connection con= connectionPool.getConnection();
        Statement statement=null;

        try {
           statement=con.createStatement();
            //������ݵ�SQL���
            String insertSensorSQL="insert into "+SensorConfigInfo.getSensorTableName()+" values('"+SensorID+"','"+spoutParams.ipAddress+"','"
                    +spoutParams.port+"',"+spoutParams.startingAddress+","+spoutParams.slaveAddress+","+spoutParams.socketTimeOut+","
                    +spoutParams.sleepTime+",'"+spoutParams.logFile+"',"+spoutParams.lon+","+spoutParams.lat+",'"+spoutParams.protocol+"')";
            statement.execute(insertSensorSQL);

            //property��Ҫѭ��
            for (ObsProperties property:spoutParams.property_Name_Unit_StartPos_Len){
                String insertPropertySQL="insert into "+SensorConfigInfo.getPropertyTableName()+"("+SensorConfigInfo.getPt_PID()
                        +","+SensorConfigInfo.getPt_propertyID()+","+SensorConfigInfo.getPt_propertyName()+","
                        +SensorConfigInfo.getPt_propertyUnit()+","+SensorConfigInfo.getPt_propertyStartPos()+","+SensorConfigInfo.getPt_propertyLen()+
                        ") values('"+SensorID+"','"+property.getId()+"','"+property.getName()+"','"+property.getUnit()+"',"
                        +property.getStartpos()+","+property.getLen()+")";
                statement.execute(insertPropertySQL);
            }

        } catch (SQLException e) {
           System.out.println("��ݲ������");
        }finally {
            if (statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("SQL statement �ͷŴ���");
                }
            }
            if (con!=null){
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println("SQL connnect �ͷŴ���");
                }
            }
        }


    }

    /**
     * ����ҳ�˻�ȡ���������Ϣ���뵽����
     * @param SensorID ���ʹ�����ID
     * @return ���ش�����������Ԫ�����Ϣ
     */
    public static SpoutParams fromXMLToParams(String SensorID){
        SpoutParams spoutParams=new SpoutParams();
        SAXReader saxReader=new SAXReader();
        Map nameSpace=new HashMap();
        nameSpace.put("sos","http://www.opengis.net/sos/1.0");
        nameSpace.put("xsi","http://www.w3.org/2001/XMLSchema-instance");
        saxReader.getDocumentFactory().setXPathNamespaceURIs(nameSpace);

        //��ȡDescribeSensor.xml���޸�SensorID
        Document document=null;
        try {
          document = saxReader.read(ClassLoader.getSystemResourceAsStream("DescribeSensor.xml"));
            //��ȡpropertyNode�ڵ㣬���޸�sensorID
           Node propertyNode= document.selectSingleNode("/sos:DescribeSensor/sos:procedure");
            propertyNode.setText(SensorID);
        } catch (DocumentException e) {
           System.out.println("�޸�SennsorID����");
        }

        //���ʹ�����Describe������ͨ��sos��ȡ��������Ϣ
        String sendMessage=document.asXML();

        HttpRequestAndPost httpRequestAndPost=new HttpRequestAndPost();
        //�õ�������Ԫ�����Ϣxml
        //��ǰ����ʹ�õĵ�ַhttp://gsw.whu.edu.cn:8080/SOSv3.5.0/sos
        String sensorDataXML=httpRequestAndPost.sendPost(SensorConfigInfo.getUrl(), sendMessage);
        //String sensorDataXML=httpRequestAndPost.sendPost("http://swe.whu.edu.cn:8080/SOSv3.5.0/sos", sendMessage);
        //����describe������ȡ��xml��Ӧ�Ĳ�����Ϣ��������spoutParams��
        SAXReader saxReader1=new SAXReader();
        saxReader1.setEncoding("GB2312");
        //saxReader1.setEncoding("UTF-8");
        Map nameSpace1=new HashMap();//xml������ռ�
        nameSpace1.put("sml","http://www.opengis.net/sensorML/1.0.1");
        nameSpace1.put("gml","http://www.opengis.net/gml");
        nameSpace1.put("swe","http://www.opengis.net/swe/1.0.1");
        nameSpace1.put("xlink","http://www.w3.org/1999/xlink");
        nameSpace1.put("xsi","http://www.w3.org/2001/XMLSchema-instance");
        nameSpace1.put("ows","http://www.opengeospatial.net/ows");
        nameSpace1.put("ogc","http://www.opengis.net/ogc");
        nameSpace1.put("om", "http://www.opengis.net/om/1.0");

        saxReader1.getDocumentFactory().setXPathNamespaceURIs(nameSpace1);//��������ռ�

        Document document1=null;
        InputStream inputStream=new ByteArrayInputStream(sensorDataXML.getBytes());//��stringתStream
        try {
            document1=saxReader1.read(inputStream);

            //��ȡ��γ��
            List<Node> posNodes= document1.selectNodes("/sml:SensorML/sml:member/sml:System/sml:position/swe:Position/swe:location/swe:Vector/swe:coordinate/swe:Quantity");
               for (Node node:posNodes) {
               Element ele = (Element) node;
               //��axisIDΪyʱ���γ�ȣ�xʱ��?��
               if (ele.attribute("axisID").getText().equals("y")){
                   Node valueNode= ele.selectSingleNode("swe:value");
                   spoutParams.lat=Double.valueOf(valueNode.getText());
               }else if (ele.attribute("axisID").getText().equals("x")){
                   Node valueNode=ele.selectSingleNode("swe:value");
                   spoutParams.lon=Double.valueOf(valueNode.getText());
               }else{
                   //System.out.println("���ڵ���ݻ�ȡ����");
               }
           }
            //��ȡ������Ϣ��ID,NAME,Unit
            List<Node> propertyNodes=document1.selectNodes("/sml:SensorML/sml:member/sml:System/sml:outputs/sml:OutputList/sml:output");
            for (Node node:propertyNodes){
                ObsProperties property=new ObsProperties();//�½�ObsProperty
                Element ele=(Element)node;
                //��ȡproperty����������Ϣ

                 property.setName(ele.attribute("name").getText());//�������
                //swe:Quantity�ڵ�
                Element quanEle=(Element)ele.selectSingleNode("swe:Quantity");
                 property.setId(quanEle.attribute("definition").getText());
                //swe:uom
                Element uomEle=(Element)quanEle.selectSingleNode("swe:uom");
                property.setUnit(uomEle.attribute("code").getText());
                spoutParams.property_Name_Unit_StartPos_Len.add(property);
            }


        } catch (DocumentException e) {
            System.out.println("��ȡSensorML���ؽ�����");
        }

        return spoutParams;
    }

}
