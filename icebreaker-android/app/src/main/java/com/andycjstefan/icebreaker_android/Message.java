package com.andycjstefan.icebreaker_android;

/**
 * Wrapper for a message sent in a private chat.
 * Stores message text, ids of sending and receiving user,
 * and timestamp (long).
 */

public class Message {

    private int senderId = -1, receiverId = -1;
    private String text = "";
    private long timeStamp = 0;


    public Message() {

    }

    public Message(int senderId, int receiverId, String text, long timeStamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.timeStamp = timeStamp;
    }

    // parses Message from string
    // String is simply comma-separated values TODO: COMMA-SEPARATION DOESN'T ALLOW MESSAGES TO CONTAIN COMMAS
    public static Message parse(String fromString) {
        Message parsed = new Message();
        String[] values = fromString.split(",");
        parsed.senderId = Integer.parseInt(values[0]);
        parsed.receiverId = Integer.parseInt(values[1]);
        parsed.text = values[2];
        parsed.timeStamp = Long.parseLong(values[3]);
        return parsed;
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
