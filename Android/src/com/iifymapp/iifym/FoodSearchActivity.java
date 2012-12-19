package com.iifymapp.iifym;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.iifymapp.iifym.Model.NutritionData;

public class FoodSearchActivity extends MainActivity {
	
	private SimpleCursorAdapter dataAdapter;
	
	@SuppressWarnings("deprecation")
	private void doSearch(){
        // build SQL for query
        EditText editText = (EditText) findViewById(R.id.lookupFoodInfo);
        String message = editText.getText().toString();		

        NutritionData n = new NutritionData(this); 
        Cursor c = n.FoodSearch(message);
        
        // set fields to be used in listview
        String[] from 	= new String[] {"FdGrp_Desc", "Long_Desc", "kcal", "Pro", "Fat", "Carb", "Fibre"};
        int[]	 to		= new int[] {R.id.group, R.id.name, R.id.kcal, R.id.protein, R.id.fat, R.id.carb, R.id.fibre};
        
        // set data Adapter for listview
        dataAdapter = new SimpleCursorAdapter(this, R.layout.food_description, c, from, to);
        
        final ListView listView = (ListView) findViewById(R.id.list);
        
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		        Cursor o = (Cursor)listView.getItemAtPosition(position);
		        
		        setFoodAmount(o.getString(o.getColumnIndex("_id")));
        	}
        });
        
        
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
		EditText editText = (EditText) findViewById(R.id.lookupFoodInfo);
		
		editText.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	        	//android.util.Log.i("ATC", s.toString());
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){
	        	//android.util.Log.i("otc", s.toString() + " s:" + start + " b:" + before + " c:" + count);
	        	if (count > before) {
	        		doSearch();
	        	}
	        }
	    }); 
		
		try {
			if (search != null && !search.trim().equals("")) {
				android.util.Log.i("SEarching for: ", search);
				editText.setText(search, TextView.BufferType.EDITABLE);
			}
			doSearch();
		} catch (Exception e) {
			android.util.Log.i("Error", e.getMessage());
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
