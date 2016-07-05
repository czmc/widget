package me.czmc.widgetdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.czmc.library.dialog.TipDialog;
import me.czmc.library.widget.GridViewWithHeaderAndFooter;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.gv)
    GridViewWithHeaderAndFooter gv;
    @OnClick(R.id.btn_open)
    public void open(){
        TipDialog tipDialog = TipDialog.newInstance(this);
        tipDialog.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        TextView header = new TextView(this);
        header.setText("GridView头测试");
        header.setGravity(Gravity.CENTER);
        header.setPadding(30,30,30,30);
        gv.addHeaderView(header);
        GvAdapter mAdapter = new GvAdapter(this,R.layout.item_gv);
        gv.setAdapter(mAdapter);

        ArrayList<Bean> datas =  new ArrayList<Bean>();
        datas.add(new Bean("条目1"));
        datas.add(new Bean("条目2"));
        datas.add(new Bean("条目3"));
        datas.add(new Bean("条目4"));

        mAdapter.setData(datas);
    }
}
