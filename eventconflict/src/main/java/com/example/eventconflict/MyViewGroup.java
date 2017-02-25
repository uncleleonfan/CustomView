package com.example.eventconflict;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class MyViewGroup extends RelativeLayout {

	private static final String TAG = "MyViewGroup";
	private float mDownX;
	private float mDownY;

	public MyViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	
	/**
	 * 有选择的拦截事件
	 * 
	 * 如果左右滑动，将事件拦截下来， MyViewGroup自己处理  如果dx>dy 判定左右滑动
	 * 其他情况就不拦截
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownX = ev.getX();
			mDownY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			float moveX = ev.getX();
			float moveY = ev.getY();
			float dx = Math.abs(moveX - mDownX);
			float dy = Math.abs(moveY - mDownY);
			//如果dx>dy 判定左右滑动  拦截事件
			if (dx > dy) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "处理左右滚动");
		return true;
	}

}
