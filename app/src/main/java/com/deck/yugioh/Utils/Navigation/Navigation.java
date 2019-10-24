package com.deck.yugioh.Utils.Navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class Navigation {

    public static void push(FragmentActivity activity, Fragment fragment, Bundle bundle, int id) {

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

        transaction.replace(id, fragment);
        transaction.addToBackStack(null);

        transaction.commit();

    }

    public static void back() {

    }

    public static void setPage() {

    }

}
