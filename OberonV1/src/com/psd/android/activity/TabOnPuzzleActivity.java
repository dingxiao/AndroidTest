/**
 * 
 */
package com.psd.android.activity;

import com.psd.android.activity.TabOnTop20Activity.ImageAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
public class TabOnPuzzleActivity extends Activity {
 	TextView mySelection;
	Gallery myGallery;
	static int currentPos = 1;
	ImageAdapter imageAdp;
	public int[] myImageIds00 = { R.drawable.game1,R.drawable.game2,R.drawable.dl_01, R.drawable.dl_02,R.drawable.dl_03,R.drawable.dl_04,
			R.drawable.game4,R.drawable.game5,R.drawable.game6};
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		setContentView(R.layout.tab_puzzle);
		
		//get this layout textview
		mySelection = (TextView) findViewById(R.id.puzzleSelection);		
		
		// Bind the gallery defined in the tab_puzzle.xml
		// Apply a new (customized) ImageAdapter to it.
		myGallery = (Gallery) findViewById(R.id.puzzleGallery);

		//myGallery.setAdapter(new ImageAdapter(this));
		imageAdp = new ImageAdapter(this);
		imageAdp.setMyImageIds(myImageIds00);
		
		myGallery.setAdapter(imageAdp);
		
		myGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			   public void  onItemSelected  (AdapterView<?>  parent, View  v, int position, long id) {
			        Animation grow = AnimationUtils.loadAnimation(TabOnPuzzleActivity.this, R.anim.grow);

			        View sideView = parent.findViewById(position - 1);
			        if (sideView != null)
			           ((ImageView)sideView).setLayoutParams(new Gallery.LayoutParams(200, 100));

			        sideView = parent.findViewById(position + 1);
			        if (sideView != null)
			           ((ImageView)sideView).setLayoutParams(new Gallery.LayoutParams(200, 100));
			        imageAdp.setSelectPos(position);
			        v.startAnimation(grow);
			        v.setLayoutParams(new Gallery.LayoutParams(300, 150));
			    }

			    public void  onNothingSelected  (AdapterView<?>  parent) {
			        System.out.println("NOTHING SELECTED");

			    }


		});
		myGallery.setSelection(currentPos, false);
	}// onCreate

	public class ImageAdapter extends BaseAdapter {
		/** The parent context */
		private Context myContext;
		public int selectPos = 0;
		int mGalleryItemBackground;
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
	        TypedArray attr = myContext.obtainStyledAttributes(R.styleable.PuzzleGallery);
	        mGalleryItemBackground = attr.getResourceId(
	                R.styleable.PuzzleGallery_android_galleryItemBackground, 0);
	        attr.recycle();
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
			ImageView imageView = new ImageView(this.myContext);
		       
			int selected = this.getSelectPos();
	        imageView.setImageResource(myImageIds[position]);
	        if (position == 1)
	        imageView.setLayoutParams(new Gallery.LayoutParams(600, 200));
	        else
	        imageView.setLayoutParams(new Gallery.LayoutParams(200, 100));
	        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	        imageView.setBackgroundResource(mGalleryItemBackground);
	        
	        imageView.setId(position);
	        return imageView;

		}
		Drawable drawable_from_url(String url, String src_name) throws java.net.MalformedURLException, java.io.IOException 
	    {
	        //return Drawable.createFromStream(((java.io.InputStream)new java.net.URL(url).awagetContent()), src_name);
	        return Drawable.createFromStream(((java.io.InputStream)new java.net.URL(url).getContent()), src_name);
	    }

	}// ImageAdapter    
}
