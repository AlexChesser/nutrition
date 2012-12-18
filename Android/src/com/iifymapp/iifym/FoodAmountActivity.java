package com.iifymapp.iifym;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.iifymapp.iifym.Model.NutritionData;

public class FoodAmountActivity extends MainActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_amount);
		// Show the Up button in the action bar.
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		String search = intent.getStringExtra(FoodSearchActivity.EXTRA_MESSAGE);
		NutritionData n = new NutritionData(this);
		ArrayList<HashMap<String,String>> data = n.FoodData(search);
		
		ArrayList<String> theArray = new ArrayList<String>();
		for (HashMap<String,String> d : data) {
			android.util.Log.i(d.get("Description")+":", d.get("Value")+d.get("Units"));
			theArray.add(d.get("Description")+":\t"+ d.get("Value")+d.get("Units"));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_food_amount, menu);
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
