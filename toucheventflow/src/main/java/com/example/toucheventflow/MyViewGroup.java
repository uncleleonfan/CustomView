package com.example.toucheventflow;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class MyViewGroup extends RelativeLayout {

	private static final String TAG = "MyViewGroup";

	public MyViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * 分发事件
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.d(TAG, "dispatchTouchEvent");
		return super.dispatchTouchEvent(ev);
	}
	
	/**
	 * 拦截孩子的事件
	 * 
	 * @return true 表示拦截， false 不拦截
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.d(TAG, "onInterceptTouchEvent");
		boolean result = false;
		if (result) {
			Log.d(TAG, "拦截事件");
		} else {
			Log.d(TAG, "不拦截");
		}
		return result;
	}
	
	/**
	 * 消费事件
	 * 
	 * @return true 自己处理事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "onTouchEvent");
		boolean result = false;
		if (result) {
			Log.d(TAG, "消费事件");
		} else {
			Log.d(TAG, "不消费");
		}
		return result;
	}

}
