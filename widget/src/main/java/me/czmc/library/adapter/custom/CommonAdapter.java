package me.czmc.library.adapter.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int mlayoutId;

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        this.mDatas = datas;
        this.mlayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }
    public CommonAdapter(Context context,int layoutId) {
        this.mContext = context;
        this.mDatas = new ArrayList<>();
        this.mlayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }
    /**
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return mDatas.size();
    }

    /**
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    /**
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, mlayoutId, position);

        convert(holder, getItem(position), position);

        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T t, int position);

    public void setData(List<T> list) {
        if (list!=null &&list.size()>0) {
            this.mDatas.clear();
            this.mDatas.addAll(list);
            notifyDataSetChanged();
        }
    }
    public void clearAll() {
        if(mDatas!=null){
            mDatas.clear();
        }
    }
}