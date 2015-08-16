package me.shreyasr.arrow;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DeathPacketHandler implements PacketHandler {

    public static int TYPE = 7;

    public static byte[] encodePacket(int killerId, int victimId) {
        return ByteBuffer.wrap(new byte[12])
                .putInt(TYPE)
                .putInt(killerId)
                .putInt(victimId)
                .array();
    }

    @Override
    public void handlePacket(ByteBuffer data) {
        int killerId = data.getInt();
        int victimId = data.getInt();
        for (Listener l : listeners) {
            l.onReceive(killerId, victimId);
        }
    }

    List<Listener> listeners = new ArrayList<Listener>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public interface Listener {
        void onReceive(int killerId, int victimId);
    }
}