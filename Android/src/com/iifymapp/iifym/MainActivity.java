package com.iifymapp.iifym;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	// this setting comes from a tutorial.
	public final static String EXTRA_MESSAGE = "com.iifymapp.iifym.MESSAGE";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hopefully prevents a crash on first bood load.
        DatabaseHelper x = new DatabaseHelper(this);
        try {
        	x.createDataBase();
        } catch (IOException ioe) {
        	throw new Error("Unable to create database");
        }
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void lookupFoodInfo(View view){
    	//android.util.Log.i("FOO", view.getTag().toString());
    	Intent intent = new Intent(this, FoodSearchActivity.class);
    	EditText editText = (EditText) findViewById(R.id.lookupFoodInfo);
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    }
    
    public void setFoodAmount(String foodID){
    	Intent intent = new Intent(this, FoodAmountActivity.class);
    	intent.putExtra(EXTRA_MESSAGE, foodID);
    	startActivity(intent);
    }
    
    
    
    @Override
    public boolean onSearchRequested() {

        // your logic here
    	this.lookupFoodInfo(null);
    	android.util.Log.i("INFO", "Search Requested");
        return false;  // don't go ahead and show the search box
    }
    
}
