package com.andycjstefan.icebreaker_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class AddChatsActivity extends Activity {

    private Button addChatButton, addGroupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_choice_activity);

        addChatButton = (Button) findViewById(R.id.join_room_button);
        addGroupButton = (Button) findViewById(R.id.make_room_button);
    }


    public void onJoinRoom(View view) {
        startActivity(new Intent(this, DashboardActivity.class));
    }

    public void onMakeRoom(View view) {
        startActivity(new Intent(this, DashboardActivity.class));
    }

}
