package com.iifymapp.iifym;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.iifymapp.iifym.Model.NutritionData;
import com.iifymapp.iifym.Model.NutritionData.Food;
import com.iifymapp.iifym.Model.NutritionData.Nutrient;
import com.iifymapp.iifym.Model.NutritionData.NutrientAdapter;
import com.iifymapp.iifym.Model.NutritionData.Weight;

public class FoodAmountActivity extends MainActivity {
	public Food f;
	public ArrayList<Nutrient> data;
	public NutritionData n;
	public ListView ni;
	public NutrientAdapter Nadapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_amount);
		// Show the Up button in the action bar.
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		String search = intent.getStringExtra(FoodSearchActivity.EXTRA_MESSAGE);
		n = new NutritionData(this);
		
		f = n.new Food();
		f = f.init(search);
		
		setTitle(f.LongDesc);
		TextView longDesc = (TextView)findViewById(R.id.longDesc);
		longDesc.setText(f.LongDesc);
		
		data = f.getNutrients();
		for (Nutrient d : data) {
			android.util.Log.i(d.description, d.Value+d.Units);
		}
		
		ni = (ListView) findViewById(R.id.nutritionalInformation);
		Nadapter = n.new NutrientAdapter(this, R.layout.nutrient_row, data);
		ni.setAdapter(Nadapter);
	    
		EditText setWeight = (EditText) findViewById(R.id.setWeight);
		setWeight.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	        	//android.util.Log.i("ATC", s.toString());
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){
	        	android.util.Log.i("OTC", s.toString());
	        	setWeight(Float.parseFloat(s.toString()));
	        }
	    });		
		
		
		ListView w = (ListView) findViewById(R.id.weights);
		
		w.setOnItemClickListener(new OnItemClickListener() {
		    
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		        // do whatever stuff you wanna do here
		    	EditText setWeight = (EditText) findViewById(R.id.setWeight);
		    	ArrayList<Weight> weights = f.getWeights();
		    	Weight setTo = weights.get(position);
		    	android.util.Log.i(weights.get(position).toString(), String.valueOf(position));
		    	setWeight.setText(String.valueOf(setTo.Grams));
		    }
		});
		
		ArrayAdapter<Weight> weightAdapter = new ArrayAdapter<Weight>(this, android.R.layout.simple_list_item_1, f.getWeights());
		w.setAdapter(weightAdapter);
		for (Weight weight : f.getWeights()) {
			android.util.Log.i("Weight option", weight.toString());
		}
	    
	}
	
	public void setWeight(Float g) {
		g = g / 100;
    	
    	for (Nutrient n : data) {
    		String numberformat = "#0.00";
    		switch (Integer.parseInt(n.Decimals)) {
    			case 0:
    				numberformat = "#0";
    				break;
    			case 1:
    				numberformat = "#0.0";
    				break;
    			case 2:
    				numberformat = "#0.00";
    				break;
    			case 3:
    				numberformat = "#0.000";
    				break;
    		}
    		NumberFormat formatter = new DecimalFormat(numberformat);
    		n.Value = formatter.format((n.BaseValue * g));
    	}
    	android.util.Log.i("Updated", "TO: "+String.valueOf(g));
    	Nadapter.notifyDataSetChanged();
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
