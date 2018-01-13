package com.andycjstefan.icebreaker_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

// Shows all Chatrooms the user has access to. Selecting one will start a new ChatRoomActivity
// showing the profiles logged in to the room.

public class AllChatRoomsActivity extends Activity implements ChatRoomAdapter.OnChatRoomClickListener {

    // displays scrolling list of chat rooms
    private RecyclerView roomRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_chatrooms);

        roomRecyclerView = findViewById(R.id.rooms_recyclerview);
        roomRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        int user_id = 1;

        List<ChatRoom> chat_rooms = new LinkedList<>();
        List<Profile> users = new LinkedList<>();
        users.add(new Profile("Stefan"));
        users.add(new Profile("Andy"));
        users.add(new Profile("CJ"));
        chat_rooms.add(new ChatRoom("Chat Room", "First Chat Room", "AA23", users));
        Log.d("AllChatRoomsActivity", chat_rooms.get(0).toString());
        ChatRoomAdapter chatRoomAdapter = new ChatRoomAdapter(this, chat_rooms, user_id);
        // set this class to receive events
        chatRoomAdapter.setListener(this);
        roomRecyclerView.setAdapter(chatRoomAdapter);
    }

    // called when the user selects a chat room. Start a ChatRoomActivity with the chat room's id.
    @Override
    public void onChatRoomClicked(ChatRoom selectedChatRoom) {
        Intent room_intent = new Intent(this, ChatRoomActivity.class);
        room_intent.putExtra(ChatRoomActivity.EXTRA_ROOM_ID, selectedChatRoom.getPassword());
//        chat_intent.putExtra(ChatActivity.EXTRA_USER_ID, 1); // TODO: PUT CURRENT USER ID
//        chat_intent.putExtra(ChatActivity.EXTRA_PARTNER_ID, 2); // TODO: PUT PARTNER ID
        startActivity(room_intent);
    }
}
