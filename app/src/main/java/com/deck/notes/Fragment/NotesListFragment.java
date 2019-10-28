package com.deck.notes.Fragment;


import android.accounts.NetworkErrorException;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deck.notes.Activities.SignInActivity;
import com.deck.notes.Adapters.NotesAdapter;
import com.deck.notes.Components.DialogView;
import com.deck.notes.Components.Swipe.SwipeController;
import com.deck.notes.Components.Swipe.SwipeControllerActions;
import com.deck.notes.Fragment.Utils.MasterFragment;
import com.deck.notes.HttpRequest.NotesAPI;
import com.deck.notes.HttpRequest.NotesDeleteAPI;
import com.deck.notes.HttpRequest.Utils.RequestCallBack;
import com.deck.notes.HttpRequest.Utils.RequestWithResponseCallback;
import com.deck.notes.Model.Notes.NotesView;
import com.deck.notes.R;
import com.deck.notes.Utils.ActionBar.NavigationBar;
import com.deck.notes.Utils.Navigation.Navigation;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

enum Status {

    LOADING,
    ERROR,
    EMPTY,
    SUCCESS

}

public class NotesListFragment extends MasterFragment {

    private FirebaseUser user;
    private ArrayList<NotesView> notes;
    private RecyclerView list;

    private ConstraintLayout notesLayout;
    private ConstraintLayout loadingLayout;
    private ConstraintLayout errorLayout;
    private TextView errorLayoutMessage;

    public NotesListFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);

        this.user = FirebaseAuth.getInstance().getCurrentUser();

        this.notesLayout = view.findViewById(R.id.fragment_list_notes);
        this.errorLayout = view.findViewById(R.id.fragment_list_notes_error);
        this.loadingLayout = view.findViewById(R.id.fragment_list_notes_loading);

        this.errorLayout = view.findViewById(R.id.fragment_list_notes_error);
        this.errorLayoutMessage = view.findViewById(R.id.fragment_list_notes_error_message);

        this.list = view.findViewById(R.id.fragment_list_notes_recycle_view);

        Button errorLayoutButton = view.findViewById(R.id.fragment_list_notes_error_button);

        errorLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callNotes();
            }
        });

        this.callNotes();

        return view;

    }

    private void setFragmentStatus(Status status) {

        if(status == Status.LOADING) {

            this.notesLayout.setVisibility(View.INVISIBLE);
            this.errorLayout.setVisibility(View.INVISIBLE);
            this.loadingLayout.setVisibility(View.VISIBLE);

        }

        else if(status == Status.EMPTY) {


        }

        else if(status == Status.ERROR) {

            this.notesLayout.setVisibility(View.INVISIBLE);
            this.loadingLayout.setVisibility(View.INVISIBLE);
            this.errorLayout.setVisibility(View.VISIBLE);

        }

        else if(status == Status.SUCCESS) {

            this.loadingLayout.setVisibility(View.INVISIBLE);
            this.errorLayout.setVisibility(View.INVISIBLE);
            this.notesLayout.setVisibility(View.VISIBLE);

        }

    }

    private void callNotes() {

        this.setFragmentStatus(Status.LOADING);

        NotesAPI api = new NotesAPI(user.getUid());

        api.callRequest(null, new RequestWithResponseCallback<ArrayList<NotesView>>() {
            @Override
            public void success(ArrayList<NotesView> response) {
                notes = response;
                setupRecyclerView();
            }

            @Override
            public void error(Exception ex) {

                setFragmentStatus(Status.ERROR);

                try {

                    throw ex;

                } catch (NetworkErrorException ignore) {

                    errorLayoutMessage.setText(R.string.fragment_list_notes_error_message_no_internet);

                } catch (Exception ignore) {

                    errorLayoutMessage.setText(R.string.fragment_list_notes_error_message_generic);

                }

            }

        });

    }

    private void setupRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        RecyclerView.Adapter adapter = new NotesAdapter(this.notes, getContext(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int itemPosition = list.getChildLayoutPosition(view);
                NotesView notesView = notes.get(itemPosition);

                Bundle bundle = new Bundle();

                bundle.putString(getString(R.string.fragment_show_notes_bundle_title), notesView.getTitle());
                bundle.putString(getString(R.string.fragment_show_notes_bundle_date), notesView.getDate());
                bundle.putString(getString(R.string.fragment_show_notes_bundle_message), notesView.getMessage());

                FragmentActivity activity = getActivity();

                if(activity != null)
                    Navigation.push(activity, new NotesShowFragment(), bundle, R.id.activity_sign_in_fragment);

            }

        });

        /*
         * SET LAYOUT MANAGER
         */
        this.list.setLayoutManager(layoutManager);

        /*
         * SET ADAPTER
         */
        this.list.setAdapter(adapter);


        /*
         * SET SWIPE CONTROLLER
         */
        final SwipeController swipeController = new SwipeController(getContext(), new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {

                setFragmentStatus(Status.LOADING);

                NotesDeleteAPI deleteAPI = new NotesDeleteAPI(user.getUid());

                notes.remove(position);

                deleteAPI.callRequest(notes, new RequestCallBack() {

                    @Override
                    public void success() {

                        setFragmentStatus(Status.SUCCESS);

                        Toast.makeText(getContext(), R.string.fragment_list_notes_success_delete, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void error(Exception exception) {

                        setFragmentStatus(Status.SUCCESS);

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

            @Override
            public void onLeftClicked(int position) {

                NotesView note = notes.get(position);

                Bundle bundle = new Bundle();

                bundle.putBoolean(getString(R.string.fragment_form_notes_bundle_is_updating), true);
                bundle.putInt(getString(R.string.fragment_form_notes_bundle_position), position);
                bundle.putString(getString(R.string.fragment_form_notes_bundle_title), note.getTitle());
                bundle.putString(getString(R.string.fragment_form_notes_bundle_text), note.getMessage());

                FragmentActivity activity = getActivity();

                if(activity != null)
                    Navigation.push(activity, new NotesFormFragment(), bundle, R.id.activity_sign_in_fragment);

            }

        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(this.list);

        list.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

        this.setFragmentStatus(Status.SUCCESS);

    }

    private void setupMenuButton() {

        SignInActivity activity = (SignInActivity) getActivity();

        if(activity != null) {

            Toolbar toolbar = activity.findViewById(R.id.activity_sign_in_toolbar);
            DrawerLayout drawer = activity.findViewById(R.id.activity_sign_in);
            NavigationView navigationView = activity.findViewById(R.id.nav_view);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    activity, drawer, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close
            );

            drawer.addDrawerListener(toggle);
            toggle.syncState();
            navigationView.setNavigationItemSelectedListener(activity);

        }

    }

    @Override
    public void setNavBar() {

        SignInActivity activity = (SignInActivity) getActivity();

        if(activity != null) {

            this.setupMenuButton();
            NavigationBar.setActionBar(activity, "Notas", false);

        }

    }

}
