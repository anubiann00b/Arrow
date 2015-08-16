package me.shreyasr.arrow;

import java.nio.ByteBuffer;

public class PacketRouter {

    public static final int MAX_PACKET_SIZE = 41;

    public final PlayerPacketHandler playerPacketHandler;
    public final ProjectilePacketHandler projectilePacketHandler;
    public final CollisionPacketHandler collisionPacketHandler;
    public final NameChangePacketHandler nameChangePacketHandler;

    public PacketRouter() {
        playerPacketHandler = new PlayerPacketHandler();
        projectilePacketHandler = new ProjectilePacketHandler();
        collisionPacketHandler = new CollisionPacketHandler();
        nameChangePacketHandler = new NameChangePacketHandler();
    }

    public void handleIncomingPacket(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);

        int type = buffer.getInt();

        if (type == PlayerPacketHandler.TYPE){
            playerPacketHandler.handlePacket(buffer);
        } else if (type == ProjectilePacketHandler.TYPE){
            projectilePacketHandler.handlePacket(buffer);
        } else if (type == CollisionPacketHandler.TYPE){
            collisionPacketHandler.handlePacket(buffer);
        }
    }
}
