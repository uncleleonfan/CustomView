package com.example.click;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class MyViewGroup extends RelativeLayout {

	public MyViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;//拦截事件
	}

}
