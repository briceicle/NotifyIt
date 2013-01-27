package com.android.app.notifyit;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

/**
 * This class sends the SMS notification
 * at the scheduled date
 * 
 * @author bnkengsa
 *
 */
public class NotifyActivity extends BroadcastReceiver {
	
	private NotificationEntity entity;
	private Context context;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		this.entity = intent.getParcelableExtra("NotificationEntity");
		this.context = context;
		sendNotification();
	}
	
	private void sendNotification() {
		if (entity.getVia() == 0) {
			SmsManager smsManager = SmsManager.getDefault();
			PendingIntent sentPI = PendingIntent.getActivity(context, 0, new Intent("SMS_SENT"), 0);
			PendingIntent deliveredPI = PendingIntent.getActivity(context, 0, new Intent("SMS_DELIVERED"), 0);
			smsManager.sendTextMessage(entity.getPhoneno(), null, entity.getMessage(), sentPI, deliveredPI);
		}
	}
}
