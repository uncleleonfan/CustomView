package com.example.crosslayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CrossLayout extends ViewGroup {

	private boolean isStartLeft = false;

	public CrossLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//测量孩子
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		//布局孩子
		//把孩子 摆在同一个位置
//		for (int i = 0; i < getChildCount(); i++) {
//			View child = getChildAt(i);
//			int left = 0;
//			int top = 0;
//			int right = left + child.getMeasuredWidth();
//			int bottom = top + child.getMeasuredHeight();
//			child.layout(left, top, right, bottom);
//		}
		//垂直方向展开孩子
/*		int top = 0;
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			int left = 0;
			
			int right = left + child.getMeasuredWidth();
			int bottom = top + child.getMeasuredHeight();
			child.layout(left, top, right, bottom);
			
			//更新top,准备画下一个孩子
			top = top + child.getMeasuredHeight();
		}*/
		
		int top = 0;
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			int left = 0;
			if (isStartLeft) {
				if (i % 2 ==0) {
					//摆左边
					left = 0;
				} else {
					//i是奇数摆右边
					left = getMeasuredWidth() - child.getMeasuredWidth();
				}
			} else {
				if (i % 2 ==0) {
					//偶数摆右边
					left = getMeasuredWidth() - child.getMeasuredWidth();
				} else {
					//i是奇数左边
					left = 0;
				}
			}
			
			int right = left + child.getMeasuredWidth();
			int bottom = top + child.getMeasuredHeight();
			child.layout(left, top, right, bottom);
			
			//更新top,准备画下一个孩子
			top = top + child.getMeasuredHeight();
		}
	}

	/**
	 * 实现交叉布局
	 */
	public void revert() {
		isStartLeft = !isStartLeft;
//		invalidate();//触发重新绘制
		requestLayout();//请求重新布局-》onLayout
	}

}
