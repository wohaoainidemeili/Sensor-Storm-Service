package storm.mysqlDB;

import storm.SensorConfigInfo;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ���ڳ�ʼ�����ݿ������ִ��DBInitial.sql�ļ�
 * Created by Yuan on 2016/4/20.
 */
public class DBIntial {
    public static void initial(){
        //��ȡ����sql�ļ�
        List<String> sqlList=new ArrayList<String>();
        try {
            InputStream sqlFile=ClassLoader.getSystemResourceAsStream("DBInitial");//����Resources�е�DBInitial�ļ�
            StringBuffer sqlBuffer=new StringBuffer();
            byte[] buff=new byte[1024];
            int byteReader=0;
            while ((byteReader=sqlFile.read(buff))!=-1){
                sqlBuffer.append(new String(buff,0,byteReader));
            }

            // Windows �»����� /r/n, Linux ���� /n
            String[] sqlArr = sqlBuffer.toString().split("(;\\s*\\r\\n)|(;\\s*\\n)");//������ʽ
            for (int i=0;i<sqlArr.length;i++){
                String sql=sqlArr[i].replace("- -.*","").trim();
                if (!sql.equals("")) sqlList.add(sql);
            }
        } catch (Exception e) {
            System.out.println("��ȡSQL�ļ����󣡴�����Ϣ��" + e.getMessage());
        }

        //�������ݿ�ز�ִ�����ݿ����
        //��ȡ���ݿ����ӵĲ�����Ϣ
        String host= SensorConfigInfo.getHost();
        String user=SensorConfigInfo.getUser();
        String passWord=SensorConfigInfo.getPassword();
        String dbname= SensorConfigInfo.getDbName();
        String driver=SensorConfigInfo.getDriver();

        //�������ݿ�
        SqlConnectionPool connectionPool=new SqlConnectionPool(host,dbname,user,passWord,driver);
        Connection con= connectionPool.getConnection();
        Statement state=null;

        //��������sql��䣬��ִ��
        try {
            state=con.createStatement();
            for(String sql:sqlList) {
                state.addBatch(sql);
            }
            state.executeBatch();
        } catch (SQLException e) {
            System.out.print("���ݿ�����ʧ�ܣ�"+e.getMessage());
        }
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

    }
}
