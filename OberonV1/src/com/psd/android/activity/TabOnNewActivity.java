/**
 * 
 */
package com.psd.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ViewSwitcher.ViewFactory;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
 
/**
 * @author ding xiao
 *
 
public class TabOnNewActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the New tab");
        setContentView(textview);
    }
	
}
*/
public class TabOnNewActivity extends Activity 
implements ViewFactory
{    
    //---the images to display---
    Integer[] imageIDs = {
            R.drawable.dl_01,
            R.drawable.dl_02,
            R.drawable.dl_03,
            R.drawable.dl_04                    
    };
 
    private ImageSwitcher imageSwitcher;
 
    @Override    
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_new);
 
        imageSwitcher = (ImageSwitcher) findViewById(R.id.switcher1);
        imageSwitcher.setFactory(this);
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));
 
        Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        gallery.setAdapter(new ImageAdapter(this));
        gallery.setOnItemClickListener(new OnItemClickListener() 
        {
            public void onItemClick(AdapterView parent, 
            View v, int position, long id) 
            {                
            	imageSwitcher.setImageResource(imageIDs[position]);
            }
        });  
    }
 
    public View makeView() 
    {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundColor(0xFF000000);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new 
                ImageSwitcher.LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.FILL_PARENT));
        imageView.bringToFront();
        return imageView;
    }
 
    public class ImageAdapter extends BaseAdapter 
    {
        private Context context;
        private int itemBackground;
 
        public ImageAdapter(Context c) 
        {
            context = c;
 
            //---setting the style---                
            TypedArray a = obtainStyledAttributes(R.styleable.PuzzleGallery);
            itemBackground = a.getResourceId(
                    R.styleable.PuzzleGallery_android_galleryItemBackground, 0);
            a.recycle();                                                    
        }
 
        //---returns the number of images---
        public int getCount() 
        {
            return imageIDs.length;
        }
 
        //---returns the ID of an item--- 
        public Object getItem(int position) 
        {
            return position;
        }
 
        public long getItemId(int position) 
        {
            return position;
        }
 
        //---returns an ImageView view---
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(imageIDs[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new Gallery.LayoutParams(150, 120));
            imageView.setBackgroundResource(itemBackground);
            return imageView;
        }
   }    
    }