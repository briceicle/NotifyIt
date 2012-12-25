package com.example.myfirstapp;

import java.util.ArrayList;

import android.os.Bundle;

public class SetRepeatActivity extends SetViaActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
