package com.android.app.notifyit;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationEntity implements Parcelable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String phoneno;
	private String message;
	private String date;
	private int via;
	private int repeat;
	
	public NotificationEntity() {
	}
	
	public NotificationEntity(Parcel in) {
		id = in.readInt();
		name = in.readString();
		phoneno = in.readString();
		message = in.readString();
		date = in.readString();
		via = in.readInt();
		repeat = in.readInt();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void setVia(int via) {
		this.via = via;
	}

	public int getVia() {
		return via;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	public int getRepeat() {
		return repeat;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flag) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(phoneno);
		dest.writeString(message);
		dest.writeString(date);
		dest.writeInt(via);
		dest.writeInt(repeat);
	}
	
	public static final Parcelable.Creator<NotificationEntity> CREATOR = new Parcelable.Creator<NotificationEntity>() {
	    public NotificationEntity createFromParcel(Parcel in) {
	        return new NotificationEntity(in);
	    }

	    public NotificationEntity[] newArray(int size) {
	        return new NotificationEntity[size];
	    }
	};
}
