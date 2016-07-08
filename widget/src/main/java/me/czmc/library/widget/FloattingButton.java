package me.czmc.library.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * Created by czmc on 2016/7/5.
 */

public class FloattingButton extends ImageView implements View.OnLongClickListener {

    private WindowManager mWindowManager;
    private float x;
    private float y;
    private float mTouchStartX;
    private float mTouchStartY;
    private int statusHeight=0;
    private WindowManager.LayoutParams mLayout;

    public FloattingButton(Context context) {
        super(context);
        init(0,0);
    }
    public FloattingButton(Context context,int x,int y) {
        super(context);
        init(x,y);
    }
    private void init(int x,int y) {
        setClickable(true);
        setOnLongClickListener(this);
        // 取得系统窗体
        mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        // 窗体的布局样式
        mLayout = new WindowManager.LayoutParams();
        // 设置窗体显示类型——TYPE_SYSTEM_ALERT(系统提示)
        mLayout.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        // 设置窗体焦点及触摸：
        // FLAG_NOT_FOCUSABLE(不能获得按键输入焦点)
        mLayout.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 设置显示的模式
        mLayout.format = PixelFormat.RGBA_8888;
        // 设置对齐的方法
        mLayout.gravity = Gravity.TOP | Gravity.LEFT;
        this.x = mLayout.x = x;
        this.y = mLayout.y = y;
        // 设置窗体宽度和高度
        mLayout.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayout.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowManager.addView(this,mLayout);
        getStatusHeight();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // 获取相对屏幕的坐标，即以屏幕左上角为原点
        x = event.getRawX();
        y = event.getRawY() - statusHeight; // 25是系统状态栏的高度
        Log.i("startP", "startX" + mTouchStartX + "====startY"
                + mTouchStartY);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 获取相对View的坐标，即以此View左上角为原点
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                Log.i("startP", "startX" + mTouchStartX + "====startY"
                        + mTouchStartY);
                break;
            case MotionEvent.ACTION_MOVE:
                // 更新浮动窗口位置参数
                mLayout.x = (int) (x - mTouchStartX);
                mLayout.y = (int) (y - mTouchStartY);
                mWindowManager.updateViewLayout(this, mLayout);
                break;
            case MotionEvent.ACTION_UP:
                // 更新浮动窗口位置参数
                mLayout.x = (int) (x - mTouchStartX);
                mLayout.y = (int) (y - mTouchStartY);
                mWindowManager.updateViewLayout(this, mLayout);
                // 可以在此记录最后一次的位置
                mTouchStartX = mTouchStartY = 0;
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    public void onDestroy(){
        mWindowManager.removeViewImmediate(this);
    }

    /**
     * 获取状态栏高度
     */
    public void getStatusHeight(){
        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            statusHeight = getResources().getDimensionPixelSize(Integer.parseInt(field.get(obj).toString()));
            Log.i("FloattingButton","statusHeight:"+statusHeight);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    @Override
    public boolean onLongClick(View v) {
        return true;
    }
}
