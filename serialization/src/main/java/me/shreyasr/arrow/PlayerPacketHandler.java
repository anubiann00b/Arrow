package me.shreyasr.arrow;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class PlayerPacketHandler implements PacketHandler {


    public static byte[] encodePacket(int playerId, int health, int x, int y, int direction){
        return ByteBuffer.wrap(new byte[16]).putInt(playerId).putInt(health).putInt(x).putInt(y).putInt(y).array();
    }

    @Override
    public void handlePacket(ByteBuffer data) {
        for (Listener l : listeners) {
            l.onReceive(data.getInt(), data.getInt(), data.getInt(), data.getInt(), data.getInt());
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
