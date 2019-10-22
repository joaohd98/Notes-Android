package com.deck.yugioh.Alerts;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.deck.yugioh.Components.InputView;
import com.deck.yugioh.R;
import com.deck.yugioh.Utils.Validators.ValidatorModel;

import java.util.ArrayList;

public class DialogInputView {

    private AlertDialog alert;
    private String title;
    private int type;
    private String label;
    private String placeholder;
    private ArrayList<ValidatorModel> rules;
    private String btnSuccess;
    private View.OnClickListener btnSuccessCallback;

    public DialogInputView(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.dialog_input_view);
        this.alert = builder.create();

        this.btnSuccessCallback = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.hide();
            }
        };

    }

    public void show() {

        this.alert.show();

        final TextView txtTitle = this.alert.findViewById(R.id.view_input_dialog_title);
        final InputView viewInput = this.alert.findViewById(R.id.view_input_dialog_input);
        final Button btnSubmit = this.alert.findViewById(R.id.view_input_dialog_button);

        txtTitle.setText(this.title);

        viewInput.setContent(type, placeholder, label, rules);
        viewInput.setFormValidCallback(new InputView.ViewCallBack() {

            @Override
            public void onInput() {
                btnSubmit.setEnabled(viewInput.isValid());
            }

        });

        btnSubmit.setText(this.btnSuccess);
        btnSubmit.setOnClickListener(this.btnSuccessCallback);

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public void setRules(ArrayList<ValidatorModel> rules) {
        this.rules = rules;
    }

    public void setBtnSuccess(String btnSuccess) {
        this.btnSuccess = btnSuccess;
    }

    public void setBtnSuccessCallback(View.OnClickListener btnSuccessCallback) {
        this.btnSuccessCallback = btnSuccessCallback;
    }

}
