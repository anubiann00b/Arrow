package me.shreyasr.arrow;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class UdpClient {

    public final InetAddress ip;
    public final int port;

    public UdpClient(InetAddress ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public UdpClient(DatagramPacket packet) {
        this(packet.getAddress(), packet.getPort());
    }

    @Override
    public int hashCode() {
        int result = ip.hashCode();
        result = 31 * result + port;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof UdpClient && ((UdpClient)o).ip.equals(ip) && ((UdpClient)o).port == port;
    }

    @Override
    public String toString() {
        return ip + ":" + port;
    }
}
