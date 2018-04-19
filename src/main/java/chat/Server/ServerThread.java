package chat.Server;

import chat.Message.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
