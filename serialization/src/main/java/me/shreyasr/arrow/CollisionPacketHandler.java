package me.shreyasr.arrow;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class CollisionPacketHandler implements PacketHandler {

    public static int TYPE = 2;

    public static byte[] encodePacket(int projectileId, int playerId, int hitPlayerId) {
        return ByteBuffer.wrap(new byte[16])
                .putInt(TYPE)
                .putInt(projectileId)
                .putInt(playerId)
                .putInt(hitPlayerId)
                .array();
    }

    @Override
    public void handlePacket(ByteBuffer data) {
        int projectileId = data.getInt();
        int playerId = data.getInt();
        int hitPlayerId = data.getInt();
//        System.out.println("Collision");
        for (Listener l : listeners) {
            l.onReceive(projectileId, playerId, hitPlayerId);
        }
    }

    List<Listener> listeners = new ArrayList<Listener>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public interface Listener {
        void onReceive(int projectileId, int playerId, int hitPlayerId);
    }
}
