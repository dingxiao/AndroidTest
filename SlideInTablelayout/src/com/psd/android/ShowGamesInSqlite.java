/**
 * 
 */
package com.psd.android;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.ContentValues;
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
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author ding xiao
 *
 */
public class ShowGamesInSqlite extends ListActivity {
    protected SQLiteDatabase db;
    protected Cursor cursor;
    protected ListAdapter adapter;
    protected EditText searchText;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_list_sqlite);
        //String searchText0 = getIntent().getStringExtra("searchText");
        db = (new DatabaseHelper(this)).getWritableDatabase();
        searchText = (EditText) findViewById (R.id.searchText3);
        searchText.setText("game");
        //employeeList = (ListView) findViewById (R.id.list);
        //need to add button handler
        final Button button = (Button) findViewById(R.id.searchButton3);
        button.setOnClickListener(new View.OnClickListener() {
                   public void onClick(View v) {
                        // Perform action on click
                    	search(v);
                    }
          });
          //need to add new person button handler
          final Button button2 = (Button) findViewById(R.id.newGameButton3);
          button2.setOnClickListener(new View.OnClickListener() {
                     public void onClick(View v) {
                          // Perform action on click
                      	//newPerson(v);
                    	 insertXmlToDb();
                      }
           });
                  
    }
    
    public void search(View view) {
    	
        cursor = db.rawQuery("SELECT _id, title, score FROM game4list WHERE title LIKE ?", 
                                        new String[]{"%" + searchText.getText().toString() + "%"});
        adapter = new SimpleCursorAdapter(
                        this, 
                        R.layout.display_listitem_layout, 
                        cursor, 
                        new String[] {"_id", "title", "score"}, 
                        new int[] { R.id.id_title, R.id.item_title, R.id.item_subtitle });
        //game4userList.setAdapter(adapter);
        //call method from ListActivity
        setListAdapter(adapter);
        //do update
        ContentValues values2 = new ContentValues();

        String id = "1234";
        values2.put("_id", id);
        values2.put("title", "not 3 game5");
        values2.put("score", "54321");
        //db.insert("game4list", "notagame", values2);
        String wherestr = "_id="+ id; //for string need to use title='
        insertOrUpdate("game4list", values2);
        id = "12345";
        values2.put("_id", id);
        values2.put("title", "not a game5");
        values2.put("score", "54321");
        //db.insert("game4list", "notagame", values2);
        wherestr = "_id="+ id; //for string need to use title='
        insertOrUpdate("game4list", values2);
    }
    //override method from listActivity, also change main.xml from @+id/ to  @android:id/, plus add to manifest activity
    public void onListItemClick(ListView parent, View view, int position, long id) {
    	//Intent intent = new Intent(this, UserDetail.class);
    	Cursor cursor = (Cursor) adapter.getItem(position);
    	//intent.putExtra("EMPLOYEE_ID", cursor.getInt(cursor.getColumnIndex("_id")));
    	//startActivity(intent);
		//String key = "name";
		//Toast.makeText(getApplicationContext(),
		//	"Click the user " + mylist.get(position).get(key), Toast.LENGTH_LONG).show();
		TextView stringPassin = (TextView) findViewById(R.id.gameResult);		
	    stringPassin.setText("user name is " + cursor.getString(cursor.getColumnIndex("title")));
    }
    public int insertOrUpdate(String tableName,ContentValues val){
    	String id = val.getAsString("_id");
    	if (rowExists(id,tableName)){
    		//update
            String wherestr = "_id="+ id; //for string need to use title='
            db.update(tableName, val, wherestr, null);
    	}
    	else{
    		db.insert(tableName, "notagame", val);
    	}
    	return 1;
    }
    public boolean rowExists(String idstr, String yourTable) {
    
    	//String idstr = Integer.toString(id);
    
    	Cursor cursor = db.rawQuery("select 1 from " + yourTable + " where _id= ?", new String[] { idstr });
	   boolean exists = (cursor.getCount() > 0);
	   cursor.close();
	   return exists;
	}


    public int insertXmlToDb(){
        String srcUrl = "http://mmah-portal.psddev.com:8180/cms/listtest1.xml";//"http://p-xr.com/xml";

        String xml = XMLfunctions.getXMLPost(srcUrl);
        Document doc = XMLfunctions.XMLfromString(xml);

        int numResults = XMLfunctions.numResults(doc);

        if((numResults <= 0)){
            Toast.makeText(ShowGamesInSqlite.this, "Did not get any item from "+srcUrl, Toast.LENGTH_LONG).show();  
            //finish();Call this when your activity is done and should be closed. back to who launched you
            return 0;
        }

        NodeList nodes = doc.getElementsByTagName("result");

        for (int i = 0; i < nodes.getLength(); i++) {                           
            Element e = (Element)nodes.item(i);
            //put to db
            ContentValues values2 = new ContentValues();

            String id = XMLfunctions.getValue(e, "id");
            values2.put("_id", id);
            values2.put("title", XMLfunctions.getValue(e, "name"));
            values2.put("score", XMLfunctions.getValue(e, "score"));
            insertOrUpdate("game4list", values2);
        }       
    	
    	return nodes.getLength();
    }
}
