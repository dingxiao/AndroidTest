package com.psd.android.dingtest;

import java.io.File;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

public class InstallApp extends ListActivity{

	
	public int installFromSd(String myAppPath){
		String fileName = Environment.getExternalStorageDirectory() + "/" + myAppPath;//"/myApp.apk";
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
		startActivity(intent);	//from ListActivity
		
		return 0;
	}
	
	public int installAfterDownLoad(String url){
        //Now launch the installer for this APK
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + "pending.apk")), "application/vnd.android.package-archive");
        startActivity(intent); 

        return 0;
	}
}
