package me.shreyasr.arrow;

import java.nio.ByteBuffer;

public interface PacketHandler {

    void handlePacket(ByteBuffer data);
}
