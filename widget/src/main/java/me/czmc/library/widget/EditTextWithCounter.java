package me.czmc.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;

import me.czmc.library.utils.DisplayUtil;

/**
 * Created by czmc on 2016/7/4.
 * 带有统计字数和限制字数的编辑框
 */

public class EditTextWithCounter extends EditText {
    private Paint mCounterPaint;
    private int mCurrentTextLength;

    public int getmLimit() {
        return mLimit;
    }

    public void setmLimit(int mLimit) {
        this.mLimit = mLimit;
    }

    public int getmCurrentTextLength() {
        return mCurrentTextLength;
    }

    public void setmCurrentTextLength(int mCurrentTextLength) {
        this.mCurrentTextLength = mCurrentTextLength;
    }

    public int getmCounterTextColor() {
        return mCounterTextColor;
    }

    public void setmCounterTextColor(int mCounterTextColor) {
        this.mCounterTextColor = mCounterTextColor;
    }

    public int getmCounterTextSize() {
        return mCounterTextSize;
    }

    public void setmCounterTextSize(int mCounterTextSize) {
        this.mCounterTextSize = mCounterTextSize;
    }

    private int mCounterTextColor;
    private int mCounterTextSize;
    private int mLimit;
    private int defaultPadding = DisplayUtil.dip2px(getContext(),5);
//    private int mLine;
    private String mContent="0";
    public int MAXLINES = 2;

    public EditTextWithCounter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public EditTextWithCounter(Context context) {
        super(context);
        init(null);
    }

    public EditTextWithCounter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        TypedArray ta = getResources().obtainAttributes(attrs, R.styleable.EditTextWithCounter);
        mCounterTextColor = ta.getColor(R.styleable.EditTextWithCounter_counterColor, Color.GRAY);
        mCounterTextSize = ta.getColor(R.styleable.EditTextWithCounter_counterSize, DisplayUtil.dip2px(getContext(), 16));
        mLimit = ta.getInt(R.styleable.EditTextWithCounter_limit, -1);
//        mLine = ta.getInt(R.styleable.EditTextWithCounter_line, -1);
        ta.recycle();

        setGravity(Gravity.START);
        mCounterPaint = new Paint();
        mCounterPaint.setColor(mCounterTextColor);
        mCounterPaint.setTextSize(mCounterTextSize);
        mCounterPaint.setAntiAlias(true);
        setPadding(defaultPadding,defaultPadding,defaultPadding,defaultPadding+(int)mCounterPaint.getTextSize());
        if(mLimit>=0){
            mContent="0/"+mLimit;
        }
        setBackgroundDrawable(getResources().getDrawable(R.drawable.white_card_state));
        initEvent();
    }

    private void initEvent() {
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCurrentTextLength = s.length();
//                if (mLine != -1) {
//                    if (getLineCount() > mLine) {
//                        String str = s.toString();
//                        int cursorStart = getSelectionStart();
//                        int cursorEnd = getSelectionEnd();
//                        if (cursorStart == cursorEnd && cursorStart < str.length() && cursorStart >= 1) {
//                            str = str.substring(0, cursorStart - 1) + str.substring(cursorStart);
//                        } else {
//                            str = str.substring(0, s.length() - 1);
//                        }
//                        // setText会触发afterTextChanged的递归
//                        setText(str);
//                        // setSelection用的索引不能使用str.length()否则会越界
//                        setSelection(getText().length());
//                    }
//                }
                if (mLimit != -1) {
                    if (s.length() > mLimit) {
                        String str = s.toString();
                        int cursorStart = getSelectionStart();
                        int cursorEnd = getSelectionEnd();
                        if (cursorStart == cursorEnd && cursorStart < str.length() && cursorStart >= 1) {
                            str = str.substring(0, cursorStart - 1) + str.substring(cursorStart);
                        } else {
                            str = str.substring(0, s.length() - 1);
                        }
                        // setText会触发afterTextChanged的递归
                        setText(str);
                        // setSelection用的索引不能使用str.length()否则会越界
                        setSelection(getText().length());
                    }
                    mContent=mCurrentTextLength+"/"+mLimit;
                }
                else {
                    mContent=mCurrentTextLength+"";
                }
                postInvalidate();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(mContent, getRight() - mCounterPaint.getTextSize() / 2 * mContent.length() - getPaddingRight(), getHeight()  - getPaddingBottom()+mCounterPaint.getTextSize(), mCounterPaint);
    }

}
