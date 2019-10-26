package com.deck.yugioh.Utils.Navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.deck.yugioh.R;

public class Navigation {

    public static void push(FragmentActivity activity, Fragment fragment, Bundle bundle, int id) {


        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public static void push(FragmentManager fragmentManager, Fragment fragment, Bundle bundle, int id) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }

    public static void back() {

    }

    public static void setPage() {

    }

}
