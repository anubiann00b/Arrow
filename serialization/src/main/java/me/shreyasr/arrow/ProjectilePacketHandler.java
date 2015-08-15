package me.shreyasr.arrow;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ProjectilePacketHandler implements PacketHandler {

    public static int type=1;

    public static byte[] encodePacket(int playerId, int projectileId, int startX, int startY,
                                      long startTime, double direction, int velocity) {
        return ByteBuffer.wrap(new byte[36])
                .putInt(playerId)
                .putInt(projectileId)
                .putInt(startX)
                .putInt(startY)
                .putLong(startTime)
                .putDouble(direction)
                .putInt(velocity)
                .array();
    }

    @Override
    public void handlePacket(ByteBuffer data) {
        for (Listener l : listeners) {
            l.onReceive(
                    data.getInt(),
                    data.getInt(),
                    data.getInt(),
                    data.getInt(),
                    data.getLong(),
                    data.getDouble(),
                    data.getInt()
            );
        }
    }

    List<Listener> listeners = new ArrayList<Listener>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public interface Listener {
        void onReceive(int playerId, int projectileId, int startX, int startY,
                       long startTime, double direction, int velocity);
    }
}
