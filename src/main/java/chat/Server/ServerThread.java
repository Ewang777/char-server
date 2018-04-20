package chat.Server;

import chat.Helper.JsonHelper;
import chat.Message.model.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * created by ewang on 2018/4/19.
 */
public class ServerThread implements Runnable {

    private final Long id;
    private final Socket socket;
    private final BufferedReader br;

    public ServerThread(Long id, Socket socket) throws IOException {
        this.id = id;
        this.socket = socket;
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
                    OutputStream outputStream = toSocket.getOutputStream();
                    outputStream.write(message.getContent().getBytes("utf-8"));
                } catch (IOException e) {
                    e.printStackTrace();
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
        }
        return null;
    }
}
