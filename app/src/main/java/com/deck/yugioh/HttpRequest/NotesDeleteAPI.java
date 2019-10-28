package com.deck.yugioh.HttpRequest;


import android.accounts.NetworkErrorException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.deck.yugioh.HttpRequest.Utils.Request;
import com.deck.yugioh.HttpRequest.Utils.RequestCallBack;
import com.deck.yugioh.Model.Notes.NotesView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotesDeleteAPI implements Request<ArrayList<NotesView>> {

    private DatabaseReference reference;
    private boolean internetConnection;
    private ValueEventListener listener;

    public NotesDeleteAPI(String uuid) {

        this.reference = FirebaseDatabase.getInstance().getReference().child(uuid).child("notes");

    }

    @Override
    public void callRequest(final ArrayList<NotesView> object, final RequestCallBack callback) {

        this.internetConnection = false;

        if(listener == null)  {

            listener = new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    internetConnection = true;

                    reference.setValue(object, new DatabaseReference.CompletionListener() {

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
