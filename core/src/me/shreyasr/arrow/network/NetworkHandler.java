package me.shreyasr.arrow.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import me.shreyasr.arrow.CollisionPacketHandler;
import me.shreyasr.arrow.DeathPacketHandler;
import me.shreyasr.arrow.NameChangePacketHandler;
import me.shreyasr.arrow.PacketRouter;
import me.shreyasr.arrow.PlayerPacketHandler;
import me.shreyasr.arrow.ProjectilePacketHandler;
import me.shreyasr.arrow.entity.Player;
import me.shreyasr.arrow.projectiles.Projectile;

public class NetworkHandler implements Runnable {

    private String ipAddress;
    private InetAddress inetAddress;
    public int clientId = -1;
    PacketRouter router = new PacketRouter();
    Socket tcpSocket;
    DatagramSocket socket;

    public NetworkHandler(String ipAddress,
                          PlayerPacketHandler.Listener playerPacketListener,
                          ProjectilePacketHandler.Listener projectilePacketListener,
                          CollisionPacketHandler.Listener collisionPacketListener,
                          NameChangePacketHandler.Listener nameChangePackeListener,
                          DeathPacketHandler.Listener deathListener) {
        this.ipAddress = ipAddress;
        try {
            inetAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            Log.exception(e);
        }
        router.playerPacketHandler.addListener(playerPacketListener);
        router.projectilePacketHandler.addListener(projectilePacketListener);
        router.collisionPacketHandler.addListener(collisionPacketListener);
        router.nameChangePacketHandler.addListener(nameChangePackeListener);
        router.deathPacketHandler.addListener(deathListener);
    }

    @Override
    public void run() {
        try {
            tcpSocket = new Socket(ipAddress,9999);
            clientId = tcpSocket.getInputStream().read();
        } catch (IOException e) {
            Log.exception(e);
            return;
        }

        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            Log.exception(e);
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bytes = new byte[PacketRouter.MAX_PACKET_SIZE];
                DatagramPacket recvPacket = new DatagramPacket(bytes,bytes.length);

                while (true) {
                    try {
                        socket.receive(recvPacket);
                    } catch (IOException e) {
                        Log.exception(e);
                        break;
                    }

                    router.handleIncomingPacket(recvPacket.getData());
                }
            }
        }).start();
    }

    public void update(Player p) {
        byte[] data = PlayerPacketHandler
                .encodePacket(clientId, p.health, (int) p.pos.x, (int) p.pos.y, p.dir);
        send(data);
    }

    public void sendProjectile(Projectile p) {
        byte[] data = ProjectilePacketHandler.encodePacket(clientId, p.id,
                (int) p.startPos.x, (int) p.startPos.y, p.beginningTime,
                p.velocity.getDirection(), (int) p.velocity.getSpeed());
        send(data);
    }

    private synchronized void send(byte[] data) {
        if (socket == null) return;
        if (clientId == -1) return;
        try {
            socket.send(new DatagramPacket(data, data.length, inetAddress, 9998));
        } catch (IOException e) {
            Log.exception(e);
            socket = null;
        }
    }

    public void updateName(String newName) {
        send(NameChangePacketHandler.encodePacket(clientId, newName));
    }
}
