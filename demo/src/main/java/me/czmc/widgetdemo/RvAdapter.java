package me.czmc.widgetdemo;

import android.content.Context;

import me.czmc.library.adapter.recyclerview.CommonAdapter;
import me.czmc.library.adapter.recyclerview.ViewHolder;

/**
 * Created by MZone on 7/11/2016.
 */

public class RvAdapter extends CommonAdapter<Bean> {

    public RvAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, Bean bean, int position) {
        holder.setText(R.id.tv1,bean.attr1);
    }
}
