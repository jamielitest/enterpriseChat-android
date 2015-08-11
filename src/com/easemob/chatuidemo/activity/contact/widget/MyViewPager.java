package com.easemob.chatuidemo.activity.contact.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class MyViewPager extends ViewPager {
	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("NewApi")
	@Override
	protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
		if (v instanceof AutoHorizontalScrollView) {
			// AutoHorizontalScrollView view = (AutoHorizontalScrollView) v;
			// if(android.os.Build.VERSION.SDK_INT >= 14){
			// if(view.canScrollHorizontally(-1) ||
			// view.canScrollHorizontally(1))
			// return true;
			// }else{
			return true;
			// }

		}
		return super.canScroll(v, checkV, dx, x, y);
	}

}
