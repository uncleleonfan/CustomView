package com.example.progressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Leon on 2017/2/25.
 */

public class ProgressBar extends View {

    private RectF mRectF = new RectF();
    private Paint mPaint;
    private int mProgress;

    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int progressLeft = 10;
        int progressTop = 10;
        int progressRight = w - 10;
        int progressBottom = h - 10;
        mRectF.set(progressLeft, progressTop, progressRight, progressBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int sweepAngle = (int) (mProgress * 1.0f / 100 * 360);
        canvas.drawArc(mRectF, -90, sweepAngle, false, mPaint);
    }

    public void setProgress(int progress) {
        mProgress = progress;
//        invalidate();//触发重新绘制--> onDraw
        postInvalidate();//触发在主线程调用 invalidate
    }
}
