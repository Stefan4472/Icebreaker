package com.andycjstefan.icebreaker_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewUserActivity extends Activity {

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
    }

    // called when user clicks button to submit Name
    public void onNameSubmitted(View view) {
        // send user to dashboard
        startActivity(new Intent(this, PictureChoiceActivity.class));
    }
}
