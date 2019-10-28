package com.deck.yugioh.HttpRequest;

import android.accounts.NetworkErrorException;

import androidx.annotation.NonNull;

import com.deck.yugioh.HttpRequest.Utils.RequestWithResponse;
import com.deck.yugioh.HttpRequest.Utils.RequestWithResponseCallback;
import com.deck.yugioh.Model.Notes.NotesView;
import com.deck.yugioh.Utils.Helpers.Helpers;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotesAPI implements RequestWithResponse<Object, ArrayList<NotesView>> {

    private Query reference;
    private boolean internetConnection;
    private ValueEventListener listener;

    public NotesAPI(String uuid) {

        this.reference = Helpers.getDatabaseReference(uuid, "notes");

    }

    @Override
    public void callRequest(Object object, final RequestWithResponseCallback<ArrayList<NotesView>> callback) {

        this.internetConnection = false;

        if(this.listener == null) {

            this.listener = new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    internetConnection = true;

                    ArrayList<NotesView> list = new ArrayList<>();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                        list.add(postSnapshot.getValue(NotesView.class));

                    callback.success(list);

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


        reference.removeEventListener(this.listener);
        reference.addValueEventListener(this.listener);

    }


}
