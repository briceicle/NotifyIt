package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class EditNotificationActivity extends ScheduleActivity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.edit_notification_activity_name);
        Intent intent = getIntent();
        entity = intent.getParcelableExtra("NotificationEntity");
        Toast.makeText(this, "Id="+entity.getId(), Toast.LENGTH_SHORT).show();
        
        initView();
    }
	
	private void initView() {
		TextView name = (TextView) findViewById(R.id.firstname_textbox);
    	TextView phoneNo = (TextView) findViewById(R.id.phoneno_textbox);
    	name.setText(entity.getName());
    	phoneNo.setText(entity.getPhoneno());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	   MenuInflater inflater = getMenuInflater();
	   inflater.inflate(R.menu.edit_notification_menu, menu);
	   return true;
	 }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		    case R.id.delete_item:
		    	super.clearInputFormAndResetEntity();
		    	dbHelper.deleteEntity(entity.getId()+"");
		    	setResult(RESULT_OK, null);
		    	finish();
		    	break;
		    case R.id.save_item:
		    	TextView name = (TextView) findViewById(R.id.firstname_textbox);
		    	TextView phoneNo = (TextView) findViewById(R.id.phoneno_textbox);
		    	entity.setName(name.getText().toString());
		    	entity.setPhoneno(phoneNo.getText().toString());
		    	if (validateInputForm()) {
			    	
			    	dbHelper.updateEntity(entity);
			    	
			    	//schedule a notify activity
			    	setNotificationAlert();
			    	
			    	showSuccessDialog();
			    	
			    	finish();
			    	
		    	}
		    	break;
		    default:
		    	break;
	    }	
	    return true;
	}
}
