/**
 * 
 */
package com.psd.android;

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

/**
 * @author ding xiao
 *
 */
public class UseSqlite4list extends ListActivity {
    protected SQLiteDatabase db;
    protected Cursor cursor;
    protected ListAdapter adapter;
    protected EditText searchText;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_from_sqlite);
        String searchText0 = getIntent().getStringExtra("searchText");
        db = (new DatabaseHelper(this)).getWritableDatabase();
        searchText = (EditText) findViewById (R.id.searchText2);
        searchText.setText(searchText0);
        //employeeList = (ListView) findViewById (R.id.list);
        //need to add button handler
        final Button button = (Button) findViewById(R.id.searchButton2);
        button.setOnClickListener(new View.OnClickListener() {
                   public void onClick(View v) {
                        // Perform action on click
                    	search(v);
                    }
          });
          //need to add new person button handler
          final Button button2 = (Button) findViewById(R.id.newPersonButton2);
          button2.setOnClickListener(new View.OnClickListener() {
                     public void onClick(View v) {
                          // Perform action on click
                      	newPerson(v);
                      }
           });
                  
    }
    
    public void search(View view) {
        // || is the concatenation operation in SQLite
                cursor = db.rawQuery("SELECT _id, firstName, lastName, title FROM game4user WHERE firstName || ' ' || lastName LIKE ?", 
                                                new String[]{"%" + searchText.getText().toString() + "%"});
                adapter = new SimpleCursorAdapter(
                                this, 
                                R.layout.name_list_item, 
                                cursor, 
                                new String[] {"firstName", "lastName", "title"}, 
                                new int[] {R.id.firstName, R.id.lastName, R.id.title});
                //game4userList.setAdapter(adapter);
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
    	//Intent intent = new Intent(this, UserDetailNewPerson.class);
    	//Cursor cursor = (Cursor) adapter.getItem(position);
    	//intent.putExtra("EMPLOYEE_ID", cursor.getInt(cursor.getColumnIndex("_id")));
    	//startActivity(intent);
    }
}
