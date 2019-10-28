package com.deck.yugioh.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.deck.yugioh.Components.InputView;
import com.deck.yugioh.R;
import com.deck.yugioh.Utils.Helpers.Helpers;
import com.deck.yugioh.Utils.Validators.ValidatorModel;

import java.util.ArrayList;

public class NotesFormFragment extends Fragment {

    private TextView txtTitle;
    private InputView inputTitle;
    private InputView inputMessage;
    private Button btnSubmit;


    public NotesFormFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes_form, container, false);

        this.txtTitle = view.findViewById(R.id.fragment_form_notes_title);

        this.inputTitle = view.findViewById(R.id.fragment_form_notes_input_title);
        this.inputMessage = view.findViewById(R.id.fragment_form_notes_input_text);

        this.btnSubmit = view.findViewById(R.id.fragment_form_notes_button);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        this.setTitleField();
        this.setTextField();

    }

    private void setTitleField() {

        int type = InputType.TYPE_TEXT_VARIATION_PERSON_NAME;
        String label = getString(R.string.fragment_form_notes_input_title_label);
        String placeholder = getString(R.string.fragment_form_notes_input_title_placeholder);

        ArrayList<ValidatorModel> rules = new ArrayList<>();

        rules.add(new ValidatorModel(R.string.validators_required, getString(R.string.fragment_form_notes_input_title_validation_required)));

        this.inputTitle.setFormValidCallback(new InputView.ViewCallBack() {

            @Override
            public void onInput() {
                isFormValid();
            }

        });

        this.inputTitle.setContent(type, placeholder, label, rules);

    }

    private void setTextField() {

        int type = InputType.TYPE_TEXT_FLAG_MULTI_LINE;
        String label = getString(R.string.fragment_form_notes_input_message_label);
        String placeholder = getString(R.string.fragment_form_notes_input_message_placeholder);

        ArrayList<ValidatorModel> rules = new ArrayList<>();

        rules.add(new ValidatorModel(R.string.validators_required, getString(R.string.fragment_form_notes_input_message_validation_required)));

        this.inputMessage.setFormValidCallback(new InputView.ViewCallBack() {

            @Override
            public void onInput() {
                isFormValid();
            }

        });

        this.inputMessage.setContent(type, placeholder, label, rules);

    }

    private void isFormValid() {

        Helpers.checkIsValid(this.btnSubmit, this.inputTitle.isValid() && this.inputMessage.isValid());

    }
}
