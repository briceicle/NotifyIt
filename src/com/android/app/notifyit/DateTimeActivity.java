package com.android.app.notifyit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.android.app.notifyit.R;

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
	private Calendar cal;
	private SimpleDateFormat dateFormat;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_time_picker_layout);
        setTitle(R.string.date_activity_name);
        cal = Calendar.getInstance();
        dateFormat = new SimpleDateFormat();
        Intent intent = getIntent();
        entity = intent.getParcelableExtra("NotificationEntity");
        initView();
    }
	
	private void initView() {
		try {
			Date date = dateFormat.parse(entity.getDate());
			cal.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
		datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), this);
		
		TimePicker timePicker = (TimePicker) findViewById(R.id.time_picker);
		timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(cal.get(Calendar.MINUTE));
		timePicker.setOnTimeChangedListener(this);
		
		TextView label1 = (TextView) findViewById(R.id.date_label);
		label1.setText("Date: " + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1)  + "-" + cal.get(Calendar.DAY_OF_MONTH));
		
		TextView label2 = (TextView) findViewById(R.id.time_label);
		label2.setText("Time: " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		TextView label = (TextView) findViewById(R.id.date_label);
		label.setText("Date: " + year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
		cal.set(year, monthOfYear, dayOfMonth);
	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		TextView label = (TextView) findViewById(R.id.time_label);
		label.setText("Time: " + hourOfDay + ":" + minute);
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
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
		    	entity.setDate(dateFormat.format(cal.getTime()));
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
