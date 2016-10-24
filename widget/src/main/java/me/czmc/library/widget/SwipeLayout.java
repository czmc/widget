package me.czmc.library.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @author czmc
 */
public class SwipeLayout extends FrameLayout {
    private ViewDragHelper mDragHelper;
    private View mBackView;
    private View mFrontView;
    private int mHeight;
    private int mWidth;
    private int mRange;
    private boolean enableSwipe = true;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//第一步 初始化ViewDragHelper
        mDragHelper = ViewDragHelper.create(this, mCallback);
    }

    ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //在这里处理放置的逻辑拖拽的前view
            if (child == mFrontView) {
                if (left > 0) {
                    return 0;
                } else if (left < -mRange) {
                    return -mRange;
                }
            }
            //拖拽的后view
            else if (child == mBackView) {
                if (left > mWidth) {
                    return mWidth;
                } else if (left < mWidth - mRange) {
                    return mWidth - mRange;
                }
            }
            return left;
        }
        /**
         * 当view位置改变的时候
         * @param changedView 改变的view
         * @param left
         * @param top
         * @param dx x轴偏移量
         * @param dy
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            //传递事件，如果是拖拽的前view，
            if (changedView == mFrontView){
            //Offset this view's horizontal location by the specified amount of pixels.
            //也就是说我的我的前view左滑了dx，那么我的后view也是左滑dx，右滑同理
                mBackView.offsetLeftAndRight(dx);
            } else if (changedView == mBackView){
            //拖拽的是后view的话，前View的处理方式一样
                mFrontView.offsetLeftAndRight(dx);
            }
            //兼容老版本
            invalidate();
            dispatchSwipeEvent();
        }
        /**
         * 拖拽的view释放的时候
         *
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (xvel == 0 && mFrontView.getLeft() < -mRange / 2.0f) {
                open();
            } else if (xvel < 0) {
                open();
            } else {
                close();
            }
        }
    };
    public void close() {
        close(true);
    }
    /**
     * 关闭
     *
     * @param isSmooth
     */
    public void close(boolean isSmooth) {
        int finalLeft = 0;
        if (isSmooth) {
//开始动画 如果返回true表示没有完成动画
            if (mDragHelper.smoothSlideViewTo(mFrontView, finalLeft, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layoutContent(false);
        }
    }
    public void open() {
        open(true);
    }
    /**
     * 打开
     *
     * @param isSmooth
     */
    public void open(boolean isSmooth) {
        int finalLeft = -mRange;
        if (isSmooth) {
//开始动画
            if (mDragHelper.smoothSlideViewTo(mFrontView, finalLeft, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layoutContent(true);
        }
    }
    /**
     * 持续动画
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
//这个是固定的
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
    /**
     * 摆放位置
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        layoutContent(false);
    }
    private void layoutContent(boolean isOpen) {
//摆放前view
        Rect frontRect = computeFrontViewRect(isOpen);
        mFrontView.layout(frontRect.left, frontRect.top, frontRect.right, frontRect.bottom);
//摆放后view
        Rect backRect = computeBackViewRect(frontRect);
        mBackView.layout(backRect.left,backRect.top,backRect.right,backRect.bottom);
//前置前view
        bringChildToFront(mFrontView);
    }
    /**
     * 我们可以把前view相当于一个矩形
     *
     * @param frontRect
     * @return
     */
    private Rect computeBackViewRect(Rect frontRect) {
        int left = frontRect.right;
        return new Rect(left, 0, left + mRange, 0 + mHeight);
    }
    private Rect computeFrontViewRect(boolean isOpen) {
        int left = 0;
        if (isOpen) {
            left = -mRange;
        }
        return new Rect(left, 0, left + mWidth, 0 + mHeight);
    }

    /**
     * 当xml填充完毕的时候
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
/**
 * 后view
 */
        mBackView = getChildAt(0);
/**
 * 前view
 */
        mFrontView = getChildAt(1);
    }

    /**
     * 在这里获取宽和高
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
/**
 * 高度
 */
        mHeight = mFrontView.getMeasuredHeight();
/**
 * 宽度
 */
        mWidth = mFrontView.getMeasuredWidth();
/**
 * 移动距离
 */
        mRange = mBackView.getMeasuredWidth();
    }

    /**
     * 传递触摸事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(enableSwipe) {
            return mDragHelper.shouldInterceptTouchEvent(ev);
        }else {
            return false;
        }
    }
    float x=0;
    float y=0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            if(enableSwipe) {
                mDragHelper.processTouchEvent(event);
                if(event.getAction()== MotionEvent.ACTION_DOWN){
                    x=event.getX();
                    y=event.getY();
                }
                if(status!= Status.Draging && status!= Status.Open
                        && event.getY()==y && event.getX()==x
                        && event.getAction()== MotionEvent.ACTION_UP){
                    if(mOnClickAction!=null){
                        mOnClickAction.onClick(this);
                    }
                }
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public void setEnableSwipe(boolean enableSwipe){
        this.enableSwipe = enableSwipe;
    }
    /**
     * 默认状态是关闭
     */
    private Status status = Status.Close;
    private OnSwipeLayoutListener swipeLayoutListener;
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public OnSwipeLayoutListener getSwipeLayoutListener() {
        return swipeLayoutListener;
    }
    public void setSwipeLayoutListener(OnSwipeLayoutListener swipeLayoutListener) {
        this.swipeLayoutListener = swipeLayoutListener;
    }
    /**
     * 定义三种状态
     */
    public enum Status {
        Close, Open, Draging
    }

    /**
     * 定义回调接口 这个在我们
     */
    public interface OnSwipeLayoutListener {
        /**
         * 关闭
         *
         * @param mSwipeLayout
         */
        void onClose(SwipeLayout mSwipeLayout);
        /**
         * 打开
         *
         * @param mSwipeLayout
         */
        void onOpen(SwipeLayout mSwipeLayout);
        /**
         * 绘制
         *
         * @param mSwipeLayout
         */
        void onDraging(SwipeLayout mSwipeLayout);
        /**
         * 要去关闭
         */
        void onStartClose(SwipeLayout mSwipeLayout);
        /**
         * 要去开启
         */
        void onStartOpen(SwipeLayout mSwipeLayout);
    }

    public OnClickAction mOnClickAction;
    protected void dispatchSwipeEvent() {
//判断是否为空
        if (swipeLayoutListener != null) {
            swipeLayoutListener.onDraging(this);
        }
// 记录上一次的状态
        Status preStatus = status;
// 更新当前状态
        status = updateStatus();
        if (preStatus != status && swipeLayoutListener != null) {
            if (status == Status.Close) {
                swipeLayoutListener.onClose(this);
            } else if (status == Status.Open) {
                swipeLayoutListener.onOpen(this);
            } else if (status == Status.Draging) {
                if (preStatus == Status.Close) {
                    swipeLayoutListener.onStartOpen(this);
                } else if (preStatus == Status.Open) {
                    swipeLayoutListener.onStartClose(this);
                }
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(!enableSwipe) {
            if (ev.getAction()== MotionEvent.ACTION_DOWN) {
                mOnClickAction.onClick(this);
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    public void setOnClickAction(OnClickAction mOnClickAction){
        this.mOnClickAction= mOnClickAction;
    }
    public  interface OnClickAction{
        void onClick(View view);
    }

    /**
     * 更新状态
     *
     * @return
     */
    private Status updateStatus() {
//得到前view的左边位置
        int left = mFrontView.getLeft();
        if (left == 0) {
//如果位置是0，就是关闭状态
            return Status.Close;
        } else if (left == -mRange) {
//如果左侧边距是后view的宽度的负值，状态为开
            return Status.Open;
        }
//其他状态就是拖拽
        return Status.Draging;
    }

}