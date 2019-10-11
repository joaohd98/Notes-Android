package com.deck.yugioh.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.deck.yugioh.Fragment.InputFragment;
import com.deck.yugioh.HttpRequest.AuthAPI;
import com.deck.yugioh.HttpRequest.Utils.RequestCallBack;
import com.deck.yugioh.Model.Auth.AuthRequestModel;
import com.deck.yugioh.R;
import com.deck.yugioh.Utils.Helpers.Helpers;
import com.deck.yugioh.Utils.Validators.ValidatorModel;
import com.deck.yugioh.Wrapper.AuthWrapper;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private AuthWrapper authWrapper = new AuthWrapper();
    private InputFragment emailFrag;
    private InputFragment passwordFrag;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.emailFrag = (InputFragment) getSupportFragmentManager().findFragmentById(R.id.email_frag);
        this.passwordFrag = (InputFragment) getSupportFragmentManager().findFragmentById(R.id.password_frag);

        this.submitBtn = findViewById(R.id.loginBtn);



    }

    @Override
    protected void onStart() {
        super.onStart();

//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        if(currentUser != null)
//            return;

        this.setEmailField();
        this.setPasswordField();
        this.setSubmitBtn();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        Helpers.removeFocusClickOutside(this, event);

        return super.dispatchTouchEvent(event);

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

        this.emailFrag.setFormValidCallback(new InputFragment.InputFragmentCallBack() {

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

        this.passwordFrag.setFormValidCallback(new InputFragment.InputFragmentCallBack() {

            @Override
            public void onInput() {
                isFormValid();
            }

        });

        this.passwordFrag.setContent(bundle);

    }

    private void setSubmitBtn() {

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

    public void submitForm(View view) {

        AuthRequestModel auth = new AuthRequestModel(this.emailFrag.getInputValue(), this.passwordFrag.getInputValue());

        this.authWrapper.setAuthRequestModel(auth);

        AuthAPI authAPI = new AuthAPI();

        authAPI.callRequest(this.authWrapper, new RequestCallBack<AuthWrapper>() {
            @Override
            public void success(AuthWrapper response) {
                Toast.makeText(LoginActivity.this, "Sucesso", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void error() {
                Toast.makeText(LoginActivity.this, "Falha", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
