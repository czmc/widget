package me.czmc.widgetdemo.v105;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.czmc.library.widget.HorizontalSelector;
import me.czmc.widgetdemo.R;

public class V105Activity extends AppCompatActivity implements HorizontalSelector.OnSelectedCallBack {
    @BindView(R.id.selector)
    HorizontalSelector selector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v105);
        ButterKnife.bind(this);
        testHorizontalWheelView();
    }
    private void testHorizontalWheelView() {
        ArrayList<String> mTitles = new ArrayList();
        mTitles.add("菜单一");
        mTitles.add("菜单二");
        mTitles.add("菜单三");
        mTitles.add("菜单四");
        mTitles.add("菜单五");
        selector.setItems(mTitles,0);
        selector.setOnSelectedCallBack(this);
    }

    @Override
    public void onSelected(boolean isUserScroll, int index, String title) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
        Log.i("onSelected",title);
    }
}
