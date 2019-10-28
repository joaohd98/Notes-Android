package com.deck.yugioh.Fragment;


import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deck.yugioh.Adapters.NotesAdapter;
import com.deck.yugioh.Components.Swipe.SwipeController;
import com.deck.yugioh.Components.Swipe.SwipeControllerActions;
import com.deck.yugioh.HttpRequest.NotesAPI;
import com.deck.yugioh.HttpRequest.Utils.RequestCallBack;
import com.deck.yugioh.HttpRequest.Utils.RequestWithResponseCallback;
import com.deck.yugioh.Model.Notes.NotesView;
import com.deck.yugioh.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

enum Status {

    LOADING,
    ERROR,
    EMPTY,
    SUCCESS

}

public class ListNotesFragment extends Fragment {

    private Status status = Status.LOADING;
    private FirebaseUser user;
    private ArrayList<NotesView> notes;
    private RecyclerView list;

    public ListNotesFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);

        this.user = FirebaseAuth.getInstance().getCurrentUser();
//        this.list = view.findViewById(R.id.fragment_list_notes_recycle_view);

//        this.callNotes();

        return view;

    }

    private void callNotes() {

        NotesAPI api = new NotesAPI();

        api.callRequest(user.getUid(), new RequestWithResponseCallback<ArrayList<NotesView>>() {
            @Override
            public void success(ArrayList<NotesView> response) {
                notes = response;
                setupRecyclerView();
            }

            @Override
            public void error() {
                Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void setupRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView.Adapter adapter = new NotesAdapter(this.notes, getContext());

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
    }

}
