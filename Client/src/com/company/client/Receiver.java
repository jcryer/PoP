package com.company.client;

import com.company.server.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver implements Runnable {
    Socket socket;
    ObjectInputStream clientIn;

    public Receiver(Socket socket) throws IOException {
        this.socket = socket;
        clientIn = new ObjectInputStream(socket.getInputStream());
    }
    @Override
    public void run() {
        while (true) {
            try {
                Message message = (Message)clientIn.readObject();
                System.out.println(message.content);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
