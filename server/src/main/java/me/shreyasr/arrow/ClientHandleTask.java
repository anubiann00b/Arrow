package me.shreyasr.arrow;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ClientHandleTask implements Runnable {

    private ClientManager cm;
    private Socket clientSocket;

    public ClientHandleTask(ClientManager cm, Socket clientSocket) {
        this.cm = cm;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        InputStream in;
        int clientId = cm.clientCounter.incrementAndGet();
        try {
            clientSocket.getOutputStream().write(ByteBuffer.wrap(new byte[4])
                    .putInt(clientId).array());
            in = clientSocket.getInputStream();
        } catch (IOException e) {
            Log.exception(e);
            return;
        }
        cm.addClient(new Client(clientId, clientSocket));

        byte[] arr = new byte[PacketRouter.MAX_PACKET_SIZE];
        while (true) {
            Arrays.fill(arr, (byte) 0);
            try {
                in.read(arr);
            } catch (IOException e) {
                Log.exception(e);
                continue;
            }
            cm.handlePacket(arr);
        }
    }
}
