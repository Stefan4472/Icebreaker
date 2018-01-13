package com.andycjstefan.icebreaker_android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

/**
 * Shows all profiles of users logged in to the chat room. Selecting one opens at new ChatActivity
 * with the specified profile.
 */

public class ChatRoomActivity extends Activity {

    // string used to retrieve room id, which is passed in as an extra
    public static final String EXTRA_ROOM_ID = "EXTRA_ROOM_ID"; // TODO: USE ID, OR PASSWORD? OR ARE THEY THE SAME?

    private TextView roomNameText;
    private RecyclerView profilesView;

    // id of the chat room
    private String roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom_activity);

        roomId = getIntent().getStringExtra(EXTRA_ROOM_ID);

        if (roomId == null || roomId.isEmpty()) {
            throw new IllegalArgumentException("ChatRoomActivity requires EXTRA_ROOM_ID parameter");
        }

        roomNameText = findViewById(R.id.chatroom_name);
        roomNameText.setText("Chat Room");

        profilesView = findViewById(R.id.chatroom_profiles_view);
    }
}
