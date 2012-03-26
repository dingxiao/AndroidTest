package com.psd.android;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

	public class DownLoadFileActivity extends Activity {
	   
	    public static final String LOG_TAG = "Android Downloadertest";
	   
	    //initialize our progress dialog/bar
	    private ProgressDialog mProgressDialog;
	    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	   
	    //initialize root directory
	    File rootDir = Environment.getExternalStorageDirectory();
	    String downlaodDir = "/psdfile_downloads";
	    //defining file name and url
	    public String fileName = "dr-becker-hub.jpg";
	    String[] fileNames = new String[100];
	    //http://cdn.psddev.com/9d/1022e0b79711e0aa480050568d634f/file/boxer-promo-mk072611.jpg
	    public String fileURL = "http://cdn.psddev.com/00/9099e0bd1e11e095940050568d634f/file/dr-becker-hub-banner.jpg";
	    //
	    String fileURL2 = "http://cdn.psddev.com/9d/1022e0b79711e0aa480050568d634f/file/boxer-promo-mk072611.jpg2";
	    	//"https://lh4.googleusercontent.com/-HiJOyupc-tQ/TgnDx1_HDzI/AAAAAAAAAWo/DEeOtnRimak/s800/DSC04158.JPG";
	    String[] fileURLs = {"http://cdn.psddev.com/d0/196b90bdfc11e095940050568d634f/file/beagle+breed+good.jpg",
	    "http://cdn.psddev.com/90/8caa709e8d11e0a2380050568d634f/file/Border-Collie-1-645mk062111.jpg",
	    "http://cdn.psddev.com/83/f56b30be1311e095940050568d634f/file/Chihuahua+Promo.jpg"
	    };
	    @Override
	    public void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	        //setting some display
	        setContentView(R.layout.file_download);
	        TextView tv = new TextView(this);
	        tv.setText("Android Download File With Progress Bar");
	   
	        //making sure the download directory exists
	        checkAndCreateDirectory(downlaodDir);
	       
	        //executing the asynctask
	        //new DownloadFileAsync().execute(fileURL);
	        
	    	EditText urlEdit = (EditText)findViewById(R.id.editText4download);
	    	urlEdit.setText(fileURL2);
	        //need to add button handler
	        final Button buttonNew = (Button) findViewById(R.id.button4download);
	        buttonNew.setOnClickListener(new View.OnClickListener() {
	                 public void onClick(View v) {
	                      // Perform action on click
	                  	clickOnButton4download(v);
	                  }
	        });
	    }
	    public void clickOnButton4download(View view) {
	    	// Enable Layout 2 and Disable Layout 1
	    	//Layout1 .setVisibility(View.GONE);
	    	//Layout2.setVisibility(View.VISIBLE);
			//myGallery = (Gallery) findViewById(R.id.puzzleGallery);
	    	//get fileURL from text
	    	EditText urlEdit = (EditText)findViewById(R.id.editText4download);
	    	String urlt = urlEdit.getText().toString();
	    	if (urlt != null && urlt.length() > 0)
	    		fileURL = urlt;
	    	new DownloadFileAsync().execute(fileURL);
	    }	
	 
	    //this is our download file asynctask
	    class DownloadFileAsync extends AsyncTask<String, String, String> {
	       
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            showDialog(DIALOG_DOWNLOAD_PROGRESS);
	        }

	       
	        @Override
	        protected String doInBackground(String... aurl) {

	        	int numUrl = fileURLs.length;
	        	for (int idx=0; idx < numUrl; idx++){
	            try {
	            	String fileWebUrl = fileURLs[idx];
	                //connecting to url
	                URL u = new URL(fileWebUrl);//(fileURL);
	                HttpURLConnection c = (HttpURLConnection) u.openConnection();
	                c.setRequestMethod("GET");
	                c.setDoOutput(true);
	                c.connect();
	               
	                //lenghtOfFile is used for calculating download progress
	                int lenghtOfFile = c.getContentLength();
	               
	                int start = fileWebUrl.lastIndexOf("/");
	                if (start < 0) start = -1;//not exist return -1
	                fileNames[idx] = fileWebUrl.substring(start+1, fileWebUrl.length());
	                Log.i(LOG_TAG, "saveTofile="+fileNames[idx]);
	                //this is where the file will be seen after the download
	                FileOutputStream f = new FileOutputStream(new File(rootDir + downlaodDir, fileNames[idx]));
	                //file input is from the url
	                InputStream in = c.getInputStream();

	                //here's the download code
	                byte[] buffer = new byte[1024];
	                int len1 = 0;
	                long total = 0;
	               
	                while ((len1 = in.read(buffer)) > 0) {
	                    total += len1; //total = total + len1
	                    publishProgress("" + (int)((total*100)/(numUrl*lenghtOfFile)));
	                    f.write(buffer, 0, len1);
	                }
	                f.close();
	               
	            } catch (Exception e) {
	                Log.d(LOG_TAG, e.getMessage());
	            }
	        	}
	            return null;
	        }
	       
	        protected void onProgressUpdate(String... progress) {
	             Log.d(LOG_TAG,progress[0]);
	             mProgressDialog.setProgress(Integer.parseInt(progress[0]));
	        }

	        @Override
	        protected void onPostExecute(String unused) {
	            //dismiss the dialog after the file was downloaded
	            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
	            //show the image
	            File f = new File(rootDir + downlaodDir, fileNames[0]);
	            BitmapDrawable result = new BitmapDrawable(f.getAbsolutePath());	
	            ImageView img = (ImageView) findViewById(R.id.img4download);
	            img.setImageDrawable(result);
	        }
	        
	        
	    }
	   
	    //function to verify if directory exists
	    public void checkAndCreateDirectory(String dirName){
	        File new_dir = new File( rootDir + dirName );
	        if( !new_dir.exists() ){
	            new_dir.mkdirs();
	        }
	    }
	   
	    //our progress bar settings
	    @Override
	    protected Dialog onCreateDialog(int id) {
	        switch (id) {
	            case DIALOG_DOWNLOAD_PROGRESS: //we set this to 0
	                mProgressDialog = new ProgressDialog(this);
	                mProgressDialog.setMessage("Downloading file...");
	                mProgressDialog.setIndeterminate(false);
	                mProgressDialog.setMax(100);
	                //mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	                mProgressDialog.setCancelable(true);
	                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	                mProgressDialog.show();
	                return mProgressDialog;
	            default:
	                return null;
	        }
	    }
	    //@Override
	    protected void onPrepareDialog00(int id, Dialog dialog) {
	        switch (id){


	            case DIALOG_DOWNLOAD_PROGRESS:
	            	ProgressDialog p = (ProgressDialog) dialog;//.findViewById(android.R.id.progress);
	                p.setProgress(0);
	                //p.setVisibility(View.VISIBLE);
	            break;
	        }
	    }
	}