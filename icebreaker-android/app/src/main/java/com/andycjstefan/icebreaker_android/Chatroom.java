package com.andycjstefan.icebreaker_android;

import android.graphics.BitmapFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Stores all data on a Chatroom. This includes the name, description, password, and a list of
 * logged in user Profiles.
 */

public class Chatroom {

    private String name, description, password;
    private List<Profile> users = new LinkedList<>();

    public Chatroom(String name, String description, String password, List<Profile> users) {
        this.name = name;
        this.description = description;
        this.password = password;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPassword() {
        return password;
    }

    public List<Profile> getUsers() {
        return users;
    }

    // parses chatroom from string
    // String is simply comma-separated values TODO: COMMA-SEPARATION DOESN'T ALLOW MESSAGES TO CONTAIN COMMAS
    public static Chatroom parse(String fromString) {
        String[] values = fromString.split(",");
        String name = values[0];
        String description = values[1];
        String password = values[2];
        List<Profile> users = new LinkedList();
        for (int i = 3; i < values.length; i++) {
            users.add(new Profile(values[i]));//BitmapFactory.decodeResource(getResources(), R.drawable.my_image));
        }
        return new Chatroom(name, description, password, users);
    }
}
