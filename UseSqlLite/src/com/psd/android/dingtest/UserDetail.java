/**
 * 
 */
package com.psd.android.dingtest;

/**
 * @author ding xiao
 *
 */
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserDetail extends Activity{
	protected TextView employeeName;
	protected TextView employeeTitle;
	protected TextView officePhone;
	protected TextView cellPhone;
	protected TextView email;
	protected TextView test22;
    protected int employeeId;

    String webUrl = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);
        
        employeeId = getIntent().getIntExtra("EMPLOYEE_ID", 0);
        SQLiteDatabase db = (new DatabaseHelper(this)).getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT emp._id, emp.firstName, emp.lastName, emp.title, emp.officePhone, emp.cellPhone, emp.email, emp.managerId, mgr.firstName managerFirstName, mgr.lastName managerLastName FROM employee emp LEFT OUTER JOIN employee mgr ON emp.managerId = mgr._id WHERE emp._id = ?", 
				new String[]{""+employeeId});

        if (cursor.getCount() == 1)
        {
        	cursor.moveToFirst();
        
	        employeeName = (TextView) findViewById(R.id.employeeName);
	        employeeName.setText(cursor.getString(cursor.getColumnIndex("firstName")) + " " + cursor.getString(cursor.getColumnIndex("lastName")));
	
	        employeeTitle = (TextView) findViewById(R.id.title);
	        employeeTitle.setText(cursor.getString(cursor.getColumnIndex("title")));

	        officePhone = (TextView) findViewById(R.id.officePhone);
	        officePhone.setText(cursor.getString(cursor.getColumnIndex("officePhone")));

	        cellPhone = (TextView) findViewById(R.id.cellPhone);
	        cellPhone.setText(cursor.getString(cursor.getColumnIndex("cellPhone")));

	        email = (TextView) findViewById(R.id.email);
	        email.setText(cursor.getString(cursor.getColumnIndex("email")));
	        //try test to add another row
	        test22 = (TextView) findViewById(R.id.test22);
	        test22.setText("extra text");
	        
        }
        
        //handler for button
        final Button button = (Button) findViewById(R.id.getPage);
        button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                  // Perform action on click
              	getPageUrl(v);
              }
         });

    }
    public void getPageUrl(View view) {
        // || is the concatenation operation in SQLite
                //cursor = db.rawQuery("SELECT _id, firstName, lastName, title FROM employee WHERE firstName || ' ' || lastName LIKE ?", 
                  //                              new String[]{"%" + searchText.getText().toString() + "%"});
    	//Intent intent = new Intent(this, UserDetailNewPerson.class);
    	//Cursor cursor = (Cursor) adapter.getItem(position);
    	//intent.putExtra("EMPLOYEE_ID", cursor.getInt(cursor.getColumnIndex("_id")));
    	//startActivity(intent);
    	String url = "http://www.fullissue.com/wp-content/uploads/2010/12/Adam-Lambert.jpg";
    	File extStor = Environment.getExternalStorageDirectory();
    	//should check state before using extStor
    	//Environment.getExternalStorageState()
    	String filename = "filename.txt";
    	File file = new File(Environment.getExternalStorageDirectory(), filename);
    	String fileName =  extStor.getAbsolutePath()+ "/dingtest11"; //"/LocalDisk/jmtest11"; // save in your sdcard
       	//GetWebPage.getAPAge(webUrl);
       	//GetWebPage.getBinaryFromUrlToSd(url, fileName);
		int buffSize = 1024;

        try{
        	java.net.URL urlSt = new java.net.URL(url);
        	InputStream os = urlSt.openStream();
            java.io.BufferedInputStream in = new java.io.BufferedInputStream(os);
            java.io.FileOutputStream fos = new java.io.FileOutputStream(fileName);
            java.io.BufferedOutputStream bout = new BufferedOutputStream(fos,buffSize);
            byte[] data = new byte[buffSize];
            int x=0;
            int n = 0;
                while((x=in.read(data,0,buffSize))>=0){
                    bout.write(data,0,x); 
                    n = n + x;
                }
            fos.flush();
            bout.flush();
            fos.close();
            bout.close();
            in.close();
	        test22 = (TextView) findViewById(R.id.test22);
	        test22.setText("get file from " + url+ ", save to " + fileName);

        }catch (Exception e){
            /* Display any Error to the GUI. */
            //tv.setText("Error: " + e.getMessage());
        }
    }

}
