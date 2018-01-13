package com.andycjstefan.icebreaker_android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.LinkedList;
import java.util.List;

// The ChatActivity displays and facilitates a private conversation between two given userIds--
// the user on this phone (specified by userId) and the user being messaged (partnerId). Those
// are used to determine the coloring and position of chat messages. TODO: NETWORK CALLS
public class ChatActivity extends Activity {

    // keys used to send "extras" to ChatActivity via the calling Inent
    public static final String EXTRA_USER_ID = "EXTRA_USER_ID";
    public static final String EXTRA_PARTNER_ID = "EXTRA_PARTNER_ID";

    private static String[] messageObjects = {
            "1,2,Hey how are you?,1515814620397",
            "2,1,Fine how are you?,1515814648408",
            "1,2,Doing well thanks. Wanna meet up?,1515814748408",
            "2,1,Sure! I'm at the bar,1515814848408"
    };

    // displays scrolling list of chat messages
    private RecyclerView chatRecyclerView;
    // adapter to chatRecyclerView
    private ChatLogAdapter chatLogAdapter;
    // box where user enters message
    private EditText messageBox;

    // list of message objects in the chat
    private List<Message> messages = new LinkedList<>();

    // id of this user, and the user chatting with TODO: SEND IN BUNDLE
    private int userId = -1, partnerId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);

        userId = getIntent().getIntExtra(EXTRA_USER_ID, -1);
        partnerId = getIntent().getIntExtra(EXTRA_PARTNER_ID, -1);

        if (userId == -1 || partnerId == -1) {
            throw new IllegalArgumentException("Missing UserId or PartnerId Extra! Undefined");
        }

        chatRecyclerView = (RecyclerView) findViewById(R.id.chat_recycler_view);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageBox = (EditText) findViewById(R.id.type_message_field);

        // init messages from messageObjects
        for (String msg_str : messageObjects) {
            messages.add(Message.parse(msg_str));
            Log.d("Chat Activity", "Reading " + messages.get(messages.size() - 1));
        }

        chatLogAdapter = new ChatLogAdapter(this, messages, userId, partnerId);
        chatRecyclerView.setAdapter(chatLogAdapter);

    }

    // called when user clicks button to send typed message
    public void onMessageSent(View view) {
        Log.d("ChatActivity", "Adding Message");
        // construct message object
        Message sent = new Message(userId, partnerId, messageBox.getText().toString(), System.currentTimeMillis());
        chatLogAdapter.addMessage(sent);
        // clear text box
        messageBox.setText("");
    }
}
