package me.czmc.widgetdemo.v104;

import me.czmc.library.adapter.recyclerview.ItemViewDelegate;
import me.czmc.library.adapter.recyclerview.ViewHolder;
import me.czmc.widgetdemo.R;
import me.czmc.widgetdemo.v102.Bean;

/**
 * Created by MZone on 7/11/2016.
 */

public class Rv1Adapter implements ItemViewDelegate<Bean> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_1;
    }

    @Override
    public boolean isForViewType(Bean item, int position) {
        return item.isSingleItem;
    }

    @Override
    public void convert(ViewHolder holder, Bean bean, int position) {
        holder.setText(R.id.tv1,bean.attr1);
    }
}
