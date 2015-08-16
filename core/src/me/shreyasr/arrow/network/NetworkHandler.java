package me.shreyasr.arrow.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import me.shreyasr.arrow.PacketRouter;
import me.shreyasr.arrow.PlayerPacketHandler;
import me.shreyasr.arrow.entity.Player;

public class NetworkHandler implements Runnable {

    private String ipAddress;
    private InetAddress inetAddress;
    public int clientId = -1;
    PacketRouter router = new PacketRouter();
    Socket tcpSocket;
    DatagramSocket socket;

    public NetworkHandler(String ipAddress, PlayerPacketHandler.Listener playerPacketListener) {
        this.ipAddress = ipAddress;
        try {
            inetAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            Log.exception(e);
        }
        router.playerPacketHandler.addListener(playerPacketListener);
    }

    @Override
    public void run() {
        try {
            tcpSocket = new Socket(ipAddress,9999);
            clientId = tcpSocket.getInputStream().read();
        } catch (IOException e) {
            Log.exception(e);
            return;
        }

        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            Log.exception(e);
            return;
        }

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

                    router.handleIncomingPacket(recvPacket.getData());
                }
            }
        }).start();
    }

    public void update(Player p) {
        if (socket == null) return;
        if (clientId == -1) return;
        try {
            byte[] data = PlayerPacketHandler
                    .encodePacket(clientId, p.health, (int) p.pos.x, (int) p.pos.y, p.dir);
            socket.send(new DatagramPacket(data, data.length, inetAddress, 9998));
        } catch (IOException e) {
            Log.exception(e);
            socket = null;
        }
    }
}
