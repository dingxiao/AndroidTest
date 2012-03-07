/**
 * 
 */
package com.psd.android;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
//not use android
//import android.sax.Element;
import org.w3c.dom.Element;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author ding xiao
 *
 */
public class GetWebXmlActivity extends ListActivity {
    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_from_web);


        String srcUrl = "http://p-xr.com/xml";

        String xml = XMLfunctions.getXMLPost(srcUrl);
        Document doc = XMLfunctions.XMLfromString(xml);

        int numResults = XMLfunctions.numResults(doc);

        if((numResults <= 0)){
            Toast.makeText(GetWebXmlActivity.this, "Did not get any item from "+srcUrl, Toast.LENGTH_LONG).show();  
            //finish();Call this when your activity is done and should be closed. back to who launched you
        }

        NodeList nodes = doc.getElementsByTagName("result");

        for (int i = 0; i < nodes.getLength(); i++) {                           
            HashMap<String, String> map = new HashMap<String, String>();    

            Element e = (Element)nodes.item(i);
            map.put("id","user id:" +  XMLfunctions.getValue(e, "id"));
            map.put("name", "user name:" + XMLfunctions.getValue(e, "name"));
            map.put("Score", "Score: " + XMLfunctions.getValue(e, "score"));
            mylist.add(map);            
        }       
        //map display a map from string array=keys and to array=resource id
        ListAdapter adapter = new SimpleAdapter(GetWebXmlActivity.this, mylist , R.layout.display_listitem_layout, 
                        new String[] { "id", "name", "Score" }, 
                        new int[] { R.id.id_title, R.id.item_title, R.id.item_subtitle });

        setListAdapter(adapter);
		//listView.setOnItemClickListener(new OnItemClickListener() {
			//@Override
		//});	
	    //need to add button handler
        final Button buttonSearch = (Button) findViewById(R.id.backToMainButton2);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
                   public void onClick(View v) {
                        // Perform action on click
                    	clickOnBackToMain(v);
                    }
        });

    }	
    //handle click
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//String item = (String) getListAdapter().getItem(position);
		//Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
		String key = "name";
		//Toast.makeText(getApplicationContext(),
		//	"Click the user " + mylist.get(position).get(key), Toast.LENGTH_LONG).show();
		TextView stringPassin = (TextView) findViewById(R.id.displayItemInfo);		
	    stringPassin.setText("user name is " + mylist.get(position).get(key));
	}
    
	//button hadlers
    public void clickOnBackToMain(View view) {
    	Intent intent = new Intent(this, SlideInTablelayoutActivity.class);
    	//Cursor cursor = (Cursor) adapter.getItem(position);
    	//intent.putExtra("EMPLOYEE_ID", cursor.getInt(cursor.getColumnIndex("_id")));
    	startActivity(intent);
    }	
	
}
