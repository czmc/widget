package me.czmc.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import me.czmc.library.utils.DisplayUtil;

public class RoundProgressBarWidthCounter extends ProgressBar {
    private int mRadius = DisplayUtil.dip2px(getContext(), 30);
    private int mMaxPaintWidth;

    private int mReachedHeight = DisplayUtil.dip2px(getContext(), 5);
    private int mUnReachedHeight = DisplayUtil.dip2px(getContext(), 2);
    private float mTextSize = DisplayUtil.sp2px(getContext(), 16);
    private int mTextColor = Color.parseColor("#8194AA");
    private int mReachedColor = Color.parseColor("#8194AA");
    private int mUnReachedColor = Color.parseColor("#B2BECB");
    private boolean mTextVisible;
    private Paint mPaint = new Paint();

    public RoundProgressBarWidthCounter(Context context,AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundProgressBarWidthCounter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs,defStyleAttr);
        init(attrs);
    }
    public void init(AttributeSet attrs){
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.RoundProgressBarWithCounter);
        mRadius = (int) ta.getDimension(R.styleable.RoundProgressBarWithCounter_roundRadius, mRadius);
        ta.recycle();
        ta = getContext().obtainStyledAttributes(attrs,R.styleable.HorizontalProgressBarWithCounter);
        mReachedColor=ta.getColor(R.styleable.HorizontalProgressBarWithCounter_reachedColor,mReachedColor);
        mUnReachedColor=ta.getColor(R.styleable.HorizontalProgressBarWithCounter_unReachedColor,mUnReachedColor);
        mReachedHeight=(int)ta.getDimension(R.styleable.HorizontalProgressBarWithCounter_reachedHeight,mReachedHeight);
        mUnReachedHeight=(int)ta.getDimension(R.styleable.HorizontalProgressBarWithCounter_unReachedHeight,mUnReachedHeight);
        mTextColor=ta.getColor(R.styleable.HorizontalProgressBarWithCounter_textColor,mTextColor);
        mTextSize=ta.getDimension(R.styleable.HorizontalProgressBarWithCounter_textSize,mTextSize);
        mTextVisible = ta.getBoolean(R.styleable.HorizontalProgressBarWithCounter_textVisible, true);
        ta.recycle();
        mPaint.setStyle(Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setTextSize(mTextSize);
    }

    /**
     * 这里默认在布局中padding值要么不设置，要么全部设置
     */
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec,
                                          int heightMeasureSpec) {

        mMaxPaintWidth = Math.max(mReachedHeight,
                mUnReachedHeight);
        int expect = mRadius * 2 + mMaxPaintWidth + getPaddingLeft()
                + getPaddingRight();
        int width = resolveSize(expect, widthMeasureSpec);
        int height = resolveSize(expect, heightMeasureSpec);
        int realWidth = Math.min(width, height);
        mRadius = (realWidth - getPaddingLeft() - getPaddingRight() - mMaxPaintWidth) / 2;

        setMeasuredDimension(realWidth, realWidth);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        String text = getProgress() + "%";
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;

        canvas.save();
        canvas.translate(getPaddingLeft() + mMaxPaintWidth / 2, getPaddingTop()
                + mMaxPaintWidth / 2);
        mPaint.setStyle(Style.STROKE);
        // draw unreaded bar
        mPaint.setColor(mUnReachedColor);
        mPaint.setStrokeWidth(mUnReachedHeight);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        // draw reached bar
        mPaint.setColor(mReachedColor);
        mPaint.setStrokeWidth(mReachedHeight);
        float sweepAngle = getProgress() * 1.0f / getMax() * 360;
        canvas.drawArc(new RectF(0, 0, mRadius * 2, mRadius * 2), 0,
                sweepAngle, false, mPaint);
        if(mTextVisible) {
            mPaint.setStyle(Style.FILL);
            canvas.drawText(text, mRadius - textWidth / 2, mRadius - textHeight,
                    mPaint);
        }
        canvas.restore();
    }
}