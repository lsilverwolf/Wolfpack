package com.mobpro.wolfpack;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Build;
import android.widget.TextView;

import com.facebook.*;
import com.facebook.model.*;

import java.util.ArrayList;

public class MainActivity extends Activity {
    MyService service;
    Session session;
    GraphUser fbUser;
    User user;
    Pack pack;
    ArrayList<Pack> packs = new ArrayList<Pack>();
    android.app.ActionBar.Tab mainTab;
    android.app.ActionBar.Tab packTab;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(getApplicationContext(), MyService.class);
        startService(i);
        ServiceConnection conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                service = ((MyService.MyServiceBinder) iBinder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {}
        };

        bindService(new Intent(this, MyService.class), conn, BIND_AUTO_CREATE);

        // Define view fragments
        MainFragment mainFragment = new MainFragment();
        PackFragment packFragment = new PackFragment();
        QuestFragment questFragment = new QuestFragment();

        /*
         *  The following code is used to set up the tabs used for navigation.
         *  You shouldn't need to touch the following code.
         */
        final android.app.ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);


        mainTab = actionBar.newTab().setText(R.string.tab1);
        mainTab.setTabListener(new NavTabListener(mainFragment));

        packTab = actionBar.newTab().setText(R.string.tab2);
        packTab.setTabListener(new NavTabListener(packFragment));

        android.app.ActionBar.Tab questTab = actionBar.newTab().setText(R.string.tab3);
        questTab.setTabListener(new NavTabListener(questFragment));

        actionBar.addTab(mainTab);
        actionBar.addTab(packTab);
        actionBar.addTab(questTab);

        actionBar.setStackedBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.android_dark_blue)));

        Session.openActiveSession(this, true, new Session.StatusCallback() {

            // callback when session changes state
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                if (session.isOpened()) {

                    // make request to the /me API
                    Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

                        // callback after Graph API response with user object
                        @Override
                        public void onCompleted(GraphUser fbUser, Response response) {
                            if (fbUser != null) {
                                MainActivity.this.fbUser = fbUser;
                                TextView welcome = (TextView) findViewById(R.id.welcome);
                                welcome.setText("Hello " + fbUser.getName() + "!");
                                service.getUser(fbUser.getUsername(), MainActivity.this);
                            }
                        }
                    });
                }
            }
        });



    }

    public void updateDisplayForUser() {
        if (user == null){
            getActionBar().selectTab(packTab);
        } else {
            getActionBar().selectTab(mainTab);
        }
    }
}