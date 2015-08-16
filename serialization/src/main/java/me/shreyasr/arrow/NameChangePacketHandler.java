package me.shreyasr.arrow;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class NameChangePacketHandler implements PacketHandler {

    public static int TYPE = 6;

    public static byte[] encodePacket(int playerId, String name) {
        if (name.length() > 28)
            name = name.substring(0, 28);
        byte[] nameBytes = name.getBytes(Charset.forName("ascii"));
        return ByteBuffer.wrap(new byte[40])
                .putInt(TYPE)
                .putInt(playerId)
                .putInt(name.length())
                .put(nameBytes, 12, Math.min(28, nameBytes.length))
                .array();
    }

    @Override
    public void handlePacket(ByteBuffer data) {
        int playerId = data.getInt();
        int len = data.getInt();
        byte[] nameBytes = new byte[len];
        data.get(nameBytes, 0, len);
        String name = new String(nameBytes);
        for (Listener l : listeners) {
            l.onReceive(playerId, name);
        }
    }

    List<Listener> listeners = new ArrayList<Listener>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public interface Listener {
        void onReceive(int playerId, String name);
    }
}