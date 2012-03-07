/**
 * 
 */
package com.psd.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.view.View;

/**
 * @author ding xiao
 *
 */
public class UseListActivity extends Activity {
 	TextView stringPassin;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		//need to set layout
		setContentView(R.layout.list_layout_arrayadapter);
        String searchText = getIntent().getStringExtra("searchText");
        
        stringPassin = (TextView) findViewById(R.id.searchTextPassin);		
        stringPassin.setText("Games with word " + searchText);
       
		if (searchText == null)
			searchText = "game";
		final ListView listView = (ListView) findViewById(R.id.mylist);
		String[] values = new String[] { "Android game1", "iPhone game2", "Android game1",
				"Android game11", "Android war game1", "Android war game2", "Android dog game1", "Max Android game1",
				"Linux Android game1", "Android game2" };

		// First paramenter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the View to which the data is written
		// Forth - the Array of data
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, values);
		//android.R.layout.simple_list_item_1, android.R.id.text1, values);
		//android.R.layout.simple_list_item_1, android.R.id.text1, values);

		//you can filter
		adapter.getFilter().filter(searchText);
		// Assign adapter to ListView
		listView.setAdapter(adapter);		

		listView.setOnItemClickListener(new OnItemClickListener() {
			//@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getApplicationContext(),
					"Click ListItem " + listView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG)
					.show();
			}
		});	
	    //need to add button handler
        final Button buttonSearch = (Button) findViewById(R.id.backToMainButton);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
                   public void onClick(View v) {
                        // Perform action on click
                    	clickOnBackToMain(v);
                    }
        });
		
	}

	//button hadlers
    public void clickOnBackToMain(View view) {
    	Intent intent = new Intent(this, SlideInTablelayoutActivity.class);
    	//Cursor cursor = (Cursor) adapter.getItem(position);
    	//intent.putExtra("EMPLOYEE_ID", cursor.getInt(cursor.getColumnIndex("_id")));
    	startActivity(intent);
    }	
	
}
