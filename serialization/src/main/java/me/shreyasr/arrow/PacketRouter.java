package me.shreyasr.arrow;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class PacketRouter {

    public static final int MAX_PACKET_SIZE = 41;

    public final PlayerPacketHandler playerPacketHandler;
    public final ProjectilePacketHandler projectilePacketHandler;
    public final CollisionPacketHandler collisionPacketHandler;

    public PacketRouter() {
        playerPacketHandler = new PlayerPacketHandler();
        projectilePacketHandler = new ProjectilePacketHandler();
        collisionPacketHandler = new CollisionPacketHandler();
    }

    public void handleIncomingPacket(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);

        byte sanityCheck = buffer.get();
        if (sanityCheck != 42) {
            System.out.println("Failed sanity check! " + Arrays.toString(data));
        }

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
