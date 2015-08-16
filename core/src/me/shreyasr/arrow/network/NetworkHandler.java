package me.shreyasr.arrow.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import me.shreyasr.arrow.PacketRouter;
import me.shreyasr.arrow.PlayerPacketHandler;
import me.shreyasr.arrow.entity.Player;

public class NetworkHandler implements Runnable {

    private String ipAddress;
    public int clientId = -1;
    PacketRouter router = new PacketRouter();
    Socket socket;

    public NetworkHandler(String ipAddress, PlayerPacketHandler.Listener playerPacketListener) {
        this.ipAddress = ipAddress;
        router.playerPacketHandler.addListener(playerPacketListener);
    }

    @Override
    public void run() {
        InputStream in;
        try {
            socket = new Socket(ipAddress,9999);
            in = socket.getInputStream();
            byte[] buffer = new byte[4];
            in.read(buffer);
            clientId = ByteBuffer.wrap(buffer).getInt();
        } catch (IOException e) {
            Log.exception(e);
            return;
        }

        while (true) {
            byte[] arr = new byte[PacketRouter.MAX_PACKET_SIZE];
            try {
                in.read(arr);
            } catch (IOException e) {
                Log.exception(e);
                break;
            }
            router.handleIncomingPacket(arr);

        }
    }

    public void update(Player p) {
        if (socket == null) return;
        if (clientId == -1) return;
        try {
            socket.getOutputStream().write(
                PlayerPacketHandler.encodePacket(clientId, p.health, (int)p.pos.x, (int)p.pos.y, p.dir)
            );
        } catch (IOException e) {
            Log.exception(e);
            socket = null;
        }
    }
}
