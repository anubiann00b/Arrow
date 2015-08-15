package me.shreyasr.arrow;

public class PacketRouter {

    public static final int MAX_PACKET_SIZE = 36;

    public final ProjectilePacketHandler projectilePacketHandler;

    public PacketRouter() {
        projectilePacketHandler = new ProjectilePacketHandler();
    }

    public void handleIncomingPacket(byte[] data) {

    }
}
