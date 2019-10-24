package com.deck.yugioh.Model.Notes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deck.yugioh.R;

public class NotesViewHolder extends RecyclerView.ViewHolder {

    private final TextView title;
    private final TextView date;
    private final TextView message;

    public NotesViewHolder(@NonNull View view) {
        super(view);

        this.title = view.findViewById(R.id.adapter_notes_title);
        this.date = view.findViewById(R.id.adapter_notes_date);
        this.message = view.findViewById(R.id.adapter_notes_message);

    }

    public TextView getTitle() {
        return title;
    }

    public TextView getMessage() {
        return message;
    }

    public TextView getDate() {
        return date;
    }

}
