package com.deck.notes.Utils.ActionBar;

import android.app.Activity;

import com.deck.notes.Activities.Utils.MasterActivity;

public class NavigationBar {

    public static void setActionBar(Activity activity, String title, boolean showBackButton) {

        MasterActivity masterActivity = (MasterActivity) activity;

        if(masterActivity != null)
            masterActivity.setActionBar(title, showBackButton);

    }

}
