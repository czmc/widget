package me.czmc.widgetdemo;

/**
 * Created by czmc on 2016/7/4.
 */
public class Bean {
    public Bean(String attr1, boolean isSingleItem) {
        this.attr1 = attr1;
        this.isSingleItem = isSingleItem;
    }

    public Bean(String attr1) {
        this.attr1 = attr1;
    }

    public String attr1;
    public boolean isSingleItem;
}
