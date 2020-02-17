package com.company.client;

import com.company.server.Message;
import com.company.server.MessageType;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    Socket socket;
    ObjectInputStream clientIn;
    ObjectOutputStream clientOut;
    BufferedReader console;
    String name;
    Thread thread;

    public Client(String ipString, int port) throws IOException {
        InetAddress ip = InetAddress.getByName(ipString);
        socket = new Socket(InetAddress.getByName(ipString), port);
        clientOut = new ObjectOutputStream(socket.getOutputStream());
        console = new BufferedReader(new InputStreamReader(System.in));
        name = "Test";

        Receiver receiver = new Receiver(socket);
        Thread thread = new Thread(receiver);
        thread.start();
        loop();
    }

    void loop() {
        while (!socket.isClosed()) {
            try {
                String toSend = console.readLine();
                Message message = new Message(toSend, MessageType.Public, name);
                clientOut.writeObject(message);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
       new Client("localhost", 1111);
    }
}
