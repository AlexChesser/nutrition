package com.iifymapp.iifym;

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
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void lookupFoodInfo(View view){
    	Intent intent = new Intent(this, FoodSearchActivity.class);
    	EditText editText = (EditText) findViewById(R.id.lookupFoodInfo);
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    }
    
    @Override
    public boolean onSearchRequested() {

        // your logic here
    	this.lookupFoodInfo(null);
        return false;  // don't go ahead and show the search box
    }
    
}
