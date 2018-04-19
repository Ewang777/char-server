package chat.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * created by ewang on 2018/4/18.
 */
public class Server {

    static Map<Long, Socket> socketMap = new HashMap<>();

    public static void main(String args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7777);
        while (true) {
            Socket socket = serverSocket.accept();
            Long socketId = getSocketId(socket);
            if (socketId != null) {
                socketMap.put(socketId, socket);
                new Thread().start();
            }

        }
    }

    static Long getSocketId(Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
        String idStr = bufferedReader.readLine();
        try {
            return Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
