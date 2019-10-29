package com.deck.notes.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.deck.notes.R;
import com.deck.notes.Utils.Navigation.Navigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final Activity activity = this;

        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                Navigation.setActivity(activity, user != null ? SignInActivity.class : GuestActivity.class, false);

            }
        }, 2000);

    }

}
