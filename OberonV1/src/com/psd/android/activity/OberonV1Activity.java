package com.psd.android.activity;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class OberonV1Activity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, TabOnNewActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("new").setIndicator("New",
                          res.getDrawable(R.drawable.ic_tab_new))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, TabOnTop20Activity.class);
        spec = tabHost.newTabSpec("top20").setIndicator("Top20",
                          res.getDrawable(R.drawable.ic_tab_top20))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, TabOnPuzzleActivity.class);
        spec = tabHost.newTabSpec("puzzle").setIndicator("Puzzle",
                          res.getDrawable(R.drawable.ic_tab_puzzle))
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(2);
    }
}