package com.deck.yugioh.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.deck.yugioh.Fragment.InputFragment;
import com.deck.yugioh.R;

public class LoginActivity extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();
    InputFragment emailFrag;
    InputFragment passwordFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.emailFrag = (InputFragment) this.fm.findFragmentById(R.id.email_frag);
        this.passwordFrag = (InputFragment) this.fm.findFragmentById(R.id.password_frag);

        this.setEmailField();
        this.setPasswordField();

    }

    private void setEmailField() {

        Bundle bundle = new Bundle();

        bundle.putString("label", "Email");
        bundle.putString("placeholder", "Digite o seu email");

        this.emailFrag.setArguments(bundle);

        this.fm.beginTransaction().replace(R.id.email_frag, this.emailFrag).commit();

    }

    private void setPasswordField() {

        Bundle bundle = new Bundle();

        bundle.putString("label", "Senha");
        bundle.putString("placeholder", "Digite a sua senha");

        this.passwordFrag.setArguments(bundle);

        this.fm.beginTransaction().replace(R.id.password_frag, this.passwordFrag).commit();


    }

    private void submitForm() {


    }

}
