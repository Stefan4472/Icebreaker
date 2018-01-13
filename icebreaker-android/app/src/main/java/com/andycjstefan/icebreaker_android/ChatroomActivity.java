package com.andycjstefan.icebreaker_android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class ChatRoomActivity extends Activity {

    // displays scrolling list of chat rooms
    private RecyclerView roomRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom_layout);

        roomRecyclerView = findViewById(R.id.rooms_recyclerview);
        roomRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        int user_id = 1;

        List<ChatRoom> chat_rooms = new LinkedList<>();
        List<Profile> users = new LinkedList<>();
        users.add(new Profile("Stefan"));
        users.add(new Profile("Andy"));
        users.add(new Profile("CJ"));
        chat_rooms.add(new ChatRoom("Chat Room", "First Chat Room", "AA23", users));
        ChatRoomAdapter chatRoomAdapter = new ChatRoomAdapter(this, chat_rooms, user_id);
        roomRecyclerView.setAdapter(chatRoomAdapter);
    }
}
