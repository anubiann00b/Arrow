package me.shreyasr.arrow;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import me.shreyasr.arrow.PacketHandler;

public class CollisionPacketHandler implements PacketHandler {
    /* order: projectileId, playerId, hitPlayerId */

    public static byte[] encodePacket(int projectileId, int playerId, int hitPlayerId) {
        return ByteBuffer.wrap(new byte[12]).putInt(projectileId).putInt(playerId).putInt(hitPlayerId).array();
    }

    @Override
    public void handlePacket(ByteBuffer data) {
        for (Listener l : listeners) {
            l.onReceive(data.getInt(), data.getInt(), data.getInt());
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
