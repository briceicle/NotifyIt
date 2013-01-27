package com.android.app.notifyit;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * This activity retrieves all notification from the DB
 * and display them in a list view
 * 
 * @author bnkengsa
 *
 */
public class NotificationsActivity extends ListActivity implements OnClickListener {
	
	private List<String> list;
	private List<NotificationEntity> entityList;
	private NotificationOpenHelper dbHelper;
	
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_layout);
        setTitle("Notifications");
        dbHelper = new NotificationOpenHelper(this);
        
        initWidget();
    }
	
	private void initWidget() {
		list = createListData();
		setListAdapter(new MyAdapter(this, R.layout.row, list));
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		initWidget();
	}
	
	private class MyAdapter extends ArrayAdapter<String> {

		private List<String> items;
		private String COLOR = "#348283";
		
		public MyAdapter(Context context, int textViewResourceId, List<String> items) {
		    super(context, textViewResourceId, items);
		    this.items = items;
		}
		
		public View getView(final int position, View view, ViewGroup parent) {
		    LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    view = vi.inflate(R.layout.notifications_row, null);
		
		    view.setBackgroundColor(Color.parseColor(COLOR));
		
		    TextView textView = (TextView) view.findViewById(R.id.textView);
		    textView.setText(items.get(position));
		    
		    textView.setTag(position);
		    textView.setOnClickListener(NotificationsActivity.this);
		
		    return view;
		}
    }

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(NotificationsActivity.this, EditNotificationActivity.class);
		intent.putExtra("NotificationEntity", entityList.get((Integer) view.getTag()));
		startActivityForResult(intent, 1);
	}
	
	private List<String> createListData() {
		List<String> list = new ArrayList<String>();
		entityList = dbHelper.getEntities();
		for (int i = 0; i < entityList.size(); i++) {
			NotificationEntity entity = entityList.get(i);
			list.add(entity.getName());
		}
		return list;
	}

}
