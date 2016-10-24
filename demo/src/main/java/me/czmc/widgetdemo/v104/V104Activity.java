package me.czmc.widgetdemo.v104;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.czmc.library.dialog.EditDialog;
import me.czmc.library.dialog.TipDialog;
import me.czmc.widgetdemo.R;

public class V104Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v104);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.btn_open,R.id.btn_open_edit, R.id.btn_start})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_open:
                TipDialog.newInstance(this).hideTitle().setContent("测试").show();
                break;
            case R.id.btn_start:
                Intent intent = new Intent(V104Activity.this,TestAdapterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_open_edit:
                EditDialog.newInstance(this).setTitle("编辑对话框").setOnBtnClickListener(new EditDialog.OnDialogBtnClickListener() {
                    @Override
                    public void onConfirmBtnClicked(EditDialog newClassifyDialog, String value) {
                        Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
                    }
                }).show();
        }

    }
}
