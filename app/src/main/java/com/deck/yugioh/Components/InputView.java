package com.deck.yugioh.Components;

import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.deck.yugioh.R;
import com.deck.yugioh.Utils.Helpers.Helpers;
import com.deck.yugioh.Utils.Validators.ValidatorModel;
import com.deck.yugioh.Utils.Validators.Validators;

import java.util.ArrayList;

public class InputView extends ConstraintLayout {

    private TextView label;
    private TextView message;
    private EditText input;

    private boolean focus;
    private boolean isValid;

    private ArrayList<ValidatorModel> rules = new ArrayList<>();

    private @Nullable
    ViewCallBack inputViewCallBack;

    public interface ViewCallBack {
        void onInput();
    }

    public InputView(Context context) {
        super(context);
        this.init(context);
    }

    public InputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public InputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context);
    }

    private void init(Context context){

        View view = inflate(context, R.layout.sample_input_view, this);

        this.input = view.findViewById(R.id.customInput);
        this.label = view.findViewById(R.id.customLabel);
        this.message = view.findViewById(R.id.customMessage);

        this.input.addTextChangedListener(this.textChangedListener());
        this.input.setOnFocusChangeListener(this.focusChangeListener());

        this.setBorder(R.color.colorAccent);

    }

    public void setContent(int type, String placeholder, String label, ArrayList<ValidatorModel> rules) {

        if(type == InputType.TYPE_TEXT_VARIATION_PASSWORD) {

            this.input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            this.input.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }

        else
            this.input.setInputType(InputType.TYPE_CLASS_TEXT | type);

        this.input.setHint(placeholder);
        this.label.setText(label);
        this.rules = rules;

    }

    private TextWatcher textChangedListener() {

        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable editable) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(start != before)
                    checkValidation();

            }

        };

    }

    private View.OnFocusChangeListener focusChangeListener() {

        return new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                focus = hasFocus;
                checkValidation();

            }

        };

    }

    private void checkValidation() {

        boolean isValid = true;
        String message = "";

        for(ValidatorModel rule: this.rules) {

            if(rule.getParameterInt() != null)
                isValid = Validators.isValid(rule.getRule(), this.input.getText().toString(), rule.getParameterInt());

            else
                isValid = Validators.isValid(rule.getRule(), this.input.getText().toString());

            if(!isValid) {

                message = rule.getMessage();
                break;

            }

        }

        this.isValid = isValid;

        int color = this.isValid ? R.color.colorSuccess : this.focus ? R.color.colorWarning : R.color.colorDanger;

        this.message.setText(message);
        this.message.setTextColor(getResources().getColor(color));

        this.setBorder(color);

        if (this.inputViewCallBack != null)
            this.inputViewCallBack.onInput();

    }

    private void setBorder(int color) {

        this.input.setBackground(null);

        LayerDrawable bottomBorder = Helpers.getBorders(
                getResources().getColor(R.color.colorBackground), getResources().getColor(color),
                0, 0, 0, 4
        );

        this.input.setBackground(bottomBorder);

    }

    public String getInputValue() {
        return this.input.getText().toString();
    }

    public boolean isValid() {
        return isValid;
    }

    public void setFormValidCallback(@Nullable ViewCallBack inputViewCallBack) {
        this.inputViewCallBack = inputViewCallBack;
    }

}
