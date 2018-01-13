package com.andycjstefan.icebreaker_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

        ChatRoomAdapter chatRoomAdapter = new ChatRoomAdapter(this, loadChatRoomsForUser(user_id), user_id);
        // set this class to receive events
        chatRoomAdapter.setListener(this);
        roomRecyclerView.setAdapter(chatRoomAdapter);
    }

    // called when the user selects a chat room. Start a ChatRoomActivity with the chat room's password.
    @Override
    public void onChatRoomClicked(ChatRoom selectedChatRoom) {
        Intent room_intent = new Intent(this, ChatRoomActivity.class);
        room_intent.putExtra(ChatRoomActivity.EXTRA_ROOM_PASSWORD, selectedChatRoom.getPassword());
        startActivity(room_intent);
    }

    private static List<ChatRoom> loadChatRoomsForUser(int userId) {
        List<ChatRoom> chat_rooms = new LinkedList<>();
        List<Profile> users = new LinkedList<>();
        users.add(new Profile("Stefan"));
        users.add(new Profile("Andy"));
        users.add(new Profile("CJ"));
        chat_rooms.add(new ChatRoom("The Hack Team", "First Chat Room", "AA23", users));

        users.clear();
        users.add(new Profile("CJ's Mom"));
        users.add(new Profile("Stefan's Mom"));
        users.add(new Profile("Andy's Mom"));
        chat_rooms.add(new ChatRoom("Moms of the Hack Team", "A chat room for the moms", "1218", users));

        return chat_rooms;
    }
}
