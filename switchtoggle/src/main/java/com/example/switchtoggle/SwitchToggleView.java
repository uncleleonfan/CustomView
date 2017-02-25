package com.example.switchtoggle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class SwitchToggleView extends View {

	private Bitmap mSwitchBackground;
	private Paint mPaint;
	private Bitmap mSwitchButton;
	private boolean isClose = false;
	private OnSwitchListener mOnSwitchListener;

	public SwitchToggleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mSwitchBackground = BitmapFactory
				.decodeResource(getResources(), R.mipmap.switch_background);
		mSwitchButton = BitmapFactory.decodeResource(getResources(), R.mipmap.switch_button);
		
		setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//改变开关状态
				isClose = !isClose;
				//触发view的重新绘制
				invalidate();//->onDraw
				//step 4, 在事件发生的地方，通知外界
				if (mOnSwitchListener != null) {
					mOnSwitchListener.onSwitchChange(isClose);
				}
			}
		});
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//不听从父容器的期望，自己设置宽高
		//宽高为背景图片的宽高
		int measuredWidth = mSwitchBackground.getWidth();
		int measuredHeight = mSwitchBackground.getHeight();
		setMeasuredDimension(measuredWidth, measuredHeight);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//画背景图片
		canvas.drawBitmap(mSwitchBackground, 0, 0, mPaint);
		//画滑块
		if (isClose) {
			//滑动开关关闭
			canvas.drawBitmap(mSwitchButton, 0, 0, mPaint);
		} else {
			//滑动开关打开
			int left = mSwitchBackground.getWidth() - mSwitchButton.getWidth();
			canvas.drawBitmap(mSwitchButton, left, 0, mPaint);
		}
	}
	
	//step 1, 定义接口
	public interface OnSwitchListener {
		//step 2，定义接口方法，什么事件就定义什么方法
		void onSwitchChange(boolean isClose);
	}
	
	//step3 提供设置监听的方法,谁想监听事件，谁就调用该方法设置监听器
	public void setOnSwitchListener(OnSwitchListener l) {
		mOnSwitchListener = l;
	}
	

}
