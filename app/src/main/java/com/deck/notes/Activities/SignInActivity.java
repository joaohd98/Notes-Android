package com.deck.notes.Activities;

import android.os.Bundle;

import com.deck.notes.Activities.Utils.MasterActivity;
import com.deck.notes.Fragment.NotesFormFragment;
import com.deck.notes.R;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;

import android.view.MenuItem;

import com.deck.notes.Utils.Navigation.Navigation;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;


public class SignInActivity extends MasterActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Toolbar toolbar = findViewById(R.id.activity_sign_in_toolbar);
        setSupportActionBar(toolbar);
        setNavBar(R.id.activity_sign_in_fragment);

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.activity_sign_in);

        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);

        else
            super.onBackPressed();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_add) {
            Navigation.push(this, new NotesFormFragment(), new Bundle(), R.id.activity_sign_in_fragment);
        }

        else if (id == R.id.nav_out) {

            FirebaseAuth.getInstance().signOut();
            Navigation.setActivity(this, GuestActivity.class);

        }

        DrawerLayout drawer = findViewById(R.id.activity_sign_in);
        drawer.closeDrawer(GravityCompat.START);

        return true;

    }

}
