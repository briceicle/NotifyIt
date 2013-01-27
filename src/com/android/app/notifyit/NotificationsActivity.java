package com.android.app.notifyit;

import java.util.List;

import com.android.app.notifyit.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This activity retrieves all notification from the DB
 * 
 * @author bnkengsa
 *
 */
public class NotificationsActivity extends Activity {
	
	private NotificationOpenHelper dbHelper;
	
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_layout);
        setTitle("Notifications");
        dbHelper = new NotificationOpenHelper(this);
        initWidget();
    }
	
	private void initWidget() {
		List<NotificationEntity> list = dbHelper.getEntities();
		LinearLayout layout = (LinearLayout) findViewById(R.id.notifications_layout);
		layout.removeAllViews();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				final NotificationEntity entity = list.get(i);
				TextView textView = new TextView(this);
				textView.setText(entity.getName());
				textView.setClickable(true);
				textView.setPadding(0, 0, 0, 10);
				textView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				textView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(NotificationsActivity.this, EditNotificationActivity.class);
						intent.putExtra("NotificationEntity", entity);
						startActivityForResult(intent, 1);
					}
				});
				layout.addView(textView);
			}
		} else {
			TextView textView = new TextView(this);
			textView.setText(R.string.no_notification_message);
			textView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			layout.addView(textView);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		initWidget();
	}

}
