package com.example.slidelock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

public class LockView extends View {

	private static final String TAG = null;
	private Bitmap mSlidingButton;
	private Paint mPaint;
	private float mDownX;
	private float mMax;
	
	//实现平滑滚动的工具类
	private Scroller mScroller;
	private OnUnlockListener mOnUnlockListener;

	public LockView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mSlidingButton = BitmapFactory
				.decodeResource(getResources(), R.mipmap.switch_button);
		//step1 初始化
		mScroller = new Scroller(context);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//高度为滑块高度，宽度可以沿用父容器的期望
		int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
		int measuredHeight = mSlidingButton.getHeight();
		setMeasuredDimension(measuredWidth, measuredHeight);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(mSlidingButton, 0, 0, mPaint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		//1. 点击滑块，滑块中线滚动到鼠标下面
		case MotionEvent.ACTION_DOWN:
			mDownX = event.getX();
			//2. 点击滑块外面，不会滚动
			if (mDownX > mSlidingButton.getWidth()) {
				return false;
			}
			
			
			float x1 = mDownX - mSlidingButton.getWidth() / 2;
			float scrollX1 = -x1;
			//如果滚动偏移量大于0，向左边滚出界
			if (scrollX1 > 0) {
				scrollX1 = 0;
			}
			scrollTo((int) scrollX1, 0);
			break;
		case MotionEvent.ACTION_MOVE:
			float moveX = event.getX();
			float x2 = moveX - mSlidingButton.getWidth() / 2;
//			mMax = getWidth() - mSlidingButton.getWidth();
			if (x2 > mMax) {
				x2 = mMax;
			} else if (x2 < 0){
				x2 = 0;
			}
			float scrollX2 = -x2;
			scrollTo((int) scrollX2, 0);
			break;
		case MotionEvent.ACTION_UP:
			float upX = event.getX();
			float x3 = upX - mSlidingButton.getWidth() / 2;
			//4. 手指松开时，如果滑块没有滑到最右边，滚回去，如果滑到最右边，解锁
			if (x3 < mMax) {
//				scrollTo(0, 0);//滚回到原点
				//平滑滚动到原点
				int startX = getScrollX();//起始点的滚动偏移量
				int startY = 0;
				int endX = 0;//终点的滚动偏移量
				int dx = endX - startX;
				int dy = 0;
				int duration = 500;//滚动的时长
				mScroller.startScroll(startX, startY, dx, dy, duration);
				//触发重新绘制
				invalidate();
			} else {
				//滑动解锁
				if (mOnUnlockListener != null) {
					mOnUnlockListener.onUnlock();
				}
			}
			break;

		}
		return true;//消费事件
	}
	
	/**
	 * 触发重新绘制的时候会触发computeScroll，计算滚动
	 */
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			//滚动到当前应该在的滚动偏移量
			scrollTo(mScroller.getCurrX(), 0);
			//再次触发重新绘制
			invalidate();
		}
	}
	
	public interface OnUnlockListener{
		void onUnlock();
	}
	
	public void setUnlockListener(OnUnlockListener l) {
		mOnUnlockListener = l;
	}
	
	/**
	 * 当控件大小发生变化时候
	 * 布局之后会调用到
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mMax = getWidth() - mSlidingButton.getWidth();
	}

}
