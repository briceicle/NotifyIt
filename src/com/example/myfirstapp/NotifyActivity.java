package com.example.myfirstapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;

public class NotifyActivity extends Activity {
	
	private NotificationEntity entity;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        entity = intent.getParcelableExtra("NotificationEntity");
        
        sendNotification();
    }
	
	private void sendNotification() {
		if (entity.getVia() == 0) {
			SmsManager smsManager = SmsManager.getDefault();
			PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, NotifyActivity.class), 0);
			smsManager.sendTextMessage(entity.getPhoneno(), null, entity.getMessage(), pi, null); 
		}
	}
}
