package com.deck.notes.Fragment.Utils;

import androidx.fragment.app.Fragment;

public abstract class MasterFragment extends Fragment {

    abstract public void setNavBar();

    @Override
    public void onStart() {

        super.onStart();
        this.setNavBar();

    }

}
