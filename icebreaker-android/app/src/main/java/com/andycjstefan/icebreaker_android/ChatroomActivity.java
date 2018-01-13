package com.andycjstefan.icebreaker_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Shows all profiles of users logged in to the chat room. Selecting one opens at new ChatActivity
 * with the specified profile.
 */

public class ChatRoomActivity extends Activity implements ProfileAdapter.OnProfileSelectedListener {

    // string used to retrieve room id, which is passed in as an extra
    public static final String EXTRA_ROOM_PASSWORD = "EXTRA_ROOM_PASSWORD"; // TODO: USE ID, OR PASSWORD? OR ARE THEY THE SAME?

    private TextView roomNameText;
    private RecyclerView profilesView;

    // user id
    private int userId = 1;
    // id/password of the chat room
    private String roomPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom_activity);

        roomPassword = getIntent().getStringExtra(EXTRA_ROOM_PASSWORD);

        if (roomPassword == null || roomPassword.isEmpty()) {
            throw new IllegalArgumentException("ChatRoomActivity requires EXTRA_ROOM_PASSWORD parameter");
        }

        roomNameText = findViewById(R.id.chatroom_name);
        roomNameText.setText("Chat Room Name Goes Here");

        profilesView = findViewById(R.id.chatroom_profiles_view);
        profilesView.setLayoutManager(new LinearLayoutManager(this));

        ProfileAdapter profileAdapter = new ProfileAdapter(this, loadProfilesForRoom(roomPassword), roomPassword);
        profileAdapter.setmListener(this);
        profilesView.setAdapter(profileAdapter);
    }

    @Override // callback from ProfileAdapter notifying that a Profile has been selected.
    // Launch new ChatActivity
    public void onProfileSelected(Profile profile) {
        Intent chat_intent = new Intent(this, ChatActivity.class);
        chat_intent.putExtra(ChatActivity.EXTRA_USER_ID, userId); // TODO: PUT CURRENT USER ID
        chat_intent.putExtra(ChatActivity.EXTRA_PARTNER_ID, profile.getUserId());
        startActivity(chat_intent);
    }

    private List<Profile> loadProfilesForRoom(String password) {
        List<Profile> loaded = new ArrayList<>();
        loaded.add(new Profile("Stefan"));
        loaded.add(new Profile("Andy"));
        loaded.add(new Profile("CJ"));
        return loaded;
    }
}
