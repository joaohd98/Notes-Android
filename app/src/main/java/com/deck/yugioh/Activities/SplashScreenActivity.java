package com.deck.yugioh.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.deck.yugioh.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

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

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                FirebaseUser user = firebaseAuth.getCurrentUser();

                Intent intent;

                if(user == null)
                    intent = new Intent(activity, GuestActivity.class);

                else
                    intent = new Intent(activity, UserActivity.class);

                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        }, 2000);

    }

}
