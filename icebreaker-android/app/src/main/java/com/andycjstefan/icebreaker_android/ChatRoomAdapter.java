package com.andycjstefan.icebreaker_android;

/**
 * Created by Stefan on 1/13/2018.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter for RecyclerView showing chat rooms a user belongs to.
 */

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ChatRoomViewHolder> {

    // listener interface for receiving selections
    public interface OnChatRoomClickListener {
        void onChatRoomClicked(ChatRoom selectedChatRoom);
    }

    // chat rooms in the display
    private List<ChatRoom> rooms;
    private Context context;
    private int userId;
    // listener registered with this instance
    private OnChatRoomClickListener mListener;

    public ChatRoomAdapter(Context context, List<ChatRoom> rooms, int userId) { // todo: give the JSON objects?
        this.context = context;
        this.rooms = rooms;
        this.userId = userId;
    }

    public void setListener(OnChatRoomClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public ChatRoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatroom_adapter, null);
        return new ChatRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatRoomViewHolder holder, final int position) {
        ChatRoom room = rooms.get(position);

        // bind data from the specified message to the holder layout
        holder.setName(room.getName());
        holder.setDescription(room.getDescription());
        holder.setPassword(room.getPassword());
        holder.setNumUsers(room.getUsers().size());

        // create new onClickListener to send events to the registered OnChatRoomClickListener
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onChatRoomClicked(rooms.get(position));
                }
            }
        };
        // TODO: ATTACH TO WHOLE VIEW ELEMENT
        holder.nameText.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    // adds room to the list
    public void addChatRoom(ChatRoom newRoom) { // TODO: FIND POSITION USING TIMESTAMP
        rooms.add(newRoom);
        // update
        notifyItemInserted(rooms.size() - 1);
    }

    class ChatRoomViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, descriptionText, passwordText, numUsersText;

        ChatRoomViewHolder(View view) {
            super(view);
            nameText = (TextView) view.findViewById(R.id.room_name_text);
            descriptionText = (TextView) view.findViewById(R.id.room_description_text);
            passwordText = (TextView) view.findViewById(R.id.room_password_text);
            numUsersText = (TextView) view.findViewById(R.id.room_num_users_text);
        }

        public void setName(String roomName) {
            nameText.setText(roomName);
        }

        void setDescription(String description) {
            descriptionText.setText(description);
        }

        void setPassword(String password) {
            passwordText.setText(password);
        }

        void setNumUsers(int numUsers) {
            numUsersText.setText(numUsers + " Users");
        }
    }
}