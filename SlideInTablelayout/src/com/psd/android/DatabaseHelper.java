package com.psd.android;

/**
 * ding xiao
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "psd_user_db";
       
        public DatabaseHelper(Context context) {
                super(context, DATABASE_NAME, null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                /*
                 * Create the a table and populate it with sample data.
                 * In step 6, we will move these hardcoded statements to an XML document.
                 */
                String sql = "CREATE TABLE IF NOT EXISTS game4user (" +
                                                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                "firstName TEXT, " +
                                                "lastName TEXT, " +
                                                "title TEXT, " +
                                                "officePhone TEXT, " +
                                                "cellPhone TEXT, " +
                                                "email TEXT, " +
                                                "managerId INTEGER)";
                db.execSQL(sql);
                
                //table for game
                String sql2 = "CREATE TABLE IF NOT EXISTS game4list (" +
                "_id INTEGER PRIMARY KEY, " +
                "title TEXT, " +
                "score INTEGER)";
                
                db.execSQL(sql2);
                ContentValues values2 = new ContentValues();

                values2.put("_id", "1234");
                values2.put("title", "not a game");
                values2.put("score", "4321");
                db.insert("game4list", "notagame", values2);
             
                ContentValues values = new ContentValues();

                values.put("firstName", "John");
                values.put("lastName", "Smith");
                values.put("title", "CEO");
                values.put("officePhone", "617-219-2001");
                values.put("cellPhone", "617-456-7890");
                values.put("email", "jsmith@email.com");
                db.insert("game4user", "lastName", values);

                values.put("firstName", "Robert");
                values.put("lastName", "Jackson");
                values.put("title", "VP Engineering");
                values.put("officePhone", "617-219-3333");
                values.put("cellPhone", "781-444-2222");
                values.put("email", "rjackson@email.com");
                values.put("managerId", "1");
                db.insert("game4user", "lastName", values);

                values.put("firstName", "Marie");
                values.put("lastName", "Potter");
                values.put("title", "VP Sales");
                values.put("officePhone", "617-219-2002");
                values.put("cellPhone", "987-654-3210");
                values.put("email", "mpotter@email.com");
                values.put("managerId", "1");
                db.insert("game4user", "lastName", values);
               
                values.put("firstName", "Lisa");
                values.put("lastName", "Jordan");
                values.put("title", "VP Marketing");
                values.put("officePhone", "617-219-2003");
                values.put("cellPhone", "987-654-7777");
                values.put("email", "ljordan@email.com");
                values.put("managerId", "2");
                db.insert("game4user", "lastName", values);

                values.put("firstName", "Christophe");
                values.put("lastName", "Coenraets");
                values.put("title", "Evangelist");
                values.put("officePhone", "617-219-0000");
                values.put("cellPhone", "617-666-7777");
                values.put("email", "ccoenrae@adobe.com");
                values.put("managerId", "2");
                db.insert("game4user", "lastName", values);

                values.put("firstName", "Paula");
                values.put("lastName", "Brown");
                values.put("title", "Director Engineering");
                values.put("officePhone", "617-612-0987");
                values.put("cellPhone", "617-123-9876");
                values.put("email", "pbrown@email.com");
                values.put("managerId", "2");
                db.insert("game4user", "lastName", values);

                values.put("firstName", "Mark");
                values.put("lastName", "Taylor");
                values.put("title", "Lead Architect");
                values.put("officePhone", "617-444-1122");
                values.put("cellPhone", "617-555-3344");
                values.put("email", "mtaylor@email.com");
                values.put("managerId", "2");
                db.insert("game4user", "lastName", values);
               
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS game4users");
            db.execSQL("DROP TABLE IF EXISTS game4list");
            onCreate(db);
        }
       
}