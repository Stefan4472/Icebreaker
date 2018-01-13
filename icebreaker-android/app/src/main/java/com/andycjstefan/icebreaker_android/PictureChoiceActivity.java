package com.andycjstefan.icebreaker_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PictureChoiceActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_choice_activity);
    }

    // called when user clicks button to submit Name
    public void onDoneWithPicture(View view) {
        // send user to dashboard
        startActivity(new Intent(this, DashboardActivity.class));
    }
}
