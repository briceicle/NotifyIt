package com.example.myfirstapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScheduleActivity extends Activity {
	
	private NotificationOpenHelper dbHelper;
	public  NotificationEntity entity;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);
        setTitle(R.string.schedule_activity_name);
        initView();
        
        dbHelper = new NotificationOpenHelper(getApplicationContext());
        entity = new NotificationEntity();
    }
	
	private void initView() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		Button messageButton = (Button) findViewById(R.id.set_message_button);		
		messageButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View paramView) {
				handleMessageButtonClick();
			}
		});
		
		Button dateButton = (Button) findViewById(R.id.set_date_button);		
		dateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				handleDateButtonClick();
			}
		});
		
		Button viaButton = (Button) findViewById(R.id.set_via_button);		
		viaButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View paramView) {
				handleViaButtonClick();
			}
		});
		
		Button repeatButton = (Button) findViewById(R.id.set_repeat_button);		
		repeatButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View paramView) {
				handleRepeatButtonClick();	
			}
		});
	}
	
	private void handleMessageButtonClick() {
		Intent intent = new Intent(this.getApplicationContext(), MessageActivity.class);
		intent.putExtra("NotificationEntity", entity);
		startActivityForResult(intent, 1);
	}
	
	private void handleDateButtonClick() {
		Intent intent = new Intent(this.getApplicationContext(), DateTimeActivity.class);
		intent.putExtra("NotificationEntity", entity);
		startActivityForResult(intent, 1);
	}
	
	private void handleViaButtonClick() {
		Intent intent = new Intent(this.getApplicationContext(), SetViaActivity.class);
		intent.putExtra("NotificationEntity", entity);
		startActivityForResult(intent, 1);
	}
	
	private void handleRepeatButtonClick() {
		Intent intent = new Intent(this.getApplicationContext(), SetRepeatActivity.class);
		intent.putExtra("NotificationEntity", entity);
		startActivityForResult(intent, 1);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	   MenuInflater inflater = getMenuInflater();
	   inflater.inflate(R.menu.schedule_menu, menu);
	   return true;
	 }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		    case R.id.clear_item:
		    	break;
		    case R.id.save_item:
		    	TextView name = (TextView) findViewById(R.id.firstname_textbox);
		    	TextView phoneNo = (TextView) findViewById(R.id.phoneno_textbox);
		    	entity.setName(name.getText().toString());
		    	entity.setPhoneno(phoneNo.getText().toString());
		    	entity.setId(dbHelper.count() + 1);
		    	dbHelper.saveEntity(entity);
		    	//TODO clear the form, reset entity, and schedule a notify activity
		    	setNotificationAlert();
		    	break;
		    default:
		    	break;
	    }	
	    return true;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && requestCode == 1){
			entity = data.getParcelableExtra("NotificationEntityResult");
		 }
	}
	
	private void setNotificationAlert() {
		AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(getApplicationContext(), NotifyActivity.class);
		intent.putExtra("NotificationEntity", entity);
		PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
		
		try {
			Date date = new SimpleDateFormat("MMMM d, yyyy hh:mm:ss", Locale.ENGLISH).parse(entity.getDate());
			alarm.setRepeating(AlarmManager.RTC_WAKEUP, date.getTime(), 1000 * 5 , pi);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
