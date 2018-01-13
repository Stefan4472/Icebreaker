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

    // sharedpreferences key where user id is stored
    public static final String PREFERENCES_FILE_KEY = "andycjstefan.CONTEXT_FILE_KEY";
    public static final String USER_ID_KEY = "USER_ID_KEY";

    private Button nextButton;
    private EditText userNameEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_layout);

        userNameEntry = findViewById(R.id.user_name_entry);

        //next button(goes to picture upload/taking)
        nextButton = (Button) findViewById(R.id.next_button);

        // read SharedPreferences to see if a user id has been saved
        SharedPreferences data = getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
        if (data.contains(USER_ID_KEY)) {
            onUserIdValidated(data.getInt(USER_ID_KEY, -1));
        }

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://partrico.pythonanywhere.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("NewUser", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("NewUser", "couldn't get request from server");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    // called when user clicks button to submit Name
    public void onNameSubmitted(View view) {
        // no name entered: show error message
        if (userNameEntry.getText().toString().isEmpty()) {
            findViewById(R.id.error_message_text).setVisibility(View.VISIBLE);
        } else {
            // send user to dashboard
            startActivity(new Intent(this, PictureChoiceActivity.class));
        }
    }

    // user id validated without setting up new profile: launch dashboard immediately
    public void onUserIdValidated(int userId) {
        startActivity(new Intent(this, DashboardActivity.class));
    }

}
