package com.example.slidingmenu;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

public class SlidingMenu extends ViewGroup {

	private View mLeftChild;
	private View mRightChild;
	private float mDownX;
	
	private Scroller mScroller;
	
	private ImageView mBack;
	private boolean isOpen;
	private float mDownY;


	public SlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);

	}
	
	/**
	 * 完成解析布局后的调用
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mBack = (ImageView) findViewById(R.id.back);
		mBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toogle();//打开或者关闭
			}
		});
	}
	
	
	protected void toogle() {
		if (isOpen) {
			//关闭
			close();
		} else {
			//打开
			open();
		}
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		measureChildren(widthMeasureSpec, heightMeasureSpec);//测量孩子
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		mLeftChild = getChildAt(0);
		mRightChild = getChildAt(1);
		//布局左边的孩子
		mLeftChild.layout(-mLeftChild.getMeasuredWidth(), 0, 0, mLeftChild.getMeasuredHeight());
		//布局右边孩子
		mRightChild.layout(0, 0, mRightChild.getMeasuredWidth(), mRightChild.getMeasuredHeight());
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownX = event.getX(); //event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			float moveX = event.getX();
			float diffX = mDownX - moveX;//期望要发生的滚动偏移量
			//滚动偏移量的范围 0 到 -左边孩子的宽度
			//最终的滚动偏移量 = 当前的滚动偏移量 + diffX
			float finalScrollX = getScrollX() + diffX;
			if (finalScrollX > 0) {
				scrollTo(0, 0);
				return true;
			} else if (finalScrollX < -mLeftChild.getMeasuredWidth()) {
				scrollTo(-mLeftChild.getMeasuredWidth(), 0);
				return true;
			}
			
			//滚动
			scrollBy((int) diffX, 0);
			//更新down的位置
			mDownX = moveX;
			break;
		case MotionEvent.ACTION_UP:
			//滚动偏移量的临界点
			float threshold = -mLeftChild.getWidth() / 2;
			//如果当前的滚动偏移量小于threshold，滚出来
			if (getScrollX() < threshold) {
				//滚粗来
//				scrollTo(-mLeftChild.getWidth(), 0);
//				int startX = getScrollX();
//				int startY = 0;
//				int endX = -mLeftChild.getWidth();
//				int dx = endX - startX;
//				int dy = 0;
//				int duration = 1000;
//				mScroller.startScroll(startX, startY, dx, dy, duration);
//				//触发重新的绘制
//				invalidate();
				open();
			} else {
//				scrollTo(0, 0);
//				smoothScrollTo(0);
				close();
			}
			break;
		}
		return true;//表示消费事件
	}


	private void open() {
		smoothScrollTo(-mLeftChild.getWidth());
		//更新标记
		isOpen = true;
	}
	
	public void close() {
		smoothScrollTo(0);
		//更新标记
		isOpen = false;
	}


	private void smoothScrollTo(int endX) {
		int startX = getScrollX();
		int startY = 0;
//		int endX = 0;
		int dx = endX - startX;
		int dy = 0;
		int duration = 500;
		mScroller.startScroll(startX, startY, dx, dy, duration);
		//触发重新的绘制
		invalidate();
	}
	
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), 0);
//			invalidate();
			ViewCompat.postInvalidateOnAnimation(this);//兼容性问题
		}
	}
	
	/**
	 * 有选择的拦截事件
	 * 
	 * 如果左右滑动，将事件拦截下来， 侧滑菜单自己处理  如果dx>dy 判定左右滑动
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

}
