package me.czmc.widgetdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.czmc.library.dialog.TipDialog;
import me.czmc.library.utils.DisplayUtil;
import me.czmc.library.widget.FloattingButton;
import me.czmc.library.widget.GridViewWithHeaderAndFooter;
import me.czmc.library.widget.ViewPagerIndicator;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.gv)
    GridViewWithHeaderAndFooter gv;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.viewPagerIndicator)
    ViewPagerIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        testFloattingButton();
        testGridViewWithHeaderAndFooter();
        testViewPagerIndicator();
    }

    @OnClick({R.id.btn_open, R.id.btn_start})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_open:
                TipDialog.newInstance(this).hideTitle().setContent("测试").show();
                break;
            case R.id.btn_start:
                Intent intent = new Intent(MainActivity.this,TestAdapterActivity.class);
                startActivity(intent);
                break;
        }

    }

    public void testFloattingButton() {
        FloattingButton btn1 = new FloattingButton(this, DisplayUtil.getScreenWidth(this), DisplayUtil.getScreenHeight(this) * 3 / 4);
        btn1.setImageResource(R.mipmap.icon_fb);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void testViewPagerIndicator() {
        ArrayList mContents = new ArrayList();
        ArrayList mTitles = new ArrayList();
        mContents.add(TagFragment.newInstance("part1"));
        mContents.add(TagFragment.newInstance("part2"));
        mContents.add(TagFragment.newInstance("part3"));
        mContents.add(TagFragment.newInstance("part4"));
        mContents.add(TagFragment.newInstance("part5"));
        mTitles.add("菜单一");
        mTitles.add("菜单二");
        mTitles.add("菜单三");
        mTitles.add("菜单四");
        mTitles.add("菜单五");
        viewPager.setAdapter(new TagFragmentPagerAdapter(this.getSupportFragmentManager(), mContents, mTitles));
        indicator.setViewPager(viewPager);
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
