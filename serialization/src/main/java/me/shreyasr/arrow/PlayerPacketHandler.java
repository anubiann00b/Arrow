package me.shreyasr.arrow;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class PlayerPacketHandler implements PacketHandler {

    public static int TYPE = 0;

    public static byte[] encodePacket(int playerId, int health, int x, int y, int direction){
        return ByteBuffer.wrap(new byte[PacketRouter.MAX_PACKET_SIZE])
                .putInt(TYPE)
                .putInt(playerId)
                .putInt(health)
                .putInt(x)
                .putInt(y)
                .putInt(direction)
                .array();
    }

    @Override
    public void handlePacket(ByteBuffer data) {
        int playerId = data.getInt();
        int health = data.getInt();
        int x = data.getInt();
        int y = data.getInt();
        int dir = data.getInt();
//        System.out.println("id:" + playerId
//                + " | hp:" + health
//                + " | (" + x + "," + y + ")"
//                + " | dir:" + dir);
        for (Listener l : listeners) {
            l.onReceive(playerId, health, x, y, dir);
        }
    }

    List<Listener> listeners = new ArrayList<Listener>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public interface Listener {
        void onReceive(int playerId, int health, int x, int y, int direction);
    }
}