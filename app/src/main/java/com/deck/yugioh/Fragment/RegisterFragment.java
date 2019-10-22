package com.deck.yugioh.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.deck.yugioh.Components.InputView;
import com.deck.yugioh.R;
import com.deck.yugioh.Utils.Validators.ValidatorModel;

import java.util.ArrayList;

public class RegisterFragment extends Fragment {

    private InputView inputName;
    private InputView inputEmail;
    private InputView inputPassword;
    private Button btnSubmit;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        this.inputName = view.findViewById(R.id.fragment_register_name);
        this.inputEmail = view.findViewById(R.id.fragment_register_email);
        this.inputPassword = view.findViewById(R.id.fragment_register_password);
        this.btnSubmit = view.findViewById(R.id.fragment_register_btn);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();


        this.setNameField();
        this.setEmailField();
        this.setPasswordField();

        this.checkIsValid();

    }

    private void setNameField() {

        int type = InputType.TYPE_TEXT_VARIATION_NORMAL;
        String label = getString(R.string.fragment_register_name_label);
        String placeholder = getString(R.string.fragment_register_name_placeholder);

        ArrayList<ValidatorModel> rules = new ArrayList<>();

        rules.add(new ValidatorModel(R.string.validators_required, getString(R.string.fragment_register_name_validation_required)));

        this.inputName.setFormValidCallback(new InputView.ViewCallBack() {

            @Override
            public void onInput() {
                checkIsValid();
            }

        });

        this.inputName.setContent(type, label, placeholder, rules);

    }


    private void setEmailField() {

        int type = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
        String label = getString(R.string.fragment_register_email_label);
        String placeholder = getString(R.string.fragment_register_email_placeholder);

        ArrayList<ValidatorModel> rules = new ArrayList<>();

        rules.add(new ValidatorModel(R.string.validators_required, getString(R.string.fragment_register_email_validation_required)));
        rules.add(new ValidatorModel(R.string.validators_email, getString(R.string.fragment_register_email_validation_invalid)));

        this.inputEmail.setFormValidCallback(new InputView.ViewCallBack() {

            @Override
            public void onInput() {
                checkIsValid();
            }

        });

        this.inputEmail.setContent(type, label, placeholder, rules);

    }


    private void setPasswordField() {

        int type = InputType.TYPE_TEXT_VARIATION_PASSWORD;
        String label = getString(R.string.fragment_register_password_label);
        String placeholder = getString(R.string.fragment_register_password_placeholder);

        ArrayList<ValidatorModel> rules = new ArrayList<>();

        rules.add(new ValidatorModel(R.string.validators_required, getString(R.string.fragment_register_password_validation_required)));
        rules.add(new ValidatorModel(R.string.validators_min_length, getString(R.string.fragment_register_password_validation_min_length), 6));

        this.inputPassword.setFormValidCallback(new InputView.ViewCallBack() {

            @Override
            public void onInput() {
                checkIsValid();
            }

        });

        this.inputPassword.setContent(type, label, placeholder, rules);

    }

    private void checkIsValid() {

        if(this.inputName.isValid() && this.inputEmail.isValid() && this.inputPassword.isValid()) {

            this.btnSubmit.setAlpha(1);
            this.btnSubmit.setClickable(true);

        }

        else {

            this.btnSubmit.setAlpha(.5f);
            this.btnSubmit.setClickable(false);

        }

    }


}
