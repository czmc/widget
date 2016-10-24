package me.czmc.widgetdemo.v104;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.czmc.library.adapter.recyclerview.DividerItemDecoration;
import me.czmc.library.adapter.recyclerview.MultiItemTypeAdapter;
import me.czmc.widgetdemo.R;
import me.czmc.widgetdemo.v102.Bean;
import me.czmc.widgetdemo.v104.Rv1Adapter;
import me.czmc.widgetdemo.v104.Rv2Adapter;

import static me.czmc.library.adapter.recyclerview.DividerItemDecoration.VERTICAL_LIST;

public class TestAdapterActivity extends AppCompatActivity {
    @BindView(R.id.rv_test)
    RecyclerView mRecyclerView;
    private ArrayList<Bean> datas;
    private MultiItemTypeAdapter mMultiItemTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_adapter);
        ButterKnife.bind(this);
        datas = new ArrayList<>();
        datas.add(new Bean("条目1",true));
        datas.add(new Bean("条目2",false));
        datas.add(new Bean("条目3",true));
        datas.add(new Bean("条目4",false));
        datas.add(new Bean("条目5",false));
        datas.add(new Bean("条目6",false));
        datas.add(new Bean("条目7",true));
        datas.add(new Bean("条目8",true));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,VERTICAL_LIST)) ;
//设置增加或删除条目的动画
//        RvAdapter mRvAdapter = new RvAdapter(this,R.layout.item_gv);
        mMultiItemTypeAdapter = new MultiItemTypeAdapter(this);
        mMultiItemTypeAdapter.setData(datas);
        mMultiItemTypeAdapter.addItemViewDelegate(new Rv1Adapter());
        mMultiItemTypeAdapter.addItemViewDelegate(new Rv2Adapter());
        mRecyclerView.setAdapter(mMultiItemTypeAdapter);

    }
}
