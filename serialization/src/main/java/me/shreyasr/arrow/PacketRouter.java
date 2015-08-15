package me.shreyasr.arrow;

public class PacketRouter {

    public static final int MAX_PACKET_SIZE = 36;

    public final ProjectilePacketHandler projectilePacketHandler;
    public final CollisionPacketHandler collisionPacketHandler;

    public PacketRouter() {

        projectilePacketHandler = new ProjectilePacketHandler();
        collisionPacketHandler = new CollisionPacketHandler();
    }

    public void handleIncomingPacket(byte[] data) {

    }
}
