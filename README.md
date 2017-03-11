# 自定义控件（View）#
* 什么是自定义控件?
	1. 原生控件：SDK已经有，Google提供
	2. 自定义控件: 开发者自己开发的控件
		1. 组合式控件：将原生控件组合在一起
		2. 自己绘制的控件：绘制 触摸事件的处理

* 自定义控件在工作中的运用
	
* 课程的重点和难点
  1. 控件（View）的绘制流程
  2. Touch事件处理
  3. 新api多

## 组合式控件 下拉选择框 ##
思路：先在Activity里面实现功能，再抽取成自定义控件

* 功能分析
	1. 点击箭头，弹出下拉列表 （Popupwindow + ListView）
	2. 点击列表选项，在编辑框中显示内容
	3. 点击删除按钮，删除列表选项

* 组合式自定义控件
	1. 继承布局
	2. 创建布局xml，加入到自定义控件里面
	3. 模块化思想，提高代码复用率


## View和ViewGroup ##
* View
	1. View是用户界面一个组件(控件)
	2. View是一个矩形
	3. View的职责是绘制和事件处理

* ViewGroup
	1. ViewGroup是一个特殊的View,它继承自View
	2. ViewGroup可包含其他View（孩子）
	3. ViewGroup是常用layout的基类
	4. ViewGroup定义了孩子的布局参数(带layout_前缀的属性)

* View和ViewGroup的关系
	1. 继承关系
	![icon](img/view_arc.png)

	2.	组合关系
	
    ![icon](img/viewgroup.png)

## View的绘制流程 ##

### xml的实质  ###
1. xml不是必须
2. xml是为了开发者开发布局便利，谷歌给开发者开发糖
3. xml最终还是会转成代码执行

### 控制View的大小 测量###
1. measure
2. 找出View的大小
3. measure方法调用onMeasure方法
4. 真正的测量工作是在onMeasure方法里面做的
5. onMeasure方法必须调用setMeasuredDimension来保存测量的宽和高
6. setMeasuredDimension真正决定的view测量宽高
7. mMeasuredWidth和mMeasuredHeight在setMeasuredDimension赋值，才可以完成说完成测量工作

### 控制View的位置 布局###
1. layout
2. layout四个位置left，top，right,bottom都是相对于父容器。
3. layout-->setFrame方法
4. setFrame方法对成员变量mLeft,mTop,mRight,mBottom赋值，才完成布局工作
5. layout方法被父容器调用，父容器指定孩子的left,top,right,bottom.


### View的绘制 绘制###
1. draw
2. draw-> onDraw 
3. 实现onDraw完成View的绘制

### 绘制实战 ###
1. 画直线
2. 画圆
3. 画空心圆
4. 画图片
5. 画三角形
6. 裁剪
7. 画扇形

#### 绘制案例 进度条 ####
* invalidate();//触发View的重新绘制 --》 onDraw
* postInvalidate();//触发在主线程更新ui -》主线程调用onDraw

### View绘制流程综合案例 自定义控件滑动开关 ###


## ViewGroup的绘制流程 ##
ViewGroup继承View, ViewGroup的绘制流程遵循View的绘制的流程
### ViewGroup的测量 ###
相同点:measure - > onMeasure
不同点：作为一个父容器，有责任去测量孩子,调用孩子measure方法，传入期望

### ViewGroup的布局 ###
相同点:layout(ViewGroup父容器发起布局)
不同点:作为一个父容器，有责任去布局孩子，在onLayout方法里面，调用孩子layout方法，指定孩子上下左右的位置

### ViewGroup的绘制 ###
相同点:draw -> onDraw
不同点：ViewGroup默认实现dispatchDraw方法去绘制了孩子

### getWidth和getMeasuredWidth的区别 ###
1. getMeasuredWidth：获取测量后宽高
2. getWidth：获取布局之后的宽高


### ViewGroup绘制流程综合案例 交叉布局 ##	
requestLayout();//请求重新布局--> onLayout，并不会触发重新绘制


## View的Touch事件 ##
事件序列：ACTION_DOWN,ACTION_MOVE,ACTION_UP
### View Touch事件的传递机制 ###
1. dispatchTouchEvent 分发触摸事件 return true，分发下去有view处理事件
2. onInterceptTouchEvent 拦截孩子的触摸事件 true表示拦截，默认false
3. onTouchEvent 处理触摸事件 true表示消费事件，false表示没有消费

### 事件传递流程 ###
* 父容器拿到触摸事件，默认不拦截，分发给孩子，看孩子是否消费，孩子不消费，事件有传回父容器，看父容器是否消费。
* 父容器拿到触摸事件，如果ACTION_DWON事件传递下去没有孩子消费，那么后续的事件就不会传了。
* 父容器拿到触摸事件，默认不拦截，分发给孩子，看孩子是否消费，孩子消费，事件传递结束。
* 父容器拿到触摸事件，拦截，事件不会分发给孩子，交给自己是否消费（调用父容器自己的onTouchEvent）
* 如果点击的位置没有孩子，那么即使父容器不拦截，事件也不会传递给孩子

### Activity与事件传递 ###
* Actvity先获取到Touch事件
* Activity调用getWindow().superDispatchTouchEvent(ev)分发给PhoneWindow
* PhoneWindow继续将事件分发下去
* 如果分发下去没有view消费，那就调用Activity的onTouchEvent

### View点击事件的触发和拦截 ###
1. 点击事件的触发：在View的onTouchEvent里面处理ACTION_UP时，调用performClick方法，里面回调了OnClickListner的onClick
2. 点击事件拦截:覆写onInterceptTouchEvent方法，return true.


### setOnTouchListener与 onTouchEvent的关系###
1. 先后关系：onTouch先于onTouchEvent执行
2. 如果onTouch返回true，表示消费，onTouchEvent就不会执行。

### Touch事件冲突 ###
有条件的拦截 dx > dy, 判断为左右滑动

### 滚动的处理 ###
滚动时滚动View绘制的内容，View本身是不动

* View的scrollTo(x, y)

		mScrollX = x;
		mScrollX滚动偏移量 = 起始位置 - 最终位置
		x为负数，往右边滚，x为正数，往左边

* View的scrollBy(x, y)

		scrollBy(x, y) = scrollTo(mScrollX + x, mScrollY + y);
		增量滚动，在原来的滚动偏移量上再滚动x个像素的偏移量

### 滑动解锁（案例） ###
* 功能分析
	1. 点击滑块的时候，滑块中线移动鼠标下面
	2. 点击滑块外面，不做处理
	3. 拖动滑块
	4. 滑块没有拖到最右边，则自动滚回去，如果拖到最右边，滑动解锁

## 综合案例 侧滑菜单 ##
* 常用侧滑菜单：SlidingMenu, DrawerLayout
* 功能分析
	1. 左边菜单列表布局在屏幕外面，中间内容区域(自定义ViewGroup来实现)
	2. 拖动拉出左边菜单列表
	3. 松开时要么关闭要么打开侧滑菜单
	4. 标题栏返回按钮，打开或者关闭侧滑菜单
	5. 点击菜单列表选项，关闭侧滑菜单，弹出toast

# 自定义控件案例与练习 #

## 组合式自定义控件 新闻轮播图ViewPager ##
先在Activity实现，再抽取自定义控件

* 功能分析
  	1. 滑动切换图片 (ViewPager)
  	2. 切换图片的同时，切换标题和点
  	3. 循环切换

* V4包源码关联
	1. 点右键，将v4包从build path 移除.
	2. 找libs下的v4包，点右键，添加到build path.
	3. 点ViewPager,出现attach source按钮，点击按钮，添加v4包路径
	4. 点击右键，Android Tools -> Fix Project Properties.
* ViewPager

* dimen的使用

* 自定义属性

	    <declare-styleable name = "LoopNewsView">
	        <attr name = "dot_size" format="dimension"/>
	    </declare-styleable>

## 组合式控件 优酷菜单 （难度系数 2）##
* 功能分析

## 自定义ViewGroup 滑动删除 （难度系数 3）##
[第三方组件](https://github.com/Trinea/android-open-project)

* 功能分析
	1. ListView
	2. 每个条目能滑动拖出删除按钮，点击删除

## 自定义View 黑马时钟 （难度系数 4）##
* 绘制
1. 画圆环
2. 画刻度
3. 画数字
4. 画logo
5. 画指针
6. 画背景
7. 指针动起来

## 黑马开发者 公众号 ##
>大家可订阅该公众号，直接反馈学习中遇到的问题或者困惑，欢迎大家提出对该课程的建议。
>另外，在该课程上课期间，会推送课程相关的文章，作为该课程的扩展，敬请关注！

![icon](img/heima_developer.jpg)

