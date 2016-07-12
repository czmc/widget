package me.czmc.widgetdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestAdapterActivity extends AppCompatActivity {
    @BindView(R.id.rv_test)
    RecyclerView mRecyclerView;
    private ArrayList<Bean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_adapter);
        ButterKnife.bind(this);
        datas = new ArrayList<>();
        datas.add(new Bean("条目1"));
        datas.add(new Bean("条目2"));
        datas.add(new Bean("条目3"));
        datas.add(new Bean("条目4"));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//设置增加或删除条目的动画
        RvAdapter mRvAdapter = new RvAdapter(this,R.layout.item_gv);
        mRecyclerView.setAdapter(mRvAdapter);
        mRvAdapter.setData(datas);
    }
}
