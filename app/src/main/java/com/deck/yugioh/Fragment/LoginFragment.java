package com.deck.yugioh.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.deck.yugioh.Activities.SignInActivity;
import com.deck.yugioh.Components.DialogView;
import com.deck.yugioh.Components.InputView;
import com.deck.yugioh.Components.LoadingView;
import com.deck.yugioh.Fragment.Utils.MasterFragment;
import com.deck.yugioh.HttpRequest.AuthAPI;
import com.deck.yugioh.HttpRequest.Utils.RequestCallBack;
import com.deck.yugioh.Model.Auth.AuthRequestModel;
import com.deck.yugioh.R;
import com.deck.yugioh.Utils.ActionBar.NavigationBar;
import com.deck.yugioh.Utils.Helpers.Helpers;
import com.deck.yugioh.Utils.Navigation.Navigation;
import com.deck.yugioh.Utils.Validators.ValidatorModel;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.ArrayList;

public class LoginFragment extends MasterFragment {

    private LoadingView loadingView;

    private InputView emailFrag;
    private InputView passwordFrag;

    private Button submitBtn;
    private TextView forgotPasswordBtn;
    private Button registerBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        this.loadingView = view.findViewById(R.id.fragment_login_loading);
        this.emailFrag =  view.findViewById(R.id.fragment_login_input_email);
        this.passwordFrag = view.findViewById(R.id.fragment_login_input_password);

        this.submitBtn = view.findViewById(R.id.fragment_login_button_submit);
        this.registerBtn = view.findViewById(R.id.fragment_login_button_register);
        this.forgotPasswordBtn = view.findViewById(R.id.fragment_login_button_forgot_password);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        this.setEmailField();
        this.setPasswordField();
        this.setSubmitBtn();
        this.setForgotPasswordBtn();
        this.setRegisterBtn();

    }

    public void setNavBar() {

        NavigationBar.setActionBar(getActivity(), "Login", false);

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

    private void setForgotPasswordBtn() {

        this.forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();

                FragmentActivity activity = getActivity();

                if(activity != null)
                    Navigation.push(activity, new ForgotPasswordFragment(), bundle, R.id.activity_guest_fragment);

            }

        });

    }

    private void setRegisterBtn() {

        this.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();

                FragmentActivity activity = getActivity();

                if(activity != null)
                    Navigation.push(activity, new RegisterFragment(), bundle, R.id.activity_guest_fragment);

            }

        });

    }

    private void submitForm() {

        this.loadingView.show();

        AuthRequestModel request = new AuthRequestModel(this.emailFrag, this.passwordFrag);

        AuthAPI authAPI = new AuthAPI();

        authAPI.callRequest(request, new RequestCallBack() {

            @Override
            public void success() {

                loadingView.hide();

                Navigation.setActivity(getActivity(), SignInActivity.class);

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

    private void isFormValid() {

        Helpers.checkIsValid(this.submitBtn, this.emailFrag.isValid() && this.passwordFrag.isValid());

    }

}
