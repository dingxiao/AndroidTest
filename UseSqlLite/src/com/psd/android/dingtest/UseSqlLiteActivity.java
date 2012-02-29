package com.psd.android.dingtest;

//import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class UseSqlLiteActivity extends ListActivity {
        

            
            protected EditText searchText;
            protected SQLiteDatabase db;
            protected Cursor cursor;
            protected ListAdapter adapter;
            //protected ListView employeeList;
            
        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main);
            db = (new DatabaseHelper(this)).getWritableDatabase();
            searchText = (EditText) findViewById (R.id.searchText);
            //employeeList = (ListView) findViewById (R.id.list);
            //need to add button handler
            final Button button = (Button) findViewById(R.id.searchButton);
                  button.setOnClickListener(new View.OnClickListener() {
                       public void onClick(View v) {
                            // Perform action on click
                        	search(v);
                        }
                   });
                  //need to add new person button handler
                  final Button button2 = (Button) findViewById(R.id.newPersonButton);
                        button2.setOnClickListener(new View.OnClickListener() {
                             public void onClick(View v) {
                                  // Perform action on click
                              	newPerson(v);
                              }
                         });
                      
        }
        
        public void search(View view) {
            // || is the concatenation operation in SQLite
                    cursor = db.rawQuery("SELECT _id, firstName, lastName, title FROM employee WHERE firstName || ' ' || lastName LIKE ?", 
                                                    new String[]{"%" + searchText.getText().toString() + "%"});
                    adapter = new SimpleCursorAdapter(
                                    this, 
                                    R.layout.name_list_item, 
                                    cursor, 
                                    new String[] {"firstName", "lastName", "title"}, 
                                    new int[] {R.id.firstName, R.id.lastName, R.id.title});
                    //employeeList.setAdapter(adapter);
                    //
                    setListAdapter(adapter);
        }
        //override method from listActivity, also change main.xml from @+id/ to  @android:id/, plus add to manifest activity
        public void onListItemClick(ListView parent, View view, int position, long id) {
        	Intent intent = new Intent(this, UserDetail.class);
        	Cursor cursor = (Cursor) adapter.getItem(position);
        	intent.putExtra("EMPLOYEE_ID", cursor.getInt(cursor.getColumnIndex("_id")));
        	startActivity(intent);
        }

        public void newPerson(View view) {
            // || is the concatenation operation in SQLite
                    //cursor = db.rawQuery("SELECT _id, firstName, lastName, title FROM employee WHERE firstName || ' ' || lastName LIKE ?", 
                      //                              new String[]{"%" + searchText.getText().toString() + "%"});
        	Intent intent = new Intent(this, UserDetailNewPerson.class);
        	//Cursor cursor = (Cursor) adapter.getItem(position);
        	//intent.putExtra("EMPLOYEE_ID", cursor.getInt(cursor.getColumnIndex("_id")));
        	startActivity(intent);
        }
        
}
