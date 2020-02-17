package com.company.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;
    private ConnectionListener connectionListener;
    private List<ClientHandler> clientList = new ArrayList<>();
    private List<Message> messageList = new ArrayList<>();

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            connectionListener = new ConnectionListener(serverSocket, this);
            new Thread(connectionListener).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    public void stop() {
        for (ClientHandler c : clientList) {
            c.endSession();
        }
        System.out.println("Server off.");
    }
    */


    public synchronized void addClient (Socket socket) {
        try {
            ClientHandler c = new ClientHandler(socket, this);
            new Thread(c).start();
            clientList.add(c);
            System.out.println("Client connected");
        }
        catch (IOException e) {
            System.out.println("Client connection failed: " + socket);
        }
    }

    public synchronized void sendServerMessage (Message message) {
        if (message.messageType == MessageType.ServerJoin) {
        }
    }

    public synchronized void sendPublicMessage(Message message) {
        messageList.add(message);
        for (ClientHandler c : clientList) {
            try {
                c.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void sendPrivateMessage (Message message) {

    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(1111);
    }
}
