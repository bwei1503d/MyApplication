package com.bwei.ydhl.viewpager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.bwei.ydhl.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyPagerAdapter extends PagerAdapter {


	private Context context ;
	private  String [] images ;
	LayoutInflater inflater ;

	public MyPagerAdapter(Context context,String [] imgs) {
		super();
		this.context = context;
		this.images = imgs;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		System.out.println("container destroyItem = " + container + "  " +  object);
		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		int i = position % images.length;
		position = Math.abs(i);
		View view = inflater.inflate(R.layout.viewpageer_item,null);
		ImageView imageView = (ImageView) view.findViewById(R.id.viewpager_imageview);
//		ViewParent parent = view.getParent();
//		if (parent != null) {
//			ViewGroup group = (ViewGroup) parent;
//			System.out.println("group = " + group);
//			group.removeView(view);
//		}
		ImageLoader.getInstance().displayImage(images[position],imageView);
		container.addView(view);
		return view;
	}
}
