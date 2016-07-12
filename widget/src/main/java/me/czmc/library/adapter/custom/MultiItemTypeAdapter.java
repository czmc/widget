package me.czmc.library.adapter.custom;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class MultiItemTypeAdapter<T> extends BaseAdapter
{
    protected Context mContext;
    protected List<T> mDatas = new ArrayList();

    private ItemViewDelegateManager mItemViewDelegateManager;


    public MultiItemTypeAdapter(Context context, List<T> datas)
    {
        this.mContext = context;
        this.mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }
    public MultiItemTypeAdapter(Context context)
    {
        this.mContext = context;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate)
    {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    private boolean useItemViewDelegateManager()
    {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    @Override
    public int getViewTypeCount()
    {
        if (useItemViewDelegateManager())
            return mItemViewDelegateManager.getItemViewDelegateCount();
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position)
    {
        if (useItemViewDelegateManager())
        {
            int viewType = mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
            return viewType;
        }
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        int layoutId = mItemViewDelegateManager.getItemViewLayoutId(mDatas.get(position), position);

        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent,
                layoutId, position);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    protected void convert(ViewHolder viewHolder, T item, int position)
    {
        mItemViewDelegateManager.convert(viewHolder, item, position);
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public T getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


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