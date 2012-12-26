package com.example.myfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotificationOpenHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "NotifyIt";
	private static final String TABLE_NAME = "Notification";
	private static final String ID_FIELD = "id";
	private static final String RECEIVER_NAME_FIELD = "name";
	private static final String RECEIVER_PHONE_FIELD = "phoneno";
	private static final String MESSAGE_FIELD = "message";
	private static final String DATE_FIELD = "date";
	private static final String VIA_FIELD = "sendvia";
	private static final String REPEAT_FIELD = "repeat";
	private static final String NOTIFICATION_TABLE_CREATE =
        "CREATE TABLE " + TABLE_NAME + " (" +
        ID_FIELD + " INTEGER, " +
        RECEIVER_NAME_FIELD + " TEXT, " +
        RECEIVER_PHONE_FIELD + " TEXT, " +
        MESSAGE_FIELD + " TEXT, " +
        DATE_FIELD + " TEXT, " +
        VIA_FIELD + " INTEGER, " +
        REPEAT_FIELD + " INTEGER);";

	public NotificationOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(NOTIFICATION_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
	
	public int count() {
		Cursor cursor = getReadableDatabase().rawQuery("select count(*) from "+ TABLE_NAME, null);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}
	
	public void saveEntity(NotificationEntity entity) {
		ContentValues values = new ContentValues();
		values.put(ID_FIELD, entity.getId());
		values.put(RECEIVER_NAME_FIELD, entity.getName());
		values.put(RECEIVER_PHONE_FIELD, entity.getPhoneno());
		values.put(MESSAGE_FIELD, entity.getMessage());
		values.put(DATE_FIELD, entity.getDate());
		values.put(VIA_FIELD, entity.getVia());
		values.put(REPEAT_FIELD, entity.getRepeat());
		getWritableDatabase().insert(TABLE_NAME, null, values);
	}

}