package com.company.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListener implements Runnable {
    ServerSocket serverSocket;
    Server server;

    public ConnectionListener(ServerSocket serverSocket, Server server) {
        this.serverSocket = serverSocket;
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            Socket socket = null;

            try {
                socket = serverSocket.accept();
                System.out.println("Connected! " + socket);
                server.addClient(socket);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
