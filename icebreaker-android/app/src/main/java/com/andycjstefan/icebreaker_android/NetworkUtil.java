package com.andycjstefan.icebreaker_android;

/**
 * Static methods working with Volley object
 */

public class NetworkUtil {

    private static final String BASE_URL = "http://partrico.pythonanywhere.com";

    // generate URL to request id for new user
    public static String getNewUserURL(String name, String photoBase64) {
        return BASE_URL + String.format("/add_user?name=%s&photo=%s", name, photoBase64);
    }

    public static String getRoomsByUser(int userId) {
        return BASE_URL + "/rooms_by_user/" + userId;
    }

    public static String getUsersByRoom(String roomPassword, int userId) {
        return BASE_URL + String.format("/users_by_room?password=%s&username=%s", roomPassword, userId);
    }
}
