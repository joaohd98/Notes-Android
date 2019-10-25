package com.deck.yugioh.Activities;

import android.os.Bundle;

import com.deck.yugioh.Activities.Utils.MasterActivity;
import com.deck.yugioh.R;

public class UserActivity extends MasterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }
}
