package chat.Server;

import chat.Helper.JsonHelper;
import chat.Message.model.Message;

import java.io.*;
import java.net.Socket;

/**
 * created by ewang on 2018/4/19.
 */
public class ServerThread extends Thread {

    private final Long id;
    private final BufferedReader br;

    public ServerThread(Long id) throws IOException {
        this.id = id;
        Socket socket = Server.socketMap.get(id);
        this.br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
    }

    @Override
    public void run() {
        String data;
        while ((data = readFromClient()) != null) {
            Message message = JsonHelper.decode(data, Message.class);
            long toUserId = message.getToUserId();
            Socket toSocket = Server.socketMap.get(toUserId);
            if (toSocket != null) {
                try {
                    OutputStream outputStream=toSocket.getOutputStream();
                    outputStream.write((message.getContent() + "\n").getBytes("utf-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("server写出消息内容错误");
                }
            }

        }
    }

    public String readFromClient() {
        try {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            Server.socketMap.remove(id);
            return null;
        }
    }
}
