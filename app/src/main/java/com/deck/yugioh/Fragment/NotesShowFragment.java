package com.deck.yugioh.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.deck.yugioh.Fragment.Utils.MasterFragment;
import com.deck.yugioh.R;
import com.deck.yugioh.Utils.ActionBar.NavigationBar;

public class NotesShowFragment extends MasterFragment {

    public NotesShowFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes_show, container, false);

        TextView txtTitle = view.findViewById(R.id.fragment_show_notes_title);
        TextView txtDate = view.findViewById(R.id.fragment_show_notes_date);
        TextView txtMessage = view.findViewById(R.id.fragment_show_notes_message);

        Bundle bundle = this.getArguments();

        if(bundle != null) {

            txtTitle.setText(bundle.getString(getString(R.string.fragment_show_notes_bundle_title)));
            txtDate.setText(bundle.getString(getString(R.string.fragment_show_notes_bundle_date)));
            txtMessage.setText(bundle.getString(getString(R.string.fragment_show_notes_bundle_message)));

        }

        return view;

    }

    @Override
    public void setNavBar() {

        NavigationBar.setActionBar(getActivity(), "Nota", true);

    }
}
