package me.shreyasr.arrow;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

    public static void main(String[] args) throws SocketException {
        System.out.println("Hello world!");

        final DatagramSocket socket = new DatagramSocket(9998);
        final ClientManager cm = new ClientManager(socket);

        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bytes = new byte[PacketRouter.MAX_PACKET_SIZE];
                DatagramPacket recvPacket = new DatagramPacket(bytes,bytes.length);

                while (true) {
                    try {
                        socket.receive(recvPacket);
                    } catch (IOException e) {
                        Log.exception(e);
                        break;
                    }

                    cm.handleUdp(recvPacket);
                }
            }
        }).start();

        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            while (true) {
                try {
                    Log.m("Waiting for client!");
                    Socket client = serverSocket.accept();
                    new Thread(new HandshakeTask(cm, client)).start();
                } catch (IOException e) {
                    Log.exception(e);
                }
            }
        } catch (IOException e) {
            Log.exception(e);
        }
    }
}
