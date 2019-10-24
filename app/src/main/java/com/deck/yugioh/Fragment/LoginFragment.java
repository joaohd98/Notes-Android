package com.deck.yugioh.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.deck.yugioh.Alerts.DialogView;
import com.deck.yugioh.Components.InputView;
import com.deck.yugioh.Components.LoadingView;
import com.deck.yugioh.HttpRequest.AuthAPI;
import com.deck.yugioh.HttpRequest.Utils.RequestCallBack;
import com.deck.yugioh.Model.Auth.AuthRequestModel;
import com.deck.yugioh.R;
import com.deck.yugioh.Utils.Validators.ValidatorModel;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.ArrayList;


public class LoginFragment extends Fragment {

    private LoadingView loadingView;

    private InputView emailFrag;
    private InputView passwordFrag;
    private Button submitBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        this.loadingView = view.findViewById(R.id.fragment_login_loading);
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

        int type = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
        String label = getString(R.string.fragment_login_email_label);
        String placeholder = getString(R.string.fragment_login_email_placeholder);

        ArrayList<ValidatorModel> rules = new ArrayList<>();

        rules.add(new ValidatorModel(R.string.validators_required, getString(R.string.fragment_login_email_validation_required)));
        rules.add(new ValidatorModel(R.string.validators_email, getString(R.string.fragment_login_email_validation_invalid)));

        this.emailFrag.setFormValidCallback(new InputView.ViewCallBack() {

            @Override
            public void onInput() {
                isFormValid();
            }

        });

        this.emailFrag.setContent(type, label, placeholder, rules);

    }

    private void setPasswordField() {

        int type = InputType.TYPE_TEXT_VARIATION_PASSWORD;
        String label = getString(R.string.fragment_login_password_label);
        String placeholder = getString(R.string.fragment_login_password_placeholder);

        ArrayList<ValidatorModel> rules = new ArrayList<>();

        rules.add(new ValidatorModel(R.string.validators_required, getString(R.string.fragment_login_password_validation_required)));
        rules.add(new ValidatorModel(R.string.validators_min_length, getString(R.string.fragment_login_password_validation_min_length), 6));

        this.passwordFrag.setFormValidCallback(new InputView.ViewCallBack() {

            @Override
            public void onInput() {
                isFormValid();
            }

        });

        this.passwordFrag.setContent(type, label, placeholder, rules);

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

        AuthRequestModel request = new AuthRequestModel(this.emailFrag, this.passwordFrag);

        AuthAPI authAPI = new AuthAPI();

        authAPI.callRequest(request, new RequestCallBack() {

            @Override
            public void success() {

                loadingView.hide();

                Toast.makeText(getContext(), "Sucesso", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void error(Exception exception) {

                loadingView.hide();

                Context context = getContext();

                if(context != null) {

                    DialogView dialog = new DialogView(getContext());

                    dialog.setTitle(context.getString(R.string.fragment_login_alert_title));

                    try {

                        throw exception;

                    } catch (FirebaseAuthUserCollisionException ignore) {

                        dialog.setInfo(context.getString(R.string.fragment_login_alert_message_email_password));

                    } catch (FirebaseNetworkException ignore) {

                        dialog.setInfo(context.getString(R.string.fragment_login_alert_message_no_internet));

                    } catch (Exception ignore) {

                        dialog.setInfo(context.getString(R.string.fragment_login_alert_message_generic));

                    }

                    dialog.setBtnSuccess(context.getString(R.string.fragment_login_alert_button));

                    dialog.show();

                }



            }

        });

    }

}
