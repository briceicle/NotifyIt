package com.example.myfirstapp;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class DateTimeActivity extends Activity implements OnDateChangedListener, OnTimeChangedListener{
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_time_picker_layout);
        initView();
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
	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		TextView label = (TextView) findViewById(R.id.time_label);
		label.setText("Time: " + hourOfDay + ":" + minute);
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
		    case R.id.save_item:;
		    	break;
		    default:
		    	break;
	    }	
	    return true;
	}
}
