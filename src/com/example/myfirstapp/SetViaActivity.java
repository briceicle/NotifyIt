package com.example.myfirstapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

public class SetViaActivity extends ListActivity implements OnClickListener {
	
	// store CheckTextView's
    private HashMap<Integer, CheckedTextView> mCheckedList = new HashMap<Integer, CheckedTextView>();
    // store state
    private HashMap<Integer, Boolean> mIsChecked =  new HashMap<Integer, Boolean>();
    // list of Strings
    private ArrayList<String> mList;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_layout);

        mList = getListData();

        setListAdapter(new MyAdapter(this, R.layout.row, mList));
    }
    
    private class MyAdapter extends ArrayAdapter<String> {

		private ArrayList<String> items;
		private String[] colors = { "#348283", "#853832" };
		
		public MyAdapter(Context context, int textViewResourceId, ArrayList<String> items) {
		    super(context, textViewResourceId, items);
		    this.items = items;
		}
		
		public View getView(final int position, View view, ViewGroup parent) {
		    // we are not reusing anything today
		    LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    view = vi.inflate(R.layout.row, null);
		
		    int colorPos = position % colors.length;
		    view.setBackgroundColor(Color.parseColor(colors[colorPos]));
		
		    CheckedTextView ct = (CheckedTextView) view.findViewById(R.id.checkedTextView);
		    ct.setText(items.get(position));
		    // set current state and color
		    if (mIsChecked.get(position) != null) {
		        if (mIsChecked.get(position)) {
		            ct.setChecked(true);
		            ct.setTextColor(Color.WHITE);
		        } else {
		            ct.setTextColor(Color.parseColor("#c0c0c0"));
		        }
		    }
		    // tag it - used when clicked upon to change state
		    ct.setTag(position);
		    mCheckedList.put(position, ct);
		    ct.setOnClickListener(SetViaActivity.this);
		
		    return view;
		}
    }
    
    protected ArrayList<String> getListData() {
    	ArrayList<String> list = new ArrayList<String>();
    	list.add("SMS\\Text Message");
    	list.add("Email");
    	
    	return list;
    }

	@Override
	public void onClick(View v) {
		// get the CheckedTextView
        CheckedTextView ct = mCheckedList.get(v.getTag());
        if (ct != null) {
            // change the state and colors
            ct.toggle();
            if (ct.isChecked()) {
                ct.setTextColor(Color.WHITE);
            } else {
                ct.setTextColor(Color.parseColor("#c0c0c0"));
            }
            // add current state to map
            mIsChecked.put((Integer) v.getTag(), ct.isChecked());
        }
        // uncheck all other views
        Set<Integer> keyset = mCheckedList.keySet();
        for(Integer key: keyset) {
        	if (key != v.getTag()) {
        		CheckedTextView val = mCheckedList.get(key);
        		val.setChecked(false);
        		mIsChecked.remove(key);
        	}
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
		    case R.id.save_item:;
		    	break;
		    default:
		    	break;
	    }
	
	    return true;
	}
}
