package com.example.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class ScheduleActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);
        
        initView();
    }
	
	private void initView() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		Button messageButton = (Button) findViewById(R.id.set_message_button);		
		messageButton.setWidth(metrics.widthPixels);
		messageButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View paramView) {
				handleMessageButtonClick();
			}
		});
		
		Button dateButton = (Button) findViewById(R.id.set_date_button);		
		dateButton.setWidth(metrics.widthPixels);
		dateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				handleDateButtonClick();
			}
		});
		
		Button viaButton = (Button) findViewById(R.id.set_via_button);		
		viaButton.setWidth(metrics.widthPixels);
		viaButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View paramView) {
				handleViaButtonClick();
			}
		});
		
		Button repeatButton = (Button) findViewById(R.id.set_repeat_button);		
		repeatButton.setWidth(metrics.widthPixels);
		repeatButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View paramView) {
				handleRepeatButtonClick();	
			}
		});
	}
	
	private void handleMessageButtonClick() {
		Intent intent = new Intent(this.getApplicationContext(), MessageActivity.class);
		startActivity(intent);
	}
	
	private void handleDateButtonClick() {
		Intent intent = new Intent(this.getApplicationContext(), DateTimeActivity.class);
		startActivity(intent);
	}
	
	private void handleViaButtonClick() {
		Intent intent = new Intent(this.getApplicationContext(), SetViaActivity.class);
		startActivity(intent);
	}
	
	private void handleRepeatButtonClick() {
		Intent intent = new Intent(this.getApplicationContext(), SetRepeatActivity.class);
		startActivity(intent);
	}

}
