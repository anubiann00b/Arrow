package me.shreyasr.arrow;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

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
        Log.m("Accepted client! " +  clientId + " @ "
                + clientSocket.getRemoteSocketAddress());
        cm.addClient(new Client(clientId, clientSocket));

        while (true) {
            byte[] arr = new byte[PacketRouter.MAX_PACKET_SIZE];
            try {
                in.read(arr);
            } catch (IOException e) {
                Log.exception(e);
                cm.removeClient(clientId);
                Log.m("Removed client: " + clientId);
                break;
            }
            cm.handlePacket(arr);
        }
    }
}
