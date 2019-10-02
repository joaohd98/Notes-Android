package com.deck.yugioh.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.deck.yugioh.R;

public class InputFragment extends Fragment {

    private TextView label;
    private EditText input;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_input, container, false);

        this.input = view.findViewById(R.id.customInput);
        this.label = view.findViewById(R.id.customLabel);

        this.label.setText(getArguments().getString("label"));

        return view;

    }

    public InputFragment() {
        // Required empty public constructor
    }


}
