package me.czmc.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import me.czmc.library.utils.DisplayUtil;

/**
 * Created by czmc on 2016/7/11.
 */

public class HorizontalProgressBarWithCounter extends ProgressBar {
    public final String TAG = "HProgressBar";
    private int mRealWidth;
    private Paint mPaint;

    private int mReachedHeight = DisplayUtil.dip2px(getContext(),5);
    private int mUnReachedHeight = DisplayUtil.dip2px(getContext(),2);
    private float mTextSize=DisplayUtil.sp2px(getContext(),16);
    private int mTextColor=Color.parseColor("#8194AA");
    private int mTextOffset = 5;
    private int mReachedColor = Color.parseColor("#8194AA");
    private int mUnReachedColor =Color.parseColor("#B2BECB");
    private boolean mTextVisible;

    public HorizontalProgressBarWithCounter(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizontalProgressBarWithCounter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    public void init(AttributeSet attrs){
        TypedArray ta = getContext().obtainStyledAttributes(attrs,R.styleable.HorizontalProgressBarWithCounter);
            mReachedColor=ta.getColor(R.styleable.HorizontalProgressBarWithCounter_reachedColor,mReachedColor);
            mUnReachedColor=ta.getColor(R.styleable.HorizontalProgressBarWithCounter_unReachedColor,mUnReachedColor);
            mReachedHeight=(int)ta.getDimension(R.styleable.HorizontalProgressBarWithCounter_reachedHeight,mReachedHeight);
            mUnReachedHeight=(int)ta.getDimension(R.styleable.HorizontalProgressBarWithCounter_unReachedHeight,mUnReachedHeight);
            mTextColor=ta.getColor(R.styleable.HorizontalProgressBarWithCounter_textColor,mTextColor);
            mTextSize=(int)ta.getDimension(R.styleable.HorizontalProgressBarWithCounter_textSize,mTextSize);
            mTextOffset=(int)ta.getDimension(R.styleable.HorizontalProgressBarWithCounter_textOffset,5);
            mTextVisible = ta.getBoolean(R.styleable.HorizontalProgressBarWithCounter_textVisible, true);
        ta.recycle();
        mPaint=new Paint();
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);

    }
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
        mRealWidth = getMeasuredWidth() - getPaddingRight() - getPaddingLeft();

    }

    private int measureHeight(int measureSpec)
    {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY)
        {
            result = specSize;
        } else
        {
            float textHeight = (mPaint.descent() - mPaint.ascent());
            result = (int) (getPaddingTop() + getPaddingBottom() + Math.max(
                    Math.max(mReachedHeight, mUnReachedHeight), Math.abs(textHeight)));
            if (specMode == MeasureSpec.AT_MOST)
            {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        //move canvas to X padding left  and Y center
        canvas.translate(getPaddingLeft(), getHeight() / 2);
        boolean isFinish = false;
        float radio = getProgress() * 1.0f / getMax();
        float progressPosX = radio*mRealWidth;
        String progressStr = getProgress() +"%";
        float textWidth = 0;
        float textHeight = 0;
        if(mTextVisible){
            textWidth=mPaint.measureText(progressStr);
            textHeight=(mPaint.descent() + mPaint.ascent()) / 2;
        }else {
            mTextOffset=0;
        }
        if(progressPosX+textWidth>mRealWidth){
            progressPosX=mRealWidth-(int)textWidth;
            isFinish=true;
        }
        float endX = progressPosX - mTextOffset / 2;
        if (endX > 0)
        {
            mPaint.setColor(mReachedColor);
            mPaint.setStrokeWidth(mReachedHeight);
            canvas.drawLine(0, 0, endX, 0, mPaint);
        }
        if (mTextVisible)
        {
            mPaint.setColor(mTextColor);
            mPaint.setAntiAlias(true);
            canvas.drawText(progressStr, progressPosX, -textHeight, mPaint);
        }
        if(!isFinish) {
            float start = progressPosX + mTextOffset / 2 + textWidth;
            mPaint.setColor(mUnReachedColor);
            mPaint.setStrokeWidth(mUnReachedHeight);
            canvas.drawLine(start, 0, mRealWidth, 0, mPaint);
            canvas.restore();
        }
    }
}
