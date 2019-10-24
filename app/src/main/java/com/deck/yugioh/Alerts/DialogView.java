package com.deck.yugioh.Alerts;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.deck.yugioh.R;

public class DialogView {

    private AlertDialog alert;
    private String title;
    private String info;
    private String btnSuccess;
    private View.OnClickListener btnSuccessCallback;

    public DialogView(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.dialog_sample_view);

        this.alert = builder.create();

        this.btnSuccessCallback = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.hide();
            }
        };

    }

    public void show() {

        alert.show();

        TextView txtTitle = alert.findViewById(R.id.view_dialog_title);
        TextView txtInfo = alert.findViewById(R.id.view_dialog_info);
        Button btnSuccess = alert.findViewById(R.id.view_dialog_ok);

        txtTitle.setText(this.title);
        txtInfo.setText(this.info);
        btnSuccess.setText(this.btnSuccess);
        btnSuccess.setOnClickListener(this.btnSuccessCallback);

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setBtnSuccess(String btnSuccess) {
        this.btnSuccess = btnSuccess;
    }

}
