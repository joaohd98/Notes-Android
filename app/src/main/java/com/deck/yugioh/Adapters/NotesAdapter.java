package com.deck.yugioh.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deck.yugioh.Model.Notes.NotesView;
import com.deck.yugioh.Model.Notes.NotesViewHolder;
import com.deck.yugioh.R;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {

    private Context context;
    private ArrayList<NotesView> notes;

    public NotesAdapter(ArrayList<NotesView> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(this.context).inflate(R.layout.adapter_notes, parent, false);

        return new NotesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

        NotesView notes = this.notes.get(position);

        holder.getTitle().setText(notes.getTitle());
        holder.getMessage().setText(notes.getMessage());
        holder.getDate().setText(notes.getDate());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

}
