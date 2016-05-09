package storm.mysqlDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ��ݿ����ӣ���ȡ��ݿ����ӳ�
 * Created by Yuan on 2016/4/20.
 */
public class SqlConnectionPool {
    String host;
    String dbName;
    String user;
    String password;
    String driver;
    public SqlConnectionPool(String host,String dbName,String user,String password,String driver){
        this.host=host;
        this.dbName=dbName;
        this.user=user;
        this.password=password;
        this.driver=driver;
    }
    //ͨ����ص���ݿ����������ݿ⣬��������ݿ�ʱ��Ҫ����con.close()�����ͷ���Դ��
    public Connection getConnection(){
        Connection con=null;

        //connect to the default db
        String connectUrl="jdbc:mysql://"+host+"/mysql?"+"user="+user+"&password="+password+"&useUnicode=true&characterEncoding=utf8";

        try {
            Class.forName(driver);
            con= DriverManager.getConnection(connectUrl);
            Statement statement=con.createStatement();
            statement.execute("create database if not EXISTS "+dbName);
            con=DriverManager.getConnection("jdbc:mysql://"+host+"/"+dbName,user,password);
        } catch (ClassNotFoundException e) {
            //System.out.println("��ݿ�����ʧ�ܣ�"+e.getMessage());
        } catch (SQLException e) {
           // System.out.println("��ݿⴴ��connectionʧ�ܣ�"+e.getMessage());
        }
        return con;
    }
}
