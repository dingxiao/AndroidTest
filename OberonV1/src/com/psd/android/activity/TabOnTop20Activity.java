/**
 * 
 */
package com.psd.android.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * @author ding xiao
 *
 */
public class TabOnTop20Activity extends Activity {

 	TextView mySelection;
	Gallery myGallery;
	int currentPos = 0;
	ImageAdapter imageAdp;
	public int[] myImageIds00 = { R.drawable.game1,R.drawable.game2,R.drawable.dl_01, R.drawable.dl_02,R.drawable.dl_03,R.drawable.dl_04,
			R.drawable.game4,R.drawable.game5,R.drawable.game6};
			//R.drawable.sample_0, R.drawable.sample_1, R.drawable.sample_2, R.drawable.sample_3, R.drawable.sample_4, 
			//R.drawable.sample_5, R.drawable.photo1, R.drawable.photo2, R.drawable.photo3, R.drawable.photo4, R.drawable.photo5};
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TextView textview = new TextView(this);
        //textview.setText("This is the Top 20 tab");
        //setContentView(textview);
		setContentView(R.layout.tab_top20);
		mySelection = (TextView) findViewById(R.id.mySelection);		
		
		// Bind the gallery defined in the main.xml
		// Apply a new (customized) ImageAdapter to it.

		myGallery = (Gallery) findViewById(R.id.myGallery);

		//myGallery.setAdapter(new ImageAdapter(this));
		imageAdp = new ImageAdapter(this);
		imageAdp.setMyImageIds(myImageIds00);
		
		myGallery.setAdapter(imageAdp);
		
		myGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				currentPos = position;
				imageAdp.selectPos = position;
				imageAdp.notifyDataSetChanged(); //this cause image reload
				//parent.refreshDrawableState();
				//parent.invalidate();
				//parent.postInvalidate();
				//myGallery.postInvalidate();
				mySelection.setText(" selected option: " + position + ", imageId: " + imageAdp.myImageIds[position]);
				
			}

			public void onNothingSelected(AdapterView<?> parent) {
				mySelection.setText("Nothing selected");
				
			}


		});
	}// onCreate

	public class ImageAdapter extends BaseAdapter {
		/** The parent context */
		private Context myContext;
		public int selectPos = 0;
		// Put some images to project-folder: /res/drawable/
		// format: jpg, gif, png, bmp, ...
		//private int[] myImageIds = { R.drawable.image1, R.drawable.image2,R.drawable.image3, R.drawable.mbl1 };
		public int[] myImageIds = null;
		//{ R.drawable.photo_father, R.drawable.photo_handstand,R.drawable.photo_lexus,
		//		R.drawable.sample_0, R.drawable.sample_1, R.drawable.sample_2, R.drawable.sample_3, R.drawable.sample_4, 
			//	R.drawable.sample_5, R.drawable.photo1, R.drawable.photo2, R.drawable.photo3, R.drawable.photo4, R.drawable.photo5};

		/** Simple Constructor saving the 'parent' context. */
		public ImageAdapter(Context c) {
			this.myContext = c;
		}

		public Context getMyContext() {
			return myContext;
		}

		public void setMyContext(Context myContext) {
			this.myContext = myContext;
		}

		public int getSelectPos() {
			return selectPos;
		}

		public void setSelectPos(int selectPos) {
			this.selectPos = selectPos;
		}

		public int[] getMyImageIds() {
			return myImageIds;
		}

		public void setMyImageIds(int[] myImageIds) {
			this.myImageIds = myImageIds;
		}

		// inherited abstract methods - must be implemented
		// Returns count of images, and individual IDs
		public int getCount() {
			return this.myImageIds.length;
		}
		// inherited abstract methods - must be implemented
		public Object getItem(int position) {
			return position;
		}
		// inherited abstract methods - must be implemented
		public long getItemId(int position) {
			return position;
		}
		// Returns a new ImageView to be displayed,
		public View getView(int position, View convertView, 
				ViewGroup parent) {

			// Get a View to display image data 					
			ImageView iv = new ImageView(this.myContext);
	        //iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
			if (selectPos == position){
				iv.setImageResource(this.myImageIds[position]);
				//iv.setBackgroundResource(R.drawable.picture_frame);
				//iv.setColorFilter(color);
				//iv.setLayoutParams(new Gallery.LayoutParams(600, 200));
			}
			else{
				iv.setImageResource(this.myImageIds[position]);
				//iv.setImageURI(uri);
				//iv.setBackgroundResource(R.drawable.picture_frame);
				//iv.setLayoutParams(new Gallery.);
				int color = 0xFF662233;
				Mode mode = PorterDuff.Mode.MULTIPLY;
				mode = PorterDuff.Mode.LIGHTEN;
				//iv.setColorFilter(color);
				//iv.setColorFilter(color, mode);
				//iv.setLayoutParams(new Gallery.LayoutParams(200, 100));
				//MarginLayoutParams mlp = (MarginLayoutParams) iv.getLayoutParams();
				//mlp.setMargins(mlp.leftMargin, 
				//               mlp.topMargin+20, 
				//               mlp.rightMargin, 
				//               mlp.topMargin-40
				//);
			}

			// Image should be scaled somehow
			//iv.setScaleType(ImageView.ScaleType.CENTER);
			//iv.setScaleType(ImageView.ScaleType.CENTER_CROP);			
			//iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
			//iv.setScaleType(ImageView.ScaleType.FIT_XY);
			//iv.setScaleType(ImageView.ScaleType.FIT_END);
			
			// Set the Width & Height of the individual images
			//iv.setLayoutParams(new Gallery.LayoutParams(95, 70));
			//iv.setLayoutParams(new Gallery.LayoutParams(600, 200));
			
	        /* Image should be scaled as width/height are set. */
			 
	        iv.setScaleType(ImageView.ScaleType.CENTER);
	 
	        /* Set the Width/Height of the ImageView. */
	 
	        float scale = getScaleDing((position-selectPos));
	 
	        iv.setLayoutParams(new Gallery.LayoutParams(Math.round(600*scale),Math.round(200*scale)));

			return iv;
		}
		
		
		/** Returns the size (0.0f to 1.0f) of the views
		* depending on the 'offset' to the center. */
		//@Override
		public float getScale(boolean focused, int offset) {
			/* Formula: 1 / (2 ^ offset) */
			return getScaleDing(offset);
		} 		
		//@Override
		public float getScaleByPower(boolean focused, int offset) {
			/* Formula: 1 / (2 ^ offset) */
			return Math.max(0.01f, 1.0f / (float)Math.pow(2, Math.abs(offset)));
		} 		
		public float getScaleDing(int offset) {
			float sc = 0.5f;
			if (offset == 0)
				sc = 1.0f;
			return sc;
		} 		
		
		
		public View getView2(int position, View convertView, ViewGroup parent) {
			 
	        ImageView i;
	 
	 
	 
	        int selected = ((Gallery)parent).getSelectedItemPosition();
	 
	       
	 
	        if (convertView == null) {
	 
	            i = new ImageView(this.myContext);  
	 
	        } else { // Reuse/Overwrite the View passed
	 
	            // We are assuming(!) that it is castable!
	 
	            i = (ImageView) convertView;
	 
	        }
	 
	       
	 
	        //i.setImageDrawable(myItems.get(position).getImage());
	        i.setImageResource(this.myImageIds[position]);
	 
	        /* Image should be scaled as width/height are set. */
	 
	        i.setScaleType(ImageView.ScaleType.CENTER);
	 
	        /* Set the Width/Height of the ImageView. */
	 
	        float scale = getScale2((position-selected));
	 
	        i.setLayoutParams(new Gallery.LayoutParams(Math.round(90*scale),Math.round(60*scale)));
	 
	       
	 
	           
	 
	        return i;
	 
	    }
	 
	   
	 
	    public float getScale2(int offset) {
	 
	        /* Formula: 1 / (2 ^ offset) */
	 
	          return Math.max(0, 1.0f / (float)Math.pow(2, Math.abs(offset)));
	 
	      }
	 		
		
		
		Drawable drawable_from_url(String url, String src_name) throws java.net.MalformedURLException, java.io.IOException 
	    {
	        //return Drawable.createFromStream(((java.io.InputStream)new java.net.URL(url).awagetContent()), src_name);
	        return Drawable.createFromStream(((java.io.InputStream)new java.net.URL(url).getContent()), src_name);
	    }

	}// ImageAdapter    	
	
}
