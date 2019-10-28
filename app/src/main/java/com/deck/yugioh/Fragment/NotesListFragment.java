package com.deck.yugioh.Fragment;


import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deck.yugioh.Adapters.NotesAdapter;
import com.deck.yugioh.Components.Swipe.SwipeController;
import com.deck.yugioh.Components.Swipe.SwipeControllerActions;
import com.deck.yugioh.HttpRequest.NotesAPI;
import com.deck.yugioh.HttpRequest.Utils.RequestWithResponseCallback;
import com.deck.yugioh.Model.Notes.NotesView;
import com.deck.yugioh.R;
import com.deck.yugioh.Utils.Navigation.Navigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

enum Status {

    LOADING,
    ERROR,
    EMPTY,
    SUCCESS

}

public class NotesListFragment extends Fragment {

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

        NotesAPI api = new NotesAPI();

        api.callRequest(user.getUid(), new RequestWithResponseCallback<ArrayList<NotesView>>() {
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

            }

            @Override
            public void onLeftClicked(int position) {

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

}
