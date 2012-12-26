package com.example.myfirstapp;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class SetRepeatActivity extends SetViaActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.repeat_activity_name);
    }
    
    @Override
    protected ArrayList<String> getListData() {
    	ArrayList<String> list = new ArrayList<String>();
    	list.add("Never");
    	list.add("Every Day");
    	list.add("Every Week");
    	list.add("Every 2 Weeks");
    	list.add("Every Month");
    	list.add("Every Year");
    	
    	return list;
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		    case R.id.cancel_item:
		    	finish();
		    	break;
		    case R.id.done_item:
		    	entity.setRepeat(pos);
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
