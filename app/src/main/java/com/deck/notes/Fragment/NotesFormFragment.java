package com.deck.notes.Fragment;


import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.deck.notes.Components.DialogView;
import com.deck.notes.Components.InputView;
import com.deck.notes.Components.LoadingView;
import com.deck.notes.Fragment.Utils.MasterFragment;
import com.deck.notes.HttpRequest.NotesAddAPI;
import com.deck.notes.HttpRequest.NotesEditAPI;
import com.deck.notes.HttpRequest.Utils.RequestCallBack;
import com.deck.notes.Model.Notes.NotesEditView;
import com.deck.notes.Model.Notes.NotesView;
import com.deck.notes.R;
import com.deck.notes.Utils.ActionBar.NavigationBar;
import com.deck.notes.Utils.Helpers.Helpers;
import com.deck.notes.Utils.Navigation.Navigation;
import com.deck.notes.Utils.Validators.ValidatorModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class NotesFormFragment extends MasterFragment {

    private TextView txtTitle;
    private LoadingView loadingView;

    private InputView inputTitle;
    private InputView inputMessage;
    private Button btnSubmit;

    private int position;
    private boolean isUpdate;

    public NotesFormFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes_form, container, false);

        this.inputTitle = view.findViewById(R.id.fragment_form_notes_input_title);
        this.inputMessage = view.findViewById(R.id.fragment_form_notes_input_text);

        this.txtTitle = view.findViewById(R.id.fragment_form_notes_title);

        this.btnSubmit = view.findViewById(R.id.fragment_form_notes_button);

        this.loadingView = view.findViewById(R.id.fragment_form_notes_loading);

        return view;

    }

    @Override
    public void setNavBar() {

        NavigationBar.setActionBar(getActivity(), "Nota", true);

    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle bundle = this.getArguments();

        if(bundle != null && bundle.getBoolean(getString(R.string.fragment_form_notes_bundle_is_updating))) {

            this.txtTitle.setText(R.string.fragment_form_notes_title_edit);
            this.btnSubmit.setText(R.string.fragment_form_notes_button_edit);

            this.position = bundle.getInt(getString(R.string.fragment_form_notes_bundle_position));
            this.inputTitle.setInputValue(bundle.getString(getString(R.string.fragment_form_notes_bundle_title)));
            this.inputMessage.setInputValue(bundle.getString(getString(R.string.fragment_form_notes_bundle_text)));

            this.isUpdate = true;

        }


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

        this.btnSubmit.setOnClickListener(isUpdate ? editNote() : addNote());

        this.isFormValid();

    }

    private View.OnClickListener addNote() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingView.show();

                final String uuid = FirebaseAuth.getInstance().getUid();

                NotesView notesView = new NotesView(
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

                        Toast.makeText(getContext(), R.string.fragment_form_notes_alert_message_add_success, Toast.LENGTH_SHORT).show();

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
        };
    }

    private View.OnClickListener editNote() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingView.show();

                final String uuid = FirebaseAuth.getInstance().getUid();

                NotesView notesView = new NotesView(
                        inputTitle.getInputValue(),
                        inputMessage.getInputValue(),
                        Helpers.getCurrentDate(),
                        uuid
                );

                NotesEditView editView = new NotesEditView(position, notesView);

                NotesEditAPI api = new NotesEditAPI(uuid);

                api.callRequest(editView, new RequestCallBack() {

                    @Override
                    public void success() {

                        loadingView.hide();

                        Toast.makeText(getContext(), R.string.fragment_form_notes_alert_message_edit_success, Toast.LENGTH_SHORT).show();

                        FragmentActivity activity = getActivity();

                        if (activity != null)
                            Navigation.back(activity);

                    }

                    @Override
                    public void error(Exception exception) {

                        loadingView.hide();

                        Context context = getContext();

                        if (context != null) {

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
        };

    }

    private void isFormValid() {

        Helpers.checkIsValid(this.btnSubmit, this.inputTitle.isValid() && this.inputMessage.isValid());

    }
}
