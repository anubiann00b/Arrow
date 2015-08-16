package me.shreyasr.arrow;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import me.shreyasr.arrow.model.ObstacleModel;
import me.shreyasr.arrow.model.PlayerModel;
import me.shreyasr.arrow.model.ProjectileModel;
import me.shreyasr.arrow.model.util.CartesianPosition;
import me.shreyasr.arrow.model.util.PolarVelocity;

public class ClientManager {

    private final List<Client> clients = new ArrayList<Client>();
    private final Map<Integer, PlayerModel> players = new HashMap<Integer, PlayerModel>();
    private final Map<ProjectileID, ProjectileModel> projectiles = new HashMap<ProjectileID, ProjectileModel>();
    private final Map<Integer, ObstacleModel> obstacles = new HashMap<Integer, ObstacleModel>();
    private LinkedHashMap<Integer, String> messageQueue = new LinkedHashMap<Integer, String>();
    private final Queue<byte[]> packetQueue = new LinkedBlockingQueue<byte[]>();
    public final AtomicInteger clientCounter = new AtomicInteger();
    private static final int DAMAGE = 5;

    PacketRouter packetRouter = new PacketRouter();
    private DatagramSocket socket;

    final Set<UdpClient> udpClients = new HashSet<UdpClient>();

    public void handleUdp(DatagramPacket packet) {
        synchronized (udpClients) {
            udpClients.add(new UdpClient(packet));
        }
        handlePacket(packet.getData());
    }

    private void handlePacket(byte[] arr) {
        packetRouter.handleIncomingPacket(arr);
    }

    public ClientManager(DatagramSocket socket) {
        this.socket = socket;
        registerListeners();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    updateClients();
                } catch (Exception e) {
                    Log.exception(e);
                }
            }
        }, 16, 16);
    }

    private void updateClients() {
        //calculate damage taken and update model
        synchronized (projectiles) {
            for (PlayerModel player : new ArrayList<PlayerModel>(players.values())) {
                for (Iterator<ProjectileModel> iterator
                     = projectiles.values().iterator(); iterator.hasNext(); ) {
                    ProjectileModel projectile = iterator.next();
                    if (projectile.playerID == player.playerId
                            && System.currentTimeMillis()-projectile.beginningTime < 800) continue;
                    if (ProjectileCollisionDetector.hasCollided(player, projectile)) {
                        iterator.remove();

                        player.health -= DAMAGE;

                        packetQueue.add(CollisionPacketHandler.encodePacket(
                                projectile.projectileID, projectile.playerID, player.playerId));
//                            System.out.println(projectile.playerID + " hit " + player.playerId);
                    } else {
//                            System.out.println("miss");
                    }
                    //but client is still rendering projectile
                }
            }
        }
        synchronized (udpClients) {
            byte[] packet;
            while ((packet = packetQueue.poll()) != null) {
                for (Iterator<UdpClient> iterator = udpClients.iterator(); iterator.hasNext(); ) {
                    UdpClient client = iterator.next();
                    try {
                        socket.send(new DatagramPacket(packet, packet.length, client.ip, client.port));
//                            System.out.println("Send to " + client);
                    } catch (IOException e) {
                        Log.exception(e);
                        iterator.remove();
                        Log.m("Removed client: " + client);
                    }
                }
            }
        }
    }

    public void addClient(Client client) {
        synchronized (clients) {
            clients.add(client);
        }
    }

    public void removeClient(int clientId) {
        synchronized (clients) {
            clients.remove(new Client(clientId, null));
        }
    }

    private void registerListeners() {
        packetRouter.playerPacketHandler.addListener(new PlayerPacketHandler.Listener() {
            @Override
            public void onReceive(int playerId, int health, int x, int y, int direction) {
                PlayerModel player;
                if (players.containsKey(playerId)) {
                    player = players.get(playerId);
                } else {
                    player = new PlayerModel(playerId);
                    players.put(playerId, player);
                }
//                    System.out.println("Recv from " + player.playerId);

                player.x = x;
                player.y = y;
                player.direction = direction;

                //prepare to write back to client
                packetQueue.add(PlayerPacketHandler
                        .encodePacket(playerId, player.health, x, y, direction));
            }
        });
        packetRouter.projectilePacketHandler.addListener(new ProjectilePacketHandler.Listener() {
            @Override
            public void onReceive(int playerId, int projectileId, int startX, int startY,
                                  long startTime, double direction, int speed) {
                ProjectileModel projectileModel = new ProjectileModel(projectileId, playerId,
                        startTime, new PolarVelocity(direction, speed),
                        new CartesianPosition(startX, startY));
                synchronized (projectiles) {
                    projectiles.put(new ProjectileID(playerId, projectileId), projectileModel);
                }
                packetQueue.add(ProjectilePacketHandler.encodePacket(playerId, projectileId, startX,
                        startY, startTime, direction, speed));
            }
        });
        packetRouter.nameChangePacketHandler.addListener(new NameChangePacketHandler.Listener() {
            @Override
            public void onReceive(int playerId, String name) {
                packetQueue.add(NameChangePacketHandler.encodePacket(playerId, name));
            }
        });
    }
}
