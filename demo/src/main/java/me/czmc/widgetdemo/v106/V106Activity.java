package me.czmc.widgetdemo.v106;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.czmc.library.widget.ActionSheet;
import me.czmc.library.widget.BadgeView;
import me.czmc.widgetdemo.R;

public class V106Activity extends AppCompatActivity {
    @BindView(R.id.tv_test)
    TextView tv_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v106);
        ButterKnife.bind(this);
        BadgeView badgeView = new BadgeView(this);
        badgeView.setTargetView(tv_test);
        badgeView.setBadgeCount(1);
    }
    public void open(View view){
        setTheme(R.style.ActionSheetStyleIOS7);
        ActionSheet
                .createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("列表一", "列表二").setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {
            @Override
            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                switch (index){
                    case 0:
                        break;
                    case 1:
                        break;
                }
            }
        }).show();
    }
}
