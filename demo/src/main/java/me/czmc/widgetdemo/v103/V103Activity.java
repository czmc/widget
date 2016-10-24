package me.czmc.widgetdemo.v103;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.czmc.library.utils.DisplayUtil;
import me.czmc.library.widget.FloattingButton;
import me.czmc.library.widget.ViewPagerIndicator;
import me.czmc.widgetdemo.R;

public class V103Activity extends AppCompatActivity {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.viewPagerIndicator)
    ViewPagerIndicator indicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v103);
        ButterKnife.bind(this);
        testViewPagerIndicator();
        testFloattingButton();
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
    public void testFloattingButton() {
        FloattingButton btn1 = new FloattingButton(this, DisplayUtil.getScreenWidth(this), DisplayUtil.getScreenHeight(this) * 3 / 4);
        btn1.setImageResource(R.mipmap.icon_fb);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(V103Activity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
