package com.deck.yugioh.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.deck.yugioh.R;
import com.deck.yugioh.Utils.Validators.ValidatorModel;
import com.deck.yugioh.Utils.Validators.Validators;

import java.util.ArrayList;

import javax.xml.validation.Validator;

public class InputFragment extends Fragment {

    public InputFragment() {}

    private TextView label;
    private TextView message;
    private EditText input;

    private boolean focus = false;
    private boolean isValid = false;
    private ArrayList<ValidatorModel> rules = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_input, container, false);

        this.input = view.findViewById(R.id.customInput);
        this.label = view.findViewById(R.id.customLabel);
        this.message = view.findViewById(R.id.customMessage);

        this.input.addTextChangedListener(this.textChangedListener());
        this.input.setOnFocusChangeListener(this.focusChangeListener());

        return view;

    }

    public void setContent(Bundle savedInstanceState) {

        if(savedInstanceState != null) {

            this.label.setText(savedInstanceState.getString("label"));
            this.input.setHint(savedInstanceState.getString("placeholder"));

            this.rules = savedInstanceState.getParcelableArrayList("rules");


        }

    }

    private TextWatcher textChangedListener() {

      return new TextWatcher() {

          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

          @Override
          public void afterTextChanged(Editable editable) { }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {

              InputFragment.this.checkValidation();

          }

      };

    }

    private View.OnFocusChangeListener focusChangeListener() {

        return new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                InputFragment.this.focus = hasFocus;

                InputFragment.this.checkValidation();

            }

        };


    }

    private void checkValidation() {

        int color = this.focus ? R.color.colorWarning : R.color.colorDanger;

        boolean isValid = true;
        String message = "";

        for(ValidatorModel rule: this.rules) {

            isValid = Validators.isValid(rule.getRule(), rule.getMessage());

            if(!isValid) {

                message = rule.getMessage();
                break;

            }

        }

        this.isValid = isValid;
        this.message.setText(message);

        if(isValid) {

//            this.input.bor

        }

        else {


            this.message.setTextColor(getResources().getColor(color));

        }



    }

}
