package me.shreyasr.arrow;

import java.net.Socket;

public class Client {

    public final int id;
    public final Socket socket;

    public Client(int id, Socket socket) {
        this.id = id;
        this.socket = socket;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Client && id == ((Client) o).id;
    }
}
