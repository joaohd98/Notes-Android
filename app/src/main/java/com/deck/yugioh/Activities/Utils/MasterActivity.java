package com.deck.yugioh.Activities.Utils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.deck.yugioh.Utils.Helpers.Helpers;

@SuppressLint("Registered")
public abstract class MasterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        Helpers.removeFocusClickOutside(this, event);

        return super.dispatchTouchEvent(event);

    }

    public void setActionBar(String title, boolean showBackButton) {

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {

            actionBar.setTitle(title);

            if(showBackButton) {

                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);

            }

            else {

                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowHomeEnabled(false);

            }

        }

    }


}
