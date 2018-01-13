package com.andycjstefan.icebreaker_android;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter for RecyclerView showing chat messages in the chat log.
 */

public class ChatLogAdapter extends RecyclerView.Adapter<ChatLogAdapter.ChatMessageViewHolder> {

    // messages in the chat
    private List<Message> messages;
    private Context context;
    private int userId, partnerId;

    public ChatLogAdapter(Context context, List<Message> messages, int userId, int partnerId) { // todo: give the JSON objects?
        this.context = context;
        this.messages = messages;
        this.userId = userId;
        this.partnerId = partnerId;
    }

    @Override
    public ChatMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_layout, null);
        return new ChatMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatMessageViewHolder holder, int position) {
        Message m = messages.get(position);

        // notify whether message in question was sent by this user
        holder.setSentByUser(m.getSenderId() == userId);

        // bind data from the specified message to the holder layout
        holder.textView.setText(m.getText());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    // adds message to the chat
    public void addMessage(Message newMsg) { // TODO: FIND POSITION USING TIMESTAMP
        messages.add(newMsg);
        // update
        notifyItemInserted(messages.size() - 1);
    }

    class ChatMessageViewHolder extends RecyclerView.ViewHolder {
        protected TextView textView;
        // whether message was sent by the user (false = received)
        protected boolean sentByUser;

        public ChatMessageViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.message_textview);
        }

        public void setSentByUser(boolean sentByUser) {
            this.sentByUser = sentByUser;
            if (sentByUser) { // set cyan background and right-justify TODO: LEFT/RIGHT JUSTIFY
                textView.setBackgroundColor(Color.CYAN);
                textView.setGravity(Gravity.RIGHT);
            } else { // set white background and left-justify
                textView.setBackgroundColor(Color.WHITE);
            }

        }
    }
}