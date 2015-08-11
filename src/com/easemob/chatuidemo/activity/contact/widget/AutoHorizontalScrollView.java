package com.easemob.chatuidemo.activity.contact.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class AutoHorizontalScrollView extends HorizontalScrollView {
	public AutoHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		fullScroll(HorizontalScrollView.FOCUS_RIGHT);
	}
}
