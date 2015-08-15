package me.shreyasr;

public class PacketRouter {

    public final ProjectilePacketHandler projectilePacketHandler;

    public PacketRouter() {
        projectilePacketHandler = new ProjectilePacketHandler();
    }

    public void handleIncomingPacket(byte[] data) {

    }
}
