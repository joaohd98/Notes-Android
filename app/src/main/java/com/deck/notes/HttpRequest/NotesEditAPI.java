package com.deck.notes.HttpRequest;


import android.accounts.NetworkErrorException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.deck.notes.HttpRequest.Utils.Request;
import com.deck.notes.HttpRequest.Utils.RequestCallBack;
import com.deck.notes.Model.Notes.NotesEditView;
import com.deck.notes.Model.Notes.NotesView;
import com.deck.notes.Utils.Helpers.Helpers;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotesEditAPI implements Request<NotesEditView> {

    private DatabaseReference reference;
    private boolean internetConnection;
    private ValueEventListener listener;

    public NotesEditAPI(String uuid) {

        this.reference = Helpers.getDatabaseReference(uuid, "notes");

    }

    @Override
    public void callRequest(final NotesEditView object, final RequestCallBack callback) {

        this.internetConnection = false;

        if(listener == null)  {

            listener = new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    internetConnection = true;

                    ArrayList<NotesView> list = new ArrayList<>();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                        list.add(postSnapshot.getValue(NotesView.class));

                    list.set(object.getPosition(), object.getNote());

                    reference.setValue(list, new DatabaseReference.CompletionListener() {

                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                            if (databaseError == null)
                                callback.success();

                            else
                                callback.error(databaseError.toException());


                        }

                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    internetConnection = true;

                    callback.error(databaseError.toException());

                }

            };

        }

        new android.os.Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if(!internetConnection) {

                    reference.removeEventListener(listener);
                    callback.error(new NetworkErrorException());

                }

            }

        }, 20000);

        this.reference.addListenerForSingleValueEvent(listener);


    }

}
