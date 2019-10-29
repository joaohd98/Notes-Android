package com.deck.notes.Activities;

import android.os.Bundle;

import com.deck.notes.Activities.Utils.MasterActivity;
import com.deck.notes.Fragment.NotesFormFragment;
import com.deck.notes.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import android.view.MenuItem;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;

import com.deck.notes.Utils.Navigation.Navigation;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        this.setupMenuBar();

    }

    private void setupMenuBar() {

        Toolbar toolbar = findViewById(R.id.activity_sign_in_toolbar);
        DrawerLayout drawer = findViewById(R.id.activity_sign_in);
        NavigationView navigationView = findViewById(R.id.nav_view);

        this.toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );

        drawer.addDrawerListener(this.toggle);
        this.toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        View headerView =  navigationView.getHeaderView(0);

        TextView headerName = headerView.findViewById(R.id.nav_header_name);
        TextView headerEmail = headerView.findViewById(R.id.nav_header_email);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null) {

            headerName.setText(user.getDisplayName());
            headerEmail.setText(user.getEmail());

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);

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
