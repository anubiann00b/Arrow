package me.shreyasr.arrow;

import java.nio.ByteBuffer;

public class PacketRouter {

    public static final int MAX_PACKET_SIZE = 36;

    public final PlayerPacketHandler playerPacketHandler;
    public final ProjectilePacketHandler projectilePacketHandler;
    public final CollisionPacketHandler collisionPacketHandler;

    public PacketRouter() {
        playerPacketHandler = new PlayerPacketHandler();
        projectilePacketHandler = new ProjectilePacketHandler();
        collisionPacketHandler = new CollisionPacketHandler();
    }

    public void handleIncomingPacket(byte[] data) {
        ByteBuffer bufferdata = ByteBuffer.wrap(data);
        int type = bufferdata.getInt();

        if (type == PlayerPacketHandler.type){
            playerPacketHandler.handlePacket(bufferdata);
        } else if (type == ProjectilePacketHandler.type){
            projectilePacketHandler.handlePacket(bufferdata);
        } else if (type == CollisionPacketHandler.type){
            collisionPacketHandler.handlePacket(bufferdata);
        }
    }
}
