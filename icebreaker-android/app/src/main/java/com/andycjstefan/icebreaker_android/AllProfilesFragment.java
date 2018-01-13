package com.andycjstefan.icebreaker_android;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays all Profiles user is chatting with
 */

public class AllProfilesFragment extends Fragment implements ProfileAdapter.OnProfileSelectedListener {

    // user id
    private int userId = 1;
    // id/password of the chat room
    private String roomPassword;
    // displays scrolling pane of user profiles
    private RecyclerView profilesView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_profiles, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();

//        roomPassword = getIntent().getStringExtra(EXTRA_ROOM_PASSWORD);

//        if (roomPassword == null || roomPassword.isEmpty()) {
//            throw new IllegalArgumentException("ChatRoomActivity requires EXTRA_ROOM_PASSWORD parameter");
//        }

        profilesView = (RecyclerView) view.findViewById(R.id.all_profiles_view);
        profilesView.setLayoutManager(new LinearLayoutManager(getContext()));

        ProfileAdapter profileAdapter = new ProfileAdapter(getContext(), loadProfilesForRoom(roomPassword), roomPassword);
        profileAdapter.setmListener(this);
        profilesView.setAdapter(profileAdapter);
    }


    @Override // callback from ProfileAdapter notifying that a Profile has been selected.
    // Launch new ChatActivity
    public void onProfileSelected(Profile profile) {
        Intent chat_intent = new Intent(getContext(), ChatActivity.class);
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
