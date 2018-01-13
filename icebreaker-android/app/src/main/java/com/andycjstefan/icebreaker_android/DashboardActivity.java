package com.andycjstefan.icebreaker_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends Activity {

    private Button chatLogsButton;
    private Button chatRoomsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);


    }

    // responds to user clicking to see rooms they are in
    public void onViewRoomsClicked(View view) {
        startActivity(new Intent(this, ChatRoomActivity.class));
    }

    // responds to user clicking to see chats they are in
    public void onViewChatsClicked(View view) {
        Intent chat_intent = new Intent(this, ChatActivity.class);
        chat_intent.putExtra(ChatActivity.EXTRA_USER_ID, 1); // TODO: PUT CURRENT USER ID
        chat_intent.putExtra(ChatActivity.EXTRA_PARTNER_ID, 2); // TODO: PUT PARTNER ID
        startActivity(chat_intent);
    }
}
