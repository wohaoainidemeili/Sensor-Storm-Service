package storm.FinalTopology;

import java.net.ServerSocket;

/**
 * Created by Administrator on 2016/5/8.
 */
public class StaticServerSocket {
    public static ServerSocket serverSocket;
    public StaticServerSocket(ServerSocket serverSocket){
        this.serverSocket=serverSocket;
    }
}
