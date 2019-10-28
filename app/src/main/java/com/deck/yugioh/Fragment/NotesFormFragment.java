package com.deck.yugioh.Fragment;


import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.deck.yugioh.Components.DialogView;
import com.deck.yugioh.Components.InputView;
import com.deck.yugioh.Components.LoadingView;
import com.deck.yugioh.Fragment.Utils.MasterFragment;
import com.deck.yugioh.HttpRequest.NotesAPI;
import com.deck.yugioh.HttpRequest.NotesAddAPI;
import com.deck.yugioh.HttpRequest.Utils.RequestCallBack;
import com.deck.yugioh.HttpRequest.Utils.RequestWithResponseCallback;
import com.deck.yugioh.Model.Notes.NotesView;
import com.deck.yugioh.R;
import com.deck.yugioh.Utils.ActionBar.NavigationBar;
import com.deck.yugioh.Utils.Helpers.Helpers;
import com.deck.yugioh.Utils.Navigation.Navigation;
import com.deck.yugioh.Utils.Validators.ValidatorModel;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.ArrayList;

public class NotesFormFragment extends MasterFragment {

    private LoadingView loadingView;

    private InputView inputTitle;
    private InputView inputMessage;
    private Button btnSubmit;


    public NotesFormFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes_form, container, false);

        this.inputTitle = view.findViewById(R.id.fragment_form_notes_input_title);
        this.inputMessage = view.findViewById(R.id.fragment_form_notes_input_text);

        this.btnSubmit = view.findViewById(R.id.fragment_form_notes_button);

        this.loadingView = view.findViewById(R.id.fragment_form_notes_loading);

        Bundle bundle = this.getArguments();

        if(bundle != null && bundle.getBoolean("isUpdating")) {

            TextView txtTitle = view.findViewById(R.id.fragment_form_notes_title);

            txtTitle.setText(R.string.fragment_form_notes_title_edit);
            this.btnSubmit.setText(R.string.fragment_form_notes_button_edit);

        }

        return view;

    }

    @Override
    public void setNavBar() {

        NavigationBar.setActionBar(getActivity(), "Nota", true);

    }

    @Override
    public void onStart() {
        super.onStart();

        this.setTitleField();
        this.setTextField();
        this.setBtnSubmit();

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

    private void setBtnSubmit() {

        this.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingView.show();

                final String uuid = FirebaseAuth.getInstance().getUid();

                NotesView notesView = new NotesView(
                    "11",
                    inputTitle.getInputValue(),
                    inputMessage.getInputValue(),
                    Helpers.getCurrentDate(),
                    uuid
                );

                NotesAddAPI api = new NotesAddAPI(uuid);

                api.callRequest(notesView, new RequestCallBack() {

                    @Override
                    public void success() {

                        loadingView.hide();

                        Toast.makeText(getContext(), R.string.fragment_form_notes_alert_message_success, Toast.LENGTH_SHORT).show();

                        FragmentActivity activity = getActivity();

                        if(activity != null)
                            Navigation.back(activity);

                    }

                    @Override
                    public void error(Exception exception) {

                        loadingView.hide();

                        Context context = getContext();

                        if(context != null) {

                            DialogView dialog = new DialogView(getContext());

                            dialog.setTitle(context.getString(R.string.fragment_form_notes_alert_title));

                            try {

                                throw exception;

                            } catch (NetworkErrorException ignore) {

                                dialog.setInfo(context.getString(R.string.fragment_form_notes_alert_message_no_internet));


                            } catch (Exception ignore) {

                                dialog.setInfo(context.getString(R.string.fragment_form_notes_alert_message_generic));

                            }

                            dialog.setBtnSuccess(context.getString(R.string.fragment_form_notes_alert_button));

                            dialog.show();

                        }

                    }

                });
            }
        });

        this.isFormValid();

    }

    private void isFormValid() {

        Helpers.checkIsValid(this.btnSubmit, this.inputTitle.isValid() && this.inputMessage.isValid());

    }
}
