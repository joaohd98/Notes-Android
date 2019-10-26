package com.deck.yugioh.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deck.yugioh.Adapters.NotesAdapter;
import com.deck.yugioh.Model.Notes.NotesView;
import com.deck.yugioh.R;

import java.util.ArrayList;

public class ListNotesFragment extends Fragment {

    private ArrayList<NotesView> notes;
    private RecyclerView list;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public ListNotesFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);

        this.list = view.findViewById(R.id.fragment_list_notes_recycle_view);

        layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);

        this.notes = new ArrayList<>();

        this.notes.add(new NotesView("1", "Titulo", "Mensagem", "11/12/2018"));
        this.notes.add(new NotesView("2", "Titulo2", "Mensagem1", "04/01/2018"));
        this.notes.add(new NotesView("3", "Titulo3", "Mensagem2", "01/11/2014"));

        adapter = new NotesAdapter(this.notes, getContext());
        list.setAdapter(adapter);

        return view;


    }

}
