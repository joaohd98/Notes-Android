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
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;

public class NotesAdapter extends UltimateViewAdapter<NotesViewHolder> {

    private Context context;
    private ArrayList<NotesView> notes;

    public NotesAdapter(ArrayList<NotesView> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent) {

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
    public NotesViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public NotesViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public int getAdapterItemCount() {
        return notes.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }


    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }


}
