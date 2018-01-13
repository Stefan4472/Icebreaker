package com.andycjstefan.icebreaker_android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Stefan on 1/12/2018.
 */

public class ChatLogAdapter extends RecyclerView.Adapter<ChatLogAdapter.ChatMessageViewHolder> {

    // messages in the chat
    private List<Message> messages;
    private Context context;

    public ChatLogAdapter(Context context, List<Message> messages) { // todo: give the JSON objects?
        this.context = context;
        this.messages = messages;
    }

    @Override
    public ChatMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_layout, null);
        return new ChatMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatMessageViewHolder holder, int position) {
        Message m = messages.get(position);
        // bind data from the specified message to the holder layout
        holder.textView.setText(m.getText());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ChatMessageViewHolder extends RecyclerView.ViewHolder {
        protected TextView textView;

        public ChatMessageViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.message_textview);
        }
    }
}
