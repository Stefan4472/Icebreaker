package com.andycjstefan.icebreaker_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class NewUserActivity extends Activity {

    private EditText userNameEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_layout);

        userNameEntry = findViewById(R.id.user_name_entry);
    }

    // called when user clicks button to submit Name
    public void onNameSubmitted(View view) {
        // no name entered: show error message
        if (userNameEntry.getText().toString().isEmpty()) {
            findViewById(R.id.error_message_text).setVisibility(View.VISIBLE);
        } else {
            // send user to PictureChoiceActivity with name as an intent extra
            Intent picture_intent = new Intent(this, PictureChoiceActivity.class);
            picture_intent.putExtra(PictureChoiceActivity.USER_NAME_EXTRA, userNameEntry.getText().toString());
            startActivity(picture_intent);
        }
    }
}
