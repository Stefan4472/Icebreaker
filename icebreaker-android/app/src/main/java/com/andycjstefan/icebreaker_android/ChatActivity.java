package com.andycjstefan.icebreaker_android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

public class ChatActivity extends Activity {

    private static String[] messageObjects = {
            "1, 2, Hey how are you?, 1515814620397",
            "2, 1, Fine how are you?, 1515814648408",
            "1, 2, Doing well thanks. Wanna meet up? I'm in the back, 1515814748408",
            "2, 1, Sure! Meet you there... ;), 1515814848408"
    };

    // displays scrolling list of chat messages
    private RecyclerView chatRecyclerView;

    // list of message objects in the chat
    private List<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);

        chatRecyclerView = (RecyclerView) findViewById(R.id.chat_recycler_view);

        // init messages from messageObjects
        for (String msg_str : messageObjects) {
            messages.add(new Message(msg_str));
            Log.d("Chat Activity", "Reading " + messages.get(messages.size() - 1));
        }

        ChatLogAdapter chat_log_adapter = new ChatLogAdapter(this, messages);
        chatRecyclerView.setAdapter(chat_log_adapter);
    }
}
