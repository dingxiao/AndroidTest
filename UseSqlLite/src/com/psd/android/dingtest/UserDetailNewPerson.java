/**
 * 
 */
package com.psd.android.dingtest;

/**
 * @author ding xiao
 *
 */
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserDetailNewPerson extends Activity{
	protected TextView employeeName;
	protected TextView employeeTitle;
	protected TextView officePhone;
	protected TextView cellPhone;
	protected TextView email;
	protected TextView test22;
    protected int employeeId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details_newperson);
        
        //employeeId = getIntent().getIntExtra("EMPLOYEE_ID", 0);
        //SQLiteDatabase db = (new DatabaseHelper(this)).getWritableDatabase();
        //Cursor cursor = db.rawQuery("SELECT emp._id, emp.firstName, emp.lastName, emp.title, emp.officePhone, emp.cellPhone, emp.email, emp.managerId, mgr.firstName managerFirstName, mgr.lastName managerLastName FROM employee emp LEFT OUTER JOIN employee mgr ON emp.managerId = mgr._id WHERE emp._id = ?", 
			//	new String[]{""+employeeId});

        final Button button = (Button) findViewById(R.id.newPerson2);
        button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                  // Perform action on click
              	createNewPerson(v);
              }
         });
 
    }

    public void createNewPerson(View view) {
        // || is the concatenation operation in SQLite
                //cursor = db.rawQuery("SELECT _id, firstName, lastName, title FROM employee WHERE firstName || ' ' || lastName LIKE ?", 
                  //                              new String[]{"%" + searchText.getText().toString() + "%"});
        ContentValues values = new ContentValues();
        SQLiteDatabase db = (new DatabaseHelper(this)).getWritableDatabase();

        EditText nameText1 = (EditText) findViewById (R.id.employeeNameFirst);
        EditText nameText2 = (EditText) findViewById (R.id.employeeNameLast);
        EditText titleText = (EditText) findViewById (R.id.title);
        //        String ff = 
        values.put("firstName", nameText1.getText().toString());
        values.put("lastName", nameText2.getText().toString());
        values.put("title", titleText.getText().toString());
        //values.put("title", "test");
        values.put("officePhone", "617-219-2001");
        values.put("cellPhone", "617-456-7890");
        values.put("email", "jsmith@email.com");
        db.insert("employee", "lastName", values);

        Intent intent = new Intent(this, UseSqlLiteActivity.class);
    	//Cursor cursor = (Cursor) adapter.getItem(position);
    	//intent.putExtra("EMPLOYEE_ID", cursor.getInt(cursor.getColumnIndex("_id")));
    	startActivity(intent);
    }
    
}
