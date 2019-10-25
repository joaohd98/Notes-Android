package com.deck.yugioh.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.deck.yugioh.Activities.Utils.MasterActivity;
import com.deck.yugioh.Fragment.ForgotPasswordFragment;
import com.deck.yugioh.Fragment.LoginFragment;
import com.deck.yugioh.R;
import com.deck.yugioh.Utils.Navigation.Navigation;

public class GuestActivity extends MasterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        Toolbar toolbar = findViewById(R.id.activity_guest_toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;

    }

    @Override
    protected void onStart() {
        super.onStart();

        Navigation.push(this, new LoginFragment(), new Bundle(), R.id.activity_guest_fragment);

    }

}
