package com.andycjstefan.icebreaker_android;

/**
 * Wrapper for a message sent in a private chat.
 * Stores message text, ids of sending and receiving user,
 * and timestamp (long).
 */

public class Message {

    private int senderId, receiverId;
    private String text;
    private long timeStamp;

    // parses Message from string
    // String is simply comma-separated values TODO: COMMA-SEPARATION DOESN'T ALLOW MESSAGES TO CONTAIN COMMAS
    public Message(String fromString) {
        String[] values = fromString.split(",");
        senderId = Integer.parseInt(values[0]);
        receiverId = Integer.parseInt(values[1]);
        text = values[2];
        timeStamp = Long.parseLong(values[3]);
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getText() {
        return text;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return "Message from " + senderId + " to " + receiverId + ": '" + text + "'. Sent " + timeStamp;
    }
}
