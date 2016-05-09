package storm.socketOperation.protocol;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.Document;
import storm.mysqlDB.SensorInsertAndGet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Yuan on 2016/4/21.
 */
public class XOR {
    public static void main(String[] args) throws FileNotFoundException {

        SensorInsertAndGet.fromXMLToParams("urn:liesmars:insitusensor:BaoxieSoilMoistureStation-LVDSC12");
//        SAXReader saxReader=new SAXReader();
//        Map nameSpace=new HashMap();
//        nameSpace.put("sml","http://www.opengis.net/sensorML/1.0.1");
//        nameSpace.put("gml","http://www.opengis.net/gml");
//        nameSpace.put("swe","http://www.opengis.net/swe/1.0.1");
//        nameSpace.put("xlink","http://www.w3.org/1999/xlink");
//        nameSpace.put("xsi","http://www.w3.org/2001/XMLSchema-instance");
//        nameSpace.put("ows","http://www.opengeospatial.net/ows");
//        nameSpace.put("ogc","http://www.opengis.net/ogc");
//        nameSpace.put("om", "http://www.opengis.net/om/1.0");
//        saxReader.getDocumentFactory().setXPathNamespaceURIs(nameSpace);
//
//        //��ȡDescribeSensor.xml���޸�SensorID
//        org.dom4j.Document document=null;
//        try {
//            document = saxReader.read("F:\\DescribeSensorReturn.xml");
//            //��ȡpropertyNode�ڵ㣬���޸�sensorID
//            List<Node> nodes= document.selectNodes("/sml:SensorML/sml:member/sml:System/sml:capabilities/swe:DataRecord/swe:field/swe:Envelope/swe:lowerCorner/swe:Vector/swe:coordinate/swe:Quantity");
//            for (Node node:nodes) {
//                Element ele = (Element) node;
//                //��axisIDΪyʱ����γ��
//                if (ele.attribute("axisID").getText().equals("y")){
//                    Node valueNode= ele.selectSingleNode("swe:value");
//                    double lat=Double.valueOf(valueNode.getText());
//                    int x=0;
//                }
//            }
//        } catch (DocumentException e) {
//            System.out.println("�޸�SennsorID����");
//        }
//        String result=document.asXML();
//        int x=0;
//        SAXReader saxReader=new SAXReader();
//        Map nameSpace=new HashMap();
//                nameSpace.put("sos", "http://www.opengis.net/sos/1.0");
//                nameSpace.put("ows", "http://www.opengis.net/ows/1.1");
//                nameSpace.put("ogc", "http://www.opengis.net/ogc");
//                nameSpace.put("om", "http://www.opengis.net/om/1.0");
//                nameSpace.put("sa", "http://www.opengis.net/sampling/1.0");
//                nameSpace.put("gml", "http://www.opengis.net/gml");
//                nameSpace.put("swe", "http://www.opengis.net/swe/1.0.1");
//                nameSpace.put("xlink", "http://www.w3.org/1999/xlink");
//                nameSpace.put("xsi", "http://www.w3.org/2001/XMLSchema-instance");
//        try {
//            saxReader.getDocumentFactory().setXPathNamespaceURIs(nameSpace);
//            org.dom4j.Document document=saxReader.read(ClassLoader.getSystemResource("InsertObservation.xml"));
//            //�۲����Խڵ�
//            Node propertyNode=document.selectSingleNode("/sos:InsertObservation/om:Observation/om:observedProperty/swe:CompositePhenomenon");
//            Element element= DocumentHelper.createElement("swe:component");
//            element.addAttribute("xlink:href", "asdasdasdasdasdas");
//            Element propertyele=(Element)propertyNode;
//            propertyele.add(element);
//
//            String documentStr=document.asXML();
//            int x=0;
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        Document document=null;
//        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
//        factory.setNamespaceAware(true);
//        try {
//            DocumentBuilder builder=factory.newDocumentBuilder();
//            try {
//                String name= String.valueOf(ClassLoader.getSystemResource("InsertObservation.xml"));
//                document =builder.parse(name);
//                document.normalize();
//
//                document=builder.parse(String.valueOf(ClassLoader.getSystemResource("InsertObservation.xml")));
//                document.normalize();
//                //�������ռ����nameSpace��
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
//                //���������ռ��޸�XML Document����Ҫ�д�����ID���۲�ʱ�䡢�۲���̣��豸����������ID���������ơ�
//
//                NodeList nodeList= document.getElementsByTagNameNS(nameSpace.get("sos").toString(), "AssignedSensorId");
//                nodeList.item(0).setTextContent("sadasd");
//
//                String docString=doc2FromatString(document);
//                //��DocumentתΪstring
//                int x=0;
//
//            } catch (SAXException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        }

//        Logger logger= LoggerFactory.getLogger(XOR.class);
//        logger.info("test");
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");//����ʱ���ʽ��Ҫת��UTCʱ��
//        String recieveDataTime=df.format(new Date());
//        System.out.println(recieveDataTime);
//        byte[] bytes=new byte[2];
//        bytes[0]=(byte)61;
//        bytes[1]=(byte)255;
//        short res= (short)((bytes[0])|(bytes[1]<<8));
//        System.out.print(res);
//        InputStream inputStream=new BufferedInputStream(System.in);
//        byte[] b=new byte[1024];
//
//        try {
//            inputStream.read(b);
//            System.out.print("sada");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        int x=0x00ff;
//        System.out.println(x);
////        byte b=(byte)1;
//        x=x^b;
//        System.out.println(x);
    }

    /**
     *��document תΪstring
     * @param doc document
     * @return
     */
    public static String doc2FromatString(Document doc){
        StringWriter stringWriter=null;
        stringWriter = new StringWriter();
        if (doc!=null){
            OutputFormat format = new OutputFormat(doc,"UTF-8",true);
            XMLSerializer serializer = new XMLSerializer(stringWriter,format);
            try {
                serializer.asDOMSerializer();
                serializer.serialize(doc);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return stringWriter.toString();
    }
}
