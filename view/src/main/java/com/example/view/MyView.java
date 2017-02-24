package com.example.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Leon on 2017/2/24.
 */

public class MyView extends View {

    private static final String TAG = "MyView";

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
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
}
