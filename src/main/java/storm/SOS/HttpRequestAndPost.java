package storm.SOS;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * ��������Ĳ����͵�ַ��ȡ������
 * Created by Yuan on 2016/4/20.
 */
public class HttpRequestAndPost {
    //��������ַ��post����
    public String sendPost(String url,String param){
        String result="";
        BufferedReader in=null;
        PrintWriter out=null;
        //http post������ȡ���
        try {
            URL realURL=new URL(url);//��URL����
            URLConnection connection=realURL.openConnection();
            connection.setRequestProperty("accept","*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0(compatible;MSIE 6.0;Windows NT 5.1;SV1)");

            connection.setDoOutput(true);//����post��Ҫ����
            connection.setDoInput(true);

            //��ȡURLconnection��Ӧ�������
            out=new PrintWriter(new OutputStreamWriter(connection.getOutputStream(),"UTF-8"));
            out.print(param);
            out.flush();//д��Ҫ���͵Ĳ�����Ϣ

            //����Bufferedreader��ȡ�Ľ����Ϣ
            in=new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String line;
            while ((line=in.readLine())!=null){
                result+=line;
            }
            String encode=connection.getContentEncoding();
            String contentType=connection.getContentType();
            return result;
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return  result;
    }
}
