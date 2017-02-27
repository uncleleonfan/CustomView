package com.example.viewdraghelperdemo;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * Created by Leon on 2017/2/25.
 */

public class DragLayout extends FrameLayout {

    private static final String TAG = "DragLayout";

    private ViewDragHelper mViewDragHelper;
    private View mCover;
    private View mBottomBar;
    private View mImageCover;

    private int mCoverImageInitX;
    private int mCoverImageInitY;

    private int mCoverImagePivotX;
    private int mCoverImagePivotY;

    private int mMaxTop;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, mCallback);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mCoverImageInitX = mImageCover.getLeft();
                mCoverImageInitY = mImageCover.getTop();
                mMaxTop = getHeight() - mBottomBar.getHeight();
            }
        });
        mCoverImagePivotX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        mCoverImagePivotY = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
    }

    @Override
    protected void onFinishInflate() {
        mCover = findViewById(R.id.cover);
        mBottomBar = findViewById(R.id.bottom_bar);
        mImageCover = findViewById(R.id.image_cover);
    }


    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mCover;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            int max = getHeight() - mBottomBar.getHeight();
/*            if (top > max) {
                return max;
            } else if(top <= 0) {
                return 0;
            }*/
//            return top;
            return Math.min(Math.max(0, top), max);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            float rate = top * 1.0f / mMaxTop;
            mImageCover.setTranslationX(rate * - mCoverImageInitX);
            mImageCover.setTranslationY(rate * - mCoverImageInitY);
            mImageCover.setPivotX(mCoverImagePivotX);
            mImageCover.setPivotY(mCoverImagePivotY);

            mImageCover.setScaleX(1 - rate * 3/4);
            mImageCover.setScaleY(1 - rate * 3/4);

            Log.d(TAG, "onViewPositionChanged: " + mImageCover.getPivotX() + " " + mImageCover.getPivotY());

            mBottomBar.setTranslationY(-top);
            mBottomBar.setScaleX(1 - rate * 2 / 5);//[1, 1/4]
            mBottomBar.setScaleY(1 - rate * 2 / 5);//[1, 1/4]
            mBottomBar.setPivotX(mBottomBar.getWidth());
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (mCover.getTop() > mMaxTop / 2) {
                mViewDragHelper.smoothSlideViewTo(releasedChild, 0, mMaxTop);
            } else {
                mViewDragHelper.smoothSlideViewTo(releasedChild, 0, 0);
            }
            invalidate();
        }
    };

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
}
