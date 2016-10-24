package me.czmc.widgetdemo.v102;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.czmc.library.widget.GridViewWithHeaderAndFooter;
import me.czmc.widgetdemo.R;

public class V102Activity extends AppCompatActivity {
    @BindView(R.id.gv)
    GridViewWithHeaderAndFooter gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v102);
        ButterKnife.bind(this);
        testGridViewWithHeaderAndFooter();
    }
    public void testGridViewWithHeaderAndFooter() {

        TextView header = new TextView(this);
        header.setText("GridView头测试");
        header.setGravity(Gravity.CENTER);
        header.setPadding(30, 30, 30, 30);
        gv.addHeaderView(header);
        GvAdapter mAdapter = new GvAdapter(this, R.layout.item_gv);
        gv.setAdapter(mAdapter);

        ArrayList<Bean> datas = new ArrayList();
        datas.add(new Bean("条目1"));
        datas.add(new Bean("条目2"));
        datas.add(new Bean("条目3"));
        datas.add(new Bean("条目4"));

        mAdapter.setData(datas);
    }
}
