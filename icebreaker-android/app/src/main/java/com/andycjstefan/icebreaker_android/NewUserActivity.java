package com.andycjstefan.icebreaker_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NewUserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_layout);
    }

    // called when user clicks button to submit Name
    public void onNameSubmitted(View view) {
        // TODO: note: currently serving to start ChatActivity
        startActivity(new Intent(this, ChatActivity.class));
    }
}
