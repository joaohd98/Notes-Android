package com.deck.notes.Model.Notes;

public class NotesView {

    private String title;
    private String message;
    private String date;
    private String createdBy;

    public NotesView() { }

    public NotesView(String title, String message, String date, String createdBy) {
        this.title = title;
        this.message = message;
        this.date = date;
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
