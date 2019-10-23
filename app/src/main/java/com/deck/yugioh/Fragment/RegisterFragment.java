package com.deck.yugioh.Fragment;


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
import com.deck.yugioh.HttpRequest.RegisterAPI;
import com.deck.yugioh.HttpRequest.Utils.RequestCallBack;
import com.deck.yugioh.Model.Register.RegisterRequestModel;
import com.deck.yugioh.R;
import com.deck.yugioh.Utils.Validators.ValidatorModel;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.ArrayList;

public class RegisterFragment extends Fragment {

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

                        Toast.makeText(getContext(), "Sucesso", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void error(Exception exception) {

                        loadingView.hide();

                        DialogView dialog = new DialogView(getContext());

                        dialog.setTitle("Atenção");

                        try {

                            throw exception;

                        } catch (FirebaseAuthUserCollisionException ignore) {

                            dialog.setInfo("Email já em uso.");

                        } catch (FirebaseNetworkException ignore) {

                            dialog.setInfo("Sem conexão com a internet.");

                        } catch (Exception e) {

                            dialog.setInfo("Ocorreu um erro interno. Tente novamente mais tarde.");

                        }

                        dialog.setBtnSuccess("Ok");

                        dialog.show();
                    }

                });

            }

        });

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
