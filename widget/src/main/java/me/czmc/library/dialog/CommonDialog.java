package me.czmc.library.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.LinearLayout;

import me.czmc.library.widget.R;


public abstract class CommonDialog extends Dialog implements OnClickListener {
    public CommonDialog(Context context) {
        super(context, R.style.Custom_Dialog_Dim);
        initDialogAttrs(context);
    }

    public CommonDialog(Context context, int theme) {
        super(context, theme);
        initDialogAttrs(context);
    }

    protected void initDialogAttrs(Context context) {
        setCanceledOnTouchOutside(true);
        DisplayMetrics metric = new DisplayMetrics();
        ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metric);
        getWindow().getAttributes().width = metric.widthPixels*3/4;
        getWindow().getAttributes().height = LinearLayout.LayoutParams.WRAP_CONTENT;
        getWindow().getAttributes().y = 0;
        getWindow().setGravity(Gravity.CENTER_VERTICAL);
        getWindow().setAttributes(getWindow().getAttributes());
        if (context instanceof Activity) {
            setOwnerActivity((Activity) context);
        }
    }
    public void onClick(View v) {
    }
}