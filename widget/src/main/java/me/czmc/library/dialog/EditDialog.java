package me.czmc.library.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import me.czmc.library.widget.R;


/**
 * Created by czmc on 2016/8/18.
 */

public class EditDialog extends CommonDialog {
    private TextView title;
    private EditText et_new_classify;
    private Button btn_cancel;
    private Button btn_confirm;
    protected OnDialogBtnClickListener mOnClickListener;
    private TextView txt_content;
    private TextView txt_title;

    public interface OnDialogBtnClickListener {
        void onConfirmBtnClicked(EditDialog newClassifyDialog, String value);
    }

    public EditDialog(Context context) {
        super(context, R.style.Edit_Dialog_Dim);
        setContentView(R.layout.dialog_edit);
        this.et_new_classify = (EditText) findViewById(R.id.et_new_classify);
        this.btn_cancel = (Button) findViewById(me.czmc.library.widget.R.id.btn_cancel);
        this.btn_cancel.setOnClickListener(this);
        this.btn_confirm = (Button) findViewById(me.czmc.library.widget.R.id.btn_confirm);
        this.btn_confirm.setOnClickListener(this);
        this.title = (TextView) findViewById(R.id.tv_title);
    }

    public static EditDialog newInstance(Context context) {
        return new EditDialog(context);
    }
    public EditDialog setTitle(String title){
        if(this.title!=null){
            this.title.setText(title);
        }
        return this;
    }

    public EditDialog setOnBtnClickListener(OnDialogBtnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
        return this;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_cancel) {
           this.dismiss();
        } else if (v.getId() == R.id.btn_confirm) {
            mOnClickListener.onConfirmBtnClicked(this, et_new_classify.getText().toString());
        }
    }
}
