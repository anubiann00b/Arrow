package me.shreyasr.arrow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import me.shreyasr.arrow.model.PlayerModel;
import me.shreyasr.arrow.model.ProjectileModel;

public class ClientManager {

    private final List<Client> clients = new ArrayList<Client>();
    private final Map<Integer, PlayerModel> players = new HashMap<Integer, PlayerModel>();
    private final Map<Integer, ProjectileModel> projectiles = new HashMap<Integer, ProjectileModel>();
    private final Queue<byte[]> packetQueue = new LinkedBlockingQueue<byte[]>();
    public final AtomicInteger clientCounter = new AtomicInteger();

    PacketRouter packetRouter = new PacketRouter();

    public void handlePacket(byte[] arr) {
        packetRouter.handleIncomingPacket(arr);
    }

    public ClientManager() {
        registerListeners();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                updateClients();
            }
        }, 16, 16);
    }

    private void updateClients() {
        synchronized (packetQueue) {
            for (Client client : clients) {
                for (byte[] packet : packetQueue) {
                    try {
                        client.socket.getOutputStream().write(packet);
                    } catch (IOException e) {
                        Log.exception(e);
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

    public void removeClient(Client client) {
        synchronized (clients) {
            clients.add(client);
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

                    player.health = health;
                    player.x = x;
                    player.y = y;
                    player.direction = direction;

                    packetQueue.add(PlayerPacketHandler.encodePacket(playerId, health, x, y, direction));
                }
            }
        });
        packetRouter.projectilePacketHandler.addListener(new ProjectilePacketHandler.Listener() {
            @Override
            public void onReceive(int playerId, int projectileId, int startX, int startY,
                                  long startTime, double direction, int velocity) {

            }
        });
        packetRouter.collisionPacketHandler.addListener(new CollisionPacketHandler.Listener() {
            @Override
            public void onReceive(int projectileId, int playerId, int hitPlayerId) {

            }
        });
    }
}
