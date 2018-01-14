package com.andycjstefan.icebreaker_android;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.LinkedList;
import java.util.List;

// Shows all Chatrooms the user has access to. Selecting one will start a new ChatRoomActivity
// showing the profiles logged in to the room.

public class AllChatRoomsFragment extends Fragment implements ChatRoomAdapter.OnChatRoomClickListener {

    // displays scrolling list of chat rooms
    private RecyclerView roomRecyclerView;
    // user id
    private int userId = 1;
    private RequestQueue queue;
    private ChatRoomAdapter chatRoomAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int userId = 1;
        queue = Volley.newRequestQueue(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_chatrooms, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();

        roomRecyclerView = view.findViewById(R.id.rooms_recyclerview);
        roomRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        chatRoomAdapter = new ChatRoomAdapter(getContext(), loadChatRoomsForUser(userId), userId);
        // set this class to receive events
        chatRoomAdapter.setListener(this);
        roomRecyclerView.setAdapter(chatRoomAdapter);
    }

    @Override
    public void onChatRoomClicked(ChatRoom selectedChatRoom) {
        Intent room_intent = new Intent(getContext(), ChatRoomActivity.class);
        room_intent.putExtra(ChatRoomActivity.EXTRA_ROOM_PASSWORD, selectedChatRoom.getPassword());
        startActivity(room_intent);
    }

    private List<ChatRoom> loadChatRoomsForUser(int userId) {
        String url = NetworkUtil.getRoomsByUser(userId);

        Log.d("ChatRooms", "Creating Request " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("AllChatrooms", "received response " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AllChatrooms", "received bad response " + error.getMessage());
            }
        });
        Log.d("ChatRooms", "Finished request");
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
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
