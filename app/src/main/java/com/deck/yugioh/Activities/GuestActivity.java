package com.deck.yugioh.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;

import com.deck.yugioh.R;
import com.deck.yugioh.Utils.Helpers.Helpers;

public class GuestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        Helpers.removeFocusClickOutside(this, event);

        return super.dispatchTouchEvent(event);

    }

}
