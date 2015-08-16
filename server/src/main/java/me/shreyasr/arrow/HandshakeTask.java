package me.shreyasr.arrow;

import java.io.IOException;
import java.net.Socket;

public class HandshakeTask implements Runnable {

    private ClientManager cm;
    private Socket clientSocket;

    public HandshakeTask(ClientManager cm, Socket clientSocket) {
        this.cm = cm;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        int clientId = cm.clientCounter.incrementAndGet();
        try {
            clientSocket.getOutputStream().write(clientId);
        } catch (IOException e) {
            Log.exception(e);
            return;
        }
        Log.m("Accepted client! " +  clientId + " @ "
                + clientSocket.getRemoteSocketAddress());
        cm.addClient(new Client(clientId, clientSocket));
    }
}
