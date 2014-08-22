package com.acrinrete.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.acrinrete.R;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;

	public ImageAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return mThumbIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return mThumbIds[position];
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			imageView = new ImageView(mContext);
			Activity a = (Activity) mContext;
			DisplayMetrics metrics = new DisplayMetrics();
			a.getWindowManager().getDefaultDisplay().getMetrics(metrics);
			int height = metrics.heightPixels;
			int value = (int) (height * 0.275);
			imageView.setLayoutParams(new GridView.LayoutParams(value, value));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(0, 0, 0, 0);
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(mThumbIds[position]);
		return imageView;
	}

	public void shuffle() {
		List<Integer> l = new ArrayList<Integer>();
		for (int i = 0; i < mThumbIds.length; i++) {
			l.add(mThumbIds[i]);
		}
		Collections.shuffle(l);
		mThumbIds = l.toArray(mThumbIds);
	}

	// references to our images
	private Integer[] mThumbIds = { R.drawable.primo,R.drawable.news,
			R.drawable.meteo,R.drawable.contatti,R.drawable.foto,R.drawable.comune
			
	};

}