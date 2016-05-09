package storm;

import storm.mysqlDB.SensorInsertAndGet;
import storm.socketOperation.NewSingleSocket;
import storm.socketOperation.RecievedSensorDataSolve;
import storm.socketOperation.SingleThreadRecieveSensorDataSolve;
import storm.spout.SpoutParams;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2016/5/6.
 */
public class SocketTestMain {
    public static void main(String[] args){
        //initial all parameters
        String sensorID="urn:liesmars:insitusensor:BaoxieWeatherSoilStation1-FY-H2";
        SensorConfigReader.reader();
        SpoutParams spoutParams= SensorInsertAndGet.getSpoutParams(sensorID);

        //Socket part
//        SingleThreadRecieveSensorDataSolve singleThreadRecieveSensorDataSolve=new SingleThreadRecieveSensorDataSolve(params);
//        singleThreadRecieveSensorDataSolve.StartServer();
//        singleThreadRecieveSensorDataSolve.RecieveMessage();
//        NewSingleSocket singleSocket=new NewSingleSocket(params);
//        singleSocket.StartServer();
        ServerSocket serverSocket=null;
        Socket socket=null;
        try {
            serverSocket=new ServerSocket();
            serverSocket.bind(new InetSocketAddress(spoutParams.ipAddress,Integer.valueOf(spoutParams.port)));
            socket=serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                serverSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        RecievedSensorDataSolve recievedSensorDataSolve=new RecievedSensorDataSolve(spoutParams);
        recievedSensorDataSolve.RecieveMessage(socket);
    }
}
