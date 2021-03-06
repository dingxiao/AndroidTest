/**
 * 
 */
package com.psd.android.activity;

import com.psd.android.activity.TabOnTop20Activity.ImageAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

/**
 * @author ding xiao
 *
 */
public class TabOnPuzzleActivity extends Activity {
 	TextView mySelection;
 	TextView puzzleRecommend;
	Gallery myGallery;
	Gallery gameGallery;
	static int imageNormalWidth = 120;
	static int imageLeftRighlWidth = 120;
	static int imageSelectedWidth = 540;
	static int imageSelectedHeight = 180;
	static int imageNormalHeight = 140;
	static int imageLeftRighlHeight = 160;
	static int imageGamedWidth = 150;
	static int imageGameHeight = 100;
	static int currentPos = 3;
	ImageAdapter imageAdp;
	SimpleImageAdapter gameImageAdp;
	public int[] myImageIds00 = { R.drawable.game1,R.drawable.game2, R.drawable.dl_04,R.drawable.dl_01, R.drawable.dl_02,R.drawable.dl_03,
			R.drawable.game4,R.drawable.game5,R.drawable.game6};
	public int[] gameImageIds00 = { R.drawable.game1,R.drawable.game2, R.drawable.game3,R.drawable.game5,R.drawable.game6,R.drawable.game7,
			R.drawable.game4,R.drawable.game5,R.drawable.game6,R.drawable.game7,R.drawable.game8,R.drawable.game9};
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		setContentView(R.layout.tab_puzzle);
		
		//get this layout textview
		mySelection = (TextView) findViewById(R.id.puzzleSelection);		
		puzzleRecommend = (TextView) findViewById(R.id.puzzleRecommend);		
		puzzleRecommend.setText("Recommended for you-- some games below");
		// Bind the gallery defined in the tab_puzzle.xml
		// Apply a new (customized) ImageAdapter to it.
		myGallery = (Gallery) findViewById(R.id.puzzleGallery);

		//myGallery.setAdapter(new ImageAdapter(this));
		imageAdp = new ImageAdapter(this);
		imageAdp.setMyImageIds(myImageIds00);
		
		myGallery.setAdapter(imageAdp);
		
		myGallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			/*
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
			*/
		     private Animation grow = AnimationUtils.loadAnimation(TabOnPuzzleActivity.this, R.anim.grow);
		     private int last = currentPos;

		     public void  Select() {
		        grow = AnimationUtils.loadAnimation(TabOnPuzzleActivity.this, R.anim.grow);
		        last = 0;
		     }

		     public void  onItemSelected  (AdapterView<?>  parent, View  v, int position, long id) {
		        View sideView = parent.findViewById(last);
		        
		        mySelection.setText(" selected option: " + position);
		        if (sideView != null && last != position){
		           sideView.clearAnimation();
		           sideView.setLayoutParams(new Gallery.LayoutParams(imageNormalWidth, imageNormalHeight));
		        }
		        if (last > 0){
		        	sideView = parent.findViewById(last - 1);
		        
			        if (sideView != null){
			           ((ImageView)sideView).setLayoutParams(new Gallery.LayoutParams(imageNormalWidth, imageNormalHeight));
			        	int padRight = sideView.getPaddingRight();
			        	int padLeft = sideView.getPaddingLeft();
			        	int padTop = sideView.getPaddingTop();
			        	int padBot = sideView.getPaddingBottom();
			        	sideView.setPadding(padLeft, padTop, padRight+20, padBot);
			        }
		        }
		        if (last < myImageIds00.length-1){
		        	sideView = parent.findViewById(last + 1);
		        
			        if (sideView != null){
			           ((ImageView)sideView).setLayoutParams(new Gallery.LayoutParams(imageNormalWidth, imageNormalHeight));
			        	int padRight = sideView.getPaddingRight();
			        	int padLeft = sideView.getPaddingLeft();
			        	int padTop = sideView.getPaddingTop();
			        	int padBot = sideView.getPaddingBottom();
			        	sideView.setPadding(padLeft+20, padTop, padRight, padBot);
			        }
		        }
		        if (position > 0){
		        	sideView = parent.findViewById(position - 1);
		        
			        if (sideView != null){
				        ((ImageView)sideView).setLayoutParams(new Gallery.LayoutParams(imageLeftRighlWidth, imageLeftRighlHeight));
			        	Matrix m = ((ImageView)sideView).getImageMatrix();
			        	//m.postRotate(degrees);
			        	//m.postTranslate(dx, dy);			        	
				        //((ImageView)sideView).setImageMatrix(m);
			        	//MarginLayoutParams la = (MarginLayoutParams) sideView.getLayoutParams();
			        	//MarginLayoutParams la = (MarginLayoutParams) ((ImageView)sideView).getLayoutParams();
			        	//left,top, right, bot
			        	//if (la != null) la.setMargins(la.leftMargin, la.topMargin, la.rightMargin-20, la.bottomMargin);
			        	int padRight = sideView.getPaddingRight();
			        	int padLeft = sideView.getPaddingLeft();
			        	int padTop = sideView.getPaddingTop();
			        	int padBot = sideView.getPaddingBottom();
			        	sideView.setPadding(padLeft, padTop, padRight-20, padBot);
			        }
		        }
		        if (position < myImageIds00.length-1){
		        	sideView = parent.findViewById(position + 1);
		        
			        if (sideView != null){
			           ((ImageView)sideView).setLayoutParams(new Gallery.LayoutParams(imageLeftRighlWidth, imageLeftRighlHeight));
			        	//MarginLayoutParams la = (MarginLayoutParams) sideView.getLayoutParams();
			        	//left,top, right, bot
			        	//la.setMargins(la.leftMargin-20, la.topMargin, la.rightMargin, la.bottomMargin);
			        	int padRight = sideView.getPaddingRight();
			        	int padLeft = sideView.getPaddingLeft();
			        	int padTop = sideView.getPaddingTop();
			        	int padBot = sideView.getPaddingBottom();
			        	sideView.setPadding(padLeft-20, padTop, padRight, padBot);
			        }
		        }
		        v.startAnimation(grow);
		        v.setLayoutParams(new Gallery.LayoutParams(imageSelectedWidth, imageSelectedHeight));
		        last = position;
		     }

		    public void  onNothingSelected  (AdapterView<?>  parent) {
		    }
			

		});
		myGallery.setSelection(currentPos, false);
		
		
		

		//now for games, use another gallery
		gameGallery = (Gallery) findViewById(R.id.puzzleGameGallery);

		//myGallery.setAdapter(new ImageAdapter(this));
		gameImageAdp = new SimpleImageAdapter(this);
		gameImageAdp.setGameImageIds(gameImageIds00);
		
		gameGallery.setAdapter(gameImageAdp);
		
		gameGallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			   public void  onItemSelected  (AdapterView<?>  parent, View  v, int position, long id) {
		            Toast.makeText(TabOnPuzzleActivity.this, "This is game " + position, Toast.LENGTH_SHORT).show();
			    }

			    public void  onNothingSelected  (AdapterView<?>  parent) {
			        System.out.println("NOTHING SELECTED");

			    }
		});		
		gameGallery.setSelection(5);
		
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
	        //if (position == currentPos)
	        //imageView.setLayoutParams(new Gallery.LayoutParams(600, 200));
	        //else
	        imageView.setLayoutParams(new Gallery.LayoutParams(imageNormalWidth, imageNormalHeight));
	        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	        //imageView.setBackgroundResource(mGalleryItemBackground);
	        
	        imageView.setId(position);
	        return imageView;

		}
		Drawable drawable_from_url(String url, String src_name) throws java.net.MalformedURLException, java.io.IOException 
	    {
	        //return Drawable.createFromStream(((java.io.InputStream)new java.net.URL(url).awagetContent()), src_name);
	        return Drawable.createFromStream(((java.io.InputStream)new java.net.URL(url).getContent()), src_name);
	    }

	}// ImageAdapter    
	
	

	//simple version image from sdk
	public class SimpleImageAdapter extends BaseAdapter {
	    int mGalleryItemBackground;
	    private Context mContext;

	    public int[] mImageIds = null;
	   

	    public void setGameImageIds(int[] imgIds){
	    	mImageIds = imgIds;
	    }
	    public SimpleImageAdapter(Context c) {
	        mContext = c;
	        TypedArray attr = mContext.obtainStyledAttributes(R.styleable.PuzzleGallery);
	        mGalleryItemBackground = attr.getResourceId(
	                R.styleable.PuzzleGallery_android_galleryItemBackground, 0);
	        attr.recycle();
	    }

	    public int getCount() {
	        return mImageIds.length;
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView = new ImageView(mContext);

	        imageView.setImageResource(mImageIds[position]);
	        imageView.setLayoutParams(new Gallery.LayoutParams(imageGamedWidth, imageGameHeight));
	        //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	        imageView.setScaleType(ImageView.ScaleType.CENTER);
			//iv.setScaleType(ImageView.ScaleType.CENTER_CROP);			
			//iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			//iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
	        //imageView.setBackgroundResource(mGalleryItemBackground);

	        return imageView;
	    }
	}	
	
	
	
	
}
