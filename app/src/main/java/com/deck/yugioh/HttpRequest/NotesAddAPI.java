package com.deck.yugioh.HttpRequest;


import android.accounts.NetworkErrorException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.deck.yugioh.HttpRequest.Utils.Request;
import com.deck.yugioh.HttpRequest.Utils.RequestCallBack;
import com.deck.yugioh.Model.Notes.NotesView;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotesAddAPI implements Request<NotesView> {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("notes");
    private boolean internetConnection;

    @Override
    public void callRequest(final NotesView object, final RequestCallBack callback) {

        this.internetConnection = false;

        new android.os.Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if(!internetConnection)
                    callback.error(new NetworkErrorException());

            }

        }, 20000);

        this.reference.setValue(object,  new DatabaseReference.CompletionListener() {

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                internetConnection = true;

                if(databaseError == null)
                    callback.success();

                else
                    callback.error(databaseError.toException());

            }

        });

    }

}
