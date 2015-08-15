package me.shreyasr.arrow;

public class PacketRouter {

    public final ProjectilePacketHandler projectilePacketHandler;
    public final CollisionPacketHandler collisionPacketHandler;

    public PacketRouter() {

        projectilePacketHandler = new ProjectilePacketHandler();
        collisionPacketHandler = new CollisionPacketHandler();
    }

    public void handleIncomingPacket(byte[] data) {

    }
}
