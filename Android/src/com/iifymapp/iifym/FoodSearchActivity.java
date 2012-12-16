package com.iifymapp.iifym;

import java.io.IOException;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class FoodSearchActivity extends MainActivity {
	private SQLiteDatabase sqdb;	
	private SimpleCursorAdapter dataAdapter;
	
	@SuppressWarnings("deprecation")
	private void doSearch(){
		// Create and connect to database
        DatabaseHelper myDbHelper = new DatabaseHelper(this);
        try {        	 
        	myDbHelper.createDataBase();
        } catch (IOException ioe) {
        	throw new Error("Unable to create database"); 
        }
 
        try {
        	myDbHelper.openDataBase(); 
        }catch(SQLException sqle){
        	throw sqle; 
        }
        sqdb = myDbHelper.getReadableDatabase();

        // build SQL for query
        EditText editText = (EditText) findViewById(R.id.lookupFoodInfo);
        String message = editText.getText().toString();

        String where = "";
        if (message.trim() != "") {
        	where = "WHERE Long_Desc LIKE '%"+message.trim()+"%'";
        }        
        String sql = "	SELECT fd._id, fg.FdGrp_Desc, fd.Long_Desc " + 
        			" FROM FOOD_DES fd INNER JOIN FD_GROUP fg ON fd.FdGrp_Cd = fg.FdGrp_Cd " +
        			where +
        			" ORDER BY FdGrp_Desc, Shrt_Desc ASC";
        
        // Execute query
        Cursor c = sqdb.rawQuery(sql, null);
        if (c != null) {
            c.moveToFirst();
        }
        

        // set fields to be used in listview
        String[] from 	= new String[] {"FdGrp_Desc", "Long_Desc"};
        int[]	 to		= new int[] {R.id.name, R.id.code};
        
        // set data Adapter for listview
        dataAdapter = new SimpleCursorAdapter(this, R.layout.food_description, c, from, to);
        
        
        ListView listView = (ListView) findViewById(R.id.list);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
        	         
        
        
        
		
	}
	
    public void lookupFoodInfo(View view){
    	doSearch();
    }	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_search);
		// Show the Up button in the action bar.
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		String search = intent.getStringExtra(FoodSearchActivity.EXTRA_MESSAGE);
		if (search != null) {
			
			EditText editText = (EditText) findViewById(R.id.lookupFoodInfo);
			editText.setText(search, TextView.BufferType.EDITABLE);
			doSearch();
		}
		
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_food_search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
