package com.example.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class MyViewGroup extends ViewGroup {
	private static final String TAG = "MyViewGroup";
	private View mChild;

	public MyViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//查看孩子的测量的宽高
		mChild = getChildAt(0);
		int measureWidth = mChild.getMeasuredWidth();//获取孩子测量的宽
		int measureHeight = mChild.getMeasuredHeight();//获取孩子测量的高
//		Log.d(TAG, "measureWidth " + measureWidth + " measureHeight" + measureHeight);
		
		//获取孩子向我申请的宽高
		LayoutParams layoutParams = mChild.getLayoutParams();
		int childWidth = layoutParams.width;
		int childHeight = layoutParams.height;
		
		//组装对孩子宽高的期望
		int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
		int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
		//测量孩子
		mChild.measure(childWidthMeasureSpec, childHeightMeasureSpec);
		
//		Log.d(TAG, "测量之后 measureWidth " + mChild.getMeasuredWidth()
//				+ " measureHeight" + mChild.getMeasuredHeight());
//		
		// 默认使用父容器的期望设置MyViewGroup的宽高
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/*
    * Derived classes with children should override
    * onLayout. In that method, they should
    * call layout on each of their children
    */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int left = 50;
		int top = 50;
		int right = left + mChild.getMeasuredWidth();//获取测量后的宽度
		int bottom = top + mChild.getMeasuredHeight();
		
		int width = mChild.getWidth();//return mRight - mLeft;
		Log.d(TAG, "child width before layout " + width);
		//调用每个孩子的layout方法去布局孩子
		mChild.layout(left, top, right, bottom);
		
		Log.d(TAG, "child width before layout " + mChild.getWidth());
	}

}
