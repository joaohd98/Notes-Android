package com.deck.notes.Activities.Utils;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.deck.notes.Fragment.Utils.MasterFragment;
import com.deck.notes.Utils.Helpers.Helpers;
import com.deck.notes.Utils.Navigation.Navigation;

public abstract class MasterActivity extends AppCompatActivity {

    public ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        Helpers.removeFocusClickOutside(this, event);

        return super.dispatchTouchEvent(event);

    }

    public void setNavBar(final int id) {

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {

            @Override
            public void onBackStackChanged() {

                MasterFragment fragment = (MasterFragment) getSupportFragmentManager().findFragmentById(id);

                if(fragment != null) {
                    fragment.setNavBar();
                }

            }

        });
    }

    public void setActionBar(String title, boolean showBackButton) {

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {

            actionBar.setTitle(title);

            if(showBackButton) {

                if(toggle != null) {

                    this.toggle.setDrawerIndicatorEnabled(false);

                    this.toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onSupportNavigateUp();
                        }
                    });

                }

                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);

            }

            else {

                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowHomeEnabled(false);

                if(toggle != null) {
                    
                    this.toggle.setDrawerIndicatorEnabled(true);

                    this.toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) { }
                    });

                }

            }

        }

    }

    @Override
    public boolean onSupportNavigateUp() {

        Navigation.back(this);
        return true;

    }

}
