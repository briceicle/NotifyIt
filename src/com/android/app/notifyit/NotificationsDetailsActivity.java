package com.android.app.notifyit;

import com.android.app.notifyit.R;

import android.app.Activity;
import android.os.Bundle;

public class NotificationsDetailsActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_layout);
        setTitle("Notifications");
    }
}
