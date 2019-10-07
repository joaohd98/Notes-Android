package com.deck.yugioh.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.deck.yugioh.Fragment.InputFragment;
import com.deck.yugioh.R;
import com.deck.yugioh.Utils.Helpers.Helpers;
import com.deck.yugioh.Utils.Validators.ValidatorModel;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private FragmentManager fm = getSupportFragmentManager();
    private InputFragment emailFrag;
    private InputFragment passwordFrag;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.emailFrag = (InputFragment) this.fm.findFragmentById(R.id.email_frag);
        this.passwordFrag = (InputFragment) this.fm.findFragmentById(R.id.password_frag);

        this.submitBtn = findViewById(R.id.loginBtn);

        this.setEmailField();
        this.setPasswordField();
        this.setSubmitBtn();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        Helpers.removeFocusClickOutside(this, event);

        return super.dispatchTouchEvent(event);

    }

    private void setSubmitBtn() {

        this.isFormValid();

    }

    private void setEmailField() {

        Bundle bundle = new Bundle();

        bundle.putString("label", "Email");
        bundle.putString("placeholder", "Digite o seu email");
        bundle.putInt("type", InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        ArrayList<ValidatorModel> rules = new ArrayList<>();

        rules.add(new ValidatorModel(R.string.validators_required, "Campo E-mail é obrigatório."));
        rules.add(new ValidatorModel(R.string.validators_email, "E-mail inválido."));

        bundle.putParcelableArrayList("rules", rules);

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

        bundle.putString("label", "Senha");
        bundle.putString("placeholder", "Digite a sua senha");
        bundle.putInt("type", InputType.TYPE_TEXT_VARIATION_PASSWORD);

        ArrayList<ValidatorModel> rules = new ArrayList<>();

        rules.add(new ValidatorModel(R.string.validators_required, "Campo senha é obrigatório."));

        bundle.putParcelableArrayList("rules", rules);

        this.passwordFrag.setFormValidCallback(new InputFragment.InputFragmentCallBack() {

            @Override
            public void onInput() {
                isFormValid();
            }

        });

        this.passwordFrag.setContent(bundle);

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



    }

}
