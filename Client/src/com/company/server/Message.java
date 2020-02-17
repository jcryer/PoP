package com.company.server;

import java.io.*;

public class Message implements Serializable {
    String content;
    MessageType messageType;
    String from;
    String to;

    public Message(String content, MessageType messageType, String from, String to) {
        this.content = content;
        this.messageType = messageType;
        this.from = from;
        this.to = to;
    }

    public Message(String content, MessageType messageType, String from) {
        this.content = content;
        this.messageType = messageType;
        this.from = from;
    }
}

