package com.example.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Banner extends RelativeLayout {
	
	protected static final String TAG = "MainActivity";
	private static final int LOOP_INTERVAL = 1000;
	private ViewPager mViewPager;
	private TextView mTitle;
	private int[] mImages = {R.mipmap.news1, R.mipmap.news2, R.mipmap.news3,
			R.mipmap.news4};
	
	private String[] mTitles = {"a", "b", "c", "d"};
	
	private LinearLayout mDotsContainer;
	
	private int mLastSelectedPosition;//标记上次选中点的位置


	private int mDotSize;

	public Banner(Context context, AttributeSet attrs) {
		super(context, attrs);
		//attrs布局文件中对自定义控件所有属性
		//从所有的属性中获取自定义属性的集合
		TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.BannerStyle);
	    mDotSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.BannerStyle_dot_size, 5);
	    Log.d(TAG, "dot size from attr " + mDotSize );
	    obtainStyledAttributes.recycle();//回收属性
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.banner, this);
		initView();
        initData();
        initEvent();
	}
	

    private void initEvent() {
    	mViewPager.setOnPageChangeListener(mOnPageChangeListener);
		mViewPager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						stopLoop();
						break;
					case MotionEvent.ACTION_UP:
						startLoop();
						break;
				}
				return true;
			}
		});
	}
    
    private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
		
    	/**
    	 * 当页面被选中时候的回调
    	 * 
    	 * @param position 选中页面的下标
    	 */
		@Override
		public void onPageSelected(int position) {
			
			position = position % mImages.length;// 4 % 4 = 0, 5%4 = 1 .... 8%4 =0

			
//			Log.d(TAG, "onPageSelected " + position);
			mTitle.setText(mTitles[position]);
			
			//将当前页面的点变红
			View dot = mDotsContainer.getChildAt(position);
			dot.setBackgroundResource(R.drawable.dot_selected);
			
			//将上次选中的点变白
			View preDot = mDotsContainer.getChildAt(mLastSelectedPosition);
			preDot.setBackgroundResource(R.drawable.dot_normal);
			
			//更新上次选中点的位置
			mLastSelectedPosition = position;
		}
		
		/**
		 * 当页面滚动时候的回调
		 * 
		 * @param position 滚动页面的下标
		 * @param positionOffset 滚动页面偏移像素点的个数/viewpager的宽度
		 * @param positionOffsetPixels 滚动页面偏移像素点的个数
		 */
		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
//			Log.d(TAG, "position " + position + " " + positionOffset + " " + positionOffsetPixels);
		}
		
		/**
		 * 页面滚动状态变化的回调
		 */
		@Override
		public void onPageScrollStateChanged(int state) {
			//public static final int SCROLL_STATE_IDLE = 0;空闲状态
			//public static final int SCROLL_STATE_DRAGGING = 1;拖拽状态
			//public static final int SCROLL_STATE_SETTLING = 2;设置状态
			Log.d(TAG, "state " + state);
		}
	};


	private void initData() {
    	//ListView - > setAdapter -> 创建数据集合
    	mViewPager.setAdapter(mPagerAdapter);
//    	mViewPager.setOffscreenPageLimit(2);//设置左右两边各缓存页面的个数
//    	mViewPager.addView(new TextView(context));
    	//初始化标题
    	mTitle.setText(mTitles[0]);
    	
    	int dotSize = getResources().getDimensionPixelSize(R.dimen.dot_size);
    	Log.d(TAG, "dot size " + dotSize);
    	//初始化点
    	for(int i = 0; i < mImages.length; i++) {
    		//创建点
    		View dot = new View(getContext());
    
    		//创建布局参数
//    		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dotSize, dotSize);
    		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mDotSize, mDotSize);

    		//最后一个点不要margin
    		if (i != mImages.length - 1) {
        		layoutParams.rightMargin = 5;
    		}
    		
    		dot.setLayoutParams(layoutParams);
    		
    		//第一个点默认选中
    		if (i == 0) {
        		dot.setBackgroundResource(R.drawable.dot_selected);
    		} else {
    			dot.setBackgroundResource(R.drawable.dot_normal);
    		}
    		
    		//将点添加到LinearLayout里面
    		mDotsContainer.addView(dot);
    	}
    	int initPosition = Integer.MAX_VALUE / 2;//初始化viewpager的页面的位置
    	//调整初始化位置
    	initPosition = initPosition - initPosition % mImages.length;
    	mViewPager.setCurrentItem(initPosition);
	}


	private void initView() {
    	mViewPager = (ViewPager) findViewById(R.id.vp);
    	mTitle = (TextView)findViewById(R.id.title);
    	mDotsContainer = (LinearLayout)findViewById(R.id.dots_container);

	}
	
	private PagerAdapter mPagerAdapter = new PagerAdapter() {
		
		/**
		 * 判断view是否被标记
		 * 
		 * @param view ViewPager的孩子
		 * @param object 页面的标记
		 */
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		
		/**
		 * 返回页面的个数
		 */
		@Override
		public int getCount() {
//			return mImages.length;
			return Integer.MAX_VALUE;
		}
		
		/**
		 * 初始化页面
		 * 
		 * @param container 这里就是ViewPager
		 * @param position 加载页面的下标
		 * 
		 * @return 返回页面的标记
		 */
		public Object instantiateItem(android.view.ViewGroup container, int position) {
//			Log.d(TAG, "instantiateItem " + position);
			position = position % mImages.length;// 4 % 4 = 0, 5%4 = 1 .... 8%4 =0
			//将对应位置的页面添加到ViewPager里面来
			ImageView page = new ImageView(getContext());
			//设置图片
			page.setImageResource(mImages[position]);
			//设置缩放
			page.setScaleType(ScaleType.FIT_XY);
			
			container.addView(page);
			//返回标记
			return page;
			
		};
		
		/**
		 * 销毁页面
		 * 
		 * @param object 移除页面的标记
		 */
		public void destroyItem(android.view.ViewGroup container, int position, Object object) {
//			Log.d(TAG, "position " + position);
			//将要销毁的页面从viewpager移除
			container.removeView((ImageView)object);
		};
	};


	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		startLoop();
	}

	private Runnable mTicker = new Runnable() {
		@Override
		public void run() {
			int next = mViewPager.getCurrentItem() + 1;
			mViewPager.setCurrentItem(next);
			startLoop();
		}
	};

	private void startLoop() {
		postDelayed(mTicker, LOOP_INTERVAL);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		stopLoop();
	}

	private void stopLoop() {
		removeCallbacks(mTicker);
	}
}
