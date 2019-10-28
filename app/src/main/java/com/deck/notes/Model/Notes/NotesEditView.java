package com.deck.notes.Model.Notes;

public class NotesEditView {

    private int position;
    private NotesView note;

    public NotesEditView() { }

    public NotesEditView(int position, NotesView note) {
        this.position = position;
        this.note = note;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public NotesView getNote() {
        return note;
    }

    public void setNote(NotesView note) {
        this.note = note;
    }
}
