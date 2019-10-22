package com.deck.yugioh.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.deck.yugioh.Components.DialogView;
import com.deck.yugioh.Components.InputView;
import com.deck.yugioh.Components.LoadingView;
import com.deck.yugioh.HttpRequest.AuthAPI;
import com.deck.yugioh.HttpRequest.Utils.RequestCallBack;
import com.deck.yugioh.Model.Auth.AuthRequestModel;
import com.deck.yugioh.R;
import com.deck.yugioh.Utils.Validators.ValidatorModel;
import com.deck.yugioh.Wrapper.AuthWrapper;

import java.util.ArrayList;


public class LoginFragment extends Fragment {

    private LoadingView loadingView;
    private AuthWrapper authWrapper = new AuthWrapper();

    private InputView emailFrag;
    private InputView passwordFrag;
    private Button submitBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        this.loadingView = view.findViewById(R.id.activity_login_loading);
        this.emailFrag =  view.findViewById(R.id.email_frag);
        this.passwordFrag = view.findViewById(R.id.password_frag);

        this.submitBtn = view.findViewById(R.id.loginBtn);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        this.setEmailField();
        this.setPasswordField();
        this.setSubmitBtn();
    }

    private void setEmailField() {

        Bundle bundle = new Bundle();

        bundle.putString(getString(R.string.fragment_input_label), getString(R.string.activity_login_email_label));
        bundle.putString(getString(R.string.fragment_input_placeholder), getString(R.string.activity_login_email_placeholder));
        bundle.putInt(getString(R.string.fragment_input_type), InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        ArrayList<ValidatorModel> rules = new ArrayList<>();

        rules.add(new ValidatorModel(R.string.validators_required, getString(R.string.activity_login_email_validation_required)));
        rules.add(new ValidatorModel(R.string.validators_email, getString(R.string.activity_login_email_validation_invalid)));

        bundle.putParcelableArrayList(getString(R.string.fragment_input_rules), rules);

        this.emailFrag.setFormValidCallback(new InputView.ViewCallBack() {

            @Override
            public void onInput() {
                isFormValid();
            }

        });

        this.emailFrag.setContent(bundle);

    }

    private void setPasswordField() {

        Bundle bundle = new Bundle();

        bundle.putString(getString(R.string.fragment_input_label), getString(R.string.activity_login_password_label));
        bundle.putString(getString(R.string.fragment_input_placeholder), getString(R.string.activity_login_password_placeholder));
        bundle.putInt(getString(R.string.fragment_input_type), InputType.TYPE_TEXT_VARIATION_PASSWORD);

        ArrayList<ValidatorModel> rules = new ArrayList<>();

        rules.add(new ValidatorModel(R.string.validators_required, getString(R.string.activity_login_password_validation_required)));
        rules.add(new ValidatorModel(R.string.validators_min_length, getString(R.string.activity_login_password_validation_min_length), 6));

        bundle.putParcelableArrayList(getString(R.string.fragment_input_rules), rules);

        this.passwordFrag.setFormValidCallback(new InputView.ViewCallBack() {

            @Override
            public void onInput() {
                isFormValid();
            }

        });

        this.passwordFrag.setContent(bundle);

    }

    private void setSubmitBtn() {

        this.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        this.isFormValid();

    }

    private void isFormValid() {

        if(emailFrag.isValid() && passwordFrag.isValid()) {

            this.submitBtn.setAlpha(1);
            this.submitBtn.setClickable(true);

        }

        else {

            this.submitBtn.setAlpha(.5f);
            this.submitBtn.setClickable(false);

        }

    }

    private void submitForm() {

        this.loadingView.show();

        AuthRequestModel auth = new AuthRequestModel(this.emailFrag.getInputValue(), this.passwordFrag.getInputValue());

        this.authWrapper.setAuthRequestModel(auth);

        AuthAPI authAPI = new AuthAPI();

        authAPI.callRequest(this.authWrapper, new RequestCallBack<AuthWrapper>() {
            @Override
            public void success(AuthWrapper response) {

                loadingView.hide();

                Toast.makeText(getContext(), "Sucesso", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void error() {

                loadingView.hide();

                DialogView dialog = new DialogView(getContext());

                dialog.setTitle("Atenção");
                dialog.setInfo("Email ou/e senha incorretos.");
                dialog.setBtnSuccess("Entendi");

                dialog.show();

            }

        });

    }

}
