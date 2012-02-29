package com.psd.dingtest;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SlideWithGalleryActivity extends Activity {
 	TextView mySelection;
	Gallery myGallery;
	int currentPos = 0;
	ImageAdapter imageAdp;
	   /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		mySelection = (TextView) findViewById(R.id.mySelection);		
		
		// Bind the gallery defined in the main.xml
		// Apply a new (customized) ImageAdapter to it.

		myGallery = (Gallery) findViewById(R.id.myGallery);

		//myGallery.setAdapter(new ImageAdapter(this));
		imageAdp = new ImageAdapter(this);
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
		public int[] myImageIds = { R.drawable.photo_father, R.drawable.photo_handstand,R.drawable.photo_lexus,
				R.drawable.sample_0, R.drawable.sample_1, R.drawable.sample_2, R.drawable.sample_3, R.drawable.sample_4, 
				R.drawable.sample_5, R.drawable.photo1, R.drawable.photo2, R.drawable.photo3, R.drawable.photo4, R.drawable.photo5};

		/** Simple Constructor saving the 'parent' context. */
		public ImageAdapter(Context c) {
			this.myContext = c;
		}

		// inherited abstract methods - must be implemented
		// Returns count of images, and individual IDs
		public int getCount() {
			return this.myImageIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}
		// Returns a new ImageView to be displayed,
		public View getView(int position, View convertView, 
				ViewGroup parent) {

			// Get a View to display image data 					
			ImageView iv = new ImageView(this.myContext);
			if (selectPos == position){
				iv.setImageResource(this.myImageIds[position]);
				iv.setBackgroundResource(R.drawable.picture_frame);
				//iv.setColorFilter(color);
				iv.setLayoutParams(new Gallery.LayoutParams(400, 400));
			}
			else{
				iv.setImageResource(this.myImageIds[position]);
				//iv.setBackgroundResource(R.drawable.picture_frame);
				int color = 0xFF662233;
				Mode mode = PorterDuff.Mode.MULTIPLY;
				mode = PorterDuff.Mode.LIGHTEN;
				//iv.setColorFilter(color);
				iv.setColorFilter(color, mode);
				iv.setLayoutParams(new Gallery.LayoutParams(200, 400));
			}

			// Image should be scaled somehow
			//iv.setScaleType(ImageView.ScaleType.CENTER);
			//iv.setScaleType(ImageView.ScaleType.CENTER_CROP);			
			//iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			//iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
			iv.setScaleType(ImageView.ScaleType.FIT_XY);
			//iv.setScaleType(ImageView.ScaleType.FIT_END);
			
			// Set the Width & Height of the individual images
			//iv.setLayoutParams(new Gallery.LayoutParams(95, 70));
			//iv.setLayoutParams(new Gallery.LayoutParams(300, 400));

			return iv;
		}
		Drawable drawable_from_url(String url, String src_name) throws java.net.MalformedURLException, java.io.IOException 
	    {
	        //return Drawable.createFromStream(((java.io.InputStream)new java.net.URL(url).awagetContent()), src_name);
	        return Drawable.createFromStream(((java.io.InputStream)new java.net.URL(url).getContent()), src_name);
	    }

	}// ImageAdapter    
}