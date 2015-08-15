package me.shreyasr;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ProjectilePacketHandler implements PacketHandler {

    public static byte[] encodePacket(int playerId, int projectileId) {
        return ByteBuffer.wrap(new byte[8]).putInt(playerId).putInt(projectileId).array();
    }

    @Override
    public void handlePacket(ByteBuffer data) {
        for (Listener l : listeners) {
            l.onReceive(data.getInt(), data.getInt());
        }
    }

    List<Listener> listeners = new ArrayList<Listener>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public interface Listener {
        void onReceive(int playerId, int projectileId);
    }
}
