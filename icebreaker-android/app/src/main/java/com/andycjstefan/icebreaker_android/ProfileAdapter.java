package com.andycjstefan.icebreaker_android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter for RecyclerView showing user profiles logged in to the chat room.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {

    // profiles logged in to the chat room
    private List<Profile> profiles;
    private Context context;
    private int chatRoomId;

    public ProfileAdapter(Context context, int chatRoomId) { // todo: load profiles given chatroom id
        this.context = context;
        this.chatRoomId = chatRoomId;
    }

    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_layout, null);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {
        Profile p = profiles.get(position);

        // bind data from the specified message to the holder layout
        holder.setUserName(p.getFirstName());
        holder.setUserId(p.getUserId());
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    // adds user profile to the chat room
    public void addMessage(Profile newProfile) {
        profiles.add(newProfile);
        // update
        notifyItemInserted(profiles.size() - 1);
    }

    class ProfileViewHolder extends RecyclerView.ViewHolder {
        TextView profileNameText;
        private int userId;

        ProfileViewHolder(View view) {
            super(view);
            profileNameText = (TextView) view.findViewById(R.id.profile_name_text);
        }

        void setUserName(String name) {
            profileNameText.setText(name);
        }

        void setUserId(int id) {
            userId = id;
        }
    }
}