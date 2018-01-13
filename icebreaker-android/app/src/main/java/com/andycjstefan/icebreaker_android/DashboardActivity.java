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
        startActivity(new Intent(this, ChatroomActivity.class));
    }

    // responds to user clicking to see chats they are in
    public void onViewChatsClicked(View view) {
        startActivity(new Intent(this, ChatActivity.class));
    }
}
