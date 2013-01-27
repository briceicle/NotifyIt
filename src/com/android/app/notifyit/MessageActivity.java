package com.android.app.notifyit;

import com.android.app.notifyit.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class MessageActivity extends Activity {
	
	private NotificationEntity entity;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout);
        setTitle(R.string.message_activity_name);
        Intent intent = getIntent();
        entity = intent.getParcelableExtra("NotificationEntity");
        initView();
    }
	
	private void initView() {
		EditText textbox = (EditText) findViewById(R.id.message_view_textbox);
		if (entity.getMessage() != null) {
			textbox.setText(entity.getMessage());
		}
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
		    	EditText textbox = (EditText) findViewById(R.id.message_view_textbox);
		    	entity.setMessage(textbox.getText().toString());
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
