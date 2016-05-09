package storm.SOS;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import storm.SensorConfigInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �洢��Ҫת����SOS insertObservation��ʽ�����
 * ��Ϊstorm���͵���ݱ���̳� Serializable�ӿ�
 * Created by Yuan on 2016/4/19.
 */
public class SOSWrapper implements Serializable {
    //������ID
    String sensorID;
    //�������۲�����
    public List<ObsProperties> properties=new ArrayList<ObsProperties>();
    String simpleTime;//�۲�ʱ��
    Double lon;//����
    Double lat;//γ��
    String sosAddress;//sos�����ַ
    public SOSWrapper(){}
    public SOSWrapper(String sensorID,String simpleTime,Double lon,Double lat,String sosAddress,List<ObsProperties>properties){
        this.sensorID=sensorID;
        this.simpleTime=simpleTime;
        this.lon=lon;
        this.lat=lat;
        this.sosAddress=sosAddress;
        this.properties=properties;
    }

    /**
     * ʹ��DOM ��ȡResources�е�InsertObservation.xml�����޸�ֵ
     * @return �����޸ĵ�xml
     */
    public String ChangeInsertXML(){
        String result="";
//        //ʹ��W3C��DOM��ȡxml
//        Document document=null;//���ڴ��xml
//        DocumentBuilderFactory builderFactory=DocumentBuilderFactory.newInstance();//����Document����
//        builderFactory.setNamespaceAware(true);//��Ҫ���ÿ���ʶ��namespace�������޷�ʶ��namespace
//        try {
//            DocumentBuilder documentBuilder=builderFactory.newDocumentBuilder();
//            try {
//                document=documentBuilder.parse(String.valueOf(ClassLoader.getSystemResource("InsertObservation.xml")));
//                document.normalize();
//                //������ռ����nameSpace��
//                Map nameSpace=new HashMap();
//                nameSpace.put("sos", "http://www.opengis.net/sos/1.0");
//                nameSpace.put("ows", "http://www.opengis.net/ows/1.1");
//                nameSpace.put("ogc", "http://www.opengis.net/ogc");
//                nameSpace.put("om", "http://www.opengis.net/om/1.0");
//                nameSpace.put("sa", "http://www.opengis.net/sampling/1.0");
//                nameSpace.put("gml", "http://www.opengis.net/gml");
//                nameSpace.put("swe", "http://www.opengis.net/swe/1.0.1");
//                nameSpace.put("xlink", "http://www.w3.org/1999/xlink");
//                nameSpace.put("xsi", "http://www.w3.org/2001/XMLSchema-instance");
//
//                //�������ռ��޸�XML Document����Ҫ�д�����ID���۲�ʱ�䡢�۲��̣��豸���������ID��������ơ�
//
//                //�޸Ĵ�����ID
//
//                NodeList sensorIDList= document.getElementsByTagNameNS(nameSpace.get("sos").toString(), "AssignedSensorId");
//                sensorIDList.item(0).setTextContent(sensorID);
//
//            } catch (SAXException e) {
//                System.out.println("Document��ʼ��ʧ�ܣ�");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (ParserConfigurationException e) {
//            System.out.println("���� xml Documentʧ�ܣ�");
//        }

        //ʹ��DOM4J����������ռ��XML
        SAXReader saxReader=new SAXReader();//SAX��ȡXML
        Map nameSpace=new HashMap();
        nameSpace.put("sos", "http://www.opengis.net/sos/1.0");
        nameSpace.put("ows", "http://www.opengis.net/ows/1.1");
        nameSpace.put("ogc", "http://www.opengis.net/ogc");
        nameSpace.put("om", "http://www.opengis.net/om/1.0");
        nameSpace.put("sa", "http://www.opengis.net/sampling/1.0");
        nameSpace.put("gml", "http://www.opengis.net/gml");
        nameSpace.put("swe", "http://www.opengis.net/swe/1.0.1");
        nameSpace.put("xlink", "http://www.w3.org/1999/xlink");
        nameSpace.put("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        saxReader.getDocumentFactory().setXPathNamespaceURIs(nameSpace);//������ռ���뵽DOMFactory��
        try {
            org.dom4j.Document document=saxReader.read(ClassLoader.getSystemResource("InsertObservation.xml"));

            //��λ��������Ҫ�޸ĵ�XML�ڵ㣬
            //������ID
            Node sensorIDNode=document.selectSingleNode("/sos:InsertObservation/sos:AssignedSensorId");
            sensorIDNode.setText(sensorID);

            //�۲�ʱ��
            Node simpleTimeNode=document.selectSingleNode("/sos:InsertObservation/om:Observation/om:samplingTime/gml:TimeInstant/gml:timePosition");
            simpleTimeNode.setText(simpleTime);

            //�۲��̣��豸��
            Node procedureNode=document.selectSingleNode("/sos:InsertObservation/om:Observation/om:procedure/@xlink:href");
            procedureNode.setText(sensorID);

            //�ռ�λ��ID�������ID
            Node spIDNode=document.selectSingleNode("/sos:InsertObservation/om:Observation/om:featureOfInterest/sa:SamplingPoint/@gml:id");
            spIDNode.setText(lat+" "+lon);

            //�������ƣ�foi_name
            Node spNameNode=document.selectSingleNode("/sos:InsertObservation/om:Observation/om:featureOfInterest/sa:SamplingPoint/gml:name");
            spNameNode.setText(lat+" "+lon);

            //�����λ�ã�foi_loc
            Node spPosNode=document.selectSingleNode("/sos:InsertObservation/om:Observation/om:featureOfInterest/sa:SamplingPoint/sa:position/gml:Point/gml:pos");
            spPosNode.setText(lat+" "+lon);

            //�۲����Խڵ�
            Node propertyNode=document.selectSingleNode("/sos:InsertObservation/om:Observation/om:observedProperty/swe:CompositePhenomenon");

            //�۲�����ݼ�¼
            Node resultDataNode=document.selectSingleNode("/sos:InsertObservation/om:Observation/om:result/swe:DataArray/swe:elementType/swe:DataRecord");

            String resultValue="";
            int valueIndex=0;
            //����property�ڵ㣬�������¼ֵ
            for(ObsProperties property:properties){
                //��<swe:CompositePhenomenon>�����<swe:component>����
                Element propertyEle=(Element)propertyNode;//��propertyNodeתΪpropertyEle
                Element forAddProperty= DocumentHelper.createElement("swe:component");//������Ҫ��ӵĽڵ�
                forAddProperty.addAttribute("xlink:href", property.getId());
                propertyEle.add(forAddProperty);//��������property���Լ��뵽propertyNode�ڵ�֮��

                //��<swe:DataRecord>�ڵ������<swe:field>�ڵ�����
                Element resultDataEle=(Element)resultDataNode;
               //swe:filed�ڵ�
                Element forAddFiled=DocumentHelper.createElement("swe:field");//SWE:FILED�ֶΣ�����swe:Quantity������swe:uom
                forAddFiled.addAttribute("name",property.getName());
               //swe:filed�ڵ��µ�swe:quantity�ڵ�
                Element forAddQuantity=DocumentHelper.createElement("swe:Quantity");//filed�µ�Quantity
                forAddQuantity.addAttribute("definition", property.getId());
                //swe:quantity�µ�swe:uom�ڵ�
                Element forAddUom=DocumentHelper.createElement("swe:uom");
                forAddUom.addAttribute("code",property.getUnit());
                //������
                forAddQuantity.add(forAddUom);
                forAddFiled.add(forAddQuantity);
                resultDataEle.add(forAddFiled);
                if (valueIndex==0){
                    resultValue=String.valueOf(property.getValue());
                }else {
                    resultValue = resultValue +","+ property.getValue();
                }
                valueIndex++;
            }
            Node resultValueNode=document.selectSingleNode("/sos:InsertObservation/om:Observation/om:result/swe:DataArray/swe:values");
            resultValueNode.setText(simpleTime+","+resultValue+";");

            //��DocumentתΪxml string
            result= document.asXML();

        } catch (DocumentException e) {
           System.out.println("Document�ĵ���������");
        }
        System.out.println(result);

        return result;
    }

//    /**
//     *��org.w3c.document תΪstring
//     * @param doc document
//     * @return
//     */
//    public static String doc2FromatString(Document doc){
//        StringWriter stringWriter=null;
//        stringWriter = new StringWriter();
//        if (doc!=null){
//            OutputFormat format = new OutputFormat(doc,"UTF-8",true);
//            XMLSerializer serializer = new XMLSerializer(stringWriter,format);
//            try {
//                serializer.asDOMSerializer();
//                serializer.serialize(doc);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//        return stringWriter.toString();
//    }

    /**
     * ��ݲ��룬��post��ʽ�������
     * @return ���ز������
     */
    public String Insert(){
        String postReturnStr=null;
        HttpRequestAndPost httpRequestAndPost=new HttpRequestAndPost();
        postReturnStr=httpRequestAndPost.sendPost(SensorConfigInfo.getUrl(),ChangeInsertXML());//�����������post��ʽ����
        System.out.println(postReturnStr);
        return postReturnStr;
    }
    public String getSensorID() {
        return sensorID;
    }

    public void setSensorID(String sensorID) {
        this.sensorID = sensorID;
    }

    public List<ObsProperties> getProperties() {
        return properties;
    }

    public void setProperties(List<ObsProperties> properties) {
        this.properties.addAll( properties);
    }

    public String getSimpleTime() {
        return simpleTime;
    }

    public void setSimpleTime(String simpleTime) {
        this.simpleTime = simpleTime;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getSosAddress() {
        return sosAddress;
    }

    public void setSosAddress(String sosAddress) {
        this.sosAddress = sosAddress;
    }

    /**
     *��ʵ�֣������ο�
     * @return
     */
    public String  CreateInsertObservationXML(){
        String insertObsXML=null;

        return insertObsXML;
    }
}

