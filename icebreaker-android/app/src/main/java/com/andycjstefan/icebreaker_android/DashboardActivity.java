package com.andycjstefan.icebreaker_android;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ViewSwitcher;

public class DashboardActivity extends Activity {

    private Button chatLogsButton;
    private Button chatRoomsButton;
    private ViewSwitcher viewSwitcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);

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

    // responds to user clicking to see rooms they are in
    public void onViewRoomsClicked(View view) {
        startActivity(new Intent(this, AllChatRoomsActivity.class));
    }

    // responds to user clicking to see chats they are in
    public void onViewChatsClicked(View view) {
        Intent chat_intent = new Intent(this, ChatActivity.class);
        chat_intent.putExtra(ChatActivity.EXTRA_USER_ID, 1); // TODO: PUT CURRENT USER ID
        chat_intent.putExtra(ChatActivity.EXTRA_PARTNER_ID, 2); // TODO: PUT PARTNER ID
        startActivity(chat_intent);
    }
}