package com.andycjstefan.icebreaker_android;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ViewSwitcher;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class DashboardActivity extends Activity {

    // sharedpreferences key where user id is stored
    public static final String PREFERENCES_FILE_KEY = "andycjstefan.CONTEXT_FILE_KEY";
    // key used to save user id in preferences
    public static final String USER_ID_KEY = "USER_ID_KEY";
    // key used to transfer user name when a new user is created
    public static final String USER_NAME_EXTRA = "USER_NAME_EXTRA";
    // key used to transfer user photo as a base64 String when a new user is created
    public static final String USER_PHOTO_EXTRA = "USER_PHOTO_EXTRA";

    private Button chatLogsButton;
    private Button chatRoomsButton;
    private ViewSwitcher viewSwitcher;
    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);

        // check if extras were passed with user id and photo--this will happen when a new user
        // signed in. Make call to create new user
        if (getIntent().hasExtra(USER_NAME_EXTRA) && getIntent().hasExtra(USER_PHOTO_EXTRA)) {
            createNewUser(getIntent().getStringExtra(USER_NAME_EXTRA), getIntent().getStringExtra(USER_PHOTO_EXTRA));
        }

        // read SharedPreferences to see if a user id has been saved
        SharedPreferences data = getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
        if (data.contains(USER_ID_KEY)) {
            userId = data.getInt(USER_ID_KEY, -1);
        } else { // send user to NewUserActivity and TODO: REMOVE THIS FROM ACTIVITY STACK
            startActivity(new Intent(this, NewUserActivity.class));
        }

        viewSwitcher = findViewById(R.id.dash_view_switcher);
        final ActionBar actionBar = getActionBar();

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                Log.d("Dashboard", tab.getPosition() + " selected");
                if (tab.getPosition() == 1) {
                    viewSwitcher.showPrevious();
                } else {
                    viewSwitcher.showNext();
                }
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        actionBar.addTab(actionBar.newTab().setText("Rooms").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Chats").setTabListener(tabListener));
    }

    // makes request to server to create new user. Saves resulting userId to sharedPreferences
    private void createNewUser(String name, String photoBase64) {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = NetworkUtil.getNewUserURL(name, photoBase64);
        Log.d("Dashboard", "URL is " + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Dashboard", "received response " + response);
                        // save id to preferences under USER_ID_KEY
                        SharedPreferences prefs = getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
                        prefs.edit().putInt(USER_ID_KEY, Integer.parseInt(response)).apply();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Dashboard", "received bad response " + error.getMessage());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    // responds to user clicking to see rooms they are in
    public void onViewRoomsClicked(View view) {
        startActivity(new Intent(this, AllChatRoomsFragment.class));
    }

    // responds to user clicking to see chats they are in
    public void onViewChatsClicked(View view) {
        Intent chat_intent = new Intent(this, ChatActivity.class);
        chat_intent.putExtra(ChatActivity.EXTRA_USER_ID, 1); // TODO: PUT CURRENT USER ID
        chat_intent.putExtra(ChatActivity.EXTRA_PARTNER_ID, 2); // TODO: PUT PARTNER ID
        startActivity(chat_intent);
    }

    public void onAddRooms(View view) {
        startActivity(new Intent(this,AddChatsActivity.class));
    }
}