package me.shreyasr.arrow;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import me.shreyasr.arrow.model.util.Projectile;

public class ClientManager {

    private final List<Client> clients = new ArrayList<Client>();
    private final Map<Integer, PlayerModel> players = new HashMap<Integer, PlayerModel>();
    private final Map<Integer, ProjectileModel> projectiles = new HashMap<Integer, ProjectileModel>();
    private final Map<Integer, ObstacleModel> obstacles = new HashMap<Integer, ObstacleModel>();
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
        synchronized (packetQueue) {
            synchronized (udpClients) {
                for (Iterator<UdpClient> iterator = udpClients.iterator(); iterator.hasNext(); ) {
                    UdpClient client = iterator.next();
                    for (byte[] packet : packetQueue) {
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
            packetQueue.clear();
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
                synchronized (players) {
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

                    //calculate damage taken and update model
                    int damageTaken = 0;
                    for (ProjectileModel projectile : projectiles.values()) {
                        if (ProjectileCollisionDetector.hasCollided(player,
                                projectile)) {
                            damageTaken += DAMAGE;
                            projectiles.remove(projectile.projectileID);
                            System.out.println("hit");
                        }
                        else {
                            System.out.println("miss");
                        }
                        //but client is still rendering projectile
                    }
                    player.health -= damageTaken;

                    //prepare to write back to client
                    packetQueue.add(PlayerPacketHandler.encodePacket(playerId, player.health, x, y, direction));
                }
            }
        });
        packetRouter.projectilePacketHandler.addListener(new ProjectilePacketHandler.Listener() {
            @Override
            public void onReceive(int playerId, int projectileId, int startX, int startY,
                                  long startTime, double direction, int speed) {
                //so we would get here if a new projectile was shot
                Projectile projectile = new Projectile(new PolarVelocity(direction, speed), new CartesianPosition(startX, startY));
                ProjectileModel projectileModel = new ProjectileModel(projectileId, playerId, projectile);
                projectiles.put(projectileId, projectileModel);
            }
        });
        packetRouter.collisionPacketHandler.addListener(new CollisionPacketHandler.Listener() {
          public void onReceive(int projectileID, int playerID, int hitPlayerID)   {
              //the server is responsible for discovering projectile collisions, so would it send a
              //packet to itself?
              packetQueue.add(CollisionPacketHandler.encodePacket(projectileID, playerID, hitPlayerID));
          }
        });
    }
}
