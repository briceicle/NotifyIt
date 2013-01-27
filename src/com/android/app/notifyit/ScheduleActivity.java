package com.android.app.notifyit;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.app.notifyit.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * This class schedules a notification
 * 
 * @author bnkengsa
 *
 */
public class ScheduleActivity extends Activity {
	
	protected NotificationOpenHelper dbHelper;
	protected  NotificationEntity entity;
	public final int PICK_CONTACT = 0;
	
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);
        setTitle(R.string.add_notification_activity_name);
        initView();
        
        entity = new NotificationEntity();
        dbHelper = new NotificationOpenHelper(this);
    }
	
	private void initView() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		Button selectContactButton = (Button) findViewById(R.id.select_contact_button);
		selectContactButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				handleSelectContactButtonClick();	
			}
		});
		
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
	
	private void handleSelectContactButtonClick() {
		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 0);
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
		    	showPromptDialog();
		    	break;
		    case R.id.save_item:
		    	TextView name = (TextView) findViewById(R.id.firstname_textbox);
		    	TextView phoneNo = (TextView) findViewById(R.id.phoneno_textbox);
		    	entity.setName(name.getText().toString());
		    	entity.setPhoneno(phoneNo.getText().toString());
		    	
		    	if (validateInputForm()) {
			    	dbHelper.saveEntity(entity);
			    	
			    	//schedule a notify activity
			    	setNotificationAlert();
			    	
			    	showSuccessDialog();
			    	
		    	}
		    	break;
		    default:
		    	break;
	    }	
	    return true;
	}
	
	protected void clearInputFormAndResetEntity() {
		TextView name = (TextView) findViewById(R.id.firstname_textbox);
    	TextView phoneNo = (TextView) findViewById(R.id.phoneno_textbox);
    	name.setText("");
    	phoneNo.setText("");
    	entity = new NotificationEntity();
	}
	
	protected boolean validateInputForm() {
		boolean valid = true;
		if (entity.getName() == null || entity.getName().length() == 0 || 
			entity.getPhoneno() == null || entity.getPhoneno().length() == 0 ||
			entity.getMessage() == null || entity.getMessage().length() == 0 ||
			entity.getDate() == null || entity.getDate().length() == 0) {
			showErrorDialog();
			valid = false;
		}
		return valid;
	}
	
	private void showErrorDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Notification Details Required");
        builder.setMessage(R.string.notification_details_error_message);
        builder.setCancelable(true);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
	}
	
	private void showPromptDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Clear Notification Details");
        builder.setMessage(R.string.notification_details_clear_message);
        builder.setCancelable(true);
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	clearInputFormAndResetEntity();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
	}
	
	protected void showSuccessDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Notification Saved Successfully.");
        builder.setMessage(R.string.notification_save_message);
        builder.setCancelable(true);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	dialog.cancel();
		    	clearInputFormAndResetEntity();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && requestCode == 1){
			entity = data.getParcelableExtra("NotificationEntityResult");
		 } else if (resultCode == RESULT_OK && requestCode == PICK_CONTACT) {
			Uri contactData = data.getData();
			String name = null;
			String phoneNumber = null;
	        Cursor c =  managedQuery(contactData, null, null, null, null);
	        if (c.moveToFirst()) {
	          String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
	          name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	          phoneNumber = null;
	          
	          String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

	          if (hasPhone.equalsIgnoreCase("1")) 
	          {
		           Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,null, null);
		           if (phones.moveToFirst()) {
		             phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		           }
		           phones.close();
	          }
	        }
	        
	        if (name != null) {
	        	TextView nameTextBox = (TextView) findViewById(R.id.firstname_textbox);
	        	nameTextBox.setText(name);
	        }
	        if (phoneNumber != null) {
		    	TextView phoneNoTextBox = (TextView) findViewById(R.id.phoneno_textbox);
		    	phoneNoTextBox.setText(phoneNumber);
	        }
		 }
	}
	
	protected void setNotificationAlert() {
		AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(this, NotifyActivity.class);
		intent.putExtra("NotificationEntity", entity);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		try {
			Date date = new SimpleDateFormat().parse(entity.getDate());
			if (entity.getRepeat() == 0) {
				alarm.set(AlarmManager.RTC_WAKEUP, date.getTime(), pi);
			} else if (entity.getRepeat() == 1) {
				alarm.setRepeating(AlarmManager.RTC_WAKEUP, date.getTime(), 1000 * 60 * 60 * 24, pi);
			} else if (entity.getRepeat() == 2) {
				alarm.setRepeating(AlarmManager.RTC_WAKEUP, date.getTime(), 1000 * 60 * 60 * 24 * 7, pi);
			} else if (entity.getRepeat() == 3) {
				alarm.setRepeating(AlarmManager.RTC_WAKEUP, date.getTime(), 1000 * 60 * 60 * 24 * 7 * 2, pi);
			} else if (entity.getRepeat() == 4) {
				alarm.setRepeating(AlarmManager.RTC_WAKEUP, date.getTime(), 1000 * 60 * 60 * 24 * 30, pi);
			} else if (entity.getRepeat() == 5) {
				alarm.setRepeating(AlarmManager.RTC_WAKEUP, date.getTime(), 1000 * 60 * 60 * 24 * 365, pi);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
