package com.example.myfirstapp;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class DateTimeActivity extends Activity implements OnDateChangedListener, OnTimeChangedListener {
	
	private NotificationEntity entity;
	private Calendar date;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_time_picker_layout);
        setTitle(R.string.date_activity_name);
        initView();
        date = Calendar.getInstance();
        Intent intent = getIntent();
        entity = intent.getParcelableExtra("NotificationEntity");
    }
	
	private void initView() {
		final Calendar c = Calendar.getInstance();
		DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
		datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), this);
		
		TimePicker timePicker = (TimePicker) findViewById(R.id.time_picker);
		timePicker.setOnTimeChangedListener(this);
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		TextView label = (TextView) findViewById(R.id.date_label);
		label.setText("Date: " + year + "-" + monthOfYear + "-" + dayOfMonth);
		date.set(year, monthOfYear, dayOfMonth);
	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		TextView label = (TextView) findViewById(R.id.time_label);
		label.setText("Time: " + hourOfDay + ":" + minute);
		date.set(Calendar.HOUR_OF_DAY, hourOfDay);
		date.set(Calendar.MINUTE, minute);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	   MenuInflater inflater = getMenuInflater();
	   inflater.inflate(R.menu.main_menu, menu);
	   return true;
	 }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		    case R.id.cancel_item:
		    	finish();
		    	break;
		    case R.id.done_item:
		    	entity.setDate(date.getTime().toLocaleString());
		    	Intent intent = getIntent();
		    	intent.putExtra("NotificationEntityResult", entity);
		    	setResult(RESULT_OK, intent);
		    	finish();
		    	break;
		    default:
		    	break;
	    }	
	    return true;
	}
}
