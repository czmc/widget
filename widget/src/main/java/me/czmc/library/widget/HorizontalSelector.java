package me.czmc.library.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.IntRange;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.czmc.library.utils.DisplayUtil;

/**
 * Created by czmc on 2016/7/27.
 */

public class HorizontalSelector extends HorizontalScrollView implements View.OnClickListener {
    public final String TAG = "HorizontalWheelView";
    private LinearLayout views;
    private int initialX = 0;
    private float textSize = 15;
    private int itemWidth;
    private int displayItemCount = 5;
    private ArrayList<String> items = new ArrayList<>();
    private int offset = 4;
    private int selectedIndex = 0;
    private int mScreenWidth;
    private int textColorFocus = Color.parseColor("#ff0000");
    private int textColorNormal = Color.parseColor("#000000");
    private float previousX;
    private boolean isUserScroll = false;
    private Runnable scrollerTask = new ScrollerTask();
    private OnSelectedCallBack callback;
    private Paint mIndicatorPaint;
    private int mIndicatorWidth = 50;

    public HorizontalSelector(Context context) {
        super(context);
        init(null);
    }

    public HorizontalSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public HorizontalSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getResources().obtainAttributes(attrs, R.styleable.HorizomtalSelector);
        textColorFocus = ta.getColor(R.styleable.HorizomtalSelector_textColorFocus, textColorFocus);
        textColorNormal = ta.getColor(R.styleable.HorizomtalSelector_textColorNormal, textColorNormal);
        textSize = DisplayUtil.px2sp(getContext(),ta.getDimension(R.styleable.HorizomtalSelector_itemTextSize, textSize));
        displayItemCount = ta.getInt(R.styleable.HorizomtalSelector_displayItemCount, displayItemCount);
        mIndicatorWidth=(int)ta.getDimension(R.styleable.HorizomtalSelector_indicatorSize,mIndicatorWidth);
        ta.recycle();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mScreenWidth = wm.getDefaultDisplay().getWidth();
        setFadingEdgeLength(0);
        if (Build.VERSION.SDK_INT >= 9) {
            setOverScrollMode(OVER_SCROLL_NEVER);
        }
        setHorizontalScrollBarEnabled(false);
        views = new LinearLayout(getContext());
        views.setOrientation(LinearLayout.HORIZONTAL);
        addView(views);
        mIndicatorPaint = new Paint();
        mIndicatorPaint.setColor(textColorFocus);
        mIndicatorPaint.setStrokeWidth(10);

    }

    private void _setItems(List<String> list) {
        items.clear();
        items.addAll(list);

        // 前面和后面补全
        for (int i = 0; i < offset; i++) {
            items.add("");
        }
        for (int i = 0; i < displayItemCount - offset - 1; i++) {
            items.add(0, "");
        }
        initData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        int x = (displayItemCount - offset - 1) * itemWidth + itemWidth / 2 + getScrollX();
        canvas.drawLine(x - mIndicatorWidth / 2, getMeasuredHeight(), x + mIndicatorWidth / 2, getMeasuredHeight(), mIndicatorPaint);
        canvas.restore();
    }

    public void setItems(List<String> list, int index) {
        _setItems(list);
        setSelectedIndex(index);
    }

    private void initData() {
        views.removeAllViews();
        int i = 0;
        for (String item : items) {
            views.addView(createView(item, i));
            i++;
        }
        // 2016/1/15 焦点文字颜色高亮位置，逆推“int position = y / itemHeight + offset”
        refreshItemView(itemWidth * selectedIndex);
    }

    private TextView createView(String item, int position) {
        TextView tv = new TextView(getContext());
        tv.setSingleLine(true);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setText(item);
        tv.setId(position);
        tv.setClickable(true);
        tv.setTextSize(textSize);
        tv.setGravity(Gravity.CENTER);
        tv.setOnClickListener(this);
        int padding = DisplayUtil.dip2px(getContext(), 6);
        tv.setPadding(padding, padding, padding, padding);
        itemWidth = mScreenWidth / displayItemCount;
        views.setLayoutParams(new LayoutParams(itemWidth * displayItemCount, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.getLayoutParams();
        setLayoutParams(new LinearLayout.LayoutParams(itemWidth * displayItemCount, lp.height));
        tv.setLayoutParams(new LayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
        return tv;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        refreshItemView(l);
    }

    private void refreshItemView(int x) {
        int position = x / itemWidth;
        int remainder = x % itemWidth;
        int divided = x / itemWidth;

        if (remainder == 0) {
            position = divided + displayItemCount - offset - 1;
        } else {
            if (remainder > itemWidth / 2) {
                position = divided + displayItemCount - offset;
            }
        }

        int childSize = views.getChildCount();
        for (int i = 0; i < childSize; i++) {
            TextView itemView = (TextView) views.getChildAt(i);
            if (null == itemView) {
                return;
            }
            // 2015/12/15 可设置颜色
            if (position == i) {
                itemView.setTextColor(textColorFocus);
            } else {
                itemView.setTextColor(textColorNormal);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                previousX = ev.getX();
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, String.format("items=%s, offset=%s", items.size(), offset));
                Log.i(TAG, "selectedIndex=" + selectedIndex);
                float delta = ev.getX() - previousX;
                Log.i(TAG, "delta=" + delta);
                if (selectedIndex == displayItemCount - offset - 1 && delta < 0) {
                    //滑动到第一项时，若继续向下滑动，则自动跳到最后一项
                    setSelectedIndex(items.size() - 1);
                } else if (selectedIndex == items.size() - 1 && delta > 0) {
                    //滑动到最后一项时，若继续向上滑动，则自动跳到第一项
                    setSelectedIndex(0);
                } else {
                    isUserScroll = true;
                    startScrollerTask();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void startScrollerTask() {
        initialX = getScrollX();
        postDelayed(scrollerTask, 30);
    }

    /**
     * 从0开始计数，所有项包括偏移量
     *
     * @param index
     */
    private void setSelectedIndex(@IntRange(from = 0) final int index) {
        isUserScroll = false;
        this.post(new Runnable() {
            @Override
            public void run() {
                //滚动到选中项的位置
                smoothScrollTo(index * itemWidth, 0);
                //选中这一项的值
                selectedIndex = index;
                callBack();
            }
        });
    }

    @Override
    public void onClick(View v) {
        setSelectedIndex(v.getId());
    }

    private class ScrollerTask implements Runnable {
        @Override
        public void run() {
            // 2015/12/17 java.lang.ArithmeticException: divide by zero
            if (itemWidth == 0) {
                Log.i(TAG, "itemHeight is zero");
                return;
            }
            int newX = getScrollX();
            if (initialX - newX == 0) { // stopped
                final int remainder = initialX % itemWidth;
                final int divided = initialX / itemWidth;
                Log.i(TAG, "initialX: " + initialX + ", remainder: " + remainder + ", divided: " + divided);
                if (remainder == 0) {
                    selectedIndex = divided + displayItemCount - offset - 1;
                    callBack();
                } else {
                    if (remainder > itemWidth / 2) {
                        post(new Runnable() {
                            @Override
                            public void run() {
                                smoothScrollTo(initialX - remainder + itemWidth, 0);
                                selectedIndex = divided + 1;
                                callBack();
                            }
                        });
                    } else {
                        post(new Runnable() {
                            @Override
                            public void run() {
                                smoothScrollTo(initialX - remainder, 0);
                                selectedIndex = divided;
                                callBack();
                            }
                        });
                    }
                }
            } else {
                startScrollerTask();
            }
        }
    }

    public void callBack() {
        if (null != callback) {
            // 2015/12/25 真实的index应该忽略偏移量
            callback.onSelected(isUserScroll, displayItemCount - offset - 1 + selectedIndex, items.get(displayItemCount - offset - 1 + selectedIndex));
        }

    }

    public interface OnSelectedCallBack {
        void onSelected(boolean isUserScroll, int index, String title);
    }

    public void setOnSelectedCallBack(OnSelectedCallBack callback) {
        this.callback = callback;
    }
}
