package me.czmc.widgetdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.czmc.library.dialog.EditDialog;
import me.czmc.library.dialog.TipDialog;
import me.czmc.widgetdemo.v102.V102Activity;
import me.czmc.widgetdemo.v103.V103Activity;
import me.czmc.widgetdemo.v104.TestAdapterActivity;
import me.czmc.widgetdemo.v104.V104Activity;
import me.czmc.widgetdemo.v105.V105Activity;
import me.czmc.widgetdemo.v106.V106Activity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    Intent intent;
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_v102:
                intent = new Intent(MainActivity.this,V102Activity.class);
                startActivity(intent);
                break;
            case R.id.btn_v103:
                intent = new Intent(MainActivity.this,V103Activity.class);
                startActivity(intent);
                break;
            case R.id.btn_v104:
                intent = new Intent(MainActivity.this,V104Activity.class);
                startActivity(intent);
                break;
            case R.id.btn_v105:
                intent = new Intent(MainActivity.this,V105Activity.class);
                startActivity(intent);
                break;
            case R.id.btn_v106:
                intent = new Intent(MainActivity.this,V106Activity.class);
                startActivity(intent);
                break;
        }

    }
}
