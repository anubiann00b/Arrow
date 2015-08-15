package me.shreyasr;

import java.nio.ByteBuffer;

public interface PacketHandler {

    void handlePacket(ByteBuffer data);
}
