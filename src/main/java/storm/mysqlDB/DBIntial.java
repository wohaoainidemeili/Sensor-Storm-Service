package storm.mysqlDB;

import storm.SensorConfigInfo;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于初始化数据库操作，执行DBInitial.sql文件
 * Created by Yuan on 2016/4/20.
 */
public class DBIntial {
    public static void initial(){
        //读取数据sql文件
        List<String> sqlList=new ArrayList<String>();
        try {
            InputStream sqlFile=ClassLoader.getSystemResourceAsStream("DBInitial");//加载Resources中的DBInitial文件
            StringBuffer sqlBuffer=new StringBuffer();
            byte[] buff=new byte[1024];
            int byteReader=0;
            while ((byteReader=sqlFile.read(buff))!=-1){
                sqlBuffer.append(new String(buff,0,byteReader));
            }

            // Windows 下换行是 /r/n, Linux 下是 /n
            String[] sqlArr = sqlBuffer.toString().split("(;\\s*\\r\\n)|(;\\s*\\n)");//正则表达式
            for (int i=0;i<sqlArr.length;i++){
                String sql=sqlArr[i].replace("- -.*","").trim();
                if (!sql.equals("")) sqlList.add(sql);
            }
        } catch (Exception e) {
            System.out.println("读取SQL文件错误！错误信息：" + e.getMessage());
        }

        //加载数据库池并执行数据库语句
        //获取数据库连接的参数信息
        String host= SensorConfigInfo.getHost();
        String user=SensorConfigInfo.getUser();
        String passWord=SensorConfigInfo.getPassword();
        String dbname= SensorConfigInfo.getDbName();
        String driver=SensorConfigInfo.getDriver();

        //链接数据库
        SqlConnectionPool connectionPool=new SqlConnectionPool(host,dbname,user,passWord,driver);
        Connection con= connectionPool.getConnection();
        Statement state=null;

        //批量加入sql语句，并执行
        try {
            state=con.createStatement();
            for(String sql:sqlList) {
                state.addBatch(sql);
            }
            state.executeBatch();
        } catch (SQLException e) {
            System.out.print("数据库连接失败！"+e.getMessage());
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
