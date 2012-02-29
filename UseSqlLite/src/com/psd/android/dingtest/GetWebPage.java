package com.psd.android.dingtest;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class GetWebPage {

	public static String getAPAge(String webUrl){
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet(webUrl); //"http://www.spartanjava.com"
		try{
			HttpResponse response = httpClient.execute(httpGet, localContext);
			String result = "";
			 
			BufferedReader reader = new BufferedReader(
			    new InputStreamReader(
			      response.getEntity().getContent()
			    )
			  );
			 
			String line = null;
			while ((line = reader.readLine()) != null){
			  result += line + "\n";
			}	
			
			return result;
		}
		catch(Exception e){
			
		}
		return null;
	}
	/**
	 * down load a file given in url to sd card
 	 * @param url -- like "http://www.fullissue.com/wp-content/uploads/2010/12/Adam-Lambert.jpg";
	 * @param  FileName like  "/LocalDisk/jm"; // save in your sdcard
	 * @return
	 */
	public static int getBinaryFromUrlToSd(String url, String fileName){

		int buffSize = 1024;

        try{

            java.io.BufferedInputStream in = new java.io.BufferedInputStream(new java.net.URL(url).openStream());
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

            return n;
        }catch (Exception e){
            /* Display any Error to the GUI. */
            //tv.setText("Error: " + e.getMessage());
        }
		return 0;
	}
	
}
