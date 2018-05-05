package chat.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * created by ewang on 2018/4/18.
 */
public class Server {

    static Map<Long, Socket> socketMap = new HashMap<>();

    public static void main(String[] args) {
        try {

            ServerSocket serverSocket = new ServerSocket(7777);
            System.out.println("server is here!");
            while (true) {
                System.out.println("waiting...");
                Socket socket = serverSocket.accept();
                Long socketId = getSocketId(socket);
                System.out.println("here comes a client whose id is " + socketId);
                if (socketId != null) {
                    socketMap.put(socketId, socket);
                    ServerThread serverThread = new ServerThread(socketId);
                    serverThread.start();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static Long getSocketId(Socket socket) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
            String idStr = bufferedReader.readLine();
            return Long.parseLong(idStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
