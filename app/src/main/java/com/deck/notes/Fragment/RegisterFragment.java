package com.deck.notes.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.deck.notes.Activities.SignInActivity;
import com.deck.notes.Components.DialogView;
import com.deck.notes.Components.InputView;
import com.deck.notes.Components.LoadingView;
import com.deck.notes.Fragment.Utils.MasterFragment;
import com.deck.notes.HttpRequest.RegisterAPI;
import com.deck.notes.HttpRequest.Utils.RequestCallBack;
import com.deck.notes.Model.Register.RegisterRequestModel;
import com.deck.notes.R;
import com.deck.notes.Utils.ActionBar.NavigationBar;
import com.deck.notes.Utils.Helpers.Helpers;
import com.deck.notes.Utils.Navigation.Navigation;
import com.deck.notes.Utils.Validators.ValidatorModel;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.ArrayList;

public class RegisterFragment extends MasterFragment {

    private LoadingView loadingView;

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
        this.loadingView = view.findViewById(R.id.fragment_register_loading);

        return view;


    }

    @Override
    public void onStart() {
        super.onStart();

        this.setNameField();
        this.setEmailField();
        this.setPasswordField();

        this.setButton();

    }

    public void setNavBar() {

        NavigationBar.setActionBar(getActivity(), "Registrar", true);

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

    private void setButton() {

        this.checkIsValid();

        this.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingView.show();

                RegisterAPI registerAPI = new RegisterAPI();

                RegisterRequestModel request = new RegisterRequestModel(inputName, inputEmail, inputPassword);

                registerAPI.callRequest(request, new RequestCallBack() {

                    @Override
                    public void success() {

                        loadingView.hide();

                        Navigation.setActivity(getActivity(), SignInActivity.class, true);

                    }

                    @Override
                    public void error(Exception exception) {

                        loadingView.hide();

                        Context context = getContext();

                        if(context != null) {

                            DialogView dialog = new DialogView(getContext());

                            dialog.setTitle(context.getString(R.string.fragment_register_alert_title));

                            try {

                                throw exception;

                            } catch (FirebaseAuthUserCollisionException ignore) {

                                dialog.setInfo(context.getString(R.string.fragment_register_alert_message_email));

                            } catch (FirebaseNetworkException ignore) {

                                dialog.setInfo(context.getString(R.string.fragment_register_alert_message_no_internet));

                            } catch (Exception ignore) {

                                dialog.setInfo(context.getString(R.string.fragment_register_alert_message_generic));

                            }

                            dialog.setBtnSuccess(context.getString(R.string.fragment_register_alert_button));

                            dialog.show();

                        }

                    }

                });

            }

        });

    }

    private void checkIsValid() {

        Helpers.checkIsValid(this.btnSubmit, this.inputName.isValid() && this.inputEmail.isValid() && this.inputPassword.isValid());

    }

}
