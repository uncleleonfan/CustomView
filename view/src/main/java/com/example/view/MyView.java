package com.example.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import static android.util.TypedValue.applyDimension;

/**
 * Created by Leon on 2017/2/24.
 */

public class MyView extends View {

    private static final String TAG = "MyView";

    private Paint mPaint;
    private Bitmap mBitmap;
    private Path mPath;
    private RectF mOval;

    private int left = (int) applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
    private int top = (int) applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5,getResources().getDisplayMetrics());
    private int right = (int) applyDimension(TypedValue.COMPLEX_UNIT_DIP, 195, getResources().getDisplayMetrics());
    private int bottom = (int) applyDimension(TypedValue.COMPLEX_UNIT_DIP, 195, getResources().getDisplayMetrics());

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//去锯齿
        mPaint.setStyle(Paint.Style.STROKE);//画空心圆
        //设置画笔宽度
        mPaint.setStrokeWidth(8);
        //获取bitmap
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.haha);

        Log.d(TAG, "init: " + left + " " + top + " " + right + " " + bottom);
        mOval = new RectF(left, top, right, bottom);
        initPath();
    }

    /**
     *  初始化三角形路径
     */
    private void initPath() {
        mPath = new Path();
        //确定三个点
        int x1 = 100, y1 = 5;
        int x2 = 195, y2 = 195;
        int x3 = 5, y3 = 195;
        //将路径移动到第一个点
        mPath.moveTo(x1, y1);
        //链接第一个点和第二个点
        mPath.lineTo(x2, y2);
        //链接第二个点和第三个点
        mPath.lineTo(x3, y3);
        //链接第一个点
        mPath.lineTo(x1, y1);
    }

    /**
     * 在布局中的layout_width，layout_height表示向父容器申请的宽高
     * 父容器拿到申请的宽高之后，会打包成对孩子宽高的期望
     * 父容器会调用孩子的measure(int widthMeasureSpec, int heightMeasureSpec)
     * measure->onMeasure(int widthMeasureSpec, int heightMeasureSpec)
     *
     * @param widthMeasureSpec 父容器（这里即LinearLayout）对MyView宽度的期望 ,跟layout_width有关系
     * @param heightMeasureSpec 父容器（这里即LinearLayout）对MyView高度的期望跟layout_height有关系
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //widthMeasureSpec 32位二进制
        //前两位表示模式
        //public static final int UNSPECIFIED = 0 << MODE_SHIFT;父容器对孩子大小没有限制，孩子想多大就多大
        //public static final int EXACTLY     = 1 << MODE_SHIFT;父容器对孩子有确切的大小要求，大小就是后30位
        //public static final int AT_MOST     = 2 << MODE_SHIFT;父容器对孩子的最大值有要求，大小就是后30位
        //后30位表示大小
        //拆出模式
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        //拆出大小
        int size = MeasureSpec.getSize(widthMeasureSpec);
        Log.d(TAG, "mode " + (mode>>30) + " size" + size);
        //默认使用父容器对孩子的期望的宽高
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //onMeasure方法里面必须调用setMeasuredDimension();
        //如果特殊需求，view需要自己控制自己的宽高而不管父容器对view的期望，则调用setMeasuredDimension
        //setMeasuredDimension(50, 50);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.d(TAG, "onMeasure: ");
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        Log.d(TAG, "layout: ");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.d(TAG, "draw: ");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //		6. 裁剪
//		canvas.clipPath(mPath);
//		1. 画直线
//		int startX = 5;
//		int startY = 100;
//
//		int stopX = 195;
//		int stopY = 100;
//		canvas.drawLine(startX, startY, stopX, stopY, mPaint);
//		2. 画圆
//		int cx = 100;
//		int cy = 100;
//		int radius = 80;
//		canvas.drawCircle(cx, cy, radius, mPaint);
//		3. 画空心圆
//		4. 画图片
//		canvas.drawBitmap(mBitmap, 0, 0, mPaint);
//		5. 画三角形
//		canvas.drawPath(mPath, mPaint);
//		7. 画扇形
        int startAngle = -90;
        int sweepAngle = 45;
        boolean useCenter = false;//true 画出两边
        canvas.drawArc(mOval, startAngle, sweepAngle, useCenter, mPaint);
    }
}
