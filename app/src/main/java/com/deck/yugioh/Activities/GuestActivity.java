package com.deck.yugioh.Activities;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.deck.yugioh.Activities.Utils.MasterActivity;
import com.deck.yugioh.R;

public class GuestActivity extends MasterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        Toolbar toolbar = findViewById(R.id.activity_guest_toolbar);
        setSupportActionBar(toolbar);

    }

}
