package com.deck.yugioh.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.deck.yugioh.R;

public class NotesShowFragment extends Fragment {

    private EditText txtTitle;
    private EditText txtDate;
    private EditText txtMessage;

    public NotesShowFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes_show, container, false);

        this.txtTitle = view.findViewById(R.id.fragment_show_notes_title);
        this.txtDate = view.findViewById(R.id.fragment_show_notes_message);
        this.txtMessage = view.findViewById(R.id.fragment_show_notes_date);

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();

        if(bundle != null) {

            this.txtTitle.setText(bundle.getString(getString(R.string.fragment_show_notes_bundle_title)));
            this.txtDate.setText(bundle.getString(getString(R.string.fragment_show_notes_bundle_date)));
            this.txtMessage.setText(bundle.getString(getString(R.string.fragment_show_notes_bundle_message)));

        }

    }

}
