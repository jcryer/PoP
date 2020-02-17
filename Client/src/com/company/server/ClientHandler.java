package com.company.server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    Socket socket;
    Server server;
    ObjectInputStream serverIn;
    ObjectOutputStream serverOut;
    String name;

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.name = "unknown";
        this.socket = socket;
        this.server = server;
        serverIn = new ObjectInputStream(socket.getInputStream());
        serverOut = new ObjectOutputStream(socket.getOutputStream());
    }

    public void sendMessage(Message message) throws IOException {
        serverOut.writeObject(message);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message received = (Message)serverIn.readObject();
                switch (received.messageType) {
                    case Public:
                        server.sendPublicMessage(received);
                        break;
                    case DM:
                        server.sendPrivateMessage(received);
                        break;
                    case ServerJoin:
                        this.name = received.content;
                        server.sendServerMessage(received);
                        break;
                    case ServerLeave:
                        server.sendServerMessage(received);
                        socket.close();
                        return;
                }

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
