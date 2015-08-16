package me.shreyasr.arrow;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ProjectilePacketHandler implements PacketHandler {

    public static int TYPE = 1;

    public static byte[] encodePacket(int playerId, int projectileId, int startX, int startY,
                                      long startTime, double direction, int velocity) {
        return ByteBuffer.wrap(new byte[40])
                .putInt(TYPE)
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
        int playerId = data.getInt();
        int projectileId = data.getInt();
        int startX = data.getInt();
        int startY = data.getInt();
        long startTime = data.getLong();
        double direction = data.getDouble();
        int velocity = data.getInt();

//        System.out.println("Projectile");

        for (Listener l : listeners) {
            l.onReceive(playerId, projectileId, startX, startY, startTime, direction, velocity);
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
