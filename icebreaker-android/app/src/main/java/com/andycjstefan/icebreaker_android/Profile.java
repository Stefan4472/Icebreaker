package com.andycjstefan.icebreaker_android;

import android.graphics.Bitmap;
import android.media.Image;

/**
 * A profile stores the data on a user. It includes first name and profile photo.
 */

public class Profile {

    private String firstName;
    private Bitmap profileImg;
    private int userId;

    public Profile(String firstName) {
        this.firstName = firstName;
        //this.profileImg = profileImg;
    }

    public String getFirstName() {
        return firstName;
    }

    public Bitmap getProfileImg() {
        return profileImg;
    }

    public int getUserId() {
        return userId;
    }
}
