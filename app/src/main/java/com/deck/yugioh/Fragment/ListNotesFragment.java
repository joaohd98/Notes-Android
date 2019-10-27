package com.deck.yugioh.Fragment;


import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deck.yugioh.Adapters.NotesAdapter;
import com.deck.yugioh.Components.Swipe.SwipeController;
import com.deck.yugioh.Components.Swipe.SwipeControllerActions;
import com.deck.yugioh.Model.Notes.NotesView;
import com.deck.yugioh.R;

import java.util.ArrayList;

public class ListNotesFragment extends Fragment {

    private ArrayList<NotesView> notes;
    private RecyclerView list;

    public ListNotesFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);

        this.list = view.findViewById(R.id.fragment_list_notes_recycle_view);

        this.notes = new ArrayList<>();

        this.notes.add(new NotesView("1", "Titulo", "Mensagem", "11/12/2018"));
        this.notes.add(new NotesView("2", "Titulo2", "Mensagem1", "04/01/2018"));
        this.notes.add(new NotesView("3", "Titulo3", "Mensagem2", "01/11/2014"));

        this.setupRecyclerView();

        return view;


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
        final SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {

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
