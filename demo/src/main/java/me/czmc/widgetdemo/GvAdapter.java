package me.czmc.widgetdemo;

import android.content.Context;

import me.czmc.library.adapter.custom.CommonAdapter;
import me.czmc.library.adapter.custom.ViewHolder;

/**
 * Created by czmc on 2016/7/4.
 */

public class GvAdapter extends CommonAdapter<Bean>{
    public GvAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, Bean bean, int position) {
            holder.setText(R.id.tv1,bean.attr1);
    }
}
