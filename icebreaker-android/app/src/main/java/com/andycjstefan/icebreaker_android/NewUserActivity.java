package com.andycjstefan.icebreaker_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewUserActivity extends Activity {

    // sharedpreferences key where user id is stored
    public static final String PREFERENCES_FILE_KEY = "andycjstefan.CONTEXT_FILE_KEY";
    public static final String USER_ID_KEY = "USER_ID_KEY";

    private Button nextButton;
    private String userNameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_layout);

        //Saves the username
        EditText userNameTextField = (EditText)findViewById(R.id.user_name);
        userNameString = userNameTextField.getText().toString();
        //next button(goes to picture upload/taking)
        nextButton = (Button) findViewById(R.id.next_button);

        // read SharedPreferences to see if a user id has been saved
        SharedPreferences data = getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
        if (data.contains(USER_ID_KEY)) {
            onUserIdValidated(data.getInt(USER_ID_KEY, -1));
        }
    }

    // called when user clicks button to submit Name
    public void onNameSubmitted(View view) {
        // send user to dashboard
        startActivity(new Intent(this, PictureChoiceActivity.class));
    }

    // user id validated without setting up new profile: launch dashboard immediately
    public void onUserIdValidated(int userId) {
        startActivity(new Intent(this, DashboardActivity.class));
    }

}
