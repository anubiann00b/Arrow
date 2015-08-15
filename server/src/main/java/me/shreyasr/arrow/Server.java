package me.shreyasr.arrow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        ClientManager cm = new ClientManager();

        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            while (true) {
                try {
                    Log.m("Waiting for client!");
                    Socket client = serverSocket.accept();
                    new Thread(new ClientHandleTask(cm, client)).start();
                } catch (IOException e) {
                    Log.exception(e);
                }
            }
        } catch (IOException e) {
            Log.exception(e);
        }
    }
}
