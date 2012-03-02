/**
 * 
 */
package com.psd.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author ding xiao
 *
 */
public class TabOnHiddenObjectActivity extends Activity {

	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Hidden Object tab");
        setContentView(textview);
    }

}
